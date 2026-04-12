package woowacourse.kanban.domain.task

interface TaskRules {
    val isDeletable: Boolean

    fun moveTo(targetStatus: TaskStatus): TaskRules

    val requireAssignee: Boolean
}

class Todo : TaskRules {
    override val isDeletable: Boolean = true

    override fun moveTo(targetStatus: TaskStatus): TaskRules {
        return when (targetStatus) {
            TaskStatus.TO_DO -> this
            TaskStatus.IN_PROGRESS -> InProgress()
            TaskStatus.REVIEW, TaskStatus.DONE -> throw IllegalStateException()
        }
    }

    override val requireAssignee: Boolean = false
}

class InProgress : TaskRules {
    override val isDeletable: Boolean = true

    override fun moveTo(targetStatus: TaskStatus): TaskRules {
        return when (targetStatus) {
            TaskStatus.IN_PROGRESS -> this
            TaskStatus.TO_DO -> Todo()
            TaskStatus.REVIEW -> Review()
            TaskStatus.DONE -> throw IllegalStateException()
        }
    }

    override val requireAssignee: Boolean = true
}

class Review : TaskRules {
    override val isDeletable: Boolean = false

    override fun moveTo(targetStatus: TaskStatus): TaskRules {
        return when (targetStatus) {
            TaskStatus.REVIEW -> this
            TaskStatus.IN_PROGRESS -> InProgress()
            TaskStatus.DONE -> Done()
            TaskStatus.TO_DO -> throw IllegalStateException()
        }
    }

    override val requireAssignee: Boolean = true
}

class Done : TaskRules {
    override val isDeletable: Boolean = false

    override fun moveTo(targetStatus: TaskStatus): TaskRules {
        return when (targetStatus) {
            TaskStatus.DONE -> this
            TaskStatus.TO_DO -> Todo()
            TaskStatus.REVIEW, TaskStatus.IN_PROGRESS -> throw IllegalStateException()
        }
    }

    override val requireAssignee: Boolean = true
}
