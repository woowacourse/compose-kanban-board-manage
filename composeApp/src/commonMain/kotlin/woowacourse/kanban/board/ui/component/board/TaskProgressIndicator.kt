package woowacourse.kanban.board.ui.component.board

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TaskProgressIndicator(
    progress: Float,
    modifier: Modifier = Modifier,
) {
    LinearProgressIndicator(
        progress = { progress },
        color = Color(0xFF4F39F6),
        trackColor = Color(0xFFE5E7EB),
        strokeCap = StrokeCap.Square,
        gapSize = 0.dp,
        drawStopIndicator = { },
        modifier = modifier
            .fillMaxWidth()
            .clip(CircleShape),
    )
}

@Preview(showBackground = true)
@Composable
private fun TaskProgressIndicatorPreview() {
    TaskProgressIndicator(
        progress = 0.3f,
    )
}
