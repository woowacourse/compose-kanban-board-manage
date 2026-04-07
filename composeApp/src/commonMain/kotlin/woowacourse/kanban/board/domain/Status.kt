package woowacourse.kanban.board.domain

enum class Status(val isDeletable: Boolean, val isRequiredAssignee: Boolean) {
    TO_DO(true, false),
    IN_PROGRESS(true, true),
    REVIEW(false, true),
    DONE(false, true);

    fun isValidAssignee(assigneeState: AssigneeState): Boolean {
        return if (isRequiredAssignee) assigneeState is Assigned else true
    }

    fun isValidTransition(targetStatus: Status): Boolean {
        return when (this) {
            TO_DO -> {
                targetStatus == IN_PROGRESS
            }

            IN_PROGRESS -> {
                targetStatus == TO_DO || targetStatus == REVIEW
            }

            REVIEW -> {
                targetStatus == IN_PROGRESS || targetStatus == DONE
            }

            DONE -> {
                targetStatus == TO_DO
            }
        }
    }
}
