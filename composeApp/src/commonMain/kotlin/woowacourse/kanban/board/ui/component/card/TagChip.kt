package woowacourse.kanban.board.ui.component.card

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TagChip(
    name: String,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .background(
                Color(0xFFF3F4F6),
                CircleShape,
            )
            .padding(vertical = 4.dp, horizontal = 8.dp),
    ) {
        Text(
            text = name,
            fontSize = 12.sp,
            color = Color(0xFF364153),
        )
    }
}

@Preview
@Composable
private fun TagChipPreview() {
    TagChip(name = "다섯글자입니다.")
}
