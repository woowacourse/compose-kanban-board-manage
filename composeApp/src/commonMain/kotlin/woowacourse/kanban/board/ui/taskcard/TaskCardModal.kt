package woowacourse.kanban.board.ui.taskcard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import woowacourse.kanban.board.domain.Tags
import woowacourse.kanban.board.domain.Title
import woowacourse.kanban.board.exception.TagError
import woowacourse.kanban.board.exception.TagException
import woowacourse.kanban.board.exception.TitleError
import woowacourse.kanban.board.exception.TitleException
import woowacourse.kanban.board.ui.taskcard.components.AuthorSelectField
import woowacourse.kanban.board.ui.taskcard.components.ContentInputField
import woowacourse.kanban.board.ui.taskcard.components.TagsInputField
import woowacourse.kanban.board.ui.taskcard.components.TaskCardModalBottomButtons
import woowacourse.kanban.board.ui.taskcard.components.TaskHeader
import woowacourse.kanban.board.ui.taskcard.components.TaskStateSelectField
import woowacourse.kanban.board.ui.taskcard.components.TitleInputField
import woowacourse.kanban.board.ui.taskcard.state.TaskInputState
import woowacourse.kanban.board.util.splitByComma

@Composable
fun TaskCardModal(
    titleText: String,
    taskInputState: TaskInputState,
    onStateChange: (TaskInputState) -> Unit,
    authors: List<String>,
    onDismissRequest: () -> Unit,
    bottomButtonsContent: @Composable () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        TaskHeader(titleText = titleText, onDismissRequest = onDismissRequest)
        HorizontalDivider()
        TaskCardModalForm(
            taskInputState = taskInputState,
            modifier = Modifier.weight(1f),
            onStateChange = onStateChange,
            authors = authors,
        )
        HorizontalDivider()
        TaskCardModalBottomButtons(
            modifier = Modifier,
        ) {
            bottomButtonsContent()
        }
    }
}

@Composable
private fun TaskCardModalForm(
    taskInputState: TaskInputState,
    onStateChange: (TaskInputState) -> Unit,
    authors: List<String>,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
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
                        tagError = runCatching { Tags(splitByComma(it)) }.fold(
                            onSuccess = { TagError.NONE },
                            onFailure = { e -> if (e is TagException) e.error else TagError.NONE },
                        ),
                    ),
                )
            }
        }
        TaskStateSelectField(taskInputState.selectedState) { newTaskState ->
            onStateChange(taskInputState.copy(selectedState = newTaskState))
        }
        AuthorSelectField(isNecessary = taskInputState.needProfile, authors, taskInputState.selectedAuthor) { newAuthor ->
            onStateChange(taskInputState.copy(selectedAuthor = newAuthor))
        }
    }
}

@Preview
@Composable
private fun TaskCardModalFormPreview() {
    TaskCardModalForm(
        taskInputState = TaskInputState(),
        onStateChange = { },
        authors = listOf("다이노", "제임스"),
        modifier = Modifier,
    )
}
