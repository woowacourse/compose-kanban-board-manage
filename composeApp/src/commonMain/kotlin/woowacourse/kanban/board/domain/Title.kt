package woowacourse.kanban.board.domain

import woowacourse.kanban.board.exception.TitleError
import woowacourse.kanban.board.exception.TitleException

@JvmInline
value class Title(val value: String) {
    init {
        if (value.isBlank()) throw TitleException(TitleError.BLANK)
    }
}
