package woowacourse.kanban.board.ui.board.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
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
import woowacourse.kanban.board.ui.taskcard.TaskCardModal
import woowacourse.kanban.board.ui.taskcard.components.RoundedBottomButtons
import woowacourse.kanban.board.ui.taskcard.state.TaskInputState
import woowacourse.kanban.board.ui.theme.DeleteContainer
import woowacourse.kanban.board.ui.theme.DisabledContainer
import woowacourse.kanban.board.ui.theme.OnSurface
import woowacourse.kanban.board.ui.theme.OnSurfaceVariant
import woowacourse.kanban.board.ui.theme.PrimaryContainer
import woowacourse.kanban.board.ui.theme.TextSecondary
import woowacourse.kanban.board.util.splitByComma

@Composable
fun UpdateTaskModalDialog(
    taskInputState: TaskInputState,
    onStateChange: (TaskInputState) -> Unit,
    authors: List<String>,
    onDismissRequest: () -> Unit,
    onUpdateRequest: (Task) -> Unit,
    onDeleteRequest: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Dialog(
        onDismissRequest = {},
    ) {
        TaskCardModal(
            titleText = "기존 태스크 수정",
            taskInputState = taskInputState,
            onStateChange = onStateChange,
            authors = authors,
            onDismissRequest = onDismissRequest,
            bottomButtonsContent = {
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
                    enabled = taskInputState.isDeleteEnabled,
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
                    onClick = {
                        onUpdateRequest(
                            Task(
                                title = taskInputState.title,
                                content = taskInputState.content,
                                tags = splitByComma(taskInputState.tags),
                                taskState = taskInputState.selectedState,
                                author = taskInputState.selectedAuthor,
                            ),
                        )
                    },
                    enabled = taskInputState.isUpdateTaskEnabled,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = OnSurface,
                        contentColor = OnSurfaceVariant,
                        disabledContainerColor = DisabledContainer,
                        disabledContentColor = OnSurfaceVariant,
                    ),
                    text = "수정",
                )
            },
            modifier = modifier
                .width(800.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.White)
                .padding(16.dp),
        )
    }
}

@Preview
@Composable
private fun UpdateTaskModalDialogPreview() {
    UpdateTaskModalDialog(
        authors = listOf("다이노", "페임스"),
        onDismissRequest = {},
        onUpdateRequest = {},
        onDeleteRequest = {},
        modifier = Modifier,
        taskInputState = TaskInputState(),
        onStateChange = {},
    )
}
