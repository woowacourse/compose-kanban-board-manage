package woowacourse.kanban.board.ui.board

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import woowacourse.kanban.board.domain.Project
import woowacourse.kanban.board.domain.Task
import woowacourse.kanban.board.domain.TaskState

class ProjectScreenState(private val initialProjects: List<Project>) {
    var projects by mutableStateOf(initialProjects)
        private set

    var selectedProject by mutableStateOf(projects.first())
        private set

    fun selectProject(project: Project) {
        selectedProject = project
    }

    fun onTaskCreated(task: Task) = updateSelectedProject(selectedProject.createNewTask(task))

    fun onTaskStateChange(taskIdx: Int, fixedTaskState: TaskState) =
        updateSelectedProject(selectedProject.changeTaskState(taskIdx, fixedTaskState))

    private fun updateSelectedProject(updatedProject: Project) {
        selectedProject = updatedProject
        projects = projects.map {
            if (it.name == updatedProject.name) updatedProject else it
        }
    }

    companion object {
        val Saver: Saver<ProjectScreenState, *> = listSaver(
            save = { listOf(it.projects) },
            restore = {
                ProjectScreenState(
                    initialProjects = it[0],
                )
            },
        )
    }
}

@Composable
fun rememberProjectScreenState(initialProjects: List<Project>): ProjectScreenState =
    rememberSaveable(initialProjects, saver = ProjectScreenState.Saver) {
        ProjectScreenState(initialProjects)
    }
