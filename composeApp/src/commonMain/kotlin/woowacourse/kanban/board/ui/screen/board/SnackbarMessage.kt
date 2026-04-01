package woowacourse.kanban.board.ui.screen.board

enum class SnackbarMessage(val text: String) {
    TASK_CREATED("새로운 태스크가 추가되었습니다."),
    TASK_MOVED("태스크가 이동되었습니다."),
    TASK_EDITED("태스크가 수정되었습니다."),
    TASK_DELETED("태스크가 삭제되었습니다.")
}
