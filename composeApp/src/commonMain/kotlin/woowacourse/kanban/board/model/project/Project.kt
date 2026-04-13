package woowacourse.kanban.board.model.project

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import woowacourse.kanban.board.model.taskcard.Status
import woowacourse.kanban.board.model.taskcard.TaskCard

class Project(
    val id: String,
    val title: String,
    private val tasks: ImmutableList<TaskCard>,
) {
    val allTasksCount get() = tasks.size

    fun filterTasksbyStatus(status: Status): ImmutableList<TaskCard> =
        tasks.filter { it.status == status }.toImmutableList()

    fun addCard(data: TaskCard): Project =
        Project(
            title = title,
            tasks = (tasks + data).toImmutableList(),
            id = id,
        )

    fun calculateDoneRate(): Float {
        val totalTasks = allTasksCount
        if (totalTasks == 0) return 0f
        return filterTasksbyStatus(Status.DONE).size.toFloat() / totalTasks.toFloat()
    }

    fun findTaskById(id: String): TaskCard? = tasks.firstOrNull { it.id == id }

    fun updateTaskStatus(id: String, targetStatus: Status): Project {
        if (tasks.none { it.id == id }) return this

        val updatedTasks = tasks.map { task ->
            if (task.id == id) task.changeStatus(afterStatus = targetStatus) else task
        }.toImmutableList()

        return Project(
            title = title,
            tasks = updatedTasks,
            id = this.id,
        )
    }

    fun updateTask(id: String, updatedTask: TaskCard): Project {
        if (tasks.none { it.id == id }) return this

        val updatedTasks = tasks.map { task ->
            if (task.id == id) task.update(updatedTask) else task
        }.toImmutableList()

        return Project(
            title = title,
            tasks = updatedTasks,
            id = this.id
        )
    }

    fun deleteTask(id: String): Project {
        return Project(
            title = title,
            tasks = tasks.filterNot { it.id == id }.toImmutableList(),
            id = this.id
        )
    }
}
