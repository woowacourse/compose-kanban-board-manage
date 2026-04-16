package woowacourse.kanban.board.ui.board

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import woowacourse.kanban.board.domain.DomainResult
import java.util.UUID
import woowacourse.kanban.board.domain.Project
import woowacourse.kanban.board.domain.Task
import woowacourse.kanban.board.domain.TaskState

class ProjectScreenState(private val initialProjects: List<Project>) {
    var projects by mutableStateOf(initialProjects)
        private set

    var selectedProject by mutableStateOf(projects.first())
        private set

    var updatingTask by mutableStateOf<Task?>(null)
        private set

    var openUpdateDialog by mutableStateOf(false)

    fun selectProject(project: Project) {
        selectedProject = project
    }

    fun onTaskCreated(task: Task): DomainResult<Project> {
        val updatedProject = selectedProject.createNewTask(task)
        return updateSelectedProject(DomainResult.Success(updatedProject))
    }

    fun onTaskStateChange(taskId: UUID, fixedTaskState: TaskState): DomainResult<Project> =
        updateSelectedProject(selectedProject.changeTaskState(taskId, fixedTaskState))

    fun onTaskUpdated(task: Task): DomainResult<Project> =
        updateSelectedProject(selectedProject.updateTask(task))

    fun onTaskDeleted(task: Task): DomainResult<Project> =
        updateSelectedProject(selectedProject.deleteTask(task))

    fun onClickCard(task: Task) {
        updatingTask = task
        openUpdateDialog = true
    }

    fun closeUpdateDialog() {
        openUpdateDialog = false
        updatingTask = null
    }

    private fun updateSelectedProject(result: DomainResult<Project>): DomainResult<Project> {
        if (result is DomainResult.Success) {
            val updatedProject = result.data
            selectedProject = updatedProject
            projects = projects.map {
                if (it.name == updatedProject.name) updatedProject else it
            }
        }
        return result
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
