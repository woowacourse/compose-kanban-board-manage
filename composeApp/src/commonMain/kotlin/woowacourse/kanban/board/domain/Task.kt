package woowacourse.kanban.board.domain

import java.util.UUID

data class Task(
    val id: UUID = UUID.randomUUID(),
    val title: String,
    val content: String = "",
    val tags: List<String> = emptyList(),
    val taskState: TaskState = TaskState.ToDo,
    val author: String = "다이노",
) {
    val isDeletable: Boolean
        get() = taskState.isDeletable
    val isEditable: Boolean
        get() = ((taskState != TaskState.ToDo) && author.isBlank()).not()

    fun transState(state: TaskState): Task = copy(taskState = taskState.transferTo(state))
    fun editTask(
        updatedTask: Task,
    ): Task {
        return if (isEditable) copy(
            title = updatedTask.title,
            content = updatedTask.content,
            tags = updatedTask.tags,
            author = updatedTask.author,
            taskState = updatedTask.taskState
        )
        else this
    }
}
