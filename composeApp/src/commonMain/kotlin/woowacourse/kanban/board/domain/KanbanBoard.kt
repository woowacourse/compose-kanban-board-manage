package woowacourse.kanban.board.domain

class KanbanBoard(tasks: List<KanbanTask> = emptyList()) {
    private val tasks = tasks.toMutableList()

    fun getAllTasks() = tasks.toList()

    fun getTasksByIds(kanbanIds: List<Long>): List<KanbanTask> = tasks.filter { kanbanIds.contains(it.id) }

    fun addTask(task: KanbanTask) {
        tasks.add(task)
    }

    fun changeTaskStatus(
        task: KanbanTask,
        newStatus: Status,
    ) {
        val newTask = task.changeStatus(newStatus = newStatus)

        tasks.remove(task)
        tasks.add(newTask)
    }

    fun deleteTask(task: KanbanTask) {
        tasks.remove(task)
    }

    fun editTask(originalTask: KanbanTask, editedTask: KanbanTask) {
        val index = tasks.indexOfFirst { it.id == originalTask.id }
        if (index == -1) return

        tasks[index] = editedTask.copy(id = originalTask.id)
    }
}
