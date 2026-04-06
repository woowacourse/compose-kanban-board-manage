package woowacourse.kanban.board.task.ui.modal

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kanbanboard.composeapp.generated.resources.Res
import kanbanboard.composeapp.generated.resources.modal_title_edit_task
import kanbanboard.composeapp.generated.resources.modal_title_new_task
import org.jetbrains.compose.resources.stringResource
import woowacourse.kanban.board.task.ui.board.TaskModalMode

@Composable
fun ModalHeader(modalMode: TaskModalMode, onDismissRequest: () -> Unit, modifier: Modifier = Modifier) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(24.dp),
    ) {
        Text(
            text = when (modalMode) {
                TaskModalMode.CREATE -> stringResource(Res.string.modal_title_new_task)
                TaskModalMode.EDIT -> stringResource(Res.string.modal_title_edit_task)
            },
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
        )
        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "모달 닫기",
            modifier = Modifier.size(20.dp).clickable(onClick = onDismissRequest),
        )
    }
}

private class ModalHeaderPreviewParameterProvider : PreviewParameterProvider<TaskModalMode> {
    override val values = sequenceOf(
        TaskModalMode.CREATE,
        TaskModalMode.EDIT,
    )
}

@Preview
@Composable
private fun ModalHeaderPreview(@PreviewParameter(ModalHeaderPreviewParameterProvider::class) taskModalMode: TaskModalMode) {
    ModalHeader(
        modalMode = taskModalMode,
        onDismissRequest = { },
    )
}
