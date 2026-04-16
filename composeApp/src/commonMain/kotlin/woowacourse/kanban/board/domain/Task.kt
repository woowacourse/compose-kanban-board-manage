package woowacourse.kanban.board.domain

import java.util.UUID
import woowacourse.kanban.board.exception.TasksError

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
        get() = checkEditable(taskState, author)

    fun transState(state: TaskState): DomainResult<Task> {
        return when (val newStateResult = taskState.transferTo(state)) {
            is DomainResult.Success -> DomainResult.Success(copy(taskState = newStateResult.data))
            is DomainResult.Failure -> DomainResult.Failure(newStateResult.error)
        }
    }

    fun editTask(updatedTask: Task): DomainResult<Task> {
        if (checkNeedProfile(updatedTask.taskState) && updatedTask.author.isBlank()) return DomainResult.Failure(TasksError.INVALID_AUTHOR)
        return if (isEditable) DomainResult.Success(
            copy(
                title = updatedTask.title,
                content = updatedTask.content,
                tags = updatedTask.tags,
                author = updatedTask.author,
                taskState = updatedTask.taskState,
            ),
        )
        else DomainResult.Success(this)
    }

    companion object {
        fun checkEditable(taskState: TaskState, author: String) = ((taskState != TaskState.ToDo) && author.isBlank()).not()
        fun checkNeedProfile(taskState: TaskState) = taskState.isNeedProfile
    }
}
