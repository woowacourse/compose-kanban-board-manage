package woowacourse.kanban.board.ui.component.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import woowacourse.kanban.board.domain.KanbanTask
import woowacourse.kanban.board.domain.dialog.Status
import woowacourse.kanban.board.ui.component.dialog.component.TaskDialogCancelButton
import woowacourse.kanban.board.ui.component.dialog.component.TaskDialogSubmitButton

@Composable
fun CreateTaskDialog(
    onCreateClick: (KanbanTask) -> Unit,
    onDismissClick: () -> Unit,
    modifier: Modifier = Modifier,
    dialogState: TaskDialogState = remember { TaskDialogState() },
) {
    TaskDialog(
        titleText = "새 태스크 생성",
        modifier = modifier,
        titleValue = dialogState.titleValue,
        isTitleError = dialogState.isTitleError,
        onTitleChanged = {
            dialogState.updateTitleValue(it)
            dialogState.updateTitleDirty()
        },
        descriptionValue = dialogState.descriptionValue,
        onDescriptionChanged = { dialogState.updateDescriptionValue(it) },
        tagValue = dialogState.tagValue,
        isTagCountError = dialogState.isTagCountError,
        isTagFormatError = dialogState.isTagFormatError,
        onTagChanged = { dialogState.updateTagValue(it) },
        statuses = Status.entries,
        selectedStatus = dialogState.selectedStatus,
        onStatusChanged = {
            dialogState.updateSelectedStatus(it)
            dialogState.updateIsSelectedEmptyAssignee(false)
        },
        assignees = dialogState.assignees,
        selectedAssignee = dialogState.selectedAssignee,
        onAssigneeChanged = {
            dialogState.updateSelectedAssignee(it)
            dialogState.updateIsSelectedEmptyAssignee(false)
        },
        isSelectedEmptyAssignee = dialogState.isSelectedEmptyAssignee,
        onEmptyAssignee = { dialogState.updateIsSelectedEmptyAssignee(true) },
        onDismissClick = onDismissClick,
    ) {
        NewTaskButtons(
            onDismissClick = onDismissClick,
            onCreateClick = {
                onCreateClick(
                    dialogState.createTask(),
                )
            },
            enabled = dialogState.enabled,
        )
    }
}

@Composable
private fun NewTaskButtons(
    onDismissClick: () -> Unit,
    onCreateClick: () -> Unit,
    enabled: Boolean,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
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
                onCreateClick()
            },
            enabled = enabled,
        )
    }
}

@Preview(widthDp = 1000)
@Composable
private fun CreateTaskDialogPreview() {
    CreateTaskDialog(
        onCreateClick = { },
        onDismissClick = { },
    )
}
