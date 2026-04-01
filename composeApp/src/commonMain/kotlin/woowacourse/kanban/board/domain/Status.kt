package woowacourse.kanban.board.domain

enum class Status(val isRequiredAssignee: Boolean) {
    TO_DO(false),
    IN_PROGRESS(true),
    REVIEW(true),
    DONE(true);

    fun validateAssignee(assignee: Assignee?): Boolean {
        return !(assignee == null && isRequiredAssignee)
    }
}
