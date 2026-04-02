package woowacourse.kanban.board.ui.board

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import woowacourse.kanban.board.domain.Project
import woowacourse.kanban.board.domain.Tasks
import woowacourse.kanban.board.ui.board.components.Sidebar

@Composable
fun ProjectScreen(state: ProjectScreenState, authors: List<String>, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
    ) {
        Sidebar(
            projects = state.projects,
            selectedProject = state.selectedProject,
            onProjectChange = { state.selectProject(it) },
            modifier = Modifier.width(255.dp),
        )

        Board(
            projectName = state.selectedProject.name,
            tasks = state.selectedProject.tasks,
            onTaskCreated = { state.onTaskCreated(it) },
            onTaskStateChange = { idx, taskState -> state.onTaskStateChange(idx, taskState) },
            authors = authors,
            onTaskDeleted = { state.selectedProject.tasks.deleteTask(it) },
            modifier = Modifier.size(width = 1295.dp, height = 909.dp),
        )
    }
}

@Preview
@Composable
private fun ProjectScreenPreview() {
    ProjectScreen(
        state = ProjectScreenState(
            initialProjects = listOf(
                Project(
                    name = "Compose1",
                    tasks = Tasks(emptyList()),
                ),
            ),
        ),
        authors = listOf("다이노", "페임스"),
        modifier = Modifier,
    )
}
