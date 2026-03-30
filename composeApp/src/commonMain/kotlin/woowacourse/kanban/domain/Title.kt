package woowacourse.kanban.domain

private const val ERROR_TAG = "[ERROR]"

@JvmInline
value class Title(val content: String) {
    init {
        require(content.isNotBlank()) { "$ERROR_TAG 제목의 내용이 존재해야 합니다." }
    }
}
