package woowacourse.kanban.board.domain.result

import woowacourse.kanban.board.domain.KanbanProject

sealed class ProjectResult<out E> {
    data class Success(val project: KanbanProject) : ProjectResult<Nothing>()
    data class Failed<E>(val error: E) : ProjectResult<E>()
}
