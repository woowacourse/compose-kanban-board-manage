package woowacourse.kanban.board.task.ui.modal

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import kanbanboard.composeapp.generated.resources.Res
import kanbanboard.composeapp.generated.resources.modal_title_edit_task
import kanbanboard.composeapp.generated.resources.modal_title_new_task
import org.jetbrains.compose.resources.stringResource
import woowacourse.kanban.board.task.domain.KanbanCard
import woowacourse.kanban.board.task.domain.TaskMockData

@Composable
fun ModalCreateForm(
    state: ModalCreateFormState,
    onDismissRequest: () -> Unit,
    onCreate: (KanbanCard) -> Unit,
    modifier: Modifier = Modifier,
) {
    Dialog(
        onDismissRequest = onDismissRequest,
    ) {
        Column(
            modifier = modifier
                .clip(RoundedCornerShape(10.dp))
                .background(Color.White)
                .border(
                    1.dp,
                    Color.LightGray,
                    RoundedCornerShape(10.dp),
                ),
        ) {
            ModalHeader(
                title = stringResource(Res.string.modal_title_new_task),
                onDismissRequest = onDismissRequest,
            )

            HorizontalDivider(
                thickness = Dp.Hairline,
                color = Color.LightGray,
            )

            ModalBody(
                state = state,
                modifier = Modifier,
                actionContent = {
                    ModalCreateAction(
                        isValidTitle = state.isValidTitle,
                        isValidTag = state.isValidTag,
                        onDismissRequest = onDismissRequest,
                        onCreate = {
                            if (state.validate()) {
                                onCreate(state.toCard())
                            }
                        },
                    )
                },
            )
        }
    }
}

@Composable
fun ModalEditForm(
    state: ModalCreateFormState,
    onDismissRequest: () -> Unit,
    onEdit: (KanbanCard) -> Unit,
    onDelete: (KanbanCard) -> Unit,
    modifier: Modifier = Modifier,
) {
    Dialog(
        onDismissRequest = onDismissRequest,
    ) {
        Column(
            modifier = modifier
                .clip(RoundedCornerShape(10.dp))
                .background(Color.White)
                .border(
                    1.dp,
                    Color.LightGray,
                    RoundedCornerShape(10.dp),
                ),
        ) {
            ModalHeader(
                title = stringResource(Res.string.modal_title_edit_task),
                onDismissRequest = onDismissRequest,
            )

            HorizontalDivider(
                thickness = Dp.Hairline,
                color = Color.LightGray,
            )

            ModalBody(
                state = state,
                modifier = Modifier,
                actionContent = {
                    ModalEditAction(
                        onDismissRequest = onDismissRequest,
                        onDelete = {
                            onDelete(state.toCard())
                        },
                        onEdit = {
                            onEdit(state.toCard())
                        },
                    )
                },
            )
        }
    }
}

@Preview(
    widthDp = 672,
    heightDp = 1000,
)
@Composable
private fun ModalCreateFormPreview() {
    ModalCreateForm(
        state = RememberModalCreateFormState(TaskMockData.assignees),
        onDismissRequest = {},
        onCreate = { _ -> },
    )
}

@Preview(
    widthDp = 672,
    heightDp = 1000,
)
@Composable
private fun ModalEditFormPreview() {
    ModalEditForm(
        state = RememberModalCreateFormState(
            assignees = TaskMockData.assignees,
        ),
        onDismissRequest = {},
        onEdit = {},
        onDelete = {},
    )
}
