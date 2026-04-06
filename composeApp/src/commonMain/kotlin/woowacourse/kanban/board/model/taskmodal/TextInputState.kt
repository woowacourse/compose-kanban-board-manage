package woowacourse.kanban.board.model.taskmodal

class TextInputState(
    val value: String,
    val onChange: (String) -> Unit,
    val isError: Boolean = false,
)
