package woowacourse.kanban.board.task.domain

enum class KanbanStatus(val isDeletable: Boolean, val isAssigneeRequired: Boolean) {
    TO_DO(isDeletable = true, isAssigneeRequired = false),
    IN_PROGRESS(isDeletable = true, isAssigneeRequired = true),
    REVIEW(isDeletable = false, isAssigneeRequired = true),
    DONE(isDeletable = false, isAssigneeRequired = true),
    ;

    fun isTransitionStatus(toStatus: KanbanStatus): Boolean {
        val canTransitionStatus = TRANSITION_RULES[this] ?: emptyList()
        return canTransitionStatus.contains(toStatus)
    }

    companion object {
        private val TRANSITION_RULES = mapOf(
            TO_DO to listOf(TO_DO, IN_PROGRESS),
            IN_PROGRESS to listOf(TO_DO, IN_PROGRESS, REVIEW),
            REVIEW to listOf(IN_PROGRESS, REVIEW, DONE),
            DONE to listOf(TO_DO, DONE),
        )
    }
}
