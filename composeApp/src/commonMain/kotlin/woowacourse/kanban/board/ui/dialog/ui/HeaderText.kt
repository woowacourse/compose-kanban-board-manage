package woowacourse.kanban.board.ui.dialog.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import woowacourse.kanban.Colors

@Composable
fun HeaderText(
    title: String,
    modifier: Modifier = Modifier,
) {
    Text(title, fontWeight = FontWeight.W500, fontSize = 14.sp, color = Colors.PrimaryText, modifier = modifier)
}
