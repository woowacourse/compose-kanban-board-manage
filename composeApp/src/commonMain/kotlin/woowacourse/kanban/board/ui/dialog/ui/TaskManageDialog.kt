package woowacourse.kanban.board.ui.dialog.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import woowacourse.kanban.board.ui.dialog.ui.createTextInput.CreateTextInput
import woowacourse.kanban.board.ui.dialog.ui.radioSelector.AssigneeButton
import woowacourse.kanban.board.ui.dialog.ui.radioSelector.NoneAssigneeButton
import woowacourse.kanban.board.ui.dialog.ui.radioSelector.RadioGridSelector
import woowacourse.kanban.board.ui.dialog.ui.radioSelector.RadioSelector
import woowacourse.kanban.board.ui.dialog.ui.radioSelector.StatusButton
import woowacourse.kanban.board.ui.dialog.ui.stateholder.TaskFormState
import woowacourse.kanban.domain.Assignee
import woowacourse.kanban.domain.BoardData
import woowacourse.kanban.domain.KanbanTask
import woowacourse.kanban.domain.Tags
import woowacourse.kanban.domain.TaskStatus
import woowacourse.kanban.domain.Title

@Composable
fun TaskManageDialog(
    onDismiss: () -> Unit,
    onCreateTask: (task: KanbanTask) -> Unit,
    modifier: Modifier,
    assignees: List<Assignee> = emptyList(),
    currentTask: KanbanTask? = null,
) {
    val state = remember {
        TaskFormState()
    }
    val isToDo = remember(state.selectedStatusIndex) {
        TaskStatus.entries[state.selectedStatusIndex] == TaskStatus.TO_DO
    }

    LaunchedEffect(currentTask) {
        if (currentTask != null) {
            state.setTask(currentTask, assignees)
        }
    }

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
                RadioGridSelector(
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
                    noneButton =
                    if (isToDo) {
                        {
                            NoneAssigneeButton(
                                isSelected = state.selectedAssigneeIndex == null,
                                onClick = { state.onNoneAssigneeSelect() },
                            )
                        }
                    } else null,

                ) { index ->
                    AssigneeButton(
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
                            val task = KanbanTask(
                                data = BoardData(
                                    title = Title(state.titleInputValue),
                                    content = state.contentInputValue,
                                    tags = Tags(state.tagInputValue.split(",")),
                                    assignee = state.selectedAssigneeIndex?.let { assignees[it] },
                                ),
                                status = TaskStatus.entries[state.selectedStatusIndex],
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
