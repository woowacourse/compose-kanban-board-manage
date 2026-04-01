package woowacourse.kanban.board.ui.screen.board

enum class SnackbarMessage(val text: String) {
    TASK_CREATED("새로운 태스크가 추가되었습니다."),
    TASK_MOVED("태스크가 이동되었습니다."),
    TASK_EDITED("태스크가 수정되었습니다."),
    TASK_DELETED("태스크가 삭제되었습니다."),
    TASK_DELETE_NOT_ALLOWED("해당 상태에서는 태스크 삭제가 불가능합니다."),
    TASK_MOVE_NOT_ALLOWED("해당 상태로 옮길 수 없습니다."),
    TASK_ASSIGNEE_REQUIRED("담당자를 지정해야 상태를 옮길 수 있습니다.")
}
