package woowacourse.kanban.board.task.domain

sealed interface KanbanCardResult {
    data class Success(val card: KanbanCard) : KanbanCardResult
    sealed interface Failure : KanbanCardResult {
        val error: KanbanError

        data class InvalidTransition(val status: KanbanStatus, val toStatus: KanbanStatus) : Failure {
            override val error: KanbanError
                get() = KanbanError.INVALID_TRANSITION
        }

        data class AssigneeRequired(val status: KanbanStatus) : Failure {
            override val error: KanbanError
                get() = KanbanError.ASSIGNEE_REQUIRED
        }
    }
}

sealed interface KanbanBoardResult {
    data class Success(val board: KanbanBoard) : KanbanBoardResult
    sealed interface Failure : KanbanBoardResult {
        val error: KanbanError

        data class NotFound(val type: String, val id: String) : Failure {
            override val error: KanbanError
                get() = KanbanError.KANBAN_NOT_FOUND
        }

        data class InvalidTransition(val status: KanbanStatus, val toStatus: KanbanStatus) : Failure {
            override val error: KanbanError
                get() = KanbanError.INVALID_TRANSITION
        }

        data class AssigneeRequired(val status: KanbanStatus) : Failure {
            override val error: KanbanError
                get() = KanbanError.ASSIGNEE_REQUIRED
        }

        data class DeletionNotAllowed(val status: KanbanStatus) : Failure {
            override val error: KanbanError
                get() = KanbanError.DELETION_NOT_ALLOWED
        }
    }
}

sealed interface KanbanProjectResult {
    data class Success(val project: KanbanProject) : KanbanProjectResult
    sealed interface Failure : KanbanProjectResult {
        val error: KanbanError

        data class NotFound(val type: String, val id: String) : Failure {
            override val error: KanbanError
                get() = KanbanError.KANBAN_NOT_FOUND
        }

        data class InvalidTransition(val status: KanbanStatus, val toStatus: KanbanStatus) : Failure {
            override val error: KanbanError
                get() = KanbanError.INVALID_TRANSITION
        }

        data class AssigneeRequired(val status: KanbanStatus) : Failure {
            override val error: KanbanError
                get() = KanbanError.ASSIGNEE_REQUIRED
        }

        data class DeletionNotAllowed(val status: KanbanStatus) : Failure {
            override val error: KanbanError
                get() = KanbanError.DELETION_NOT_ALLOWED
        }
    }
}
