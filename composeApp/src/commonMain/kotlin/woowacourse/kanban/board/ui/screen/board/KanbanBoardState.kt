package woowacourse.kanban.board.ui.screen.board

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import woowacourse.kanban.board.domain.KanbanBoard
import woowacourse.kanban.board.domain.KanbanProject
import woowacourse.kanban.board.domain.KanbanTask
import woowacourse.kanban.board.domain.Status

class KanbanBoardState(
    val kanbanBoard: KanbanBoard,
    val projects: List<KanbanProject>,
) {
    var selectedProjectIndex by mutableIntStateOf(0)
        private set
    var tasks by mutableStateOf<List<KanbanTask>>(emptyList())
        private set
    var isNewTaskDialog by mutableStateOf(false)
        private set

    fun updateSelectedProjectIndex(newIndex: Int) {
        projects.getOrNull(newIndex) ?: return
        selectedProjectIndex = newIndex
        updateTasks()
    }

    fun addTask(kanbanTask: KanbanTask) {
        val project = projects.getOrNull(selectedProjectIndex) ?: return
        kanbanBoard.addTask(kanbanTask)
        project.addTaskId(kanbanTask.id)
        updateTasks()
    }

    fun moveTask(
        task: KanbanTask,
        targetStatus: Status,
    ) {
        kanbanBoard.changeTaskStatus(task, targetStatus)
        updateTasks()
    }

    fun showNewTaskDialog() {
        isNewTaskDialog = true
    }

    fun hideNewTaskDialog() {
        isNewTaskDialog = false
    }

    fun getProjectsTitles(): List<String> = projects.map { it.title }

    fun getCompleteCount(): Int = tasks.count { it.status == Status.DONE }

    fun getTotalCount(): Int = tasks.size

    private fun updateTasks() {
        tasks = loadTasks()
    }

    private fun loadTasks(): List<KanbanTask> {
        val project = projects.getOrNull(selectedProjectIndex) ?: return emptyList()
        return kanbanBoard.getTasks(project.getTaskIds())
    }
}
