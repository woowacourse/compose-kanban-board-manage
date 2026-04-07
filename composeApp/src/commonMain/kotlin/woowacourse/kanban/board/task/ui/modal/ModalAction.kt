package woowacourse.kanban.board.task.ui.modal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kanbanboard.composeapp.generated.resources.Res
import kanbanboard.composeapp.generated.resources.button_cancel
import kanbanboard.composeapp.generated.resources.button_create
import kanbanboard.composeapp.generated.resources.button_delete
import kanbanboard.composeapp.generated.resources.button_edit
import org.jetbrains.compose.resources.stringResource
import woowacourse.kanban.board.theme.CreateButtonBackground

@Composable
fun ModalCreateAction(
    isValidTitle: Boolean,
    isValidTag: Boolean,
    onDismissRequest: () -> Unit,
    onCreate: () -> Unit,
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
        ModalButton(
            onClick = onDismissRequest,
            colors = ButtonColors(
                containerColor = Color.White,
                contentColor = Color.Black,
                disabledContainerColor = Color.White,
                disabledContentColor = Color.Black,
            ),
            text = stringResource(Res.string.button_cancel),
        )

        Spacer(
            modifier = Modifier.width(12.dp),
        )

        ModalButton(
            onClick = onCreate,
            colors = ButtonColors(
                containerColor = CreateButtonBackground,
                contentColor = Color.White,
                disabledContainerColor = Color.Gray,
                disabledContentColor = Color.White,
            ),
            text = stringResource(Res.string.button_create),
            enabled = isValidTitle && isValidTag,
        )
    }
}

@Composable
fun ModalEditAction(onDismissRequest: () -> Unit, onDelete: () -> Unit, onEdit: () -> Unit, modifier: Modifier = Modifier) {
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
        ModalButton(
            onClick = onDismissRequest,
            colors = ButtonColors(
                containerColor = Color.White,
                contentColor = Color.Black,
                disabledContainerColor = Color.White,
                disabledContentColor = Color.Black,
            ),
            text = stringResource(Res.string.button_cancel),
        )

        Spacer(
            modifier = Modifier.width(12.dp),
        )

        ModalButton(
            onClick = onDelete,
            colors = ButtonColors(
                containerColor = Color.Red,
                contentColor = Color.White,
                disabledContainerColor = Color.Gray,
                disabledContentColor = Color.White,
            ),
            text = stringResource(Res.string.button_delete),
        )

        Spacer(
            modifier = Modifier.width(12.dp),
        )

        ModalButton(
            onClick = onEdit,
            colors = ButtonColors(
                containerColor = CreateButtonBackground,
                contentColor = Color.White,
                disabledContainerColor = Color.Gray,
                disabledContentColor = Color.White,
            ),
            text = stringResource(Res.string.button_edit),
        )
    }
}

@Composable
private fun ModalButton(onClick: () -> Unit, colors: ButtonColors, text: String, modifier: Modifier = Modifier, enabled: Boolean = true) {
    Button(
        modifier = modifier
            .height(44.dp)
            .width(68.dp),
        enabled = enabled,
        onClick = onClick,
        colors = colors,
        contentPadding = PaddingValues(0.dp),
        shape = RoundedCornerShape(10.dp),
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
        )
    }
}

@Preview
@Composable
private fun ModalActionPreview() {
    Box(modifier = Modifier.padding(10.dp)) {
        ModalCreateAction(
            isValidTag = true,
            isValidTitle = true,
            onDismissRequest = {},
            onCreate = {},
        )
    }
}

@Preview
@Composable
private fun ModalEditActionPreview() {
    Box(modifier = Modifier.padding(10.dp)) {
        ModalEditAction(
            onDismissRequest = {},
            onDelete = {},
            onEdit = {},
        )
    }
}
