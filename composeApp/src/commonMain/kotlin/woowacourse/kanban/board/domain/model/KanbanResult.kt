package woowacourse.kanban.board.domain.model

sealed interface KanbanResult<out T> {
    data class Success<out T>(val data: T) : KanbanResult<T>
    data class Failure(val error: KanbanError) : KanbanResult<Nothing>
}

sealed interface KanbanError {
    data class TaskCreationFailed(val message: String) : KanbanError
    data class ProjectNotFound(val projectId: String) : KanbanError
    data class TaskNotFound(val taskId: String) : KanbanError
    data class CannotDeleteTask(val status: Status) : KanbanError
    data class InvalidStatusTransition(val from: Status, val to: Status) : KanbanError
    data class AssigneeRequired(val status: Status) : KanbanError
}
