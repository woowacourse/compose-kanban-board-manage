package woowacourse.kanban.board.ui.board

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import java.util.UUID
import kanbanboard.composeapp.generated.resources.Res
import kanbanboard.composeapp.generated.resources.add_task_message
import kanbanboard.composeapp.generated.resources.author_not_exists
import kanbanboard.composeapp.generated.resources.change_task_state_message
import kanbanboard.composeapp.generated.resources.delete_task_message
import kanbanboard.composeapp.generated.resources.invalid_state_transition_message
import kanbanboard.composeapp.generated.resources.not_delete_task_message
import kanbanboard.composeapp.generated.resources.update_task_message
import org.jetbrains.compose.resources.stringResource
import woowacourse.kanban.board.domain.Task
import woowacourse.kanban.board.domain.TaskState
import woowacourse.kanban.board.ui.board.components.BoardHeader
import woowacourse.kanban.board.ui.board.components.CreateTaskModalDialog
import woowacourse.kanban.board.ui.board.components.KanbanBoardContent
import woowacourse.kanban.board.ui.board.state.ProjectState
import woowacourse.kanban.board.ui.theme.OutlineVariant
import woowacourse.kanban.board.ui.theme.Primary

@Composable
fun Board(
    project: ProjectState,
    onTaskCreated: (Task) -> Unit,
    onTaskUpdated: (UUID, Task) -> Unit,
    onTaskDeleted: (UUID) -> Unit,
    onTaskStateChange: (UUID, TaskState) -> Unit,
    modifier: Modifier = Modifier,
) {
    var openDialog by remember { mutableStateOf(false) }
    var editTask by remember { mutableStateOf<Task?>(null) }
    val snackbarHostState = remember { SnackbarHostState() }
    var snackbarMessage by remember { mutableStateOf<String?>(null) }
    val changeTaskStateMessage = stringResource(Res.string.change_task_state_message)
    val addTaskMessage = stringResource(Res.string.add_task_message)
    val updateTaskMessage = stringResource(Res.string.update_task_message)
    val deleteTaskMessage = stringResource(Res.string.delete_task_message)
    val notDeleteTaskMessage = stringResource(Res.string.not_delete_task_message)
    val invalidStateTransitionMessage = stringResource(Res.string.invalid_state_transition_message)
    val authorNotExists = stringResource(Res.string.author_not_exists)

    LaunchedEffect(snackbarMessage) {
        snackbarMessage?.let { message ->
            snackbarHostState.currentSnackbarData?.dismiss()
            snackbarHostState.showSnackbar(
                message = message,
                withDismissAction = true,
            )
            snackbarMessage = null
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
    ) { innerPadding ->
        Box(
            modifier = modifier.padding(innerPadding),
        ) {
            Column {
                BoardHeader(
                    projectName = project.name,
                    completedRate = project.completedRate,
                    doneCount = project.countByState(TaskState.DONE),
                    totalCount = project.totalCount,
                    onClick = { openDialog = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White),
                )

                HorizontalDivider(color = OutlineVariant)

                KanbanBoardContent(
                    project = project,
                    onTaskStateChange = { task, targetState ->
                        if (task.canChangeTaskState(targetState)) {
                            if (TaskState.isAvailableTransition(task.taskState, targetState)) {
                                onTaskStateChange(task.id, targetState)
                                snackbarMessage = changeTaskStateMessage
                            } else {
                                snackbarMessage = invalidStateTransitionMessage
                            }
                        } else {
                            snackbarMessage = authorNotExists
                        }
                    },
                    onClick = { task ->
                        openDialog = true
                        editTask = task
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Primary),
                )
            }

            if (openDialog) {
                CreateTaskModalDialog(
                    onDismissRequest = {
                        editTask = null
                        openDialog = false
                    },
                    onConfirmation = {
                        onTaskCreated(it)
                        snackbarMessage = addTaskMessage
                        openDialog = false
                    },
                    onDeleteClick = { id ->
                        if (id != null) {
                            onTaskDeleted(id)
                            snackbarMessage = deleteTaskMessage
                        } else {
                            snackbarMessage = notDeleteTaskMessage
                        }
                        openDialog = false
                        editTask = null
                    },
                    onUpdateClick = { id, task ->
                        if (id != null) {
                            onTaskUpdated(id, task)
                            snackbarMessage = updateTaskMessage
                        }
                        openDialog = false
                        editTask = null
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center),
                    editTask = editTask,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun BoardPreview() {
    Board(
        project = ProjectState(name = "Compose Desktop 칸반보드"),
        onTaskCreated = {},
        onTaskUpdated = { _, _ -> },
        onTaskDeleted = {},
        onTaskStateChange = { _, _ -> },
    )
}
