package woowacourse.kanban.board.theme

import woowacourse.kanban.board.domain.Status


// 상태 카드 관리 박스 제목에 사용된 색상
const val TODO_CARD_BOX_TITLE_COLOR = 0xFF155DFC
const val IN_PROGRESS_CARD_BOX_TITLE_COLOR = 0xFFE17100

const val REVIEW_CARD_BOX_TITLE_COLOR = 0xFF8B5CF6
const val DONE_CARD_BOX_TITLE_COLOR = 0xFF00A63E

// 상태 카드 관리 박스 배경에 사용된 색상
const val TODO_CARD_BOX_CONTENT_COLOR = 0xFFEFF6FF
const val IN_PROGRESS_CARD_BOX_CONTENT_COLOR = 0xFFFFFBEB
const val REVIEW_CARD_BOX_CONTENT_COLOR = 0xFFEDE9FE
const val DONE_CARD_BOX_CONTENT_COLOR = 0xFFF0FDF4

// 상태 카드 관리 태두리에 사용된 색상
const val TODO_CARD_BOX_BORDER_COLOR = 0xFFBEDBFF
const val IN_PROGRESS_CARD_BOX_BORDER_COLOR = 0xFFFEE685
const val REVIEW_CARD_BOX_BORDER_COLOR = 0xFFD2C7EA
const val DONE_CARD_BOX_BORDER_COLOR = 0xFFB9F8CF

// 칸반 보드 타이틀 색상
const val KANBANBOARD_TITLE_COLOR = 0xFF101828
const val KANBANBOARD_CONTENT_COLOR = 0xFF6A7282
const val KANBANBOARD_CREATE_BUTTON_COLOR = 0xFF4F39F6

data class StatusColor(val titleBgColor: Long, val boardBgColor: Long, val boardBorderColor: Long) {
    companion object {
        fun getStatusColor(status: Status): StatusColor {
            return when (status) {
                Status.TODO -> StatusColor(TODO_CARD_BOX_TITLE_COLOR, TODO_CARD_BOX_CONTENT_COLOR, TODO_CARD_BOX_BORDER_COLOR)
                Status.IN_PROGRESS -> StatusColor(
                    IN_PROGRESS_CARD_BOX_TITLE_COLOR,
                    IN_PROGRESS_CARD_BOX_CONTENT_COLOR,
                    IN_PROGRESS_CARD_BOX_BORDER_COLOR,
                )
                Status.REVIEW -> StatusColor(REVIEW_CARD_BOX_TITLE_COLOR, REVIEW_CARD_BOX_CONTENT_COLOR, REVIEW_CARD_BOX_BORDER_COLOR)

                Status.DONE -> StatusColor(DONE_CARD_BOX_TITLE_COLOR, DONE_CARD_BOX_CONTENT_COLOR, DONE_CARD_BOX_BORDER_COLOR)
            }
        }
    }
}
