package woowacourse.kanban.board.feature.board

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import woowacourse.kanban.board.feature.board.component.KanbanBoardContent
import woowacourse.kanban.board.feature.board.component.KanbanBoardSidebar
import woowacourse.kanban.board.feature.board.component.dialog.TaskDialog
import woowacourse.kanban.board.feature.board.component.dialog.TaskDialogMode

@Composable
fun KanbanBoardScreen(
    onShowSnackbar: (String) -> Unit,
    modifier: Modifier = Modifier,
    boardState: KanbanBoardState = rememberKanbanBoardState(),
) {
    LaunchedEffect(Unit) {
        boardState.events.collect { event ->
            val message = when (event) {
                KanbanBoardEvent.TaskMoved -> "태스크가 이동되었습니다."
                KanbanBoardEvent.TaskAdded -> "새로운 태스크가 추가되었습니다."
                KanbanBoardEvent.TaskAddFailed -> "태스크 추가에 실패했습니다."
                KanbanBoardEvent.TaskEdited -> "태스크가 수정되었습니다."
                KanbanBoardEvent.TaskEditFailed -> "태스크 수정에 실패했습니다."
                KanbanBoardEvent.TaskDeleted -> "태스크가 삭제되었습니다."
                KanbanBoardEvent.TaskDeleteFailed -> "해당 상태에서는 태스크 삭제가 불가합니다."
                KanbanBoardEvent.TaskTransitionFailed -> "해당 상태로 옮길 수 없습니다."
                KanbanBoardEvent.TaskAssigneeMissing -> "담당자를 지정해야 상태를 옮길 수 있습니다."
            }
            onShowSnackbar(message)
        }
    }

    Row(modifier = modifier.fillMaxSize()) {
        KanbanBoardSidebar()
        KanbanBoardContent(
            modifier = Modifier.weight(1f),
            kanbanBoard = boardState.kanbanBoard,
            onTaskCreateClick = boardState::showTaskDialog,
            onTaskClick = boardState::showEditDialog,
            onMoveTask = boardState::moveTask,
        )
    }

    if (boardState.isTaskDialogVisible) {
        TaskDialog(
            onConfirmClick = boardState::addTask,
            onDismissClick = boardState::hideTaskDialog,
        )
    }

    boardState.selectedTask?.let { task ->
        TaskDialog(
            mode = TaskDialogMode.Edit(task),
            onConfirmClick = { boardState.editTask(task, it) },
            onDeleteClick = if (task.status.isDeletable) {
                { boardState.deleteTask(task) }
            } else {
                null
            },
            onDismissClick = boardState::hideEditDialog,
        )
    }
}
