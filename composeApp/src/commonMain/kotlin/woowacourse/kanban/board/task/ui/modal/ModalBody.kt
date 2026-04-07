package woowacourse.kanban.board.task.ui.modal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kanbanboard.composeapp.generated.resources.Res
import kanbanboard.composeapp.generated.resources.assignee_none
import kanbanboard.composeapp.generated.resources.label_assignee
import kanbanboard.composeapp.generated.resources.label_description
import kanbanboard.composeapp.generated.resources.label_status
import kanbanboard.composeapp.generated.resources.label_tags
import kanbanboard.composeapp.generated.resources.label_title
import kanbanboard.composeapp.generated.resources.place_holder_input_description
import kanbanboard.composeapp.generated.resources.place_holder_input_tags
import kanbanboard.composeapp.generated.resources.place_holder_input_title
import kanbanboard.composeapp.generated.resources.supporting_text_tags
import org.jetbrains.compose.resources.stringResource
import woowacourse.kanban.board.task.domain.KanbanStatus
import woowacourse.kanban.board.task.domain.TaskMockData
import woowacourse.kanban.board.theme.AssigneeButtonBackground
import woowacourse.kanban.board.theme.BorderAssigneeButton
import woowacourse.kanban.board.theme.BorderStatusButton
import woowacourse.kanban.board.theme.StatusButtonBackground

@Composable
fun ModalBody(state: ModalCreateFormState, actionContent: @Composable () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        ModalBodyInput(
            title = stringResource(Res.string.label_title),
            placeholder = stringResource(Res.string.place_holder_input_title),
            maxLines = 1,
            state = state.title,
            onValueChange = {
                state.title = it
                state.resetTitleError()
            },
            isValid = state.validTitle == null,
            errorType = state.validTitle,
        )

        ModalBodyInput(
            title = stringResource(Res.string.label_description),
            placeholder = stringResource(Res.string.place_holder_input_description),
            maxLines = 5,
            state = state.content,
            onValueChange = {
                state.content = it
            },
            isValid = true,
        )

        ModalBodyInput(
            title = stringResource(Res.string.label_tags),
            placeholder = stringResource(Res.string.place_holder_input_tags),
            maxLines = 1,
            state = state.tag,
            onValueChange = {
                state.tag = it
                state.resetTagError()
            },
            isValid = state.validTag == null,
            errorType = state.validTag,
            supportingMessage = stringResource(Res.string.supporting_text_tags),
        )

        ModalSelector(
            title = stringResource(Res.string.label_status),
        ) {
            KanbanStatus.entries.forEachIndexed { id, status ->
                ModalOptionButton(
                    modifier = Modifier.height(52.dp).weight(1f),
                    onClick = {
                        state.status = id
                        if (status != KanbanStatus.TO_DO && state.assignee == null) {
                            state.assignee = 0
                        }
                    },
                    isSelected = state.status == id,
                    selectedContainerColor = StatusButtonBackground,
                    selectedBorderColor = BorderStatusButton,
                ) {
                    ModalOptionStatus(
                        modifier = Modifier,
                        kanbanStatus = status,
                    )
                }
            }
        }

        ModalSelector(
            title = stringResource(Res.string.label_assignee),
        ) {
            val currentStatus = KanbanStatus.entries[state.status]
            if (currentStatus == KanbanStatus.TO_DO) {
                ModalOptionButton(
                    modifier = Modifier.height(68.dp).weight(1f),
                    onClick = { state.assignee = null },
                    isSelected = state.assignee == null,
                    selectedContainerColor = AssigneeButtonBackground,
                    selectedBorderColor = BorderAssigneeButton,
                ) {
                    Text(
                        text = stringResource(Res.string.assignee_none),
                        fontSize = 14.sp,
                    )
                }
            }

            state.assignees.forEachIndexed { id, name ->
                ModalOptionButton(
                    modifier = Modifier.height(68.dp).weight(1f),
                    onClick = {
                        state.assignee = id
                    },
                    isSelected = state.assignee == id,
                    selectedContainerColor = AssigneeButtonBackground,
                    selectedBorderColor = BorderAssigneeButton,
                ) {
                    ModalOptionAssignee(
                        modifier = Modifier,
                        name = name,
                    )
                }
            }
        }
        actionContent()
    }
}

@Preview(
    widthDp = 672,
    heightDp = 820,
)
@Composable
private fun ModalBodyPreview() {
    ModalBody(
        state = RememberModalCreateFormState(TaskMockData.assignees),
        actionContent = {
            ModalCreateAction(
                isValidTitle = true,
                isValidTag = true,
                onDismissRequest = {},
                onCreate = { },
            )
        },
    )
}
