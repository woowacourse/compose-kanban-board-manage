package woowacourse.kanban.board.ui.stateholder

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import woowacourse.kanban.board.domain.KanbanProject
import woowacourse.kanban.domain.KanbanTask
import woowacourse.kanban.domain.TaskStatus

class BoardState(initProject: KanbanProject) {

    private var project by mutableStateOf(initProject)

    private val totalTasks by derivedStateOf { project.getTasks() }

    val totalTaskCount by derivedStateOf { totalTasks.size }

    val todoCardList: List<KanbanTask> by derivedStateOf { project.getTasksByStatus(TaskStatus.TO_DO) }

    val inProgressCardList: List<KanbanTask> by derivedStateOf { project.getTasksByStatus(TaskStatus.IN_PROGRESS) }

    val doneCardList: List<KanbanTask> by derivedStateOf { project.getTasksByStatus(TaskStatus.DONE) }

    val progress by derivedStateOf {
        if (totalTasks.isEmpty()) 0.0 else doneCardList.size.toDouble() / totalTasks.size.toDouble()
    }

    val showDialog = mutableStateOf(false)

    fun addTask(task: KanbanTask) {
        project = project.addTask(task)
    }

    fun changeStatus(
        taskId: Long,
        status: TaskStatus,
    ) {
        project = project.changeStatus(taskId, status)
    }

    fun getTasksByStatus(status: TaskStatus): List<KanbanTask> {
        return project.getTasksByStatus(status)
    }
}
