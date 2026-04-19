package woowacourse.kanban.board.domain.validator

import woowacourse.kanban.board.domain.EditError
import woowacourse.kanban.board.domain.dialog.Status

object TaskEditValidator {
    fun validateEditStatus(
        status: Status,
        newStatus: Status,
        isAssigned: Boolean,
    ): EditError? = when (status) {
        Status.TO_DO -> {
            when (newStatus) {
                Status.IN_PROGRESS if !isAssigned -> EditError.UNASSIGNED
                !in listOf(Status.TO_DO, Status.IN_PROGRESS) -> EditError.INVALID_STATUS
                else -> null
            }
        }

        Status.IN_PROGRESS -> {
            if (newStatus !in listOf(Status.TO_DO, Status.IN_PROGRESS, Status.REVIEW))
                EditError.INVALID_STATUS
            else
                null
        }

        Status.REVIEW -> {
            if (newStatus !in listOf(Status.IN_PROGRESS, Status.REVIEW, Status.DONE))
                EditError.INVALID_STATUS
            else
                null
        }

        Status.DONE -> {
            if (newStatus !in listOf(Status.TO_DO, Status.DONE))
                EditError.INVALID_STATUS
            else
                null
        }
    }
}
