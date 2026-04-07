package woowacourse.kanban.domain

private const val ERROR_TAG = "[ERROR]"

@JvmInline
value class Nickname(val nickname: String) {
    init {
        require(nickname.isNotBlank()) { "$ERROR_TAG 닉네임이 비어있으면 안됩니다." }
    }
}
