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
import woowacourse.kanban.board.ui.component.dialog.component.TaskDialogDeleteButton
import woowacourse.kanban.board.ui.component.dialog.component.TaskDialogSubmitButton

@Composable
fun EditTaskDialog(
    task: KanbanTask,
    onEditClick: (KanbanTask) -> Unit,
    onDismissClick: () -> Unit,
    onDeleteClick: (KanbanTask) -> Unit,
    modifier: Modifier = Modifier,
    dialogState: TaskDialogState = remember(task) { TaskDialogState(task) },
) {
    TaskDialog(
        titleText = "기존 태스크 수정",
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
        EditTaskButtons(
            onDismissClick = onDismissClick,
            onEditClick = {
                onEditClick(dialogState.updateTask(task))
            },
            onDeleteClick = { onDeleteClick(task) },
            enabled = dialogState.enabled,
        )
    }
}

@Composable
private fun EditTaskButtons(
    onDismissClick: () -> Unit,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
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

        TaskDialogDeleteButton(
            text = "삭제",
            onClick = onDeleteClick,
        )

        Spacer(Modifier.width(12.dp))

        TaskDialogSubmitButton(
            text = "수정",
            onClick = onEditClick,
            enabled = enabled,
        )
    }
}

@Preview(widthDp = 1000)
@Composable
private fun EditTaskDialogPreview() {
    val task = KanbanTask(
        title = "크롱 안녕하세요!",
        status = Status.TO_DO,
        tags = listOf("리뷰", "감사합니다"),
        assignee = "볼트",
    )
    EditTaskDialog(
        task = task,
        onEditClick = { },
        onDeleteClick = { },
        onDismissClick = { },
    )
}
