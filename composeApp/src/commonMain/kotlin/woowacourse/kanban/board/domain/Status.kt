package woowacourse.kanban.board.domain

enum class Status(val isDeletable: Boolean, val isRequiredAssignee: Boolean) {
    TO_DO(true, false),
    IN_PROGRESS(true, true),
    REVIEW(false, true),
    DONE(false, true);

    fun validateAssignee(assignee: Assignee?): Boolean {
        return if (isRequiredAssignee) assignee != null else true
    }
}
