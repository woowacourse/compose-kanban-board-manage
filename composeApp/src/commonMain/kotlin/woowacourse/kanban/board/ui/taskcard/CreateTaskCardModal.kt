package woowacourse.kanban.board.ui.taskcard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.util.UUID
import woowacourse.kanban.board.domain.AuthorPolicy
import woowacourse.kanban.board.domain.Tags
import woowacourse.kanban.board.domain.Task
import woowacourse.kanban.board.domain.Title
import woowacourse.kanban.board.exception.TagError
import woowacourse.kanban.board.exception.TagException
import woowacourse.kanban.board.exception.TitleError
import woowacourse.kanban.board.exception.TitleException
import woowacourse.kanban.board.ui.taskcard.components.AuthorSelectField
import woowacourse.kanban.board.ui.taskcard.components.ContentInputField
import woowacourse.kanban.board.ui.taskcard.components.CreateTaskActionButtons
import woowacourse.kanban.board.ui.taskcard.components.CreateTaskHeader
import woowacourse.kanban.board.ui.taskcard.components.TagsInputField
import woowacourse.kanban.board.ui.taskcard.components.TaskStateSelectField
import woowacourse.kanban.board.ui.taskcard.components.TitleInputField
import woowacourse.kanban.board.ui.taskcard.components.UpdateTaskActionButtons
import woowacourse.kanban.board.ui.taskcard.state.TaskInputMode
import woowacourse.kanban.board.ui.taskcard.state.TaskInputState
import woowacourse.kanban.board.util.splitByComma

@Composable
fun CreateTaskCardModal(
    taskInputState: TaskInputState,
    onStateChange: (TaskInputState) -> Unit,
    onDismissRequest: () -> Unit,
    onConfirmation: (Task) -> Unit,
    onDeleteClick: (UUID?) -> Unit,
    onUpdateClick: (UUID?, Task) -> Unit,
    modifier: Modifier = Modifier,
    editTask: Task? = null,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        CreateTaskHeader(taskInputState.taskInputMode, onDismissRequest)
        HorizontalDivider()
        TitleInputField(taskInputState.title, taskInputState.titleError) {
            onStateChange(
                taskInputState.copy(
                    title = it,
                    titleError = runCatching { Title(it) }.fold(
                        onSuccess = { TitleError.NONE },
                        onFailure = { e -> if (e is TitleException) e.error else TitleError.NONE },
                    ),
                ),
            )
        }
        ContentInputField(taskInputState.content) { onStateChange(taskInputState.copy(content = it)) }
        TagsInputField(taskInputState.tags, taskInputState.tagError) {
            if (it.isEmpty()) {
                onStateChange(taskInputState.copy(tags = it, tagError = TagError.NONE))
            } else {
                onStateChange(
                    taskInputState.copy(
                        tags = it,
                        tagError = runCatching { Tags(splitByComma((it))) }.fold(
                            onSuccess = { TagError.NONE },
                            onFailure = { e -> if (e is TagException) e.error else TagError.NONE },
                        ),
                    ),
                )
            }
        }
        TaskStateSelectField(taskInputState.selectedState) { newTaskState ->
            onStateChange(
                taskInputState.copy(
                    selectedState = newTaskState,
                    selectedAuthor = AuthorPolicy.selectableAuthors(newTaskState.inAuthorRequired).first(),
                ),
            )
        }

        AuthorSelectField(
            AuthorPolicy.selectableAuthors(taskInputState.selectedState.inAuthorRequired),
            taskInputState.selectedAuthor,
        ) { newAuthor ->
            onStateChange(taskInputState.copy(selectedAuthor = newAuthor))
        }

        HorizontalDivider()

        if (taskInputState.taskInputMode == TaskInputMode.EDIT) {
            UpdateTaskActionButtons(
                taskInputState.init.not() && taskInputState.isNewTaskEnabled,
                onDismissRequest = onDismissRequest,
                onDeleteClick = { onDeleteClick(if (taskInputState.selectedState.isDeletable && editTask != null) editTask.id else null) },
                onUpdateClick = {
                    onUpdateClick(
                        editTask?.id,
                        Task(
                            title = taskInputState.title,
                            content = taskInputState.content,
                            tags = splitByComma(taskInputState.tags),
                            taskState = taskInputState.selectedState,
                            author = taskInputState.selectedAuthor,
                        ),
                    )
                },
            )
        } else {
            CreateTaskActionButtons(
                taskInputState.init.not() && taskInputState.isNewTaskEnabled,
                onDismissRequest,
            ) {
                onConfirmation(
                    Task(
                        title = taskInputState.title,
                        content = taskInputState.content,
                        tags = splitByComma(taskInputState.tags),
                        taskState = taskInputState.selectedState,
                        author = taskInputState.selectedAuthor,
                    ),
                )
            }
        }
    }
}

@Preview(widthDp = 672)
@Composable
private fun PreviewCreateTaskCardModal() {
    CreateTaskCardModal(
        taskInputState = TaskInputState(),
        onStateChange = {},
        onDismissRequest = {},
        onConfirmation = { _ -> },
        onDeleteClick = {},
        onUpdateClick = { _, _ -> },
        modifier = Modifier
            .background(Color.White).padding(16.dp),
    )
}
