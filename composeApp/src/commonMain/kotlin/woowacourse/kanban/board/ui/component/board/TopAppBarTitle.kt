package woowacourse.kanban.board.ui.component.board

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import woowacourse.kanban.board.ui.KanbanColors
import woowacourse.kanban.board.ui.KanbanTypography

@Composable
fun TopAppBarTitle(
    text: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = text,
        color = KanbanColors.textPrimary,
        style = KanbanTypography.title24Medium,
        modifier = modifier,
    )
}

@Preview(showBackground = true)
@Composable
private fun TopAppBarTitlePreview() {
    TopAppBarTitle(text = "Compose Desktop 칸반 보드")
}
