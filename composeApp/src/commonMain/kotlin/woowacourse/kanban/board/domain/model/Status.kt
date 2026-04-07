package woowacourse.kanban.board.domain.model

enum class Status(val requiredAssignee: Boolean, val canDeleteTask: Boolean) {
    TODO(false, true),
    IN_PROGRESS(true, true),
    REVIEW(true, false),
    DONE(true, false),
    ;

    private val movableTo: List<Status> by lazy {
        when (this) {
            TODO -> listOf(TODO, IN_PROGRESS)
            IN_PROGRESS -> listOf(TODO, REVIEW)
            REVIEW -> listOf(IN_PROGRESS, DONE)
            DONE -> listOf(TODO)
        }
    }

    fun validateTransition(to: Status, hasAssignee: Boolean): KanbanResult<Unit> {
        if (to !in this.movableTo) return KanbanResult.Failure(KanbanError.InvalidStatusTransition(this, to))
        if (to.requiredAssignee && !hasAssignee) return KanbanResult.Failure(KanbanError.AssigneeRequired(this))
        return KanbanResult.Success(Unit)
    }
}
