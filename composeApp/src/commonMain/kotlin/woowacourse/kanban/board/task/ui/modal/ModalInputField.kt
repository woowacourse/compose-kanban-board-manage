package woowacourse.kanban.board.task.ui.modal

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import woowacourse.kanban.board.theme.Gray950
import woowacourse.kanban.board.theme.Red700

@Composable
fun ModalInputField(
    value: String,
    onValueChange: (String) -> Unit,
    placeHolder: String,
    maxLines: Int,
    isValid: Boolean,
    modifier: Modifier = Modifier,
    supportingMessage: String? = null,
    errorMessage: String? = null,
) {
    val valueColor = if (isValid) Color.Black else Red700
    val placeHolderColor = if (isValid) Gray950.copy(alpha = 0.5f) else Red700
    val text = errorMessage ?: supportingMessage.orEmpty()

    Column(modifier = modifier) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, RoundedCornerShape(10.dp))
                .border(
                    width = 1.dp,
                    color = valueColor,
                    shape = RoundedCornerShape(10.dp),
                ),
            value = value,
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedTextColor = valueColor,
                unfocusedTextColor = valueColor,
            ),
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    text = placeHolder,
                    color = placeHolderColor,
                )
            },
            maxLines = maxLines,
        )

        Text(
            modifier = Modifier.padding(
                top = 4.dp,
                start = 16.dp,
                end = 16.dp,
            ),
            text = text,
            fontSize = 12.sp,
            color = placeHolderColor,
        )
    }
}

@Preview
@Composable
private fun ModalInputFieldPreview() {
    Column {
        ModalInputField(
            value = "",
            onValueChange = {},
            placeHolder = "태그를 쉼표로 구분하여 입력하세요 (예: 버그,긴급)",
            maxLines = 1,
            isValid = true,
            supportingMessage = "",
            modifier = Modifier.background(Color.White),
        )
        ModalInputField(
            value = "",
            onValueChange = {},
            placeHolder = "이건,,,,올바르지 않은 형식입니다,,,,,,,,,",
            maxLines = 1,
            isValid = false,
            errorMessage = "에러 메시지",
            modifier = Modifier.background(Color.White),
        )
    }
}
