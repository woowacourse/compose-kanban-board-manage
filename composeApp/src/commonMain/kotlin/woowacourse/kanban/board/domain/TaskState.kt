package woowacourse.kanban.board.domain

enum class TaskState(val inAuthorRequired: Boolean, val isDeletable: Boolean) {
    TO_DO(false, true),
    IN_PROGRESS(true, true),
    REVIEW(true, false),
    DONE(true, false),
    ;

    companion object {
        fun isAvailableTransition(from: TaskState, to: TaskState): Boolean {
            return when (from) {
                TO_DO -> to == IN_PROGRESS
                IN_PROGRESS -> to == TO_DO || to == REVIEW
                REVIEW -> to == IN_PROGRESS || to == DONE
                DONE -> to == TO_DO
            }
        }
    }
}
