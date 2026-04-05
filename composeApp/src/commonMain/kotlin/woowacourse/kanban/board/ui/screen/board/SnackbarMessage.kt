package woowacourse.kanban.board.ui.screen.board

import kanbanboard.composeapp.generated.resources.Res
import kanbanboard.composeapp.generated.resources.snackbar_board_task_assignee_required
import kanbanboard.composeapp.generated.resources.snackbar_board_task_created
import kanbanboard.composeapp.generated.resources.snackbar_board_task_delete_not_allowed
import kanbanboard.composeapp.generated.resources.snackbar_board_task_deleted
import kanbanboard.composeapp.generated.resources.snackbar_board_task_edited
import kanbanboard.composeapp.generated.resources.snackbar_board_task_move_not_allowed
import kanbanboard.composeapp.generated.resources.snackbar_board_task_moved
import org.jetbrains.compose.resources.StringResource

enum class SnackbarMessage(val textRes: StringResource) {
    TASK_CREATED(Res.string.snackbar_board_task_created),
    TASK_MOVED(Res.string.snackbar_board_task_moved),
    TASK_EDITED(Res.string.snackbar_board_task_edited),
    TASK_DELETED(Res.string.snackbar_board_task_deleted),
    TASK_DELETE_NOT_ALLOWED(Res.string.snackbar_board_task_delete_not_allowed),
    TASK_MOVE_NOT_ALLOWED(Res.string.snackbar_board_task_move_not_allowed),
    TASK_ASSIGNEE_REQUIRED(Res.string.snackbar_board_task_assignee_required)
}
