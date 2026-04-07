package woowacourse.kanban.board.task.ui.project

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import woowacourse.kanban.board.task.domain.KanbanProject
import woowacourse.kanban.board.task.domain.TaskMockData
import woowacourse.kanban.board.task.ui.board.KanbanBoardScreen
import woowacourse.kanban.board.task.ui.board.SnackBarCard
import woowacourse.kanban.board.task.ui.modal.ModalCreateForm
import woowacourse.kanban.board.task.ui.modal.ModalEditForm
import woowacourse.kanban.board.task.ui.modal.RememberModalCreateFormState

@Composable
fun KanbanProjectScreen(kanbanProjectState: KanbanProjectState, modifier: Modifier = Modifier) {
    if (kanbanProjectState.isShowCreateModal) {
        val modalCreateFormState = RememberModalCreateFormState(TaskMockData.assignees)
        ModalCreateForm(
            state = modalCreateFormState,
            onDismissRequest = { kanbanProjectState.isShowCreateModal = false },
            onCreate = { card ->
                kanbanProjectState.onCreate(card)
            },
            modifier = Modifier.width(672.dp).height(820.dp),
        )
    }

    if (kanbanProjectState.isShowEditModal) {
        val modalCreateFormState = RememberModalCreateFormState(
            assignees = TaskMockData.assignees,
            initialCard = kanbanProjectState.editingCard,
        )
        ModalEditForm(
            state = modalCreateFormState,
            onDismissRequest = { kanbanProjectState.isShowEditModal = false },
            onEdit = { card ->
                kanbanProjectState.onEdit(card)
            },
            onDelete = {
                kanbanProjectState.onDelete()
            },
            modifier = Modifier.width(672.dp).height(820.dp),
        )
    }
    Scaffold(
        modifier = modifier,
        containerColor = Color.White,
        snackbarHost = {
            SnackbarHost(hostState = kanbanProjectState.snackbarHostState) { snackbarData ->
                SnackBarCard(
                    modifier = Modifier,
                    message = snackbarData.visuals.message,
                    onDismiss = { snackbarData.dismiss() },
                )
            }
        },
    ) { paddingValues ->
        Row(
            modifier = Modifier
                .padding(paddingValues),
        ) {
            KanbanProjectSideBar(
                modifier = Modifier.fillMaxHeight(),
                title = kanbanProjectState.kanbanProject.projectTitle,
                boardTitle = kanbanProjectState.kanbanProject.boardTitles,
                selected = kanbanProjectState.selectedBoard,
                onClick = { index ->
                    kanbanProjectState.onSelectBoard(index)
                },
            )
            KanbanBoardScreen(
                kanbanProjectState = kanbanProjectState,
                getIsDropTarget = { status ->
                    kanbanProjectState.currentDragPosition?.let { kanbanProjectState.columnBounds[status]?.contains(it) } ?: false
                },
                onBoundsChanged = { status, rect -> kanbanProjectState.columnBounds[status] = rect },
                onTaskDragStart = { task -> kanbanProjectState.draggedTask = task },
                onTaskDragChange = { pos -> kanbanProjectState.currentDragPosition = pos },
                onTaskDragEnd = {
                    val dropPosition = kanbanProjectState.currentDragPosition ?: return@KanbanBoardScreen
                    val targetStatus = kanbanProjectState.columnBounds.entries
                        .firstOrNull { (_, rect) -> rect.contains(dropPosition) }?.key

                    kanbanProjectState.draggedTask?.let { task ->
                        if (targetStatus != null && task.status != targetStatus) {
                            kanbanProjectState.onUpdateStatus(
                                card = task,
                                targetStatus = targetStatus,
                            )
                        }
                    }
                    kanbanProjectState.currentDragPosition = null
                    kanbanProjectState.draggedTask = null
                },
                onTaskDragCancel = {
                    kanbanProjectState.currentDragPosition = null
                    kanbanProjectState.draggedTask = null
                },
            )
        }
    }
}

@Preview(widthDp = 1500)
@Composable
private fun KanbanProjectScreenPreview() {
    KanbanProjectScreen(
        kanbanProjectState = RememberKanbanProjectState(
            coroutineScope = rememberCoroutineScope(),
            kanbanProject = KanbanProject(
                projectTitle = "4주차 미션 보드",
                boards = TaskMockData.boards,
            ),
        ),
    )
}
