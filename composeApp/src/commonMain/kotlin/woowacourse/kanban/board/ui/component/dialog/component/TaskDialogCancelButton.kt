package woowacourse.kanban.board.ui.component.dialog.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TaskDialogCancelButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    TaskDialogButton(
        text = text,
        onClick = onClick,
        containerColor = Color.White,
        contentColor = Color.Black,
        modifier = modifier,
        enabled = enabled,
    )
}

@Preview
@Composable
private fun TaskDialogCancelButtonPreview() {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        TaskDialogCancelButton(
            text = "취소",
            enabled = true,
            onClick = {},
        )
    }
}
