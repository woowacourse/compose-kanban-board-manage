package woowacourse.kanban.board.domain

sealed class StatusChangeResult {
    data class Success(val project: KanbanProject) : StatusChangeResult()
    data object NotAssigned : StatusChangeResult()
    data object NotChangeable : StatusChangeResult()
    data object NotFound : StatusChangeResult()
}

sealed class TaskChangeResult {
    data class Success(val task: KanbanTask) : TaskChangeResult()
    data object NotAssigned : TaskChangeResult()
    data object NotChangeable : TaskChangeResult()
}

sealed class DeleteResult {
    data class Success(val project: KanbanProject) : DeleteResult()
    data object NotDeletable : DeleteResult()
    data object NotFound : DeleteResult()
}
