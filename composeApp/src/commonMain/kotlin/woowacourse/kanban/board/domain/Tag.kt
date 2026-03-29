package woowacourse.kanban.board.domain

import woowacourse.kanban.board.exception.TagError
import woowacourse.kanban.board.exception.TagException

@JvmInline
value class Tag(val value: String) {
    init {
        if (value.isBlank()) throw TagException(TagError.INVALID_FORMAT)
        if (value.length > TAG_MAX_LENGTH) throw TagException(TagError.TOO_LONG)
    }

    companion object {
        private const val TAG_MAX_LENGTH = 5
    }
}
