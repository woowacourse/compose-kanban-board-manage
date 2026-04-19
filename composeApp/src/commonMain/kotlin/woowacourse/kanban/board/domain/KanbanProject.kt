package woowacourse.kanban.board.domain

import woowacourse.kanban.board.domain.dialog.Status
import woowacourse.kanban.board.domain.result.ProjectResult
import woowacourse.kanban.board.domain.result.TaskResult

data class KanbanProject(
    val title: String,
    private val tasks: List<KanbanTask> = emptyList(),
) {
    private val _tasks = tasks.map { it.copy() }

    fun getTasks(): List<KanbanTask> = _tasks

    fun getTaskById(taskId: Long): KanbanTask = _tasks.first { it.id == taskId }

    fun addTask(task: KanbanTask): KanbanProject {
        return copy(tasks = _tasks + task)
    }

    fun deleteTask(taskId: Long): KanbanProject {
        return copy(tasks = _tasks.filterNot { it.id == taskId })
    }

    fun editTask(task: KanbanTask): ProjectResult<EditError> {
        val beforeTask = getTaskById(task.id)

        return when (val result = beforeTask.editTask(task)) {
            is TaskResult.Success -> ProjectResult.Success(copy(tasks = _tasks.map { if (it.id == task.id) result.task else it }))
            is TaskResult.Failed -> ProjectResult.Failed(result.error)
        }
    }

    fun changeTaskStatus(
        task: KanbanTask,
        newStatus: Status,
    ): ProjectResult<EditError> {
        return when (val result = task.changeStatus(newStatus = newStatus)) {
            is TaskResult.Success -> {
                ProjectResult.Success(
                    copy(tasks = _tasks.map { if (it.id == task.id) result.task else it }),
                )
            }
            is TaskResult.Failed -> {
                ProjectResult.Failed(result.error)
            }
        }
    }

    fun getTasksByStatus(status: Status): List<KanbanTask> = _tasks.filter { it.status == status }

    fun getCompleteCount() = _tasks.count { it.status == Status.DONE }

    fun getTotalCount() = _tasks.size

    fun getCompleteRatio(): Float {
        val total = getTotalCount()
        return if (total == 0) 0f else getCompleteCount().toFloat() / total
    }
}
