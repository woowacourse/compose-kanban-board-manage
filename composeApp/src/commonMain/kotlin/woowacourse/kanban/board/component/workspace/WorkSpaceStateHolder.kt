package woowacourse.kanban.board.component.workspace

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlinx.collections.immutable.ImmutableList
import woowacourse.kanban.board.model.project.Project
import woowacourse.kanban.board.model.taskcard.TaskCardData
import woowacourse.kanban.board.model.workspace.SnackbarType

class WorkSpaceState(
    val projects: ImmutableList<Project>,
) {
    val snackbarHostState = SnackbarHostState()
    var selectedProject by mutableStateOf(projects.firstOrNull())
    var shouldShowSnackbar by mutableStateOf<SnackbarType?>(null)
    var isShowCreateModal by mutableStateOf(false)
    var isShowEditModal by mutableStateOf(false)
    var currentEditTask by mutableStateOf<TaskCardData?>(null)

    fun addTask(modalState: ModalState) {
        val data = modalState.toTaskCardData()
        selectedProject?.addTask(data)
        closeCreateModal()
        showSnackBar(SnackbarType.ADD)
    }

    fun deleteTask() {
        val taskId = currentEditTask?.id
        val isDeleteSuccess = selectedProject?.deleteTaskById(taskId) ?: false
        closeEditModal()
        if (isDeleteSuccess) showSnackBar(SnackbarType.DELETE_SUCCESS)
        else showSnackBar(SnackbarType.DELETE_FAILED)
    }

    fun editTask(modalState: ModalState) {
        val taskId = currentEditTask?.id
        val data = modalState.toTaskCardData()
        if (taskId != null && modalState.isFormValid) {
            val updateResult = selectedProject?.tryUpdateTaskData(
                id = taskId,
                updateTaskCardData = data,
            )
            if (updateResult == true) {
                showSnackBar(SnackbarType.EDIT)
                closeEditModal()
            }
        }
    }

    fun showSnackBar(snackbarType: SnackbarType) {
        shouldShowSnackbar = snackbarType
    }

    fun hideSnackbar() {
        shouldShowSnackbar = null
    }

    fun showCreateModal() {
        isShowCreateModal = true
    }

    fun showEditModal(taskCardData: TaskCardData) {
        currentEditTask = taskCardData
        isShowEditModal = true
    }

    fun closeCreateModal() {
        isShowCreateModal = false
    }

    fun closeEditModal() {
        isShowEditModal = false
    }
}

@Composable
fun rememberWorkSpaceState(projects: ImmutableList<Project>): WorkSpaceState = remember { WorkSpaceState(projects) }
