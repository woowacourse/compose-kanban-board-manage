package woowacourse.kanban.board.model

import woowacourse.kanban.board.constant.KanbanBoardColor

enum class Status(val state: String) {
    TODO("To Do"),
    IN_PROGRESS("In Progress"),
    REVIEW("Review"),
    DONE("Done"),
}

data class StatusColor(val titleBgColor: Long, val boardBgColor: Long, val boardBorderColor: Long) {
    companion object {
        fun getStatusColor(status: Status): StatusColor {
            return when (status) {
                Status.TODO -> StatusColor(
                    KanbanBoardColor.TODO_CARD_BOX_TITLE_COLOR,
                    KanbanBoardColor.TODO_CARD_BOX_CONTENT_COLOR,
                    KanbanBoardColor.TODO_CARD_BOX_BORDER_COLOR,
                )

                Status.IN_PROGRESS -> StatusColor(
                    KanbanBoardColor.IN_PROGRESS_CARD_BOX_TITLE_COLOR,
                    KanbanBoardColor.IN_PROGRESS_CARD_BOX_CONTENT_COLOR,
                    KanbanBoardColor.IN_PROGRESS_CARD_BOX_BORDER_COLOR,
                )

                Status.REVIEW -> StatusColor(
                    KanbanBoardColor.REVIEW_CARD_BOX_TITLE_COLOR,
                    KanbanBoardColor.REVIEW_CARD_BOX_CONTENT_COLOR,
                    KanbanBoardColor.REVIEW_CARD_BOX_BORDER_COLOR,
                )

                Status.DONE -> StatusColor(
                    KanbanBoardColor.DONE_CARD_BOX_TITLE_COLOR,
                    KanbanBoardColor.DONE_CARD_BOX_CONTENT_COLOR,
                    KanbanBoardColor.DONE_CARD_BOX_BORDER_COLOR,
                )
            }
        }
    }
}
