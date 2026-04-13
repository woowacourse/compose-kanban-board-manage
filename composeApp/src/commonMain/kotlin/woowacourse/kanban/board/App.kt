package woowacourse.kanban.board

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import woowacourse.kanban.board.ui.board.ProjectScreen
import woowacourse.kanban.board.ui.board.state.ProjectState
import woowacourse.kanban.board.ui.board.state.ProjectsStateHolder

@Preview(showBackground = true)
@Composable
fun App() {
    val projects = listOf(
        ProjectState(name = "Compose1"),
        ProjectState(name = "Compose2"),
        ProjectState(name = "Compose3너무너무길다란이름"),
    )

    val stateHolder = remember { ProjectsStateHolder(projects) }

    ProjectScreen(
        stateHolder = stateHolder,
        onProjectChange = { projectId -> stateHolder.selectProject(projectId) },
        onTaskCreated = { task -> stateHolder.selectedProject?.addTask(task) },
        onTaskUpdated = { taskId, task ->
            stateHolder.selectedProject?.updateTask(taskId, task)
        },
        onTaskDeleted = { taskId ->
            stateHolder.selectedProject?.deleteTask(taskId)
        },
        onTaskStateChange = { taskId, taskState ->
            stateHolder.selectedProject?.changeTaskState(taskId, taskState)
        },
        modifier = Modifier.fillMaxSize(),
    )
}
