package woowacourse.kanban.board.domain

import woowacourse.kanban.board.exception.TransStateError
import woowacourse.kanban.board.exception.TransStateException


sealed class TaskState {
    abstract val isDeletable: Boolean
    abstract val isNeedProfile: Boolean
    abstract fun transferTo(state: TaskState): TaskState

    object ToDo : TaskState() {
        override val isDeletable: Boolean = true
        override val isNeedProfile: Boolean = false

        override fun transferTo(state: TaskState): TaskState = when(state) {
            ToDo -> ToDo
            InProgress -> InProgress
            Review -> throw TransStateException(TransStateError.CANT_TRANSFER)
            Done -> throw TransStateException(TransStateError.CANT_TRANSFER)
        }
    }

    object InProgress : TaskState() {
        override val isDeletable: Boolean = true
        override val isNeedProfile: Boolean = true

        override fun transferTo(state: TaskState): TaskState = when (state) {
            ToDo -> ToDo
            InProgress -> InProgress
            Review -> Review
            Done -> throw TransStateException(TransStateError.CANT_TRANSFER)
        }
    }

    object Review : TaskState() {
        override val isDeletable: Boolean = false
        override val isNeedProfile: Boolean = true

        override fun transferTo(state: TaskState): TaskState = when (state) {
            ToDo -> throw TransStateException(TransStateError.CANT_TRANSFER)
            InProgress -> InProgress
            Review -> Review
            Done -> Done
        }
    }

    object Done : TaskState() {
        override val isDeletable: Boolean = false
        override val isNeedProfile: Boolean = true

        override fun transferTo(state: TaskState): TaskState = when(state) {
            ToDo -> ToDo
            InProgress -> throw TransStateException(TransStateError.CANT_TRANSFER)
            Review -> throw TransStateException(TransStateError.CANT_TRANSFER)
            Done -> Done
        }
    }
    companion object {
        val entries: List<TaskState> by lazy { listOf(ToDo, InProgress, Review, Done) }
    }
}
