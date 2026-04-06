package woowacourse.kanban.board.task.ui.modal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kanbanboard.composeapp.generated.resources.Res
import kanbanboard.composeapp.generated.resources.button_cancel
import kanbanboard.composeapp.generated.resources.button_create
import kanbanboard.composeapp.generated.resources.button_delete
import kanbanboard.composeapp.generated.resources.button_edit
import org.jetbrains.compose.resources.stringResource
import woowacourse.kanban.board.task.ui.board.TaskModalMode
import woowacourse.kanban.board.theme.Red500
import woowacourse.kanban.board.theme.Violet600

@Composable
fun ModalAction(
    modalMode: TaskModalMode,
    isValidTitle: Boolean,
    isValidTag: Boolean,
    onDismissRequest: () -> Unit,
    onAddOrEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    HorizontalDivider(
        thickness = Dp.Hairline,
        color = Color.LightGray,
    )
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.End,
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Button(
                modifier = Modifier
                    .height(44.dp)
                    .width(68.dp),
                onClick = onDismissRequest,
                colors = ButtonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black,
                    disabledContainerColor = Color.White,
                    disabledContentColor = Color.Black,
                ),
                contentPadding = PaddingValues(0.dp),
                shape = RoundedCornerShape(10.dp),
            ) {
                Text(
                    text = stringResource(Res.string.button_cancel),
                    fontSize = 16.sp,
                )
            }
            when (modalMode) {
                TaskModalMode.CREATE ->
                    Button(
                        modifier = Modifier
                            .height(44.dp)
                            .width(68.dp),
                        enabled = isValidTitle && isValidTag,
                        onClick = onAddOrEditClick,
                        colors = ButtonColors(
                            containerColor = Violet600,
                            contentColor = Color.White,
                            disabledContainerColor = Color.Gray,
                            disabledContentColor = Color.White,
                        ),
                        contentPadding = PaddingValues(0.dp),
                        shape = RoundedCornerShape(10.dp),
                    ) {
                        Text(
                            text = stringResource(Res.string.button_create),
                            fontSize = 16.sp,
                        )
                    }

                TaskModalMode.EDIT ->
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        Button(
                            modifier = Modifier
                                .height(44.dp)
                                .width(68.dp),
                            enabled = true,
                            onClick = onDeleteClick,
                            colors = ButtonColors(
                                containerColor = Red500,
                                contentColor = Color.White,
                                disabledContainerColor = Color.Gray,
                                disabledContentColor = Color.White,
                            ),
                            contentPadding = PaddingValues(0.dp),
                            shape = RoundedCornerShape(10.dp),
                        ) {
                            Text(
                                text = stringResource(Res.string.button_delete),
                                fontSize = 16.sp,
                            )
                        }

                        Button(
                            modifier = Modifier
                                .height(44.dp)
                                .width(68.dp),
                            enabled = isValidTitle && isValidTag,
                            onClick = onAddOrEditClick,
                            colors = ButtonColors(
                                containerColor = Violet600,
                                contentColor = Color.White,
                                disabledContainerColor = Color.Gray,
                                disabledContentColor = Color.White,
                            ),
                            contentPadding = PaddingValues(0.dp),
                            shape = RoundedCornerShape(10.dp),
                        ) {
                            Text(
                                text = stringResource(Res.string.button_edit),
                                fontSize = 16.sp,
                            )
                        }
                    }
            }
        }
    }
}

private class ModalActionPreviewParameterProvider : PreviewParameterProvider<TaskModalMode> {
    override val values = sequenceOf(
        TaskModalMode.CREATE,
        TaskModalMode.EDIT,
    )
}

@Preview
@Composable
private fun ModalActionPreview(@PreviewParameter(ModalActionPreviewParameterProvider::class) taskModalMode: TaskModalMode) {
    ModalAction(
        modalMode = taskModalMode,
        isValidTag = true,
        isValidTitle = true,
        onDismissRequest = {},
        onAddOrEditClick = {},
        onDeleteClick = {},
    )
}
