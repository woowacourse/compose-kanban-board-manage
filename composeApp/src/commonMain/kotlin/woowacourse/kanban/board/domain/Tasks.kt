package woowacourse.kanban.board.domain

import woowacourse.kanban.board.exception.TasksError

data class Tasks(private val tasks: List<Task> = emptyList()) {
    val items: List<Task> get() = tasks
    val totalCount: Int = tasks.size
    fun countByState(taskState: TaskState): Int = tasks.count { it.taskState == taskState }
    fun completedRate(): Int = if (tasks.isEmpty()) 0 else (countByState(TaskState.Done).toDouble() / tasks.size * 100).toInt()
    fun getTasksByState(taskState: TaskState): List<Task> = tasks.filter { it.taskState == taskState }

    fun updateTask(updatedTask: Task): DomainResult<Tasks> {
        var error: TasksError? = null
        val newTasks = tasks.map { task ->
            if (task.id == updatedTask.id) {
                when (val result = task.editTask(updatedTask)) {
                    is DomainResult.Success -> result.data
                    is DomainResult.Failure -> {
                        error = result.error
                        task
                    }
                }
            } else task
        }
        return error?.let { DomainResult.Failure(it) } ?: DomainResult.Success(copy(tasks = newTasks))
    }

    fun addTask(task: Task): Tasks = copy(tasks = tasks + task)

    fun deleteTask(task: Task): DomainResult<Tasks> {
        if (tasks.contains(task).not()) return DomainResult.Success(this)
        if (task.isDeletable.not()) return DomainResult.Failure(TasksError.INVALID_DELETE)
        return DomainResult.Success(copy(tasks = tasks - task))
    }
}
