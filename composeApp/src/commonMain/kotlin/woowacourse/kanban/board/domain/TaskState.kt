package woowacourse.kanban.board.domain

enum class TaskState {
    TO_DO,
    IN_PROGRESS,
    REVIEW,
    DONE,
    ;

    companion object {
        fun isCorrectStateChange(fromState: TaskState, toState: TaskState): Boolean = when (fromState) {
            TO_DO -> toState == IN_PROGRESS || toState == TO_DO
            IN_PROGRESS -> toState == TO_DO || toState == REVIEW || toState == IN_PROGRESS
            REVIEW -> toState == IN_PROGRESS || toState == REVIEW || toState == DONE
            DONE -> toState == TO_DO || toState == DONE
        }
    }
}
