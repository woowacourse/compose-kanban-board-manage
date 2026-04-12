package woowacourse.kanban.domain.project

import java.util.UUID
import woowacourse.kanban.domain.task.KanbanTask
import woowacourse.kanban.domain.task.TaskStatus

data class KanbanProject(private val tasks: List<KanbanTask> = emptyList(), val title: String = "") {

    val projectTasks: List<KanbanTask> get() = tasks

    fun getTasksWithStatus(status: TaskStatus): List<KanbanTask> = tasks.filter { it.status == status }

    fun getTaskIndexWithId(id: UUID) = tasks.indexOfFirst { it.data.id == id }

    fun getProgress(): Double {
        return if (tasks.isEmpty()) 0.0 else getTasksWithStatus(TaskStatus.DONE).size.toDouble() / tasks.size.toDouble()
    }

    fun getTaskWithID(id: UUID) = tasks.first { it.data.id == id }

    fun addTask(inputTask: KanbanTask): KanbanProject {
        return copy(tasks = tasks + inputTask)
    }

    fun changeTaskStatus(index: Int, status: TaskStatus): KanbanProject {
        val newTasks = tasks.toMutableList()
        newTasks[index] = newTasks[index].changeStatus(status)
        return copy(tasks = newTasks)
    }

    fun editTask(id: UUID, inputTask: KanbanTask): KanbanProject {
        val index = getTaskIndexWithId(id)
        if (index == -1) return this
        val newTasks = tasks.toMutableList()
        newTasks[index] = inputTask
        return copy(tasks = newTasks)
    }

    fun deleteTask(id: UUID): KanbanProject {
        val index = getTaskIndexWithId(id)
        if (index == -1) return this
        val newTasks = tasks.toMutableList()
        newTasks.removeAt(index)
        return copy(tasks = newTasks)
    }
}
