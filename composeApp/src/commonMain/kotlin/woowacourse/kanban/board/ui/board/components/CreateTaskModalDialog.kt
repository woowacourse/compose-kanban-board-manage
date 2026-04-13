package woowacourse.kanban.board.ui.board.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import java.util.UUID
import woowacourse.kanban.board.domain.AuthorPolicy
import woowacourse.kanban.board.domain.Task
import woowacourse.kanban.board.domain.TaskState
import woowacourse.kanban.board.ui.taskcard.CreateTaskCardModal
import woowacourse.kanban.board.ui.taskcard.state.TaskInputMode
import woowacourse.kanban.board.ui.taskcard.state.TaskInputState

@Composable
fun CreateTaskModalDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: (Task) -> Unit,
    onDeleteClick: (UUID?) -> Unit,
    onUpdateClick: (UUID?, Task) -> Unit,
    modifier: Modifier = Modifier,
    editTask: Task? = null,
) {
    var taskInputState by remember {
        mutableStateOf(
            if (editTask != null) {
                TaskInputState(
                    title = editTask.title,
                    content = editTask.content,
                    tags = editTask.tags.joinToString(),
                    selectedState = editTask.taskState,
                    selectedAuthor = AuthorPolicy.selectableAuthors(editTask.taskState.inAuthorRequired).first(),
                    taskInputMode = TaskInputMode.EDIT,
                )
            } else {
                TaskInputState(
                    selectedState = TaskState.TO_DO,
                    selectedAuthor = AuthorPolicy.selectableAuthors(TaskState.TO_DO.inAuthorRequired).first(),
                )
            },
        )
    }

    Dialog(
        onDismissRequest = {},
    ) {
        CreateTaskCardModal(
            taskInputState = taskInputState,
            onStateChange = { taskInputState = it },
            onDismissRequest = onDismissRequest,
            onConfirmation = onConfirmation,
            onDeleteClick = onDeleteClick,
            onUpdateClick = onUpdateClick,
            modifier = modifier
                .width(672.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.White)
                .padding(16.dp),
            editTask = editTask,
        )
    }
}

@Preview
@Composable
private fun CreateTaskModalDialogPreview() {
    CreateTaskModalDialog(
        onDismissRequest = {},
        onConfirmation = { _ -> },
        onDeleteClick = {},
        onUpdateClick = { _, _ -> },
    )
}
