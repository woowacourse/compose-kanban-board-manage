package woowacourse.kanban.dialog.edit

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import woowacourse.kanban.dialog.components.DialogBar
import woowacourse.kanban.dialog.components.EditFooterRow
import woowacourse.kanban.dialog.components.createTextInput.CreateTextInput
import woowacourse.kanban.dialog.components.radioSelector.CoachButton
import woowacourse.kanban.dialog.components.radioSelector.RadioSelector
import woowacourse.kanban.dialog.components.radioSelector.StatusButton
import woowacourse.kanban.domain.task.Assignee
import woowacourse.kanban.domain.task.KanbanTask
import woowacourse.kanban.domain.task.Tags
import woowacourse.kanban.domain.task.TaskData
import woowacourse.kanban.domain.task.TaskStatus
import woowacourse.kanban.domain.task.Title

@Composable
fun TaskEditDialog(
    targetTask: KanbanTask,
    onDismiss: () -> Unit,
    onDeleteTask: (KanbanTask) -> Unit,
    onEditTask: (() -> KanbanTask) -> Unit,
    modifier: Modifier = Modifier,
    assignees: List<Assignee> = emptyList(),
) {
    val state = remember {
        TaskEditState(
            task = targetTask,
            assignees = assignees,
        )
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
                text = "기존 태스크 수정",
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
                EditFooterRow(
                    onCancel = { onDismiss() },
                    onDelete = {
                        onDeleteTask(targetTask)
                        onDismiss()
                    },
                    onEdit = {
                        val isError = state.onCreateValidate()
                        if (isError.not()) {
                            onEditTask {
                                state.taskCreate(
                                    assignee = assignees[state.selectedAssigneeIndex],
                                )
                            }
                            onDismiss()
                        }
                    },
                    isCreateError = state.isCreateError,
                )
            }
        }
    }
}

@Preview(widthDp = 1000, heightDp = 1000)
@Composable
fun TodoTaskEditDialogPreview() {
    TaskEditDialog(
        targetTask = KanbanTask(
            data = TaskData(
                title = Title("제목"),
                content = "",
                tags = Tags(listOf("1", "2", "3")),
                assignee = Assignee.DINO,
            ),
            status = TaskStatus.REVIEW,
        ),
        onDismiss = { },
        onDeleteTask = { },
        onEditTask = { },
        assignees = Assignee.entries,
    )
}

@Preview(widthDp = 1000, heightDp = 1000)
@Composable
fun TaskEditDialogPreview() {
    TaskEditDialog(
        targetTask = KanbanTask(
            data = TaskData(
                title = Title("제목"),
                content = "",
                tags = Tags(listOf("1", "2", "3")),
                assignee = Assignee.DINO,
            ),
            status = TaskStatus.TO_DO,
        ),
        onDismiss = { },
        onDeleteTask = { },
        onEditTask = { },
        assignees = listOf(Assignee.DINO, Assignee.FAMES),
    )
}
