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
import woowacourse.kanban.board.domain.Tasks
import java.util.UUID

class ProjectScreenState(
    private val initialProjects: List<Project>,
) {
    var projects by mutableStateOf(initialProjects)
        private set

    var selectedProject by mutableStateOf(projects.first())
        private set

    fun selectProject(project: Project) {
        selectedProject = project
    }

    fun onTaskCreated(task: Task) {
        val newTasks = selectedProject.tasks.addTask(task)
        updateSelectedProjectTasks(newTasks)
    }

    fun onTaskStateChange(taskIdx: Int, fixedTaskState: TaskState) {
        val task = selectedProject.tasks.items[taskIdx].copy(taskState = fixedTaskState)
        val newTasks = selectedProject.tasks.fixStatus(task)
        updateSelectedProjectTasks(newTasks)
    }

    private fun updateSelectedProjectTasks(newTasks: Tasks) {
        val updatedProject = selectedProject.copy(tasks = newTasks)
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
fun rememberProjectScreenState(initialProjects: List<Project>): ProjectScreenState = rememberSaveable(initialProjects, saver = ProjectScreenState.Saver) {
    ProjectScreenState(initialProjects)
}
