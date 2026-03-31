package woowacourse.kanban.board.ui.dialog.ui.createTextInput

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import woowacourse.kanban.Colors

@Composable
fun CreateTextField(
    value: String,
    onChangeValue: (String) -> Unit,
    isError: Boolean,
    height: Dp,
    placeHolder: String,
    placeHolderAlignment: Alignment,
    modifier: Modifier = Modifier,
    errorPlaceholder: String = "이건,,,,올바르지 않은 형식입니다,,,,,,,,,",
) {
    BasicTextField(
        value = value,
        onValueChange = onChangeValue,
        modifier = modifier.fillMaxWidth()
            .border(
                width = if (isError) 2.dp else 1.dp,
                color = if (isError) Colors.TextFieldError else Colors.TextFieldBorder,
                shape = RoundedCornerShape(4.dp),
            )
            .heightIn(min = height)
            .padding(
                start = 16.dp,
                top = 4.dp,
                bottom = 4.dp,
            ),
        textStyle = TextStyle.Default.copy(
            fontSize = 16.sp,
            fontWeight = FontWeight.W400,
        ),
        decorationBox = { innerTextField ->
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = placeHolderAlignment,
            ) {
                if (value.isEmpty()) {
                    if (isError) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            Text(
                                text = errorPlaceholder,
                                fontWeight = FontWeight.W400,
                                fontSize = 16.sp,
                                color = Colors.TextFieldError,
                            )
                            Box(
                                modifier = Modifier.size(48.dp),
                                contentAlignment = Alignment.Center,
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Error,
                                    contentDescription = "오류 아이콘",
                                    tint = Colors.TextFieldError,
                                    modifier = Modifier.size(20.dp),
                                )
                            }
                        }
                    } else {
                        Text(
                            placeHolder,
                            fontWeight = FontWeight.W400,
                            fontSize = 16.sp,
                            color = Colors.TextFieldPlaceholder,
                        )
                    }
                } else {
                    innerTextField()
                }
            }
        },
    )
}
