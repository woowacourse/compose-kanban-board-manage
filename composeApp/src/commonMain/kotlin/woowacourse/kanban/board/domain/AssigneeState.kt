package woowacourse.kanban.board.domain

sealed interface AssigneeState

data class Assigned(val assignee: Assignee): AssigneeState

data object Unassigned: AssigneeState
