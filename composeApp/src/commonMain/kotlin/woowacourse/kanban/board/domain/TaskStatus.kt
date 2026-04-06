package woowacourse.kanban.board.domain

enum class TaskStatus {
    TODO,
    IN_PROGRESS,
    REVIEW,
    DONE,
    ;

    fun isTransitionableTo(target: TaskStatus): Boolean = target in validTransitions

    val validTransitions: List<TaskStatus>
        get() = when (this) {
            TODO -> listOf(IN_PROGRESS)
            IN_PROGRESS -> listOf(TODO, REVIEW)
            REVIEW -> listOf(IN_PROGRESS, DONE)
            DONE -> listOf(TODO)
        }

    val isDeletable: Boolean
        get() = when (this) {
            TODO, IN_PROGRESS -> true
            REVIEW, DONE -> false
        }

    val isAssigneeRequired: Boolean
        get() = when (this) {
            TODO -> false
            IN_PROGRESS, REVIEW, DONE -> true
        }
}
