package woowacourse.kanban.board.ui.component.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import kanbanboard.composeapp.generated.resources.Res
import kanbanboard.composeapp.generated.resources.assignee_null
import org.jetbrains.compose.resources.stringResource
import woowacourse.kanban.board.data.AssigneePool
import woowacourse.kanban.board.domain.Assignee
import woowacourse.kanban.board.domain.KanbanTask
import woowacourse.kanban.board.domain.Status
import woowacourse.kanban.board.ui.component.dialog.component.AssigneeOptionCard
import woowacourse.kanban.board.ui.component.dialog.component.StatusOptionCard
import woowacourse.kanban.board.ui.component.dialog.component.TaskDialogCancelButton
import woowacourse.kanban.board.ui.component.dialog.component.TaskDialogSubmitButton
import woowacourse.kanban.board.ui.component.dialog.component.TaskDialogTextField
import woowacourse.kanban.board.ui.component.dialog.component.TaskDialogTopAppBar
import woowacourse.kanban.board.ui.component.dialog.component.TaskFieldLabel

@Composable
fun TaskDialog(
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
            selectedAssigneeIndex = state.selectedAssigneeIndex,
            onAssigneeChanged = { state.selectedAssigneeIndex = it },
            enabled = enabled,
            onDismissClick = onDismissClick,
            onCreateClick = {
                onCreateClick(
                    KanbanTask(
                        title = state.titleValue,
                        description = state.descriptionValue.takeIf { it.isNotBlank() },
                        tags = if (state.tagValue.isEmpty()) emptyList() else tags,
                        status = state.selectedStatus,
                        assignee = state.assignees[state.selectedAssigneeIndex],
                    ),
                )
            },
        )
    }
}

@Composable
private fun TaskDialogContent(
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
    selectedAssigneeIndex: Int,
    onAssigneeChanged: (Int) -> Unit,
    enabled: Boolean,
    onDismissClick: () -> Unit,
    onCreateClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val isTagError = isTagCountError || isTagFormatError
    val tagErrorMessage = when {
        isTagCountError -> "태그는 5자 이내로 5개까지만 등록할 수 있습니다."
        isTagFormatError -> "태그 형식이 올바르지 않습니다."
        else -> "5자 이내의 태그를 최대 5개까지 등록할 수 있습니다."
    }

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(Color.White)
            .padding(vertical = 28.dp, horizontal = 24.dp)
            .width(672.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
    ) {
        TaskDialogTopAppBar(
            title = "새 태스크 생성",
            onClick = onDismissClick,
            modifier = Modifier.padding(bottom = 4.dp),
        )

        HorizontalDivider(
            color = Color.Black,
            thickness = Dp.Hairline,
        )

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
            selectedAssigneeIndex = selectedAssigneeIndex,
            onAssigneeChanged = onAssigneeChanged,
        )

        HorizontalDivider(
            color = Color.Black,
            thickness = Dp.Hairline,
        )

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
                onClick = onCreateClick,
                enabled = enabled,
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
        label = "설명",
        modifier = modifier,
    ) {
        TaskDialogTextField(
            value = descriptionValue,
            onValueChanged = onDescriptionChanged,
            placeholder = "태스크에 대한 자세한 설명을 입력하세요.",
            modifier = Modifier.height(116.dp),
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
        label = "제목",
        isRequired = true,
        modifier = modifier,
    ) {
        TaskDialogTextField(
            value = titleValue,
            onValueChanged = onTitleChanged,
            isError = isTitleError,
            placeholder = "태스크 제목을 입력하세요.",
            maxLines = 1,
        )

        if (isTitleError) {
            Text(
                text = "제목을 입력해 주세요.",
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
private fun TagField(
    tagValue: String,
    onTagChanged: (String) -> Unit,
    isTagError: Boolean,
    tagErrorMessage: String,
    modifier: Modifier = Modifier,
) {
    TaskLabelLayout(
        label = "태그",
        modifier = modifier,
    ) {
        TaskDialogTextField(
            value = tagValue,
            onValueChanged = onTagChanged,
            placeholder = "태그를 쉼표로 구분하여 입력하세요 (예: 버그, 긴급)",
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
        label = "상태",
        isRequired = true,
        modifier = modifier,
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            statuses.forEach {
                StatusOptionCard(
                    status = it,
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
    selectedAssigneeIndex: Int,
    onAssigneeChanged: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    TaskLabelLayout(
        label = "담당자",
        isRequired = true,
        modifier = modifier,
    ) {
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            assignees.forEachIndexed { index, assignee ->
                AssigneeOptionCard(
                    assignee = assignee,
                    isSelected = selectedAssigneeIndex == index,
                    onClick = { onAssigneeChanged(index) },
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

@Preview
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
        selectedAssigneeIndex = 0,
        onAssigneeChanged = {},
        enabled = false,
        onDismissClick = {},
        onCreateClick = {},
    )
}
