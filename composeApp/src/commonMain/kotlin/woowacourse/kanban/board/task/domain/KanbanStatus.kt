package woowacourse.kanban.board.task.domain

enum class KanbanStatus {
    TO_DO,
    IN_PROGRESS,
    REVIEW,
    DONE,
    ;

    fun canTransitionTo(next: KanbanStatus): Boolean = when (this) {
        TO_DO -> next == IN_PROGRESS
        IN_PROGRESS -> next == TO_DO || next == REVIEW
        REVIEW -> next == IN_PROGRESS || next == DONE
        DONE -> next == TO_DO
    }
}
