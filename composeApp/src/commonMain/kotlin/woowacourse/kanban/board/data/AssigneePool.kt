package woowacourse.kanban.board.data

import woowacourse.kanban.board.domain.Assignee

object AssigneePool {
    private val assignees = listOf(
        Assignee("다이노"),
        Assignee("페임스"),
    )

    fun getAll(): List<Assignee> = assignees.toList()
}
