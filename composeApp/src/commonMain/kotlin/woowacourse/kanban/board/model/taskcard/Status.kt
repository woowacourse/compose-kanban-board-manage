package woowacourse.kanban.board.model.taskcard

enum class Status {
    TODO,
    PROGRESS,
    REVIEW,
    DONE;

    fun isCanDelete(): Boolean = when (this) {
        TODO, PROGRESS -> true
        REVIEW, DONE -> false
    }

    fun isAvailableEmptyAssignee(): Boolean = when (this) {
        TODO -> true
        PROGRESS, REVIEW, DONE -> false
    }

    fun availableUpdateStatuses(): List<Status> = when (this) {
        TODO -> listOf(PROGRESS)
        PROGRESS -> listOf(TODO, REVIEW)
        REVIEW -> listOf(PROGRESS, DONE)
        DONE -> listOf(TODO)
    }
}
