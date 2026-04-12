package woowacourse.kanban.board

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import java.util.UUID
import woowacourse.kanban.board.constant.SnackBarText
import woowacourse.kanban.board.utils.SnackBarEvent
import woowacourse.kanban.domain.project.KanbanProject
import woowacourse.kanban.domain.task.KanbanTask
import woowacourse.kanban.domain.task.TaskStatus

class BoardState(inputProject: KanbanProject) {
    val project = mutableStateOf(inputProject)

    private val showDialog = mutableStateOf(false)

    private val isEditTask = mutableStateOf(false)

    fun showDialogValue() = showDialog.value

    fun isEditTaskValue() = isEditTask.value

    fun toggleDialog() {
        showDialog.value = !showDialog.value
    }

    fun toggleEditTask() {
        isEditTask.value = !isEditTask.value
    }

    var snackBarEvent by mutableStateOf<SnackBarEvent?>(null)
        private set

    private fun snackBarTrigger(message: String) {
        snackBarEvent = SnackBarEvent(message = message)
    }

    fun getTaskWithId(targetId: UUID): KanbanTask {
        return project.value.getTaskWithID(targetId)
    }

    fun addTask(inputTask: () -> KanbanTask) {
        try {
            project.value = project.value.addTask(inputTask())
            snackBarTrigger(SnackBarText.CREATE_TASK)
        } catch (e: IllegalArgumentException) {
            snackBarTrigger(SnackBarText.NONE_ASSIGNEE)
        }
    }

    fun changeTaskStatus(targetIndex: Int, targetStatus: TaskStatus) {
        try {
            project.value = project.value.changeTaskStatus(targetIndex, targetStatus)
            snackBarTrigger(SnackBarText.MOVE_TASK)
        } catch (e: IllegalStateException) {
            snackBarTrigger(SnackBarText.INVALID_MOVE_TASK)
        } catch (e: IllegalArgumentException) {
            snackBarTrigger(SnackBarText.NONE_ASSIGNEE_MOVE)
        }
    }

    fun deleteTask(targetId: UUID) {
        val isDeletable = project.value.getTaskWithID(targetId).isDeletable
        if (!isDeletable) {
            snackBarTrigger(SnackBarText.INVALID_DELETE_TASK)
            return
        }
        project.value = project.value.deleteTask(targetId)
        snackBarTrigger(SnackBarText.DELETE_TASK)
    }

    fun editTask(targetId: UUID, taskCreator: () -> KanbanTask) {
        try {
            val inputTask = taskCreator()
            project.value = project.value.editTask(targetId, inputTask)
            snackBarTrigger(SnackBarText.EDIT_TASK)
        } catch (e: IllegalArgumentException) {
            snackBarTrigger(SnackBarText.NONE_ASSIGNEE_MOVE)
        } catch (e: IllegalStateException) {
            snackBarTrigger(SnackBarText.INVALID_MOVE_TASK)
        }
    }
}
