package woowacourse.kanban.board.ui.board.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import woowacourse.kanban.board.domain.Task
import woowacourse.kanban.board.ui.taskcard.TaskCardModal
import woowacourse.kanban.board.ui.taskcard.components.RoundedBottomButtons
import woowacourse.kanban.board.ui.taskcard.state.TaskInputState
import woowacourse.kanban.board.ui.theme.DisabledContainer
import woowacourse.kanban.board.ui.theme.OnSurface
import woowacourse.kanban.board.ui.theme.OnSurfaceVariant
import woowacourse.kanban.board.ui.theme.PrimaryContainer
import woowacourse.kanban.board.ui.theme.TextSecondary
import woowacourse.kanban.board.util.splitByComma

@Composable
fun CreateTaskModalDialog(
    taskInputState: TaskInputState,
    onStateChange: (TaskInputState) -> Unit,
    authors: List<String>,
    onDismissRequest: () -> Unit,
    onConfirmation: (Task) -> Unit,
    modifier: Modifier = Modifier,
) {
    Dialog(
        onDismissRequest = {},
    ) {
        TaskCardModal(
            titleText = "새 태스크 생성",
            taskInputState = taskInputState,
            onStateChange = onStateChange,
            authors = authors,
            onDismissRequest = onDismissRequest,
            modifier = modifier
                .width(672.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.White)
                .padding(16.dp),
            bottomButtonsContent = {
                RoundedBottomButtons(
                    onClick = { onDismissRequest() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PrimaryContainer,
                        contentColor = TextSecondary,
                        disabledContentColor = DisabledContainer,
                        disabledContainerColor = DisabledContainer,
                    ),
                    enabled = true,
                    text = "취소",
                )
                Spacer(modifier = Modifier.width(12.dp))
                RoundedBottomButtons(
                    onClick = {
                        onConfirmation(
                            Task(
                                title = taskInputState.title,
                                content = taskInputState.content,
                                tags = splitByComma(taskInputState.tags),
                                taskState = taskInputState.selectedState,
                                author = taskInputState.selectedAuthor,
                            ),
                        )
                    },
                    enabled = taskInputState.init.not() && taskInputState.isNewTaskEnabled,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = OnSurface,
                        contentColor = OnSurfaceVariant,
                        disabledContainerColor = DisabledContainer,
                        disabledContentColor = OnSurfaceVariant,
                    ),
                    text = "생성",
                )
            },
        )
    }
}

@Preview
@Composable
private fun CreateTaskModalDialogPreview() {
    val authors = listOf("다이노", "페임스")
    CreateTaskModalDialog(
        authors = authors,
        onDismissRequest = {},
        taskInputState = TaskInputState(selectedAuthor = authors.first()),
        onConfirmation = {},
        onStateChange = {},
    )
}
