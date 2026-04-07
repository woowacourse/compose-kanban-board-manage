package woowacourse.kanban.board.ui.stateholder

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import woowacourse.kanban.board.domain.DeleteResult
import woowacourse.kanban.board.domain.KanbanProject
import woowacourse.kanban.board.domain.KanbanTask
import woowacourse.kanban.board.domain.StatusChangeResult
import woowacourse.kanban.domain.TaskStatus

class BoardState(initProject: KanbanProject) {

    private var project by mutableStateOf(initProject)

    private val totalTasks by derivedStateOf { project.getTasks() }

    val totalTaskCount by derivedStateOf { totalTasks.size }

    val progress by derivedStateOf {
        if (totalTasks.isEmpty()) 0.0 else project.getTasksByStatus(TaskStatus.DONE).size.toDouble() / totalTasks.size.toDouble()
    }

    fun addTask(task: KanbanTask) {
        project = project.addTask(task)
    }

    fun changeStatus(
        taskId: Long,
        status: TaskStatus,
    ): StatusChangeResult {
        val result = project.changeStatus(taskId, status)
        if (result is StatusChangeResult.Success) {
            project = result.project
        }
        return result
    }

    fun getTasksByStatus(status: TaskStatus): List<KanbanTask> {
        return project.getTasksByStatus(status)
    }

    fun deleteTask(taskId: Long): DeleteResult {
        val result = project.deleteTask(taskId)
        if (result is DeleteResult.Success) {
            project = result.project
        }
        return result
    }

    fun updateTask(task: KanbanTask) {
        project = project.updateTask(task)
    }
}
