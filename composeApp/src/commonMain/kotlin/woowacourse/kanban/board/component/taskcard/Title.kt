package woowacourse.kanban.board.component.taskcard

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import woowacourse.kanban.board.component.util.Gray10

@Composable
fun Title(
    title: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = title,
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        color = Gray10,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        modifier = modifier,
    )
}

@Preview(showBackground = true)
@Composable
private fun TitlePreview() {
    Title(
        title = "너 무 긴 제 목너 무 긴 제 목너 무 긴 제 목너 무 긴 제 목너 무 긴 제 목너 무 긴 제 목너 무 긴 제 목너 무 긴 제 목너 무 긴 제 목",
    )
}
