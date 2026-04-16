package woowacourse.kanban.board.ui.board

import woowacourse.kanban.board.exception.TasksError

val TasksError.errorMessage: String
    get() = when(this) {
        TasksError.INVALID_STATE_CHANGE -> "해당 상태로 옮길 수 없습니다."
        TasksError.INVALID_AUTHOR -> "담당자를 지정해야 상태를 옮길 수 있습니다."
        TasksError.INVALID_DELETE -> "해당 상태에서는 태스크 삭제가 불가합니다."
        TasksError.UNKNOWN -> "알 수 없는 오류가 발생했습니다."
    }
