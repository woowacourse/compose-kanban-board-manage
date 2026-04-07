package woowacourse.kanban.board.domain.model

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class KanbanWorkspace(initialProject: List<KanbanProject> = emptyList()) {

    private val _projectTasks: MutableList<KanbanProject> = initialProject.toMutableList()
    val projectTasks: List<KanbanProject> get() = _projectTasks

    fun addTask(
        title: String,
        description: String,
        tags: List<String>,
        assignee: Assignee?,
        status: Status,
        projectId: String,
    ): KanbanResult<Unit> {
        val idx = _projectTasks.indexOfFirst { it.id == projectId }
        if (idx == -1) return KanbanResult.Failure(KanbanError.ProjectNotFound(projectId))

        val createResult = createTask(null, title, description, tags, assignee, status)
        if (createResult is KanbanResult.Failure) return createResult

        return when (val result = _projectTasks[idx].addTask((createResult as KanbanResult.Success).data)) {
            is KanbanResult.Success -> {
                _projectTasks[idx] = result.data
                KanbanResult.Success(Unit)
            }

            is KanbanResult.Failure -> result
        }
    }

    fun deleteTask(projectId: String, task: Task): KanbanResult<Unit> {
        if (!task.status.canDeleteTask) return KanbanResult.Failure(KanbanError.CannotDeleteTask(task.status))

        val idx = _projectTasks.indexOfFirst { it.id == projectId }
        if (idx == -1) return KanbanResult.Failure(KanbanError.ProjectNotFound(projectId))

        return when (val result = _projectTasks[idx].deleteTask(task.id)) {
            is KanbanResult.Success -> {
                _projectTasks[idx] = result.data
                KanbanResult.Success(Unit)
            }

            is KanbanResult.Failure -> result
        }
    }

    fun editTask(
        projectId: String,
        originTask: Task,
        newTitle: String,
        newDescription: String,
        newTags: List<String>,
        newAssignee: Assignee?,
        newStatus: Status,
    ): KanbanResult<Unit> {
        val idx = _projectTasks.indexOfFirst { it.id == projectId }
        if (idx == -1) return KanbanResult.Failure(KanbanError.ProjectNotFound(projectId))

        val createResult = createTask(originTask.id, newTitle, newDescription, newTags, newAssignee, newStatus)
        if (createResult is KanbanResult.Failure) return createResult

        return when (val result = _projectTasks[idx].editTask(originTask.id, (createResult as KanbanResult.Success).data)) {
            is KanbanResult.Success -> {
                _projectTasks[idx] = result.data
                KanbanResult.Success(Unit)
            }

            is KanbanResult.Failure -> result
        }
    }

    fun updateTaskStatus(projectId: String, task: Task, newStatus: Status): KanbanResult<Unit> {
        val idx = _projectTasks.indexOfFirst { it.id == projectId }
        if (idx == -1) return KanbanResult.Failure(KanbanError.ProjectNotFound(projectId))

        val validateTransition = task.status.validateTransition(newStatus, task.assignee != null)
        if (validateTransition is KanbanResult.Failure) return validateTransition

        return when (val result = _projectTasks[idx].updateStatus(task.id, newStatus)) {
            is KanbanResult.Success -> {
                _projectTasks[idx] = result.data
                KanbanResult.Success(Unit)
            }

            is KanbanResult.Failure -> result
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    private fun createTask(
        taskId: String?,
        title: String,
        description: String,
        tags: List<String>,
        assignee: Assignee?,
        status: Status,
    ): KanbanResult<Task> {
        return try {
            val task = Task(
                id = taskId ?: Uuid.random().toString(),
                title = title,
                description = description,
                tags = Tags(tags.map { Tag(it) }),
                assignee = assignee,
                status = status,
            )
            KanbanResult.Success(task)
        } catch (e: IllegalArgumentException) {
            KanbanResult.Failure(KanbanError.TaskCreationFailed(e.message ?: "태스크 생성에 실패했습니다."))
        }
    }
}
