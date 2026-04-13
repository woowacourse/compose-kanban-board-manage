package woowacourse.kanban.board.component.board

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.launch
import woowacourse.kanban.board.component.modal.Modal
import woowacourse.kanban.board.component.sample.ProfilePreviewData
import woowacourse.kanban.board.component.sample.ProjectPreviewData
import woowacourse.kanban.board.component.util.ComponentText
import woowacourse.kanban.board.component.util.Gray80
import woowacourse.kanban.board.model.project.Project
import woowacourse.kanban.board.model.taskcard.Profile
import woowacourse.kanban.board.model.taskcard.Status
import woowacourse.kanban.board.model.taskcard.TaskCard

@Composable
fun Board(
    project: Project,
    profiles: ImmutableList<Profile>,
    onCreateTask: (TaskCard) -> Unit,
    onUpdateTask: (String, TaskCard) -> Unit,
    onDeleteTask: (String) -> Unit,
    onUpdateTaskStatus: (String, Status) -> Unit,
    modifier: Modifier = Modifier,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    fun showSnackbar(message: String) {
        coroutineScope.launch {
            snackbarHostState.showSnackbar(
                message = message,
                withDismissAction = true
            )
        }
    }

    var isShowModal by remember { mutableStateOf(false) }
    var selectedTask by remember { mutableStateOf<TaskCard?>(null) }
    val closeModal = {
        selectedTask = null
        isShowModal = false
    }

    Scaffold(
        modifier = modifier,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .background(Gray80),
        ) {
            if (isShowModal) {
                Dialog(
                    onDismissRequest = closeModal,
                    properties = DialogProperties(
                        usePlatformDefaultWidth = false,
                    ),
                ) {
                    Modal(
                        profiles = profiles,
                        initialTask = selectedTask,
                        onClickClose = closeModal,
                        onShowSnackbar = ::showSnackbar,
                        onCreateTask = { task ->
                            onCreateTask(task)
                            showSnackbar(ComponentText.BOARD_TASK_CREATE_SNACKBAR)
                            closeModal()
                        },
                        onUpdateTask = { id, task ->
                            onUpdateTask(id, task)
                            showSnackbar(ComponentText.BOARD_TASK_MODIFY_SNACKBAR)
                            closeModal()
                        },
                        onDeleteTask = { id ->
                            onDeleteTask(id)
                            showSnackbar(ComponentText.BOARD_TASK_DELETE_SNACKBAR)
                            closeModal()
                        },
                    )
                }
            }
            BoardHeader(
                title = project.title,
                doneRate = project.calculateDoneRate(),
                doneTasks = project.filterTasksbyStatus(Status.DONE).size,
                totalTasks = project.allTasksCount,
                onClickCreateTask = {
                    selectedTask = null
                    isShowModal = true
                },
            )
            TaskColumnSection(
                project = project,
                onMoveSnackBar = {
                    showSnackbar(ComponentText.BOARD_TASK_MOVE_SNACKBAR)
                },
                onInvalidStatusMove = {
                    showSnackbar(ComponentText.BOARD_TASK_INVALID_STATUS_SNACKBAR)
                },
                onRequireProfileMove = {
                    showSnackbar(ComponentText.BOARD_TASK_REQUIRE_PROFILE_SNACKBAR)
                },
                onUpdateTaskStatus = onUpdateTaskStatus,
                onTaskClick = { task ->
                    selectedTask = task
                    isShowModal = true
                },
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 1000)
@Composable
private fun BoardPreview() {
    val project = ProjectPreviewData().values.first()
    val profiles = ProfilePreviewData().values.toImmutableList()

    MaterialTheme {
        Board(
            project = project,
            profiles = profiles,
            onCreateTask = {},
            onUpdateTask = { _, _ -> },
            onDeleteTask = {},
            onUpdateTaskStatus = { _, _ -> },
        )
    }
}
