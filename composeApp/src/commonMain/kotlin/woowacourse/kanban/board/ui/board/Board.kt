package woowacourse.kanban.board.ui.board

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.util.UUID
import kotlinx.coroutines.launch
import woowacourse.kanban.board.domain.DomainResult
import woowacourse.kanban.board.domain.Project
import woowacourse.kanban.board.domain.Task
import woowacourse.kanban.board.domain.TaskState
import woowacourse.kanban.board.domain.Tasks
import woowacourse.kanban.board.exception.TasksError
import woowacourse.kanban.board.ui.board.components.BoardHeader
import woowacourse.kanban.board.ui.board.components.CreateTaskModalDialog
import woowacourse.kanban.board.ui.board.components.KanbanBoardContent
import woowacourse.kanban.board.ui.board.components.UpdateTaskModalDialog
import woowacourse.kanban.board.ui.taskcard.state.TaskInputState
import woowacourse.kanban.board.ui.theme.OutlineVariant
import woowacourse.kanban.board.ui.theme.Primary

@Composable
fun Board(
    projectName: String,
    tasks: Tasks,
    openUpdateDialog: Boolean,
    closeUpdateDialog: () -> Unit,
    onClickCard: (Task) -> Unit,
    onTaskCreated: (Task) -> Unit,
    onTaskUpdated: (Task) -> DomainResult<Project>,
    onTaskDeleted: (Task) -> DomainResult<Project>,
    authors: List<String>,
    onTaskStateChange: (UUID, TaskState) -> DomainResult<Project>,
    modifier: Modifier = Modifier,
    updatingTask: Task? = null,
) {
    var openCreateDialog by remember { mutableStateOf(false) }
    var taskInputState by remember { mutableStateOf(TaskInputState(selectedAuthor = authors.first())) }
    var updateTaskInputState by remember(updatingTask) {
        mutableStateOf(
            TaskInputState(
                title = updatingTask?.title ?: "",
                content = updatingTask?.content ?: "",
                tags = updatingTask?.tags?.joinToString(",") ?: "",
                selectedState = updatingTask?.taskState ?: TaskState.ToDo,
                selectedAuthor = updatingTask?.author?.takeIf { it.isNotBlank() } ?: authors.first(),
            ),
        )
    }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
    ) { innerPadding ->
        Box(
            modifier = modifier.padding(innerPadding),
        ) {
            Column {
                BoardHeader(
                    projectName = projectName,
                    tasks = tasks,
                    onClick = { openCreateDialog = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White),
                )

                HorizontalDivider(color = OutlineVariant)

                KanbanBoardContent(
                    tasks,
                    onTaskStateChange = { idx, targetStatus ->
                        scope.launch {
                            val message = when (val result = onTaskStateChange(idx, targetStatus)) {
                                is DomainResult.Success -> "태스크가 이동되었습니다."
                                is DomainResult.Failure -> result.error.errorMessage
                            }

                            snackbarHostState.currentSnackbarData?.dismiss()
                            snackbarHostState.showSnackbar(message = message, withDismissAction = true)
                        }
                    },
                    onClickCard = onClickCard,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Primary),
                )
            }

            if (openCreateDialog) {
                CreateTaskModalDialog(
                    taskInputState = taskInputState,
                    authors = authors,
                    onDismissRequest = {
                        openCreateDialog = false
                    },
                    onConfirmation = {
                        onTaskCreated(it)
                        openCreateDialog = false
                        scope.launch {
                            snackbarHostState.currentSnackbarData?.dismiss()
                            snackbarHostState.showSnackbar(
                                message = "새로운 태스크가 추가되었습니다.",
                                withDismissAction = true,
                            )
                        }
                        taskInputState = TaskInputState(selectedAuthor = authors.first())
                    },
                    onStateChange = { taskInputState = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center),
                )
            }

            if (openUpdateDialog && updatingTask != null) {
                UpdateTaskModalDialog(
                    taskInputState = updateTaskInputState,
                    onStateChange = { updateTaskInputState = it },
                    authors = authors,
                    onDismissRequest = { closeUpdateDialog() },
                    onUpdateRequest = {
                        scope.launch {
                            val updatedTask = updatingTask.copy(
                                title = it.title,
                                content = it.content,
                                tags = it.tags,
                                taskState = it.taskState,
                                author = it.author,
                            )

                            val message = when (val result = onTaskUpdated(updatedTask)) {
                                is DomainResult.Failure -> result.error.errorMessage
                                is DomainResult.Success<Project> -> "태스크가 수정되었습니다."
                            }
                            snackbarHostState.currentSnackbarData?.dismiss()
                            snackbarHostState.showSnackbar(message = message, withDismissAction = true)
                        }
                        closeUpdateDialog()
                    },
                    onDeleteRequest = {
                        scope.launch {
                            val message = when (val result = onTaskDeleted(updatingTask)) {
                                is DomainResult.Failure -> result.error.errorMessage
                                is DomainResult.Success<Project> -> "태스크가 삭제되었습니다."
                            }
                            snackbarHostState.currentSnackbarData?.dismiss()
                            snackbarHostState.showSnackbar(
                                message = message,
                                withDismissAction = true,
                            )
                        }
                        closeUpdateDialog()
                    },
                    modifier = Modifier,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun BoardPreview() {
    Board(
        projectName = "Compose Desktop 칸반보드",
        tasks = Tasks(emptyList()),
        onTaskCreated = {},
        authors = listOf("다이노", "페임스"),
        modifier = Modifier.size(width = 1295.dp, height = 909.dp),
        onTaskStateChange = { _, _ -> DomainResult.Failure(TasksError.UNKNOWN) },
        onTaskDeleted = { _ -> DomainResult.Failure(TasksError.UNKNOWN) },
        onTaskUpdated = { _ -> DomainResult.Failure(TasksError.UNKNOWN) },
        updatingTask = Task(title = "test"),
        onClickCard = {},
        openUpdateDialog = true,
        closeUpdateDialog = { },
    )
}
