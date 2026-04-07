package woowacourse.kanban.board.ui.dialog.ui.createTextInput

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import woowacourse.kanban.board.ui.dialog.ui.HeaderText

@Composable
fun CreateTextInput(
    title: String,
    placeHolder: String,
    height: Dp,
    value: String,
    onChangeValue: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeHolderAlignment: Alignment = Alignment.CenterStart,
    hintText: String = "",
    isError: Boolean = false,
    errorHintText: String = "태그 형식이 올바르지 않습니다.",
) {
    Column(modifier = modifier) {
        HeaderText(title = title)
        Spacer(modifier = Modifier.height(8.dp))
        CreateTextField(
            value = value,
            onChangeValue = onChangeValue,
            isError = isError,
            height = height,
            placeHolder = placeHolder,
            placeHolderAlignment = placeHolderAlignment,
        )

        if (hintText.isNotBlank()) {
            Spacer(modifier = Modifier.height(4.dp))
            HintText(
                hintText =
                if (isError) errorHintText
                else hintText,
                isErrorText = isError,
            )
        }
    }
}
