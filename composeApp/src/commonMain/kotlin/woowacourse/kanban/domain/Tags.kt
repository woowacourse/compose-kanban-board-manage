package woowacourse.kanban.domain

class Tags(val tags: List<String> = listOf()) {
    init {
        require(tags.size <= MAX_TAG_SIZE) { "태그는 5개를 초과할 수 없습니다." }
        require(tags.all { it.length <= MAX_TAG_CONTENT_SIZE }) { "태그의 내용은 5자를 초과할 수 없습니다." }
    }

    companion object {
        private const val MAX_TAG_SIZE = 5
        private const val MAX_TAG_CONTENT_SIZE = 5
    }
}
