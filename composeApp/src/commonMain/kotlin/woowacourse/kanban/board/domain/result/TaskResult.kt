package woowacourse.kanban.board.domain.result

import woowacourse.kanban.board.domain.KanbanTask

sealed class TaskResult<out E> {
    data class Success(val task: KanbanTask) : TaskResult<Nothing>()
    data class Failed<E>(val error: E) : TaskResult<E>()
}
