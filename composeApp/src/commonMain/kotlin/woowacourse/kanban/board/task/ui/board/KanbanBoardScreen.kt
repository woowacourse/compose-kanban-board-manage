package woowacourse.kanban.board.task.ui.board

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import woowacourse.kanban.board.task.domain.KanbanBoard
import woowacourse.kanban.board.task.domain.KanbanCard
import woowacourse.kanban.board.task.domain.KanbanStatus
import woowacourse.kanban.board.task.domain.TaskMockData
import woowacourse.kanban.board.task.ui.modal.ModalForm
import woowacourse.kanban.board.theme.Gray50

enum class TaskModalMode {
    CREATE,
    EDIT,
}

@Composable
fun KanbanBoardScreen(
    kanbanBoard: KanbanBoard,
    onAddCard: (
        title: String,
        content: String,
        assigneeName: String?,
        tags: List<String>,
        status: KanbanStatus,
    ) -> Unit,
    onEditCard: (
        id: String,
        title: String,
        content: String,
        assigneeName: String?,
        tags: List<String>,
        status: KanbanStatus,
    ) -> Unit,
    onDeleteCard: (String) -> Unit,
    modifier: Modifier = Modifier,
    getIsDropTarget: (KanbanStatus) -> Boolean = { false },
    onBoundsChanged: (KanbanStatus, Rect) -> Unit = { _, _ -> },
    onTaskDragStart: (KanbanCard) -> Unit = {},
    onTaskDragChange: (Offset) -> Unit = {},
    onTaskDragEnd: () -> Unit = {},
    onTaskDragCancel: () -> Unit = {},
    snackbarHostState: SnackbarHostState,
    scope: CoroutineScope,
) {
    var editingCard by remember { mutableStateOf<KanbanCard?>(null) }
    var taskModalMode by remember { mutableStateOf<TaskModalMode?>(null) }
    var isShowAddTaskModal by remember { mutableStateOf(false) }
    var isShowEditTaskModal by remember { mutableStateOf(false) }

    when (taskModalMode) {
        TaskModalMode.CREATE -> if (isShowAddTaskModal) {
            ModalForm(
                modalMode = taskModalMode!!,
                assignee = TaskMockData.assignees,
                onDismissRequest = { isShowAddTaskModal = false },
                onCreate = { title, content, assigneeName, tags, status ->
                    onAddCard(
                        title,
                        content,
                        assigneeName,
                        tags,
                        status,
                    )
                    isShowAddTaskModal = false
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            message = "새로운 태스크가 추가되었습니다.",
                            duration = SnackbarDuration.Short,
                        )
                    }
                },
                modifier = Modifier.width(672.dp).height(820.dp),
            )
        }

        TaskModalMode.EDIT -> if (isShowEditTaskModal) {
            ModalForm(
                editingCard = editingCard,
                modalMode = taskModalMode!!,
                assignee = TaskMockData.assignees,
                onDismissRequest = { isShowEditTaskModal = false },
                onEdit = { title, content, assigneeName, tags, status ->
                    editingCard?.let { card ->
                        onEditCard(
                            card.id,
                            title,
                            content,
                            assigneeName,
                            tags,
                            status,
                        )
                    }
                    isShowEditTaskModal = false
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            message = "태스크가 수정되었습니다.",
                            duration = SnackbarDuration.Short,
                        )
                    }
                },
                onDelete = {
                    try {
                        isShowEditTaskModal = false
                        editingCard?.let { card ->
                            onDeleteCard(card.id)
                        }
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                message = "태스크가 삭제되었습니다.",
                                duration = SnackbarDuration.Short,
                            )
                        }
                    } catch (e: IllegalArgumentException) {
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                message = e.message ?: "해당 상태에서는 태스크 삭제가 불가합니다.",
                                duration = SnackbarDuration.Short,
                            )
                        }
                    }
                },
            )
        }

        null -> Unit
    }

    Scaffold(
        modifier = modifier,
        containerColor = Color.White,
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) { snackbarData ->
                SnackBarCard(
                    modifier = Modifier,
                    message = snackbarData.visuals.message,
                    onDismiss = { snackbarData.dismiss() },
                )
            }
        },
        topBar = {
            KanbanBoardHeader(
                modifier = Modifier.padding(
                    vertical = 16.dp,
                    horizontal = 24.dp,
                ),
                title = kanbanBoard.title,
                doneCount = kanbanBoard.doneCount,
                totalCount = kanbanBoard.totalCount,
                onCreateClick = {
                    taskModalMode = TaskModalMode.CREATE
                    isShowAddTaskModal = true
                },
            )
        },
    ) { paddingValues ->
        val scrollState = rememberScrollState()

        KanbanBoardContent(
            kanbanBoard = kanbanBoard,
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxWidth()
                .background(Gray50)
                .padding(24.dp)
                .horizontalScroll(scrollState),
            onCardClick = { card ->
                editingCard = card
                taskModalMode = TaskModalMode.EDIT
                isShowEditTaskModal = true
            },
            getIsDropTarget = getIsDropTarget,
            onBoundsChanged = onBoundsChanged,
            onTaskDragStart = onTaskDragStart,
            onTaskDragChange = onTaskDragChange,
            onTaskDragEnd = onTaskDragEnd,
            onTaskDragCancel = onTaskDragCancel,
        )
    }
}

@Preview(
    widthDp = 1500,
    heightDp = 900,
)
@Composable
private fun KanbanBoardScreenPreview() {
    val scope = rememberCoroutineScope()
    KanbanBoardScreen(
        onAddCard = { _, _, _, _, _ -> },
        onEditCard = { _, _, _, _, _, _ -> },
        onDeleteCard = {},
        kanbanBoard = KanbanBoard(
            boardId = 0,
            title = "compose",
            cards = listOf(),
        ),
        snackbarHostState = remember { SnackbarHostState() },
        scope = scope,
    )
}
