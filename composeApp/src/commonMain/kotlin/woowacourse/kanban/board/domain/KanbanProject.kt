package woowacourse.kanban.board.domain

import woowacourse.kanban.domain.KanbanTask
import woowacourse.kanban.domain.TaskStatus

class KanbanProject(private val inputTasks: List<KanbanTask>, val title: String = "") {
    private val tasks: MutableList<KanbanTask> = inputTasks.toMutableList()

    fun copy(
        newInputTasks: List<KanbanTask> = inputTasks,
        newTitle: String = title,
    ): KanbanProject {
        return KanbanProject(newInputTasks, newTitle)
    }

    fun addTask(task: KanbanTask): KanbanProject {
        return copy(newInputTasks = tasks + task, newTitle = title)
    }

    fun changeStatus(
        taskId: Long,
        status: TaskStatus,
    ): KanbanProject {
        return copy(
            newInputTasks = tasks.map {
                if (it.data.id == taskId) it.copy(inputStatus = status) else it
            },
        )
    }

    // List로 반환하더라도 toMutableList()를 통해 캐스팅하면 원본 리스트에 대해서
    // 조작이 가능하다. 반환할 때 toList()를 사용해 새로운 리스트를 만들어 주면
    // 원본 리스트와는 다른 리스트로 반환되게 됨으로 조작이 차단된다.
    fun getTasks(): List<KanbanTask> {
        return tasks.toList()
    }

    fun getTasksByStatus(status: TaskStatus): List<KanbanTask> {
        return tasks.filter { it.status == status }
    }

    fun deleteTask(taskId: Long): KanbanProject {
        val targetIndex = tasks.indexOfFirst { it.data.id == taskId }
        tasks.removeAt(targetIndex)

        return copy(newInputTasks = tasks.toList())
    }

    fun judgeTaskRemovable(taskId: Long): Boolean {
        val targetIndex = tasks.indexOfFirst { it.data.id == taskId }

        return when (tasks[targetIndex].status) {
            TaskStatus.TO_DO -> true
            TaskStatus.IN_PROGRESS -> true
            TaskStatus.DONE -> false
            TaskStatus.REVIEW -> false
        }
    }
}
