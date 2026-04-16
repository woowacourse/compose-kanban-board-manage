package woowacourse.kanban.board.domain

import java.util.UUID

data class Project(val name: String, val tasks: Tasks) {
    fun createNewTask(task: Task): Project {
        val newTasks = tasks.addTask(task)
        return copy(tasks = newTasks)
    }

    fun changeTaskState(taskId: UUID, fixedTaskState: TaskState): DomainResult<Project> {
        val task = tasks.items.firstOrNull { it.id == taskId } ?: return DomainResult.Success(this)
        return when (val updatedTaskResult = task.transState(fixedTaskState)) {
            is DomainResult.Success -> updateTask(updatedTaskResult.data)
            is DomainResult.Failure -> DomainResult.Failure(updatedTaskResult.error)
        }
    }

    fun updateTask(task: Task): DomainResult<Project> {
        return when (val result = tasks.updateTask(task)) {
            is DomainResult.Success -> DomainResult.Success(copy(tasks = result.data))
            is DomainResult.Failure -> DomainResult.Failure(result.error)
        }
    }

    fun deleteTask(task: Task): DomainResult<Project> {
        return when (val result = tasks.deleteTask(task)) {
            is DomainResult.Success -> DomainResult.Success(copy(tasks = result.data))
            is DomainResult.Failure -> DomainResult.Failure(result.error)
        }
    }
}
