package woowacourse.kanban.domain

class Tags(val tags: List<String> = listOf()) {
    init {
        require(isLengthValid(tags)) { "태그는 5개를 초과할 수 없습니다." }
        require(isContentValid(tags)) { "태그의 내용은 5자를 초과할 수 없습니다." }
    }

    companion object {
        private const val MAX_TAG_SIZE = 5
        private const val MAX_TAG_CONTENT_SIZE = 5

        fun isLengthValid(tags: List<String>): Boolean {
            return tags.size <= MAX_TAG_SIZE
        }

        fun isContentValid(tags: List<String>): Boolean {
            return tags.all { it.length <= MAX_TAG_CONTENT_SIZE }
        }
    }
}
