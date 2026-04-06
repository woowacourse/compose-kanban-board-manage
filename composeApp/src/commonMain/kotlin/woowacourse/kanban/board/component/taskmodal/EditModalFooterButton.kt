package woowacourse.kanban.board.component.taskmodal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import woowacourse.kanban.board.Purple40
import woowacourse.kanban.board.Red70
import woowacourse.kanban.board.component.ComponentText

@Composable
fun EditModalFooterButtons(
    isButtonEnabled: Boolean,
    onDeleteClick: () -> Unit,
    onEditClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        TaskModalFooterButton(
            containerColor = Red70,
            text = ComponentText.DELETE_BUTTON,
            onClick = { onDeleteClick() },
        )
        TaskModalFooterButton(
            enabled = isButtonEnabled,
            containerColor = Purple40,
            text = ComponentText.EDIT_BUTTON,
            onClick = { onEditClick() },
        )
    }
}