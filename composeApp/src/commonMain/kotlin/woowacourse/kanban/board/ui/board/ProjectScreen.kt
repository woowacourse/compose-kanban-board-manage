package woowacourse.kanban.board.ui.board

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.util.UUID
import woowacourse.kanban.board.domain.Task
import woowacourse.kanban.board.domain.TaskState
import woowacourse.kanban.board.ui.board.components.Sidebar
import woowacourse.kanban.board.ui.board.state.ProjectsStateHolder

@Composable
fun ProjectScreen(
    stateHolder: ProjectsStateHolder,
    onProjectChange: (UUID) -> Unit,
    onTaskCreated: (Task) -> Unit,
    onTaskUpdated: (UUID, Task) -> Unit,
    onTaskDeleted: (UUID) -> Unit,
    onTaskStateChange: (UUID, TaskState) -> Unit,
    modifier: Modifier = Modifier,
) {
    val projects = stateHolder.projects
    val selectedProject = stateHolder.selectedProject

    Row(
        modifier = modifier,
    ) {
        Sidebar(
            projects = projects,
            selectedProject = stateHolder.selectedProject,
            onProjectChange = { onProjectChange(it.id) },
            modifier = Modifier.width(255.dp),
        )

        if (selectedProject != null) {
            Board(
                project = selectedProject,
                onTaskCreated = onTaskCreated,
                onTaskUpdated = onTaskUpdated,
                onTaskDeleted = onTaskDeleted,
                onTaskStateChange = onTaskStateChange,
                modifier = Modifier.size(width = 1295.dp, height = 909.dp),
            )
        }
    }
}

@Preview
@Composable
private fun ProjectScreenPreview() {
    ProjectScreen(
        stateHolder = remember { ProjectsStateHolder() },
        onProjectChange = { },
        onTaskCreated = { _ -> },
        onTaskUpdated = { _, _ -> },
        onTaskDeleted = { _ -> },
        onTaskStateChange = { _, _ -> },
    )
}
