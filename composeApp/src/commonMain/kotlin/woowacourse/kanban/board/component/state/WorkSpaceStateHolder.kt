package woowacourse.kanban.board.component.state

import kotlinx.collections.immutable.toImmutableList
import woowacourse.kanban.board.model.project.Project
import woowacourse.kanban.board.model.taskcard.Status
import woowacourse.kanban.board.model.taskcard.TaskCard
import woowacourse.kanban.board.model.workspace.WorkSpace

data class WorkSpaceStateHolder(
    val workSpace: WorkSpace,
    val selectedProjectId: String? = workSpace.projects.firstOrNull()?.id,
) {
    val selectedProject: Project?
        get() = workSpace.projects.firstOrNull { it.id == selectedProjectId }

    fun selectProject(project: Project): WorkSpaceStateHolder = copy(selectedProjectId = project.id)

    fun addTask(task: TaskCard): WorkSpaceStateHolder =
        updateSelectedProject { project -> project.addCard(task) }

    fun updateTaskStatus(id: String, targetStatus: Status): WorkSpaceStateHolder =
        updateSelectedProject { project -> project.updateTaskStatus(id, targetStatus) }

    fun updateTask(id: String, task: TaskCard): WorkSpaceStateHolder =
        updateSelectedProject { project -> project.updateTask(id, task) }

    fun deleteTask(id: String): WorkSpaceStateHolder =
        updateSelectedProject { project -> project.deleteTask(id) }

    private fun updateSelectedProject(update: (Project) -> Project): WorkSpaceStateHolder {
        val targetProjectId = selectedProjectId ?: return this
        val index = workSpace.projects.indexOfFirst { it.id == targetProjectId }
        if (index == -1) return this

        val updatedProjects = workSpace.projects.toMutableList().apply {
            this[index] = update(this[index])
        }.toImmutableList()

        return copy(workSpace = workSpace.copy(projects = updatedProjects))
    }
}
