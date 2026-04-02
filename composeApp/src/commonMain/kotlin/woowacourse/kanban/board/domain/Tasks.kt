package woowacourse.kanban.board.domain

import woowacourse.kanban.board.domain.TaskState.Companion.isCorrectStateChange
import woowacourse.kanban.board.exception.TransStateError
import woowacourse.kanban.board.exception.TransStateException

data class Tasks(private val tasks: List<Task> = emptyList()) {
    val items: List<Task> get() = tasks
    val totalCount: Int = tasks.size
    fun countByState(taskState: TaskState): Int = tasks.count { it.taskState == taskState }
    fun completedRate(): Int = if (tasks.isEmpty()) 0 else (countByState(TaskState.DONE).toDouble() / tasks.size * 100).toInt()
    fun getTasksByState(taskState: TaskState): List<Task> = tasks.filter { it.taskState == taskState }

    fun updateTask(updatedTask: Task): Tasks {
        val newTasks = tasks.map { task ->
            if (task.id == updatedTask.id) {
                checkCorrectStateChange(task, updatedTask)
                updatedTask
            } else task
        }
        return copy(tasks = newTasks)
    }

    fun addTask(task: Task): Tasks = copy(tasks = tasks + task)

    fun deleteTask(task: Task): Tasks {
        if (tasks.contains(task).not()) return copy(tasks = tasks)
        return copy(tasks = tasks - task)
    }

    private fun checkCorrectStateChange(task: Task, updatedTask: Task) {
        if (!isCorrectStateChange(task.taskState, updatedTask.taskState)) throw TransStateException(TransStateError.CANT_TRANSFER)
    }
}
