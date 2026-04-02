package woowacourse.kanban.board.ui.taskcard.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import woowacourse.kanban.board.ui.theme.DeleteContainer
import woowacourse.kanban.board.ui.theme.DisabledContainer
import woowacourse.kanban.board.ui.theme.OnSurface
import woowacourse.kanban.board.ui.theme.OnSurfaceVariant
import woowacourse.kanban.board.ui.theme.PrimaryContainer
import woowacourse.kanban.board.ui.theme.TextSecondary

@Composable
fun UpdateTaskActionButtons(
    isUpdateTaskEnabled: Boolean,
    isDeleteEnabled: Boolean,
    onDismissRequest: () -> Unit,
    onDeleteRequest: () -> Unit,
    onUpdateRequest: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        RoundedBottomButtons(
            onClick = { onDismissRequest() },
            colors = ButtonDefaults.buttonColors(
                containerColor = PrimaryContainer,
                contentColor = TextSecondary,
            ),
            enabled = true,
            text = "취소",
        )
        Spacer(modifier = Modifier.width(12.dp))
        RoundedBottomButtons(
            onClick = { onDeleteRequest() },
            enabled = isDeleteEnabled,
            colors = ButtonDefaults.buttonColors(
                containerColor = DeleteContainer,
                contentColor = OnSurfaceVariant,
                disabledContentColor = OnSurfaceVariant,
                disabledContainerColor = DisabledContainer,
            ),
            text = "삭제",
        )
        Spacer(modifier = Modifier.width(12.dp))
        RoundedBottomButtons(
            onClick = { onUpdateRequest() },
            enabled = isUpdateTaskEnabled,
            colors = ButtonDefaults.buttonColors(
                containerColor = OnSurface,
                contentColor = OnSurfaceVariant,
                disabledContainerColor = DisabledContainer,
                disabledContentColor = OnSurfaceVariant,
            ),
            text = "수정",
        )
    }
}

@Preview
@Composable
private fun UpdateTaskActionButtonsPreview() {
    UpdateTaskActionButtons(
        onDismissRequest = { },
        onUpdateRequest = { },
        onDeleteRequest = { },
        isUpdateTaskEnabled = true,
        isDeleteEnabled = true,
    )
}
