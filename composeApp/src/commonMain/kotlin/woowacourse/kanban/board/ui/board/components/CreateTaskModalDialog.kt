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
import woowacourse.kanban.board.domain.Task
import woowacourse.kanban.board.ui.taskcard.CreateTaskCardModal
import woowacourse.kanban.board.ui.taskcard.state.TaskInputState

@Composable
fun CreateTaskModalDialog(
    authors: List<String>,
    onDismissRequest: () -> Unit,
    onConfirmation: (Task) -> Unit,
    modifier: Modifier = Modifier,
) {
    var taskInputState by remember { mutableStateOf(TaskInputState(selectedAuthor = authors.first())) }

    Dialog(
        onDismissRequest = {},
    ) {
        CreateTaskCardModal(
            taskInputState = taskInputState,
            onStateChange = { taskInputState = it },
            authors = authors,
            onDismissRequest = onDismissRequest,
            onConfirmation = onConfirmation,
            modifier = modifier
                .width(672.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.White)
                .padding(16.dp),
        )
    }
}

@Preview
@Composable
private fun CreateTaskModalDialogPreview() {
    CreateTaskModalDialog(
        authors = listOf("다이노", "페임스"),
        onDismissRequest = {},
        onConfirmation = {},
    )
}
