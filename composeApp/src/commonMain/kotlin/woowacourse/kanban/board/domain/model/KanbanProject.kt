package woowacourse.kanban.board.domain.model

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
data class KanbanProject(val id: String = Uuid.random().toString(), val name: String, val tasks: List<Task> = emptyList()) {
    val totalCount: Int get() = tasks.size
    val completeCount: Int get() = tasks.count { it.status == Status.DONE }
    val completeRatio: Float get() = if (totalCount == 0) 0f else completeCount.toFloat() / totalCount.toFloat()

    fun addTask(newTask: Task): KanbanResult<KanbanProject> {
        return KanbanResult.Success(this.copy(tasks = tasks + newTask))
    }

    fun deleteTask(taskId: String): KanbanResult<KanbanProject> {
        if (tasks.none { it.id == taskId }) return KanbanResult.Failure(KanbanError.TaskNotFound(taskId))

        return KanbanResult.Success(this.copy(tasks = tasks.filter { it.id != taskId }))
    }

    fun editTask(taskId: String, newTask: Task): KanbanResult<KanbanProject> {
        if (tasks.none { it.id == taskId }) return KanbanResult.Failure(KanbanError.TaskNotFound(taskId))
        val updatedTasks = tasks.map { if (it.id == taskId) newTask else it }

        return KanbanResult.Success(this.copy(tasks = updatedTasks))
    }

    fun updateStatus(taskId: String, newStatus: Status): KanbanResult<KanbanProject> {
        if (tasks.none { it.id == taskId }) return KanbanResult.Failure(KanbanError.TaskNotFound(taskId))

        val updatedTasks = tasks.map { if (it.id == taskId) it.copy(status = newStatus) else it }

        return KanbanResult.Success(this.copy(tasks = updatedTasks))
    }
}
