package woowacourse.kanban.board.ui.board.state

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import java.util.UUID
import woowacourse.kanban.board.domain.Task
import woowacourse.kanban.board.domain.TaskState

class ProjectState(val id: UUID = UUID.randomUUID(), val name: String, initialTasks: MutableList<Task> = mutableListOf()) {
    var _tasks by mutableStateOf(initialTasks)
        private set

    val tasks: List<Task> get() = _tasks.toList()

    val totalCount: Int
        get() = _tasks.size

    val completedRate: Int
        get() = if (_tasks.isEmpty()) 0 else (countByState(TaskState.DONE).toDouble() / _tasks.size * 100).toInt()

    fun addTask(task: Task) {
        val newTasks = tasks.toMutableList()
        newTasks.add(task)
        _tasks = newTasks
    }

    fun changeTaskState(taskId: UUID, taskState: TaskState) {
        val newTasks = tasks.toMutableList()
        val index = newTasks.indexOfFirst { it.id == taskId }
        if (index != -1) {
            newTasks[index] = newTasks[index].changeTaskState(taskState)
        }
        _tasks = newTasks
    }

    fun updateTask(taskId: UUID, task: Task) {
        val newTasks = tasks.toMutableList()
        val index = newTasks.indexOfFirst { it.id == taskId }
        if (index != -1) {
            newTasks[index] = task
        }
        _tasks = newTasks
    }

    fun deleteTask(taskId: UUID) {
        val newTasks = tasks.toMutableList()
        newTasks.removeIf { it.id == taskId }
        _tasks = newTasks
    }

    fun countByState(taskState: TaskState): Int {
        return _tasks.count { it.taskState == taskState }
    }

    fun getTasksByState(taskState: TaskState): List<Task> {
        return _tasks.filter { it.taskState == taskState }
    }
}
