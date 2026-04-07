package woowacourse.kanban.board.ui.util

import kanbanboard.composeapp.generated.resources.Res
import kanbanboard.composeapp.generated.resources.error_required_assignee
import kanbanboard.composeapp.generated.resources.error_tag_count_exceeded
import kanbanboard.composeapp.generated.resources.error_tag_invalid_format
import kanbanboard.composeapp.generated.resources.error_tag_invalid_length
import kanbanboard.composeapp.generated.resources.error_title_blank
import kanbanboard.composeapp.generated.resources.snackbar_error_create_new_task
import kanbanboard.composeapp.generated.resources.snackbar_error_delete_task
import kanbanboard.composeapp.generated.resources.snackbar_error_invalid_status_transition
import kanbanboard.composeapp.generated.resources.snackbar_project_not_found
import kanbanboard.composeapp.generated.resources.snackbar_task_not_found
import kanbanboard.composeapp.generated.resources.status_done
import kanbanboard.composeapp.generated.resources.status_in_progress
import kanbanboard.composeapp.generated.resources.status_review
import kanbanboard.composeapp.generated.resources.status_todo
import org.jetbrains.compose.resources.StringResource
import woowacourse.kanban.board.domain.model.KanbanError
import woowacourse.kanban.board.domain.model.Status
import woowacourse.kanban.board.domain.validator.ValidationError

fun ValidationError.toMessage(): StringResource = when (this) {
    ValidationError.TITLE_BLANK -> Res.string.error_title_blank
    ValidationError.TAG_INVALID_FORMAT -> Res.string.error_tag_invalid_format
    ValidationError.TAG_INVALID_LENGTH -> Res.string.error_tag_invalid_length
    ValidationError.TAG_COUNT_EXCEEDED -> Res.string.error_tag_count_exceeded
    ValidationError.REQUIRED_ASSIGNEE -> Res.string.error_required_assignee
}

fun Status.toUiString(): StringResource = when (this) {
    Status.TODO -> Res.string.status_todo
    Status.IN_PROGRESS -> Res.string.status_in_progress
    Status.REVIEW -> Res.string.status_review
    Status.DONE -> Res.string.status_done
}

fun KanbanError.toMessage(): StringResource = when (this) {
    is KanbanError.TaskCreationFailed -> Res.string.snackbar_error_create_new_task
    is KanbanError.ProjectNotFound -> Res.string.snackbar_project_not_found
    is KanbanError.TaskNotFound -> Res.string.snackbar_task_not_found
    is KanbanError.CannotDeleteTask -> Res.string.snackbar_error_delete_task
    is KanbanError.InvalidStatusTransition -> Res.string.snackbar_error_invalid_status_transition
    is KanbanError.AssigneeRequired -> Res.string.error_required_assignee
}
