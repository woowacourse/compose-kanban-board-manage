package woowacourse.kanban.board.domain

import java.util.UUID

data class Project(val name: String, val tasks: Tasks) {
    fun createNewTask(task: Task): Project {
        val newTasks = tasks.addTask(task)
        return copy(tasks = newTasks)
    }

    fun changeTaskState(taskId: UUID, fixedTaskState: TaskState): Project {
        val task = tasks.items.firstOrNull { it.id == taskId } ?: return this
        val updatedTask = task.copy(taskState = fixedTaskState)
        return copy(tasks = tasks.updateTask(updatedTask))
    }
}
