package woowacourse.kanban.board.domain.validator

import woowacourse.kanban.board.domain.DeleteError
import woowacourse.kanban.board.domain.dialog.Status

object TaskDeleteValidator {
    fun validateDelete(status: Status): DeleteError? = when (status) {
        Status.TO_DO, Status.IN_PROGRESS -> null
        Status.REVIEW, Status.DONE -> DeleteError.FAILED
    }
}
