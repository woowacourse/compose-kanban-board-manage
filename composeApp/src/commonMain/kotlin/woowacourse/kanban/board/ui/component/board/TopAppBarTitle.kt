package woowacourse.kanban.board.ui.component.board

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Composable
fun TopAppBarTitle(
    text: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = text,
        fontSize = 24.sp,
        fontWeight = FontWeight.Medium,
        modifier = modifier,
    )
}

@Preview(showBackground = true)
@Composable
private fun TopAppBarTitlePreview() {
    TopAppBarTitle(text = "Compose Desktop 칸반 보드")
}
