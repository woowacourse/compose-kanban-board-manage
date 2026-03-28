package woowacourse.kanban.board.ui.screen.board

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import woowacourse.kanban.board.domain.KanbanBoard
import woowacourse.kanban.board.domain.KanbanProject
import woowacourse.kanban.board.domain.KanbanTask
import woowacourse.kanban.board.domain.dialog.Status

class KanbanBoardState(
    val kanbanBoard: KanbanBoard,
    val projects: List<KanbanProject>,
) {
    var selectedProjectIndex by mutableIntStateOf(0)
        private set
    var tasks = mutableStateListOf<KanbanTask>()
        private set
    var isNewTaskDialog by mutableStateOf(false)
        private set
    val snackBarHostState = SnackbarHostState()
    var snackbarMessage by mutableStateOf<SnackbarMessage?>(null)
        private set

    fun updateSelectedProjectIndex(newIndex: Int) {
        selectedProjectIndex = newIndex
        updateTasks()
    }

    fun addTask(kanbanTask: KanbanTask) {
        kanbanBoard.addTask(kanbanTask)
        projects[selectedProjectIndex].addTaskId(kanbanTask.id)
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

    fun showSnackbar(message: SnackbarMessage) {
        snackbarMessage = message
    }

    fun clearSnackbar() {
        snackbarMessage = null
    }

    private fun updateTasks() {
        tasks.clear()
        tasks.addAll(getTasks())
    }

    private fun getTasks(): List<KanbanTask> {
        val project = projects.getOrNull(selectedProjectIndex) ?: return emptyList()
        return kanbanBoard.getTasks(project.getTaskIds())
    }
}
