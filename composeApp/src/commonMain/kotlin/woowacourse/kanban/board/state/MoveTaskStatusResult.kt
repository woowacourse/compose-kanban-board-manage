package woowacourse.kanban.board.state

import woowacourse.kanban.board.domain.KanbanBoard
import woowacourse.kanban.board.domain.Status
import woowacourse.kanban.board.domain.Task

enum class MoveTaskStatusResult {
    SUCCESS,
    ASSIGNEE_REQUIRED,
    INVALID_TRANSITION,
}

fun resolveMoveResult(
    beforeBoard: KanbanBoard,
    afterBoard: KanbanBoard,
    sourceTask: Task?,
    targetStatus: Status,
): MoveTaskStatusResult {
    if (beforeBoard != afterBoard) return MoveTaskStatusResult.SUCCESS

    if (sourceTask?.requiresAssigneeFor(targetStatus) == true && !sourceTask.hasAssignee()) {
        return MoveTaskStatusResult.ASSIGNEE_REQUIRED
    }

    return MoveTaskStatusResult.INVALID_TRANSITION
}

