package woowacourse.kanban.board.ui.taskcard.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import kanbanboard.composeapp.generated.resources.Res
import kanbanboard.composeapp.generated.resources.exist_task_edit
import kanbanboard.composeapp.generated.resources.new_task_create
import org.jetbrains.compose.resources.stringResource
import woowacourse.kanban.board.ui.taskcard.state.TaskInputMode
import woowacourse.kanban.board.ui.theme.TextPrimary

@Composable
fun CreateTaskHeader(taskInputMode: TaskInputMode, onDismissRequest: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = if (taskInputMode ==
                TaskInputMode.CREATE
            ) stringResource(Res.string.new_task_create) else stringResource(Res.string.exist_task_edit),
            fontSize = 20.sp,
            color = TextPrimary,
            fontWeight = FontWeight.W600,
        )
        IconButton(
            onClick = { onDismissRequest() },
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "닫기",
            )
        }
    }
}

@Preview
@Composable
private fun CreateTaskHeaderPreview() {
    CreateTaskHeader(taskInputMode = TaskInputMode.CREATE, onDismissRequest = {})
}
