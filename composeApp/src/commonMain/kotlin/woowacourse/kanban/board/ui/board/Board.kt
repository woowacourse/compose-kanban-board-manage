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
import kotlinx.coroutines.launch
import woowacourse.kanban.board.domain.Task
import woowacourse.kanban.board.domain.TaskState
import woowacourse.kanban.board.domain.Tasks
import woowacourse.kanban.board.exception.TasksError
import woowacourse.kanban.board.exception.TasksException
import woowacourse.kanban.board.exception.TransStateException
import woowacourse.kanban.board.ui.board.components.BoardHeader
import woowacourse.kanban.board.ui.board.components.CreateTaskModalDialog
import woowacourse.kanban.board.ui.board.components.KanbanBoardContent
import woowacourse.kanban.board.ui.board.components.UpdateTaskModalDialog
import woowacourse.kanban.board.ui.theme.OutlineVariant
import woowacourse.kanban.board.ui.theme.Primary
import java.util.UUID

@Composable
fun Board(
    projectName: String,
    tasks: Tasks,
    openUpdateDialog: Boolean,
    closeUpdateDialog: () -> Unit,
    onClickCard: (Task) -> Unit,
    onTaskCreated: (Task) -> Unit,
    onTaskUpdated: (Task) -> Unit,
    onTaskDeleted: (Task) -> Unit,
    authors: List<String>,
    onTaskStateChange: (UUID, TaskState) -> Unit,
    modifier: Modifier = Modifier,
    updatingTask: Task? = null,
) {
    var openCreateDialog by remember { mutableStateOf(false) }
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
                            runCatching { onTaskStateChange(idx, targetStatus) }
                                .onSuccess {
                                    snackbarHostState.currentSnackbarData?.dismiss()
                                    snackbarHostState.showSnackbar(
                                        message = "태스크가 이동되었습니다.",
                                        withDismissAction = true,
                                    )
                                }.onFailure { e ->
                                    val message = when (e) {
                                        is TransStateException -> "해당 상태로 옮길 수 없습니다."
                                        is TasksException -> when (e.error) {
                                            TasksError.INVALID_AUTHOR -> "담당자를 지정해야 상태를 옮길 수 있습니다."
                                            else -> "상태 변경에 실패했습니다."
                                        }

                                        else -> "알 수 없는 에러가 발생했습니다."
                                    }
                                    snackbarHostState.currentSnackbarData?.dismiss()
                                    snackbarHostState.showSnackbar(message = message, withDismissAction = true)
                                }
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
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center),
                )
            }

            if (openUpdateDialog && updatingTask != null) {
                UpdateTaskModalDialog(
                    task = updatingTask,
                    authors = authors,
                    onDismissRequest = { closeUpdateDialog() },
                    onUpdateRequest = {
                        scope.launch {
                            runCatching {
                                onTaskUpdated(
                                    updatingTask.copy(
                                        title = it.title,
                                        content = it.content,
                                        tags = it.tags,
                                        taskState = it.taskState,
                                        author = it.author,
                                    ),
                                )
                                snackbarHostState.currentSnackbarData?.dismiss()
                                snackbarHostState.showSnackbar(
                                    message = "태스크가 수정되었습니다.",
                                    withDismissAction = true,
                                )
                            }.onSuccess {
                                snackbarHostState.currentSnackbarData?.dismiss()
                                snackbarHostState.showSnackbar(message = "태스크가 수정되었습니다.", withDismissAction = true)
                            }.onFailure { e ->
                                val message = when (e) {
                                    is TransStateException -> "해당 상태로 옮길 수 없습니다"
                                    is TasksException -> when (e.error) {
                                        TasksError.INVALID_AUTHOR -> "담당자를 지정해야 상태를 옮길 수 있습니다."
                                        else -> "수정에 실패했습니다."
                                    }

                                    else -> "알 수 없는 오류가 발생했습니다."
                                }
                                snackbarHostState.currentSnackbarData?.dismiss()
                                snackbarHostState.showSnackbar(message = message, withDismissAction = true)
                            }
                        }
                        closeUpdateDialog()
                    },
                    onDeleteRequest = {
                        scope.launch {
                            runCatching {
                                onTaskDeleted(updatingTask)
                            }.onSuccess {
                                snackbarHostState.currentSnackbarData?.dismiss()
                                snackbarHostState.showSnackbar(
                                    message = "태스크가 삭제되었습니다.",
                                    withDismissAction = true,
                                )
                                closeUpdateDialog()
                            }.onFailure { e ->
                                val message = if (e is TasksException && e.error == TasksError.INVALID_DELETE) {
                                    "해당 상태에서는 태스크 삭제가 불가합니다."
                                } else {
                                    "삭제에 실패했습니다."
                                }
                                snackbarHostState.currentSnackbarData?.dismiss()
                                snackbarHostState.showSnackbar(message = message, withDismissAction = true)
                            }
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
        onTaskStateChange = { _, _ -> },
        onTaskDeleted = {},
        onTaskUpdated = {},
        updatingTask = Task(title = "test"),
        onClickCard = {},
        openUpdateDialog = true,
        closeUpdateDialog = { },
    )
}
