package woowacourse.kanban.board.component.workspace

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import woowacourse.kanban.board.Blue50
import woowacourse.kanban.board.component.ComponentText
import woowacourse.kanban.board.component.board.TaskBoard
import woowacourse.kanban.board.component.sample.ProfilePreviewData
import woowacourse.kanban.board.component.sample.ProjectPreviewData
import woowacourse.kanban.board.component.taskmodal.EditModalFooterButtons
import woowacourse.kanban.board.component.taskmodal.TaskModal
import woowacourse.kanban.board.component.taskmodal.TaskModalFooterButton
import woowacourse.kanban.board.model.project.Project
import woowacourse.kanban.board.model.taskcard.Assignee
import woowacourse.kanban.board.model.workspace.SnackbarType

@Composable
fun WorkSpace(
    projects: ImmutableList<Project>,
    assignees: ImmutableList<Assignee>,
    modifier: Modifier = Modifier,
) {
    val workSpaceState = rememberWorkSpaceState(projects)
    val modalState = rememberModalState(assignees)

    LaunchedEffect(assignees) {
        if (assignees.isEmpty()) return@LaunchedEffect
        modalState.assignees = assignees
    }

    LaunchedEffect(workSpaceState.shouldShowSnackbar) {
        val snackbarText = when (workSpaceState.shouldShowSnackbar) {
            null -> return@LaunchedEffect
            SnackbarType.ADD -> ComponentText.BOARD_TASK_CREATE_SNACKBAR
            SnackbarType.EDIT -> ComponentText.BOARD_TASK_EDIT_SNACKBAR
            SnackbarType.MOVE_SUCCESS -> ComponentText.BOARD_TASK_MOVE_SUCCESS_SNACKBAR
            SnackbarType.MOVE_FAILED -> ComponentText.BOARD_TASK_MOVE_FAILED_SNACKBAR
            SnackbarType.MOVE_NO_ASSIGNEE -> ComponentText.BOARD_TASK_MOVE_NO_ASSIGNEE_SNACKBAR
            SnackbarType.DELETE_SUCCESS -> ComponentText.BOARD_TASK_DELETE_SUCCESS_SNACKBAR
            SnackbarType.DELETE_FAILED -> ComponentText.BOARD_TASK_DELETE_FAILED_SNACKBAR
        }
        workSpaceState.snackbarHostState.showSnackbar(
            message = snackbarText,
            withDismissAction = true,
        )
        workSpaceState.hideSnackbar()
    }

    if (workSpaceState.isShowCreateModal) {
        Dialog(
            onDismissRequest = { workSpaceState.closeCreateModal() },
            properties = DialogProperties(
                usePlatformDefaultWidth = false,
            ),
        ) {
            TaskModal(
                assignees = assignees,
                onClickClose = {
                    workSpaceState.closeCreateModal()
                },
                title = {
                    Text(
                        text = ComponentText.CREATE_MODAL_HEADER_LABEL,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                    )
                },
                footerButtonSection = {
                    TaskModalFooterButton(
                        enabled = modalState.isFormValid,
                        containerColor = Blue50,
                        text = ComponentText.CREATE_BUTTON,
                        onClick = { workSpaceState.addTask(modalState) },
                    )
                },
                modalState = modalState,
            )
        }
    }

    if (workSpaceState.isShowEditModal) {
        val taskCardData = workSpaceState.currentEditTask
        Dialog(
            onDismissRequest = { workSpaceState.closeCreateModal() },
            properties = DialogProperties(
                usePlatformDefaultWidth = false,
            ),
        ) {
            TaskModal(
                data = taskCardData,
                assignees = assignees,
                onClickClose = {
                    workSpaceState.closeEditModal()
                },
                title = {
                    Text(
                        text = ComponentText.EDIT_MODAL_HEADER_LABEL,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                    )
                },
                footerButtonSection = {
                    EditModalFooterButtons(
                        isButtonEnabled = modalState.isFormValid,
                        onDeleteClick = { workSpaceState.deleteTask() },
                        onEditClick = { workSpaceState.editTask(modalState) },
                    )
                },
                modalState = modalState,
            )
        }
    }

    Scaffold(
        modifier = modifier,
        snackbarHost = { SnackbarHost(hostState = workSpaceState.snackbarHostState) },
    ) { paddingValues ->
        workSpaceState.selectedProject?.let { selectedProject ->
            Row(
                modifier = Modifier
                    .padding(paddingValues),
            ) {
                WorkSpaceSideBar(
                    projects = workSpaceState.projects,
                    selectedProject = selectedProject,
                    onChangeProject = { workSpaceState.selectedProject = it },
                )
                TaskBoard(
                    project = selectedProject,
                    onShowMoveSuccessSnackBar = { workSpaceState.showSnackBar(SnackbarType.MOVE_SUCCESS) },
                    onShowMoveFailedSnackbar = { workSpaceState.showSnackBar(SnackbarType.MOVE_FAILED) },
                    onShowCreateTaskModal = { workSpaceState.showCreateModal() },
                    onShowEditTaskModal = { taskCardData ->
                        workSpaceState.showEditModal(taskCardData)
                    },
                    onShowNoAssigneeSnackbar = { workSpaceState.showSnackBar(SnackbarType.MOVE_NO_ASSIGNEE) },
                )
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 1500)
@Composable
private fun WorkSpacePreview() {
    val assignees = ProfilePreviewData().values.toImmutableList()
    MaterialTheme {
        WorkSpace(
            projects = ProjectPreviewData().values.toImmutableList(),
            assignees = assignees,
        )
    }
}
