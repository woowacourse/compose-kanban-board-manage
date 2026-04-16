package woowacourse.kanban.board.domain

import woowacourse.kanban.board.exception.TasksError

sealed interface DomainResult<out T> {
    data class Success<T>(val data: T) : DomainResult<T>
    data class Failure(val error: TasksError) : DomainResult<Nothing>
}
