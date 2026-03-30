package woowacourse.kanban.create.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import woowacourse.kanban.create.domain.TaskCreateAction
import woowacourse.kanban.create.ui.createTextInput.CreateTextInput
import woowacourse.kanban.create.ui.radioSelector.CoachButton
import woowacourse.kanban.create.ui.radioSelector.RadioSelector
import woowacourse.kanban.create.ui.radioSelector.StatusButton
import woowacourse.kanban.create.ui.stateholder.TaskFormState
import woowacourse.kanban.domain.Assignee
import woowacourse.kanban.domain.KanbanTask
import woowacourse.kanban.domain.TaskStatus

@Composable
fun TaskCreateDialog(
    onDismiss: () -> Unit,
    onCreateTask: (task: KanbanTask) -> Unit,
    modifier: Modifier,
    assignees: List<Assignee> = emptyList(),
) {
    val state = remember { TaskFormState() }
    val action = remember { TaskCreateAction() }

    Dialog(
        onDismissRequest = {
            onDismiss()
        },
    ) {
        Column(
            modifier = modifier.background(color = Color.White)
                .size(
                    width = 672.dp,
                    height = 900.dp,
                ),
        ) {
            DialogBar(
                modifier = Modifier.padding(
                    vertical = 28.dp,
                    horizontal = 24.dp,
                )
                    .fillMaxWidth(),
            )
            HorizontalDivider()
            Column(
                modifier = Modifier.padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp),
            ) {
                CreateTextInput(
                    title = "제목 *",
                    placeHolder = "태스크 제목을 입력하세요",
                    height = 48.dp,
                    value = state.titleInputValue,
                    onChangeValue = { newTextValue ->
                        state.onTitleChange(newTextValue)
                    },
                    isError = state.isTitleError,
                )
                CreateTextInput(
                    modifier = Modifier,
                    title = "설명",
                    placeHolder = "태스크에 대한 자세한 설명을 입력하세요",
                    height = 116.dp,
                    placeHolderAlignment = Alignment.TopStart,
                    value = state.contentInputValue,
                    onChangeValue = { newTextValue -> state.onContentChange(newTextValue) },
                )
                CreateTextInput(
                    modifier = Modifier,
                    title = "태그",
                    placeHolder = "태그를 쉼표로 구분하여 입력하세요 (예: 버그, 긴급)",
                    height = 44.dp,
                    hintText = "5자 이내의 태그를 최대 5개까지 등록할 수 있습니다.",
                    value = state.tagInputValue,
                    onChangeValue = { newTextValue ->
                        state.onTagChange(newTextValue)
                    },
                    isError = state.isTagError,
                )
                RadioSelector(
                    header = "상태 *",
                    listSize = TaskStatus.entries.size,
                ) { index ->
                    StatusButton(
                        status = TaskStatus.entries[index],
                        isSelected = state.selectedStatusIndex == index,
                        onClick = { state.onStatusSelect(index) },
                    )
                }
                RadioSelector(
                    header = "담당자 *",
                    assignees.size,
                ) { index ->
                    CoachButton(
                        assignee = assignees[index],
                        isSelected = state.selectedAssigneeIndex == index,
                        onClick = { state.onCoachSelect(index) },
                    )
                }
                HorizontalDivider()
                FooterRow(
                    onCancel = { onDismiss() },
                    onCreate = {
                        val isError = state.onCreateValidate()
                        if (isError.not()) {
                            val task = action.createTask(
                                title = state.titleInputValue,
                                content = state.contentInputValue,
                                tags = state.tagInputValue,
                                statusIndex = state.selectedStatusIndex,
                                assignee = assignees[state.selectedAssigneeIndex],
                            )
                            onCreateTask(task)
                            onDismiss()
                        }
                    },
                    isCreateError = state.isCreateError,
                )
            }
        }
    }
}
