package woowacourse.kanban.board.ui.taskcard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import woowacourse.kanban.board.domain.Tags
import woowacourse.kanban.board.domain.Task
import woowacourse.kanban.board.domain.Title
import woowacourse.kanban.board.exception.TagError
import woowacourse.kanban.board.exception.TagException
import woowacourse.kanban.board.exception.TitleError
import woowacourse.kanban.board.exception.TitleException
import woowacourse.kanban.board.ui.taskcard.components.AuthorSelectField
import woowacourse.kanban.board.ui.taskcard.components.ContentInputField
import woowacourse.kanban.board.ui.taskcard.components.CreateTaskHeader
import woowacourse.kanban.board.ui.taskcard.components.RoundedBottomButtons
import woowacourse.kanban.board.ui.taskcard.components.TagsInputField
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
    formContent: @Composable () -> Unit,
    bottomButtonsContent: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    onCreateRequest: (Task) -> Unit = {},
    onDeleteRequest: () -> Unit = {},
    onUpdateRequest: (Task) -> Unit = {},
    ) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        CreateTaskHeader(titleText = titleText/*"기존 태스크 수정"*/, onDismissRequest = onDismissRequest)
        HorizontalDivider()
        TaskCardModalForm(
            taskInputState = taskInputState,
            modifier = Modifier.weight(1f),
            onStateChange = onStateChange,
            authors = authors
        ) {
            formContent()
        }
        HorizontalDivider()
        TaskCardModalBottomButtons(
            isUpdateTaskEnabled = taskInputState.isUpdateTaskEnabled,
            isDeletedEnabled = taskInputState.isDeleteEnabled,
            onDismissRequest = onDismissRequest,
            onDeleteRequest = { onDeleteRequest() },
            onUpdateRequest = { onUpdateRequest(
                Task(
                    title = taskInputState.title,
                    content = taskInputState.content,
                    tags = splitByComma(taskInputState.tags),
                    taskState = taskInputState.selectedState,
                    author = taskInputState.selectedAuthor,
                ),
            )},
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
    content: @Composable () -> Unit,
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        item{ content() }
        item {
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
        }
        item {
            ContentInputField(taskInputState.content) { onStateChange(taskInputState.copy(content = it)) }
        }
        item {
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
        }
        item {
            TaskStateSelectField(taskInputState.selectedState) { newTaskState ->
                onStateChange(taskInputState.copy(selectedState = newTaskState))
            }
        }
        item {
            AuthorSelectField(isNecessary = taskInputState.needProfile, authors, taskInputState.selectedAuthor) { newAuthor ->
                onStateChange(taskInputState.copy(selectedAuthor = newAuthor))
            }
        }
    }
}

@Composable
private fun TaskCardModalBottomButtons(
    isUpdateTaskEnabled: Boolean,
    isDeletedEnabled: Boolean,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    onCreateRequest: (Task) -> Unit = {},
    onDeleteRequest: () -> Unit = {},
    onUpdateRequest: () -> Unit = {},
    content: @Composable () -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        content()
    }
}
