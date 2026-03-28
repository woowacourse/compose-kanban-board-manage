package woowacourse.kanban.board.domain

class KanbanProject(
    val title: String,
    taskIds: List<Long> = emptyList(),
) {
    private val taskIds = taskIds.toMutableList()

    fun getTaskIds(): List<Long> = taskIds

    fun addTaskId(taskId: Long) {
        taskIds.add(taskId)
    }
}
