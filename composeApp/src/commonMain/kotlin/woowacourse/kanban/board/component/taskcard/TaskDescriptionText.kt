package woowacourse.kanban.board.component.taskcard

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import woowacourse.kanban.board.Gray30

@Composable
fun TaskDescriptionText(
    description: String,
    modifier: Modifier = Modifier,
) {
    if (description.isNotEmpty()) {
        Text(
            text = description,
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            color = Gray30,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = modifier,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DescriptionPreview() {
    TaskDescriptionText(
        description = "세로 스크롤 가능한 리스트 컴포넌트를 만들고 성능 최적화를 적용합니다." +
            "세로 스크롤 가능한 리스트 컴포넌트를 만들고 성능 최적화를 적용합니다." +
            "세로 스크롤 가능한 리스트 컴포넌트를 만들고 성능 최적화를 적용합니다.",
    )
}
