package woowacourse.kanban.board.domain

import woowacourse.kanban.board.exception.TasksError

sealed class TaskState {
    abstract val isDeletable: Boolean
    abstract val isNeedProfile: Boolean
    abstract fun transferTo(state: TaskState): DomainResult<TaskState>

    object ToDo : TaskState() {
        override val isDeletable: Boolean = true
        override val isNeedProfile: Boolean = false

        override fun transferTo(state: TaskState) = when (state) {
            ToDo -> DomainResult.Success(ToDo)
            InProgress -> DomainResult.Success(InProgress)
            Review -> DomainResult.Failure(TasksError.INVALID_STATE_CHANGE)
            Done -> DomainResult.Failure(TasksError.INVALID_STATE_CHANGE)
        }
    }

    object InProgress : TaskState() {
        override val isDeletable: Boolean = true
        override val isNeedProfile: Boolean = true

        override fun transferTo(state: TaskState) = when (state) {
            ToDo -> DomainResult.Success(ToDo)
            InProgress -> DomainResult.Success(InProgress)
            Review -> DomainResult.Success(Review)
            Done -> DomainResult.Failure(TasksError.INVALID_STATE_CHANGE)
        }
    }

    object Review : TaskState() {
        override val isDeletable: Boolean = false
        override val isNeedProfile: Boolean = true

        override fun transferTo(state: TaskState) = when (state) {
            ToDo -> DomainResult.Failure(TasksError.INVALID_STATE_CHANGE)
            InProgress -> DomainResult.Success(InProgress)
            Review -> DomainResult.Success(Review)
            Done -> DomainResult.Success(Done)
        }
    }

    object Done : TaskState() {
        override val isDeletable: Boolean = false
        override val isNeedProfile: Boolean = true

        override fun transferTo(state: TaskState): DomainResult<TaskState> = when (state) {
            ToDo -> DomainResult.Success(ToDo)
            InProgress -> DomainResult.Failure(TasksError.INVALID_STATE_CHANGE)
            Review -> DomainResult.Failure(TasksError.INVALID_STATE_CHANGE)
            Done -> DomainResult.Success(Done)
        }
    }
    companion object {
        val entries: List<TaskState> by lazy { listOf(ToDo, InProgress, Review, Done) }
    }
}
