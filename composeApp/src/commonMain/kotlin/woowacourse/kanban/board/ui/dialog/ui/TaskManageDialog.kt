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
import woowacourse.kanban.board.domain.KanbanTask
import woowacourse.kanban.board.ui.dialog.ui.createTextInput.CreateTextInput
import woowacourse.kanban.board.ui.dialog.ui.radioSelector.AssigneeButton
import woowacourse.kanban.board.ui.dialog.ui.radioSelector.NoneAssigneeButton
import woowacourse.kanban.board.ui.dialog.ui.radioSelector.RadioGridSelector
import woowacourse.kanban.board.ui.dialog.ui.radioSelector.RadioSelector
import woowacourse.kanban.board.ui.dialog.ui.radioSelector.StatusButton
import woowacourse.kanban.board.ui.dialog.ui.stateholder.TaskFormState
import woowacourse.kanban.domain.Assignee
import woowacourse.kanban.domain.Tags
import woowacourse.kanban.domain.TaskStatus
import woowacourse.kanban.domain.Title

@Composable
fun TaskManageDialog(
    taskFormState: TaskFormState,
    onDismiss: () -> Unit,
    onCreateTask: (task: KanbanTask) -> Unit,
    onUpdateTask: (task: KanbanTask) -> Unit,
    onDeleteTask: (id: Long) -> Unit,
    modifier: Modifier,
    assignees: List<Assignee> = emptyList(),
    currentTask: KanbanTask? = null,
) {
    val isToDo = remember(taskFormState.selectedStatusIndex) {
        TaskStatus.entries[taskFormState.selectedStatusIndex] == TaskStatus.TO_DO
    }

    LaunchedEffect(currentTask) {
        if (currentTask != null) {
            taskFormState.setTask(currentTask, assignees)
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
                label = if (taskFormState.isUpdate) "기존 태스크 수정"
                else "새 태스크 생성",
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
                    value = taskFormState.titleInputValue,
                    onChangeValue = { newTextValue ->
                        taskFormState.titleChange(newTextValue)
                    },
                    isError = taskFormState.isTitleError,
                    errorHintText = "제목 형식이 올바르지 않습니다.",
                )
                CreateTextInput(
                    modifier = Modifier,
                    title = "설명",
                    placeHolder = "태스크에 대한 자세한 설명을 입력하세요",
                    height = 116.dp,
                    placeHolderAlignment = Alignment.TopStart,
                    value = taskFormState.contentInputValue,
                    onChangeValue = { newTextValue -> taskFormState.contentChange(newTextValue) },
                )
                CreateTextInput(
                    modifier = Modifier,
                    title = "태그",
                    placeHolder = "태그를 쉼표로 구분하여 입력하세요 (예: 버그, 긴급)",
                    height = 44.dp,
                    hintText = "5자 이내의 태그를 최대 5개까지 등록할 수 있습니다.",
                    value = taskFormState.tagInputValue,
                    onChangeValue = { newTextValue ->
                        taskFormState.tagChange(newTextValue)
                    },
                    isError = taskFormState.isTagError,
                )
                RadioGridSelector(
                    header = "상태 *",
                    listSize = TaskStatus.entries.size,
                ) { index ->
                    StatusButton(
                        status = TaskStatus.entries[index],
                        isSelected = taskFormState.selectedStatusIndex == index,
                        onClick = { taskFormState.statusSelect(index) },
                    )
                }
                RadioSelector(
                    header = "담당자 *",
                    assignees.size,
                    noneButton =
                    if (isToDo) {
                        {
                            NoneAssigneeButton(
                                isSelected = taskFormState.selectedAssigneeIndex == null,
                                onClick = { taskFormState.noneAssigneeSelect() },
                            )
                        }
                    } else null,

                ) { index ->
                    AssigneeButton(
                        assignee = assignees[index],
                        isSelected = taskFormState.selectedAssigneeIndex == index,
                        onClick = { taskFormState.coachSelect(index) },
                    )
                }
                HorizontalDivider()
                FooterRow(
                    onCancel = { onDismiss() },
                    onCreate = if (taskFormState.isUpdate.not()) {
                        {
                            val isError = taskFormState.createValidate()
                            if (isError.not()) {
                                val task = KanbanTask(
                                    title = Title(taskFormState.titleInputValue),
                                    content = taskFormState.contentInputValue,
                                    tags = Tags(taskFormState.tagInputValue.split(",")),
                                    assignee = taskFormState.selectedAssigneeIndex?.let { assignees[it] },
                                    status = TaskStatus.entries[taskFormState.selectedStatusIndex],
                                )
                                onCreateTask(task)
                                onDismiss()
                            }
                        }
                    } else null,
                    onUpdate = if (taskFormState.isUpdate) {
                        {
                            val isError = taskFormState.createValidate()
                            if (isError.not()) {
                                val task = KanbanTask(
                                    title = Title(taskFormState.titleInputValue),
                                    content = taskFormState.contentInputValue,
                                    tags = Tags(taskFormState.tagInputValue.split(",")),
                                    assignee = taskFormState.selectedAssigneeIndex?.let { assignees[it] },
                                    status = TaskStatus.entries[taskFormState.selectedStatusIndex],
                                    id = currentTask?.data?.id,
                                )

                                onUpdateTask(task)
                                onDismiss()
                            }
                        }
                    } else null,
                    onDelete = if (taskFormState.isUpdate && currentTask != null) {
                        {
                            onDeleteTask(currentTask.data.id)
                            onDismiss()
                        }
                    } else null,
                    isFormError = taskFormState.isFormError,
                )
            }
        }
    }
}
