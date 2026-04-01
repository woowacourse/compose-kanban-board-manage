package woowacourse.kanban.board.ui.component.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import kanbanboard.composeapp.generated.resources.Res
import kanbanboard.composeapp.generated.resources.edit_task
import org.jetbrains.compose.resources.stringResource
import woowacourse.kanban.board.domain.KanbanTask
import woowacourse.kanban.board.domain.Status
import woowacourse.kanban.board.ui.component.dialog.component.TaskDialogCancelButton
import woowacourse.kanban.board.ui.component.dialog.component.TaskDialogContent
import woowacourse.kanban.board.ui.component.dialog.component.TaskDialogDeleteButton
import woowacourse.kanban.board.ui.component.dialog.component.TaskDialogLayout
import woowacourse.kanban.board.ui.component.dialog.component.TaskDialogSubmitButton
import woowacourse.kanban.board.ui.component.dialog.component.TaskDialogTopBar

@Composable
fun EditTaskDialog(
    clickedTask: KanbanTask,
    onEditClick: (KanbanTask) -> Unit,
    onDeleteClick: () -> Unit,
    onDismissClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val state = remember {
        TaskDialogState(
            initialTitle = clickedTask.title,
            initialDescription = clickedTask.description ?: "",
            initialTag = clickedTask.tags.joinToString(","),
            initialStatus = clickedTask.status,
            initialAssignee = clickedTask.assignee,
        )
    }
    val isTitleError by remember {
        derivedStateOf {
            state.isTitleDirty && !KanbanTask.isTitleValid(state.titleValue)
        }
    }
    val tags by remember {
        derivedStateOf {
            state.tagValue.split(",").map { it.trim() }
        }
    }
    val isTagCountError by remember {
        derivedStateOf {
            state.tagValue.isNotBlank() && !KanbanTask.isTagCountValid(tags)
        }
    }
    val isTagFormatError by remember {
        derivedStateOf {
            state.tagValue.isNotBlank() && !KanbanTask.isTagFormatValid(tags)
        }
    }
    val enabled by remember {
        derivedStateOf {
            KanbanTask.isTitleValid(state.titleValue) && !isTagCountError && !isTagFormatError
        }
    }

    Dialog(
        onDismissRequest = onDismissClick,
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false,
            usePlatformDefaultWidth = false,
        ),
    ) {
        TaskDialogLayout(
            topBar = {
                TaskDialogTopBar(
                    title = stringResource(Res.string.edit_task),
                    onClick = onDismissClick,
                    modifier = Modifier.padding(bottom = 4.dp),
                )
            },
            bottomBar = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    TaskDialogCancelButton(
                        text = "취소",
                        onClick = onDismissClick,
                    )
                    Spacer(Modifier.width(12.dp))
                    TaskDialogDeleteButton(
                        text = "삭제",
                        onClick = onDeleteClick,
                    )
                    Spacer(Modifier.width(12.dp))
                    TaskDialogSubmitButton(
                        text = "수정",
                        onClick = {
                            onEditClick(
                                KanbanTask(
                                    title = state.titleValue,
                                    description = state.descriptionValue.takeIf { it.isNotBlank() },
                                    tags = if (state.tagValue.isEmpty()) emptyList() else tags,
                                    status = state.selectedStatus,
                                    assignee = state.assignee,
                                ),
                            )
                        },
                        enabled = enabled,
                    )
                }
            },
            content = {
                TaskDialogContent(
                    modifier = modifier,
                    titleValue = state.titleValue,
                    isTitleError = isTitleError,
                    onTitleChanged = {
                        state.titleValue = it
                        state.isTitleDirty = true
                    },
                    descriptionValue = state.descriptionValue,
                    onDescriptionChanged = { state.descriptionValue = it },
                    tagValue = state.tagValue,
                    isTagCountError = isTagCountError,
                    isTagFormatError = isTagFormatError,
                    onTagChanged = { state.tagValue = it },
                    statuses = Status.entries,
                    selectedStatus = state.selectedStatus,
                    onStatusChanged = { state.selectedStatus = it },
                    assignees = state.assignees,
                    assignee = state.assignee,
                    onAssigneeChanged = { state.assignee = it },
                )
            },
        )
    }
}
