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
    var isCreateTaskDialog by mutableStateOf(false)
        private set

    fun updateSelectedProjectIndex(newIndex: Int) {
        projects.getOrNull(newIndex) ?: return
        selectedProjectIndex = newIndex
        updateTasks()
    }

    fun addTask(task: KanbanTask) {
        val project = projects.getOrNull(selectedProjectIndex) ?: return
        kanbanBoard.addTask(task)
        project.addTaskId(task.id)
        updateTasks()
    }

    fun moveTask(
        task: KanbanTask,
        targetStatus: Status,
    ) {
        kanbanBoard.changeTaskStatus(task, targetStatus)
        updateTasks()
    }

    fun deleteTask(task: KanbanTask) {
        val project = projects.getOrNull(selectedProjectIndex) ?: return
        kanbanBoard.deleteTask(task)
        project.deleteTaskId(task.id)
        updateTasks()
    }

    fun editTask(originalTask: KanbanTask, editedTask: KanbanTask) {
        val project = projects.getOrNull(selectedProjectIndex) ?: return
        kanbanBoard.editTask(originalTask, editedTask)
        project.deleteTaskId(originalTask.id)
        project.addTaskId(editedTask.id)
        updateTasks()
    }

    fun showCreateTaskDialog() {
        isCreateTaskDialog = true
    }

    fun hideCreateTaskDialog() {
        isCreateTaskDialog = false
    }

    fun getProjectsTitles(): List<String> = projects.map { it.title }

    fun getCompleteCount(): Int = tasks.count { it.status == Status.DONE }

    fun getTotalCount(): Int = tasks.size

    private fun updateTasks() {
        tasks = loadTasks()
    }

    private fun loadTasks(): List<KanbanTask> {
        val project = projects.getOrNull(selectedProjectIndex) ?: return emptyList()
        return kanbanBoard.getTasksByIds(project.getTaskIds())
    }
}
