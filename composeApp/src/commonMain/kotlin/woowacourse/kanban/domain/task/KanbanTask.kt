package woowacourse.kanban.domain.task

data class KanbanTask(val data: TaskData, val status: TaskStatus) {
    private var taskRules: TaskRules = when (status) {
        TaskStatus.TO_DO -> Todo()
        TaskStatus.IN_PROGRESS -> InProgress()
        TaskStatus.REVIEW -> Review()
        TaskStatus.DONE -> Done()
    }

    val isDeletable get() = taskRules.isDeletable

    init {
        validateAssignee(taskRules, data)
    }

    private fun validateAssignee(taskRules: TaskRules, data: TaskData) {
        if (taskRules.requireAssignee && data.assignee == Assignee.NONE) throw IllegalArgumentException()
    }

    fun changeStatus(targetStatue: TaskStatus): KanbanTask {
        val nextRules = taskRules.moveTo(targetStatue)

        validateAssignee(nextRules, data)
        taskRules = nextRules

        return copy(status = targetStatue)
    }

    fun changeData(data: TaskData): KanbanTask {
        validateAssignee(taskRules, data)

        return copy(data = data)
    }
}
