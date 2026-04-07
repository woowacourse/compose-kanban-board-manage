package woowacourse.kanban.board.ui.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import woowacourse.kanban.board.domain.model.Assignee
import woowacourse.kanban.board.domain.model.Status
import woowacourse.kanban.board.domain.model.Task

@Composable
fun TaskCreateDialog(
    onDismissRequest: () -> Unit,
    onCreate: (title: String, content: String, tags: List<String>, status: Status, assignee: Assignee?) -> Unit,
    onEdit: (title: String, content: String, tags: List<String>, status: Status, assignee: Assignee?) -> Unit,
    onDelete: () -> Unit,
    originTask: Task? = null,
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            dismissOnClickOutside = false,
            usePlatformDefaultWidth = false,
        ),
        content = {
            TaskCreateForm(
                modifier = Modifier.fillMaxWidth(0.6f)
                    .fillMaxHeight(0.9f).clip(RoundedCornerShape(10.dp)).background(Color.White),
                onDismiss = onDismissRequest,
                assignees = listOf(Assignee("다이노"), Assignee("다이노소어"), Assignee("우우우")),
                originTask = originTask,
                onClickCreate = onCreate,
                onClickDelete = onDelete,
                onClickEdit = onEdit,
            )
        },
    )
}

@Composable
@Preview(showBackground = true)
private fun TaskCreateDialogPreview() {
    TaskCreateDialog(
        onDismissRequest = {},
        onCreate = { _, _, _, _, _ -> },
        onEdit = { _, _, _, _, _ -> },
        onDelete = {},
    )
}
