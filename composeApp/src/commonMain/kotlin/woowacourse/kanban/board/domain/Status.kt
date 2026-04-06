package woowacourse.kanban.board.domain

enum class Status(val state: String) {
    TODO("To Do"),
    IN_PROGRESS("In Progress"),
    REVIEW("Review"),
    DONE("Done"),
}
