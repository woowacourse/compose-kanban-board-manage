package woowacourse.kanban.board.ui.component.dialog.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kanbanboard.composeapp.generated.resources.Res
import kanbanboard.composeapp.generated.resources.error_dialog_title
import kanbanboard.composeapp.generated.resources.hint_dialog_description
import kanbanboard.composeapp.generated.resources.hint_dialog_tag
import kanbanboard.composeapp.generated.resources.hint_dialog_title
import kanbanboard.composeapp.generated.resources.label_dialog_assignee
import kanbanboard.composeapp.generated.resources.label_dialog_description
import kanbanboard.composeapp.generated.resources.label_dialog_status
import kanbanboard.composeapp.generated.resources.label_dialog_tag
import kanbanboard.composeapp.generated.resources.label_dialog_title
import kanbanboard.composeapp.generated.resources.tag_count_error
import kanbanboard.composeapp.generated.resources.tag_default_message
import kanbanboard.composeapp.generated.resources.tag_format_error
import org.jetbrains.compose.resources.stringResource
import woowacourse.kanban.board.data.AssigneePool
import woowacourse.kanban.board.domain.Assignee
import woowacourse.kanban.board.domain.Status
import woowacourse.kanban.board.ui.toTitle

@Composable
fun TaskDialogContent(
    titleValue: String,
    isTitleError: Boolean,
    onTitleChanged: (String) -> Unit,
    descriptionValue: String,
    onDescriptionChanged: (String) -> Unit,
    tagValue: String,
    isTagCountError: Boolean,
    isTagFormatError: Boolean,
    onTagChanged: (String) -> Unit,
    statuses: List<Status>,
    selectedStatus: Status,
    onStatusChanged: (Status) -> Unit,
    assignees: List<Assignee?>,
    assignee: Assignee?,
    onAssigneeChanged: (Assignee?) -> Unit,
    modifier: Modifier = Modifier,
) {
    val isTagError = isTagCountError || isTagFormatError
    val tagErrorMessage = when {
        isTagCountError -> stringResource(Res.string.tag_count_error, 5, 5)
        isTagFormatError -> stringResource(Res.string.tag_format_error)
        else -> stringResource(Res.string.tag_default_message, 5, 5)
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(24.dp),
    ) {
        TitleField(
            titleValue = titleValue,
            onTitleChanged = onTitleChanged,
            isTitleError = isTitleError,
        )

        DescriptionField(
            descriptionValue = descriptionValue,
            onDescriptionChanged = onDescriptionChanged,
        )

        TagField(
            tagValue = tagValue,
            onTagChanged = onTagChanged,
            isTagError = isTagError,
            tagErrorMessage = tagErrorMessage,
        )

        StatusSegmentedButtons(
            statuses = statuses,
            selectedStatus = selectedStatus,
            onStatusChanged = onStatusChanged,
        )

        AssigneesSegmentedButtons(
            assignees = assignees,
            assignee = assignee,
            onAssigneeChanged = onAssigneeChanged,
        )
    }
}

@Composable
private fun TitleField(
    titleValue: String,
    onTitleChanged: (String) -> Unit,
    isTitleError: Boolean,
    modifier: Modifier = Modifier,
) {
    TaskLabelLayout(
        label = stringResource(Res.string.label_dialog_title),
        isRequired = true,
        modifier = modifier,
    ) {
        TaskDialogTextField(
            value = titleValue,
            onValueChanged = onTitleChanged,
            isError = isTitleError,
            placeholder = stringResource(Res.string.hint_dialog_title),
            maxLines = 1,
        )

        if (isTitleError) {
            Text(
                text = stringResource(Res.string.error_dialog_title),
                fontSize = 12.sp,
                color = Color.Red,
                modifier = Modifier
                    .padding(top = 4.dp)
                    .padding(horizontal = 16.dp),
                maxLines = 1,
            )
        }
    }
}

@Composable
private fun DescriptionField(
    descriptionValue: String,
    onDescriptionChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    TaskLabelLayout(
        label = stringResource(Res.string.label_dialog_description),
        modifier = modifier,
    ) {
        TaskDialogTextField(
            value = descriptionValue,
            onValueChanged = onDescriptionChanged,
            placeholder = stringResource(Res.string.hint_dialog_description),
            modifier = Modifier.height(116.dp),
        )
    }
}

@Composable
private fun TagField(
    tagValue: String,
    onTagChanged: (String) -> Unit,
    isTagError: Boolean,
    tagErrorMessage: String,
    modifier: Modifier = Modifier,
) {
    TaskLabelLayout(
        label = stringResource(Res.string.label_dialog_tag),
        modifier = modifier,
    ) {
        TaskDialogTextField(
            value = tagValue,
            onValueChanged = onTagChanged,
            placeholder = stringResource(Res.string.hint_dialog_tag),
            isError = isTagError,
            modifier = Modifier.padding(bottom = 4.dp),
            maxLines = 1,
        )
        Text(
            text = tagErrorMessage,
            fontSize = 12.sp,
            color = if (isTagError) Color.Red else Color.Black,
            modifier = Modifier.padding(horizontal = 16.dp),
            maxLines = 1,
        )
    }
}

@Composable
private fun StatusSegmentedButtons(
    statuses: List<Status>,
    selectedStatus: Status,
    onStatusChanged: (Status) -> Unit,
    modifier: Modifier = Modifier,
) {
    TaskLabelLayout(
        label = stringResource(Res.string.label_dialog_status),
        isRequired = true,
        modifier = modifier,
    ) {
        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            statuses.forEach {
                StatusOptionCard(
                    statusTitle = it.toTitle(),
                    isSelected = selectedStatus == it,
                    onClick = { onStatusChanged(it) },
                )
            }
        }
    }
}

@Composable
private fun AssigneesSegmentedButtons(
    assignees: List<Assignee?>,
    assignee: Assignee?,
    onAssigneeChanged: (Assignee?) -> Unit,
    modifier: Modifier = Modifier,
) {
    TaskLabelLayout(
        label = stringResource(Res.string.label_dialog_assignee),
        isRequired = true,
        modifier = modifier,
    ) {
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            assignees.forEach {
                AssigneeOptionCard(
                    assignee = it,
                    isSelected = it == assignee,
                    onClick = { onAssigneeChanged(it) },
                )
            }
        }
    }
}

@Composable
private fun TaskLabelLayout(
    label: String,
    modifier: Modifier = Modifier,
    isRequired: Boolean = false,
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(
        modifier = modifier,
    ) {
        TaskFieldLabel(
            label = label,
            isRequired = isRequired,
        )

        Spacer(Modifier.height(8.dp))

        content()
    }
}

@Preview(device = Devices.DESKTOP)
@Composable
private fun TaskDialogContentPreview() {
    TaskDialogContent(
        titleValue = "",
        isTitleError = false,
        onTitleChanged = {},
        descriptionValue = "",
        onDescriptionChanged = {},
        tagValue = "버그, 긴급",
        isTagCountError = false,
        isTagFormatError = false,
        onTagChanged = {},
        statuses = Status.entries,
        selectedStatus = Status.TO_DO,
        onStatusChanged = {},
        assignees = AssigneePool.getAll(),
        assignee = AssigneePool.getAll()[0],
        onAssigneeChanged = {},
    )
}
