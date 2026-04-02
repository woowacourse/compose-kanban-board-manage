package woowacourse.kanban.board.domain

enum class TaskState {
    TO_DO,
    IN_PROGRESS,
    REVIEW,
    DONE;

    companion object {
        fun isCorrectStateChange(fromState: TaskState, toState: TaskState) : Boolean = when(fromState) {
            TO_DO -> toState == IN_PROGRESS
            IN_PROGRESS -> toState == TO_DO || toState == REVIEW
            REVIEW -> toState == IN_PROGRESS || toState == DONE
            DONE -> toState == TO_DO
        }
    }
}
