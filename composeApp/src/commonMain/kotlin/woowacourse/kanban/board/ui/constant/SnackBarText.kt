package woowacourse.kanban.board.ui.constant

object SnackBarText {
    const val CREATE_TASK = "새로운 태스크가 추가되었습니다."
    const val STATUS_EDIT = "태스크가 이동되었습니다."
    const val UPDATE_TASK = "태스크가 수정되었습니다."
    const val DELETE_TASK = "태스크가 삭제되었습니다."

    const val ILLEGAL_DELETE = "해당 상태에서는 태스크 삭제가 불가합니다."
    const val ILLEGAL_STATUS_EDIT = "해당 상태로 옮길 수 없습니다."
    const val ILLEGAL_STATUS_EDIT_ASSIGNEE = "담당자를 지정해야 상태를 옮길 수 있습니다."
    const val TASK_NOT_FOUND = "태스크를 찾을 수 없습니다."
}
