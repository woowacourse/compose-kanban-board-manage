package woowacourse.kanban.board.ui.taskcard.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import woowacourse.kanban.board.ui.theme.TextSecondary

@Composable
fun LabelText(label: String, modifier: Modifier = Modifier) {
    Text(
        modifier = modifier,
        text = label,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp,
        color = TextSecondary,
    )
}
