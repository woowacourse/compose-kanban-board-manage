package woowacourse.kanban.board

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import woowacourse.kanban.board.domain.model.KanbanProject
import woowacourse.kanban.board.ui.board.KanbanBoardScreen
import woowacourse.kanban.board.ui.board.ProjectStateHolder

@Composable
@Preview
fun App() {
    val projectStateHolder = remember {
        ProjectStateHolder(
            initialProjects = listOf(KanbanProject(name = "project1"), KanbanProject(name = "project2")),
        )
    }
    MaterialTheme {
        KanbanBoardScreen(
            projectStateHolder,
        )
    }
}
