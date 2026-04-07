package woowacourse.kanban.board.domain

import woowacourse.kanban.domain.TaskStatus

class KanbanProject(inputTasks: List<KanbanTask>, val title: String = "") {
    private val tasks: List<KanbanTask> = inputTasks.toList()

    fun copy(
        newInputTasks: List<KanbanTask> = tasks,
        newTitle: String = title,
    ): KanbanProject {
        return KanbanProject(newInputTasks, newTitle)
    }

    fun addTask(task: KanbanTask): KanbanProject {
        return copy(
            newInputTasks = tasks + task,
            newTitle = title,
        )
    }

    fun changeStatus(
        taskId: Long,
        status: TaskStatus,
    ): StatusChangeResult {
        val targetIndex = tasks.indexOfFirst { it.data.id == taskId }
        if (targetIndex == -1) return StatusChangeResult.NotFound
        val targetTask = tasks[targetIndex]

        return when (val result = targetTask.changeStatus(status)) {
            TaskChangeResult.NotAssigned -> StatusChangeResult.NotAssigned
            TaskChangeResult.NotChangeable -> StatusChangeResult.NotChangeable
            is TaskChangeResult.Success -> StatusChangeResult.Success(
                copy(
                    newInputTasks = tasks.map {
                        if (it.data.id == taskId)
                            result.task
                        else it
                    },
                ),
            )
        }
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

    fun deleteTask(taskId: Long): DeleteResult {
        val targetIndex = tasks.indexOfFirst { it.data.id == taskId }
        if (targetIndex == -1) return DeleteResult.NotFound
        val targetTask = tasks[targetIndex]

        return if (targetTask.status.isRemovable) {
            DeleteResult.Success(copy(newInputTasks = tasks.filter { it.data.id != taskId }))
        } else DeleteResult.NotDeletable
    }

    fun updateTask(task: KanbanTask): KanbanProject {
        return copy(
            newInputTasks = tasks.map {
                if (it.data.id == task.data.id)
                    task
                else it
            },
        )
    }
}
