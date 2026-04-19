package woowacourse.kanban.board.domain.result

import woowacourse.kanban.board.domain.KanbanBoard

sealed class BoardResult<out E> {
    data class Success(val board: KanbanBoard) : BoardResult<Nothing>()
    data class Failed<E>(val error: E) : BoardResult<E>()
}
