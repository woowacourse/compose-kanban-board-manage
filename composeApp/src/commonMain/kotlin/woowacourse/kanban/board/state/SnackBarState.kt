package woowacourse.kanban.board.state

data class SnackBarState(
    val isVisible: Boolean = false,
    val text: String = "",
)

