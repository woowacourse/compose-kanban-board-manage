package woowacourse.kanban.board

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import woowacourse.kanban.board.domain.KanbanProject
import woowacourse.kanban.board.ui.screen.board.KanbanBoardScreen

@Composable
@Preview
fun App() {
    MaterialTheme {
        KanbanBoardScreen(
            projects = listOf(
                KanbanProject("Compose1"),
                KanbanProject("Compose2"),
                KanbanProject("Compose3너무너무길어요너무너무길어요너무너무길어요너무너무길어요너무너무길어요"),
            ),
        )
    }
}
