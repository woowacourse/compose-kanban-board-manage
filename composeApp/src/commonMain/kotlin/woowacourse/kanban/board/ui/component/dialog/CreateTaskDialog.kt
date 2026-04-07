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
import kanbanboard.composeapp.generated.resources.create_task
import org.jetbrains.compose.resources.stringResource
import woowacourse.kanban.board.domain.KanbanTask
import woowacourse.kanban.board.domain.Status
import woowacourse.kanban.board.ui.component.dialog.component.TaskDialogCancelButton
import woowacourse.kanban.board.ui.component.dialog.component.TaskDialogContent
import woowacourse.kanban.board.ui.component.dialog.component.TaskDialogLayout
import woowacourse.kanban.board.ui.component.dialog.component.TaskDialogSubmitButton
import woowacourse.kanban.board.ui.component.dialog.component.TaskDialogTopBar

@Composable
fun CreateTaskDialog(
    onCreateClick: (KanbanTask) -> Unit,
    onDismissClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val state = remember { TaskDialogState() }
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
                    title = stringResource(Res.string.create_task),
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
                    TaskDialogSubmitButton(
                        text = "생성",
                        onClick = {
                            onCreateClick(
                                KanbanTask(
                                    title = state.titleValue,
                                    description = state.descriptionValue.takeIf { it.isNotBlank() },
                                    tags = if (state.tagValue.isEmpty()) emptyList() else tags,
                                    status = state.selectedStatus,
                                    assigneeState = state.assigneeState,
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
                    onTitleChanged = state::changeTitle,
                    descriptionValue = state.descriptionValue,
                    onDescriptionChanged = { state.descriptionValue = it },
                    tagValue = state.tagValue,
                    isTagCountError = isTagCountError,
                    isTagFormatError = isTagFormatError,
                    onTagChanged = { state.tagValue = it },
                    statuses = Status.entries,
                    selectedStatus = state.selectedStatus,
                    onStatusChanged = state::changeStatus,
                    assigneeStates = state.assigneeStates,
                    assigneeState = state.assigneeState,
                    onAssigneeChanged = state::changeAssignee,
                )
            },
        )
    }
}
