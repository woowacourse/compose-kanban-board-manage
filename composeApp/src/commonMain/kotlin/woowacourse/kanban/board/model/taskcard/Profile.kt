package woowacourse.kanban.board.model.taskcard

data class Profile(
    val nickname: String,
    val icon: String = "DEFAULT",
) {
    companion object {
        val NONE = Profile("없음", "NONE")
    }

    val isAssigned: Boolean
        get() = this != NONE
}
