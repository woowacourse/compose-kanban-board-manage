package woowacourse.kanban.board.domain

data class Project(val name: String, val tasks: Tasks) {
    fun createNewTask(task: Task): Project {
        val newTasks = tasks.addTask(task)
        return copy(tasks = newTasks)
    }

    fun changeTaskState(taskIdx: Int, fixedTaskState: TaskState): Project {
        val task = tasks.items[taskIdx].copy(taskState = fixedTaskState)
        val newTasks = tasks.updateTask(task)
        return copy(tasks = newTasks)
    }
}
