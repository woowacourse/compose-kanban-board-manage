package woowacourse.kanban.board.domain

import java.util.UUID

data class Task(
    val id: UUID = UUID.randomUUID(),
    val title: String,
    val content: String = "",
    val tags: List<String> = emptyList(),
    val taskState: TaskState,
    val author: Author,
) {
    fun changeTaskState(taskState: TaskState): Task = copy(taskState = taskState)

    fun canChangeTaskState(taskState: TaskState): Boolean {
        return !(taskState != TaskState.TO_DO && author == Author.NONE)
    }
}
