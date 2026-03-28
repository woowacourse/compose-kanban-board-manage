package woowacourse.kanban.board

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import woowacourse.kanban.board.domain.Project
import woowacourse.kanban.board.domain.Tasks
import woowacourse.kanban.board.ui.board.ProjectScreen
import woowacourse.kanban.board.ui.board.rememberProjectScreenState

@Preview(showBackground = true)
@Composable
fun App() {
    val projects = listOf(
        Project(
            name = "Compose1",
            tasks = Tasks(emptyList()),
        ),
        Project(
            name = "Compose2",
            tasks = Tasks(emptyList()),
        ),
        Project(
            name = "Compose3너무너무길다란이름",
            tasks = Tasks(emptyList()),
        ),
    )
    val authors = listOf("다이노", "페임스")

    val projectScreenState = rememberProjectScreenState(projects)

    ProjectScreen(
        state = projectScreenState,
        authors = authors,
        modifier = Modifier.fillMaxSize(),
    )
}
