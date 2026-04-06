package woowacourse.kanban.board.model.project

import androidx.compose.runtime.mutableStateListOf
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import woowacourse.kanban.board.model.taskcard.Status
import woowacourse.kanban.board.model.taskcard.TaskCardData
import java.util.UUID

data class Project(
    val title: String,
    val initialTasks: ImmutableList<TaskCardData>,
    val id: String = UUID.randomUUID().toString(),
) {
    private val tasks = mutableStateListOf<TaskCardData>().apply {
        addAll(initialTasks)
    }

    val allTasksCount get() = tasks.size
    val todoTasks get() = tasks.filter { it.status == Status.TODO }.toImmutableList()
    val progressTasks get() = tasks.filter { it.status == Status.PROGRESS }.toImmutableList()
    val reviewTasks get() = tasks.filter { it.status == Status.REVIEW }.toImmutableList()
    val doneTasks get() = tasks.filter { it.status == Status.DONE }.toImmutableList()

    fun addTask(data: TaskCardData) = tasks.add(data)

    fun deleteTaskById(id: String?): Boolean {
        val task = id?.let { findTaskById(id) } ?: return false
        if (task.status.isCanDelete().not()) return false
        tasks.remove(task)
        return true
    }

    fun calculateDoneRate(): Float {
        if (allTasksCount == 0) return 0f
        return doneTasks.size.toFloat() / allTasksCount.toFloat()
    }

    fun findTaskById(id: String): TaskCardData? = tasks.firstOrNull { it.id == id }

    fun tryMoveTaskStatus(id: String, targetStatus: Status): MoveResult {
        val idx = tasks.indexOfFirst { it.id == id }
        if (idx == -1) return MoveResult.INVALID_MOVE
        val moveResult = tasks[idx].isTaskStatusUpdateAvailable(targetStatus)
        if (moveResult == MoveResult.SUCCESS) {
            val updatedTask = tasks[idx].updateTaskStatus(
                moveResult = moveResult,
                targetStatus = targetStatus,
            )
            tasks[idx] = updatedTask
        }
        return moveResult
    }

    fun tryUpdateTaskData(
        id: String,
        updateTaskCardData: TaskCardData,
    ): Boolean {
        val idx = tasks.indexOfFirst { it.id == id }
        if (idx == -1) return false
        val taskData = findTaskById(id) ?: return false
        tasks[idx] = taskData.updateData(updateTaskCardData = updateTaskCardData)
        return true
    }

    fun getTasksByStatus(status: Status): ImmutableList<TaskCardData> = when (status) {
        Status.TODO -> todoTasks
        Status.PROGRESS -> progressTasks
        Status.REVIEW -> reviewTasks
        Status.DONE -> doneTasks
    }
}
