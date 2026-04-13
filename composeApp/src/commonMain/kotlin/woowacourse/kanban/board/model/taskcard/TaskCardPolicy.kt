package woowacourse.kanban.board.model.taskcard

object TaskCardPolicy {
    fun canDelete(status: Status): Boolean {
        return when (status) {
            Status.REVIEW, Status.DONE -> false
            else -> true
        }
    }

    fun requireProfile(status: Status): Boolean {
        return when (status) {
            Status.TODO -> false
            else -> true
        }
    }

    fun canModifyStatus(current: Status, after: Status): Boolean {
        return after in when (current) {
            Status.TODO -> setOf(Status.PROGRESS)
            Status.PROGRESS -> setOf(Status.TODO, Status.REVIEW)
            Status.REVIEW -> setOf(Status.PROGRESS, Status.DONE)
            Status.DONE -> setOf(Status.TODO)
        }
    }
}
