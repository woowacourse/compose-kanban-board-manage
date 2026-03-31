package woowacourse.kanban.board.ui.dialog.ui.createTextInput

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import woowacourse.kanban.Colors

@Composable
fun HintText(
    hintText: String,
    modifier: Modifier = Modifier,
    isErrorText: Boolean = false,
) {
    Text(
        hintText,
        modifier = modifier.padding(horizontal = 16.dp),
        fontWeight = FontWeight.W400,
        fontSize = 12.sp,
        color = if (isErrorText) Colors.TextFieldError else Colors.TextFieldHint,
    )
}
