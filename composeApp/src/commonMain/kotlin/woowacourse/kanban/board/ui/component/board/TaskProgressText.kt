package woowacourse.kanban.board.ui.component.board

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import kanbanboard.composeapp.generated.resources.Res
import kanbanboard.composeapp.generated.resources.completion_rate
import org.jetbrains.compose.resources.stringResource

@Composable
fun TaskProgressText(
    progressPercent: Int,
    completeCount: Int,
    totalCount: Int,
    modifier: Modifier = Modifier,
) {
    Text(
        text = stringResource(
            Res.string.completion_rate,
            progressPercent,
            completeCount,
            totalCount,
        ),
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal,
        color = Color(0xFF6A7282),
        modifier = modifier,
    )
}

@Preview(showBackground = true)
@Composable
private fun TaskProgressTextPreview() {
    TaskProgressText(
        progressPercent = 50,
        completeCount = 3,
        totalCount = 6,
    )
}
