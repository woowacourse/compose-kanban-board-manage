package woowacourse.kanban.board.component.dialog.input

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import woowacourse.kanban.board.theme.HEADER_TEXT

@Composable
fun HeaderText(title: String, modifier: Modifier = Modifier) {
    Text(title, fontWeight = FontWeight.W500, fontSize = 14.sp, color = Color(HEADER_TEXT), modifier = modifier)
}

@Preview(showBackground = true)
@Composable
private fun StatusHeaderTextPreview() {
    HeaderText(title = "상태")
}
