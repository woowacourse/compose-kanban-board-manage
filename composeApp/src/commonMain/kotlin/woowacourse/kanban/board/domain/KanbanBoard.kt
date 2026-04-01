package woowacourse.kanban.board.domain

class KanbanBoard(tasks: List<KanbanTask> = emptyList()) {
    private val tasks = tasks.toMutableList()

    fun getTasks(kanbanIds: List<Long>): List<KanbanTask> = tasks.filter { kanbanIds.contains(it.id) }

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
        tasks.remove(originalTask)
        tasks.add(editedTask)
    }
}
