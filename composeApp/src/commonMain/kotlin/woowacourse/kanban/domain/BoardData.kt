package woowacourse.kanban.domain

data class BoardData(
    val title: Title,
    val content: String = "",
    val tags: Tags,
    val nickname: Nickname,
    val id: Long = System.currentTimeMillis(),
)
