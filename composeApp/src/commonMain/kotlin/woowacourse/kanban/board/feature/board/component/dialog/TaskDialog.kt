package woowacourse.kanban.board.feature.board.component.dialog

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import woowacourse.kanban.board.domain.KanbanTask
import woowacourse.kanban.board.domain.TaskStatus
import woowacourse.kanban.board.feature.board.component.dialog.component.TaskDialogContent
import woowacourse.kanban.board.feature.board.component.dialog.model.TaskFormResult

@Composable
fun TaskDialog(
    onConfirmClick: (result: TaskFormResult) -> Unit,
    onDismissClick: () -> Unit,
    modifier: Modifier = Modifier,
    mode: TaskDialogMode = TaskDialogMode.Create,
    onDeleteClick: (() -> Unit)? = null,
    assignees: List<String> = listOf("다이노", "페임스"),
) {
    val formState = when (mode) {
        TaskDialogMode.Create -> rememberTaskFormState()
        is TaskDialogMode.Edit -> rememberTaskFormState(mode.task)
    }
    val availableStatuses = remember(mode) { mode.availableStatuses }
    val dialogTitle = when (mode) {
        TaskDialogMode.Create -> "새 태스크 생성"
        is TaskDialogMode.Edit -> "기존 태스크 수정"
    }
    val confirmButtonText = when (mode) {
        TaskDialogMode.Create -> "생성"
        is TaskDialogMode.Edit -> "수정"
    }

    Dialog(
        onDismissRequest = onDismissClick,
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false,
            usePlatformDefaultWidth = false,
        ),
    ) {
        TaskDialogContent(
            modifier = modifier,
            title = dialogTitle,
            confirmButtonText = confirmButtonText,
            titleValue = formState.title,
            isTitleError = formState.isTitleError,
            onTitleChanged = {
                formState.title = it
                formState.isTitleDirty = true
            },
            descriptionValue = formState.description,
            onDescriptionChanged = { formState.description = it },
            tagValue = formState.tagValue,
            isTagCountError = formState.isTagCountError,
            isTagFormatError = formState.isTagFormatError,
            onTagChanged = { formState.tagValue = it },
            statuses = availableStatuses,
            selectedStatus = formState.selectedStatus,
            onStatusChanged = { formState.selectedStatus = it },
            assignees = assignees,
            selectedAssignee = formState.selectedAssignee,
            onAssigneeChanged = { formState.selectedAssignee = if (formState.selectedAssignee == it) null else it },
            isAssigneeRequired = formState.isAssigneeRequired,
            enabled = formState.isConfirmButtonEnabled,
            onDismissClick = onDismissClick,
            onDeleteClick = onDeleteClick,
            onConfirmClick = {
                onConfirmClick(
                    TaskFormResult(
                        title = formState.title,
                        description = formState.description.takeIf { it.isNotBlank() },
                        tags = formState.tags,
                        status = formState.selectedStatus,
                        assignee = formState.selectedAssignee,
                    ),
                )
            },
        )
    }
}

sealed class TaskDialogMode {
    data object Create : TaskDialogMode()
    data class Edit(val task: KanbanTask) : TaskDialogMode()

    val availableStatuses: List<TaskStatus>
        get() = when (this) {
            Create -> TaskStatus.entries
            is Edit -> (task.status.validTransitions + task.status).sortedBy { it.ordinal }
        }
}
