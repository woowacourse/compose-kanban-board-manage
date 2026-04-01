package woowacourse.kanban.board.ui.stateholder

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import woowacourse.kanban.board.domain.KanbanProject
import woowacourse.kanban.board.domain.TaskReturnType
import woowacourse.kanban.domain.KanbanTask
import woowacourse.kanban.domain.TaskStatus

class BoardState(initProject: KanbanProject) {

    private var project by mutableStateOf(initProject)

    private val totalTasks by derivedStateOf { project.getTasks() }

    val totalTaskCount by derivedStateOf { totalTasks.size }

    val progress by derivedStateOf {
        if (totalTasks.isEmpty()) 0.0 else project.getTasksByStatus(TaskStatus.DONE).size.toDouble() / totalTasks.size.toDouble()
    }

    var currentTask by mutableStateOf<KanbanTask?>(null)
        private set

    var showDialog by mutableStateOf(false)
        private set

    // 키고 끄고, 수정/삭제, 신규를 표시해야함, 수정/삭제일경우 태스크값을 넘겨야함
    fun toggleDialog(
        controlValue: Boolean,
        task: KanbanTask? = null,
    ) {
        showDialog = controlValue
        currentTask = task
    }

    fun addTask(task: KanbanTask) {
        project = project.addTask(task)
    }

    fun isAssigned(taskId: Long): TaskReturnType? {
        val targetIndex = project.getTasks().indexOfFirst { it.data.id == taskId }
        val targetTask = project.getTasks()[targetIndex]
        return if (targetTask.data.assignee == null) {
            TaskReturnType.NOT_ASSIGNED
        } else {
            null
        }
    }

    fun changeStatus(
        taskId: Long,
        status: TaskStatus,
    ): TaskReturnType {
        if (status == TaskStatus.TO_DO) {
            val result = isAssigned(
                taskId = taskId,
            )
            if (result == TaskReturnType.NOT_ASSIGNED) {
                return result
            }
        }
        val targetIndex = project.getTasks().indexOfFirst { it.data.id == taskId }
        val targetTask = project.getTasks()[targetIndex]
        if (targetTask.isChangeable(status)) {
            project = project.changeStatus(taskId, status)
            return TaskReturnType.TASK_STATUS_SUCCESS
        }
        return TaskReturnType.NOT_UPDATABLE
    }

    fun getTasksByStatus(status: TaskStatus): List<KanbanTask> {
        return project.getTasksByStatus(status)
    }

    fun deleteTask(taskId: Long): TaskReturnType {
        val targetIndex = project.getTasks().indexOfFirst { it.data.id == taskId }
        val targetTask = project.getTasks()[targetIndex]
        if (targetTask.isRemovable) {
            project = project.deleteTask(taskId)
            return TaskReturnType.DELETE_SUCCESS
        }
        return TaskReturnType.NOT_DELETABLE
    }

    fun updateTask(task: KanbanTask) {
        project = project.updateTask(task)
    }
}
