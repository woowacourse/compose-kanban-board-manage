package woowacourse.kanban.board.ui.board

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kanbanboard.composeapp.generated.resources.Res
import kanbanboard.composeapp.generated.resources.snackbar_change_task_status
import kanbanboard.composeapp.generated.resources.snackbar_create_new_task
import kanbanboard.composeapp.generated.resources.snackbar_delete_task
import kanbanboard.composeapp.generated.resources.snackbar_edit_task
import woowacourse.kanban.board.domain.model.Assignee
import woowacourse.kanban.board.domain.model.KanbanProject
import woowacourse.kanban.board.domain.model.KanbanResult
import woowacourse.kanban.board.domain.model.KanbanWorkspace
import woowacourse.kanban.board.domain.model.Status
import woowacourse.kanban.board.domain.model.Task
import woowacourse.kanban.board.ui.util.SnackBarEvent
import woowacourse.kanban.board.ui.util.toMessage

class ProjectStateHolder(initialProjects: List<KanbanProject> = emptyList()) {
    private val workspace = KanbanWorkspace(initialProjects)

    var projects: List<KanbanProject> by mutableStateOf(workspace.projectTasks)
    var currentProjectId: String by mutableStateOf(projects.first().id)
        private set

    val currentProject: KanbanProject get() = projects.first { it.id == currentProjectId }
    var snackBarEvent: SnackBarEvent? by mutableStateOf(null)
        private set

    fun changeProject(projectId: String) {
        currentProjectId = projectId
    }

    var selectedTask by mutableStateOf<Task?>(null)

    fun addTask(title: String, description: String, tags: List<String>, assignee: Assignee?, status: Status) {
        val result = workspace.addTask(
            title = title,
            description = description,
            tags = tags,
            assignee = assignee,
            status = status,
            projectId = currentProject.id,
        )
        snackBarEvent = when (result) {
            is KanbanResult.Success -> {
                projects = workspace.projectTasks
                SnackBarEvent(strRes = Res.string.snackbar_create_new_task)
            }

            is KanbanResult.Failure -> {
                SnackBarEvent(
                    strRes = result.error.toMessage(),
                )
            }
        }
    }

    fun editTask(title: String, description: String, tags: List<String>, assignee: Assignee?, status: Status) {
        val target = selectedTask ?: return

        val result = workspace.editTask(
            currentProject.id, target, title,
            description, tags, assignee, status,
        )
        snackBarEvent = when (result) {
            is KanbanResult.Success -> {
                selectedTask = null
                projects = workspace.projectTasks
                SnackBarEvent(strRes = Res.string.snackbar_edit_task)
            }

            is KanbanResult.Failure -> {
                SnackBarEvent(
                    strRes = result.error.toMessage(),
                )
            }
        }
    }

    fun deleteTask() {
        val target = selectedTask ?: return
        val result = workspace.deleteTask(currentProject.id, target)

        snackBarEvent = when (result) {
            is KanbanResult.Success -> {
                selectedTask = null
                projects = workspace.projectTasks
                SnackBarEvent(strRes = Res.string.snackbar_delete_task)
            }

            is KanbanResult.Failure -> {
                SnackBarEvent(
                    strRes = result.error.toMessage(),
                )
            }
        }
    }

    fun changeTaskStatus(task: Task, newStatus: Status) {
        val result = workspace.updateTaskStatus(currentProject.id, task, newStatus)

        snackBarEvent = when (result) {
            is KanbanResult.Success -> {
                selectedTask = null
                projects = workspace.projectTasks
                SnackBarEvent(strRes = Res.string.snackbar_change_task_status)
            }

            is KanbanResult.Failure -> {
                SnackBarEvent(
                    strRes = result.error.toMessage(),
                )
            }
        }
    }

    fun clearSelectedTask() {
        selectedTask = null
    }
}
