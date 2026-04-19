package woowacourse.kanban.board.ui.component.dialog.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun TaskDialogDeleteButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    TaskDialogButton(
        text = text,
        onClick = onClick,
        containerColor = Color(0xFFDB6365),
        contentColor = Color.White,
        modifier = modifier,
        enabled = enabled,
    )
}

@Preview
@Composable
private fun TaskDialogDeleteButtonPreview() {
    TaskDialogDeleteButton(
        text = "삭제",
        onClick = {},
    )
}
