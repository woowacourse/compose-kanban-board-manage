package woowacourse.kanban.board.component.dialog.action

import androidx.compose.ui.graphics.Color
import woowacourse.kanban.board.theme.CREATE_BG
import woowacourse.kanban.board.theme.CREATE_BG_ERROR
import woowacourse.kanban.board.theme.PRIMARY_TEXT

fun cancelFooterAction(onClick: () -> Unit): FooterAction =
    FooterAction(
        text = "취소",
        backgroundColor = Color.White,
        textColor = Color(PRIMARY_TEXT),
        onClick = onClick,
    )

fun createFooterAction(onClick: () -> Unit, isEnabled: Boolean): FooterAction =
    FooterAction(
        text = "생성",
        textColor = Color.White,
        backgroundColor = if (isEnabled) Color(CREATE_BG) else Color(CREATE_BG_ERROR),
        onClick = onClick,
        enabled = isEnabled,
    )

fun editFooterAction(onClick: () -> Unit, isEnabled: Boolean): FooterAction =
    FooterAction(
        text = "수정",
        textColor = Color.White,
        backgroundColor = Color(0xFFDB6365),
        onClick = onClick,
        enabled = isEnabled,
    )

fun deleteFooterAction(onClick: () -> Unit, isEnabled: Boolean): FooterAction =
    FooterAction(
        text = "삭제",
        textColor = Color.White,
        backgroundColor = Color(0xFF4F39F6),
        onClick = onClick,
        enabled = isEnabled,
    )
