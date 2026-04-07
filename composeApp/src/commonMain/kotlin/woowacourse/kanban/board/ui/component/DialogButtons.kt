package woowacourse.kanban.board.ui.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kanbanboard.composeapp.generated.resources.Res
import kanbanboard.composeapp.generated.resources.button_cancel
import kanbanboard.composeapp.generated.resources.button_delete
import org.jetbrains.compose.resources.stringResource
import woowacourse.kanban.board.ui.theme.Gray300
import woowacourse.kanban.board.ui.theme.Gray700
import woowacourse.kanban.board.ui.theme.Purple

@Composable
fun CancelButton(modifier: Modifier = Modifier, onClickCancel: () -> Unit, enabled: Boolean = true) {
    Button(
        modifier = modifier,
        onClick = {
            onClickCancel()
        },
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = Gray700,
        ),
        contentPadding = PaddingValues(horizontal = 18.dp, vertical = 10.dp),
    ) {
        Text(
            text = stringResource(Res.string.button_cancel),
            fontSize = 16.sp,
            fontWeight = FontWeight.W500,
        )
    }
}

@Composable
fun DeleteButton(modifier: Modifier = Modifier, onClickDelete: () -> Unit) {
    Button(
        modifier = modifier,
        onClick = {
            onClickDelete()
        },
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xffDB6365),
            contentColor = Color.White,
        ),
        contentPadding = PaddingValues(horizontal = 18.dp, vertical = 10.dp),
    ) {
        Text(
            text = stringResource(Res.string.button_delete),
            fontSize = 16.sp,
            fontWeight = FontWeight.W500,
        )
    }
}

@Composable
fun ConfirmButton(modifier: Modifier = Modifier, content: @Composable () -> Unit, onClickConfirm: () -> Unit, enabled: Boolean = true) {
    Button(
        modifier = modifier,
        onClick = {
            onClickConfirm()
        },
        enabled = enabled,
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Purple,
            contentColor = Color.White,
            disabledContainerColor = Gray300,
            disabledContentColor = Color.White,
        ),
        contentPadding = PaddingValues(horizontal = 18.dp, vertical = 10.dp),
    ) {
        content()
    }
}

@Preview(showBackground = true)
@Composable
private fun ConfirmButtonPreview() {
    ConfirmButton(
        onClickConfirm = {},
        content = {
            Text(
                text = "버튼",
            )
        },
    )
}

@Preview(showBackground = true)
@Composable
private fun DeleteButtonPreview() {
    DeleteButton(
        onClickDelete = {},
    )
}

@Preview(showBackground = true)
@Composable
private fun CancelButtonPreview() {
    CancelButton(
        onClickCancel = {},
    )
}
