package woowacourse.kanban.board.ui.screen.board

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import woowacourse.kanban.board.domain.EditError
import woowacourse.kanban.board.domain.KanbanBoard
import woowacourse.kanban.board.domain.KanbanProject
import woowacourse.kanban.board.domain.KanbanTask
import woowacourse.kanban.board.domain.dialog.Status
import woowacourse.kanban.board.ui.component.board.CardGroup
import woowacourse.kanban.board.ui.component.board.KanbanBoardTopAppBar
import woowacourse.kanban.board.ui.component.board.sidebar.SideBar
import woowacourse.kanban.board.ui.component.dialog.CreateTaskDialog
import woowacourse.kanban.board.ui.component.dialog.EditTaskDialog

@Composable
fun KanbanBoardScreen(projects: List<KanbanProject> = listOf(KanbanProject("Compose1"))) {
    val kanbanBoardState = remember {
        KanbanBoardState(
            kanbanBoard = KanbanBoard(projects = projects),
        )
    }

    val snackBarHostState = remember { SnackbarHostState() }
    val snackBarChannel = remember { Channel<String>(Channel.BUFFERED) }

    val totalCount = kanbanBoardState.getTotalCount()
    val completeCount = kanbanBoardState.getCompleteCount()
    val progress = kanbanBoardState.getCompleteRatio()
    val progressPercent = (progress * 100).toInt()

    LaunchedEffect(Unit) {
        snackBarChannel.receiveAsFlow().collect { message ->
            snackBarHostState.showSnackbar(
                message = message,
                withDismissAction = true,
            )
        }
    }

    KanbanBoardContent(
        editTargetTask = kanbanBoardState.editTargetTask,
        projectTitles = kanbanBoardState.getProjectsTitles(),
        projectSelectedIndex = kanbanBoardState.selectedProjectIndex,
        completeCount = completeCount,
        totalCount = totalCount,
        progress = progress,
        progressPercent = progressPercent,
        isNewTaskDialog = kanbanBoardState.isNewTaskDialog,
        onNewTaskClick = {
            kanbanBoardState.showNewTaskDialog()
        },
        onNewTaskDismissClick = {
            kanbanBoardState.hideNewTaskDialog()
        },
        onEditTaskClick = {
            kanbanBoardState.showEditTaskDialog()
            kanbanBoardState.updateEditTargetTask(it)
        },
        onEditTaskDismissClick = {
            kanbanBoardState.hideEditTaskDialog()
        },
        snackHost = snackBarHostState,
        onCreateClick = {
            kanbanBoardState.addTask(it)
            kanbanBoardState.hideNewTaskDialog()
            snackBarChannel.trySend("새로운 태스크가 추가되었습니다.")
        },
        updateSelectedProjectIndex = { kanbanBoardState.updateSelectedProjectIndex(it) },
        onMoveTask = { taskId, targetStatus ->
            val message = when (val result = kanbanBoardState.moveTask(taskId = taskId, targetStatus = targetStatus)) {
                is EditUiEvent.Success -> "태스크가 이동되었습니다."
                is EditUiEvent.Error -> {
                    when (result.error) {
                        EditError.UNASSIGNED -> "담당자를 지정해야 상태를 옮길 수 있습니다."
                        EditError.INVALID_STATUS -> "해당 상태로 옮길 수 없습니다."
                    }
                }
            }

            snackBarChannel.trySend(message)
        },
        getTasksByStatus = { kanbanBoardState.getProjectTasksByStatus(it) },
        isEditTaskDialog = kanbanBoardState.isEditTaskDialog,
        onDeleteClick = {
            val message = when (kanbanBoardState.deleteTask(task = it)) {
                is DeleteUiEvent.Success -> "태스크가 삭제되었습니다."
                is DeleteUiEvent.Error -> "해당 상태에서는 태스크 삭제가 불가합니다."
            }

            snackBarChannel.trySend(message)
            kanbanBoardState.hideEditTaskDialog()
        },
        onEditClick = {
            val message = when (kanbanBoardState.editTask(task = it)) {
                is EditUiEvent.Success -> "태스크가 수정되었습니다."
                is EditUiEvent.Error -> "해당 상태로 옮길 수 없습니다."
            }

            snackBarChannel.trySend(message)
            kanbanBoardState.hideEditTaskDialog()
        },
    )
}

@Composable
private fun KanbanBoardContent(
    editTargetTask: KanbanTask?,
    projectTitles: List<String>,
    projectSelectedIndex: Int,
    completeCount: Int,
    totalCount: Int,
    progress: Float,
    progressPercent: Int,
    isNewTaskDialog: Boolean,
    snackHost: SnackbarHostState,
    onNewTaskClick: () -> Unit,
    onNewTaskDismissClick: () -> Unit,
    isEditTaskDialog: Boolean,
    onEditTaskClick: (KanbanTask) -> Unit,
    onEditTaskDismissClick: () -> Unit,
    onCreateClick: (KanbanTask) -> Unit,
    onEditClick: (KanbanTask) -> Unit,
    onDeleteClick: (KanbanTask) -> Unit,
    onMoveTask: (Long, Status) -> Unit,
    updateSelectedProjectIndex: (Int) -> Unit,
    getTasksByStatus: (Status) -> List<KanbanTask>,
) {
    // drag
    var draggedTask by remember { mutableStateOf<KanbanTask?>(null) }
    var currentDragPosition by remember { mutableStateOf<Offset?>(null) }
    val columnBounds = remember { mutableStateMapOf<Status, Rect>() }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackHost) },
        containerColor = Color.White,
    ) { innerPadding ->

        Row(
            modifier = Modifier
                .padding(innerPadding),
        ) {
            SideBar(
                title = "프로젝트",
                subTitle = "4주차 미션 보드",
                titles = projectTitles,
                selectedIndex = projectSelectedIndex,
                onTitleClick = updateSelectedProjectIndex,
            )

            VerticalDivider(modifier = Modifier.fillMaxHeight())

            Column {
                KanbanBoardTopAppBar(
                    title = projectTitles[projectSelectedIndex],
                    progress = progress,
                    progressPercent = progressPercent,
                    completeCount = completeCount,
                    totalCount = totalCount,
                    onNewTaskClick = onNewTaskClick,
                )

                CardGroup(
                    getTasksByStatus = getTasksByStatus,
                    modifier = Modifier
                        .padding(24.dp),
                    getIsDropTarget = { status ->
                        currentDragPosition?.let { columnBounds[status]?.contains(it) == true } ?: false
                    },
                    onBoundsChanged = { rect, status ->
                        columnBounds[status] = rect
                    },
                    onTaskDragStart = { task ->
                        draggedTask = task
                    },
                    onTaskDragChange = { pos ->
                        currentDragPosition = pos
                    },
                    onTaskDragEnd = {
                        val dropPosition = currentDragPosition ?: return@CardGroup
                        val targetStatus = columnBounds.entries
                            .firstOrNull { (_, rect) -> rect.contains(dropPosition) }?.key

                        draggedTask?.let { task ->
                            if (task.status != targetStatus) {
                                onMoveTask(task.id, targetStatus ?: return@let)
                            }
                        }
                        currentDragPosition = null
                        draggedTask = null
                    },
                    onTaskDragCancel = {
                        currentDragPosition = null
                        draggedTask = null
                    },
                    onCardClick = {
                        onEditTaskClick(it)
                    },
                )
            }
            if (isNewTaskDialog) {
                CreateTaskDialog(
                    onCreateClick = onCreateClick,
                    onDismissClick = onNewTaskDismissClick,
                )
            }

            if (isEditTaskDialog && editTargetTask != null) {
                EditTaskDialog(
                    task = editTargetTask,
                    onDismissClick = onEditTaskDismissClick,
                    onEditClick = onEditClick,
                    onDeleteClick = onDeleteClick,
                )
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 1200)
@Composable
private fun KanbanBoardContentPreview() {
    KanbanBoardContent(
        editTargetTask = null,
        projectTitles = listOf("1", "2"),
        projectSelectedIndex = 0,
        getTasksByStatus = {
            listOf(
                KanbanTask(
                    title = "LazyColumn 컴포넌트 구현",
                    description = "세로 스크롤 가능한 리스트 컴포넌트를 만들고 성능 최적화를 적용합니다.",
                    tags = listOf("컴포넌트", "성능"),
                    status = Status.TO_DO,
                    assignee = "다이노",
                ),
                KanbanTask(
                    title = "LazyColumn 컴포넌트 구현",
                    description = "세로 스크롤 가능한 리스트 컴포넌트를 만들고 성능 최적화를 적용합니다.",
                    tags = listOf("컴포넌트", "성능"),
                    status = Status.TO_DO,
                    assignee = "다이노",
                ),
                KanbanTask(
                    title = "LazyColumn 컴포넌트 구현",
                    description = "세로 스크롤 가능한 리스트 컴포넌트를 만들고 성능 최적화를 적용합니다.",
                    tags = listOf("컴포넌트", "성능"),
                    status = Status.IN_PROGRESS,
                    assignee = "다이노",
                ),
                KanbanTask(
                    title = "LazyColumn 컴포넌트 구현",
                    description = "세로 스크롤 가능한 리스트 컴포넌트를 만들고 성능 최적화를 적용합니다.",
                    tags = listOf("컴포넌트", "성능"),
                    status = Status.DONE,
                    assignee = "다이노",
                ),
                KanbanTask(
                    title = "LazyColumn 컴포넌트 구현",
                    description = "세로 스크롤 가능한 리스트 컴포넌트를 만들고 성능 최적화를 적용합니다.",
                    tags = listOf("컴포넌트", "성능"),
                    status = Status.DONE,
                    assignee = "다이노",
                ),
                KanbanTask(
                    title = "LazyColumn 컴포넌트 구현",
                    description = "세로 스크롤 가능한 리스트 컴포넌트를 만들고 성능 최적화를 적용합니다.",
                    tags = listOf("컴포넌트", "성능"),
                    status = Status.DONE,
                    assignee = "다이노",
                ),
            )
        },
        completeCount = 3,
        totalCount = 6,
        progress = 0.5f,
        progressPercent = 50,
        isNewTaskDialog = false,
        onNewTaskClick = { },
        onCreateClick = { },
        snackHost = SnackbarHostState(),
        onNewTaskDismissClick = { },
        updateSelectedProjectIndex = { },
        onMoveTask = { _, _ -> },
        onEditTaskClick = { },
        onEditTaskDismissClick = { },
        isEditTaskDialog = false,
        onDeleteClick = { },
        onEditClick = { },
    )
}
