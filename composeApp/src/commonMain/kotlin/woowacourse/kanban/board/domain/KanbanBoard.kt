package woowacourse.kanban.board.domain

data class KanbanBoard(
    val title: String,
    val taskList: List<Task> = emptyList(),
) {
    fun totalStatusCount(): Int = taskList.size
    fun doneCount(): Int = taskList.count { it.status == Status.DONE }

    fun progress(): Float =
        if (totalStatusCount() == 0) 0f else (taskList.count { it.status == Status.DONE }).toFloat() / totalStatusCount()

    fun addTask(task: Task): KanbanBoard = copy(taskList = taskList + task)

    fun updateTask(updatedTask: Task): KanbanBoard {
        val currentTask = taskList.find { it.id == updatedTask.id } ?: return this
        val updatedTaskList = taskList.map { task ->
            if (task.id == updatedTask.id) {
                updatedTask.copy(status = currentTask.status)
            } else {
                task
            }
        }
        return copy(taskList = updatedTaskList)
    }

    fun deleteTask(deleteTask: Task): KanbanBoard {
        if (!deleteTask.canBeDeleted()) return this

        return copy(taskList = taskList.filterNot { task -> task.id == deleteTask.id })
    }

    fun moveTaskStatus(taskId: String, targetStatus: Status): KanbanBoard {
        val targetIndex = taskList.indexOfFirst { it.id == taskId }
        if (targetIndex == -1) return this

        val targetTask = taskList[targetIndex]
        if (targetTask.status == targetStatus) return this

        if (!targetTask.canMoveToWithAssigneeRule(targetStatus)) return this

        val updatedTaskList = taskList.map { task ->
            if (task.id == taskId) task.copy(status = targetStatus) else task
        }

        return copy(taskList = updatedTaskList)
    }

    fun getStatusTask(status: Status): List<Task> = taskList.filter { it.status == status }
}
