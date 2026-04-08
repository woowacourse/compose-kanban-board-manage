package woowacourse.kanban.board.ui.component.dialog.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import woowacourse.kanban.board.ui.KanbanColors

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
        containerColor = KanbanColors.deleteBackground,
        contentColor = Color.White,
        modifier = modifier,
        enabled = enabled,
    )
}

@Preview
@Composable
private fun TaskDialogDeleteButtonPreview() {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        TaskDialogDeleteButton(
            text = "생성",
            enabled = true,
            onClick = {},
        )
    }
}
