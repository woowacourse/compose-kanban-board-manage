package woowacourse.kanban.board.domain

import woowacourse.kanban.board.exception.TagError
import woowacourse.kanban.board.exception.TagException

@JvmInline
value class Tags(val tags: List<String>) {
    init {
        tags.forEach { Tag(it) }
        if (tags.size > TAGS_MAX_SIZE) throw TagException(TagError.TOO_MANY)
    }

    companion object {
        private const val TAGS_MAX_SIZE = 5
    }
}
