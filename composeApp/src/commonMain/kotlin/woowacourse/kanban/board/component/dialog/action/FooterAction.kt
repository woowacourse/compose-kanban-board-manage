package woowacourse.kanban.board.component.dialog.action

import androidx.compose.ui.graphics.Color

data class FooterAction(
    val text: String,
    val backgroundColor: Color,
    val textColor: Color,
    val onClick: () -> Unit,
    val enabled: Boolean = true,
)

