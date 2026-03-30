package woowacourse.kanban.board.ui.stateholder

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import woowacourse.kanban.domain.KanbanTask
import woowacourse.kanban.domain.TaskStatus

class BoardState(initTasks: List<KanbanTask>) {

    private val totalTasks = mutableStateListOf<KanbanTask>().apply { addAll(initTasks) }

    val totalTaskCount by derivedStateOf { totalTasks.size }

    val todoCardList: List<KanbanTask> by derivedStateOf { totalTasks.filter { task -> task.status == TaskStatus.TO_DO } }

    val inProgressCardList: List<KanbanTask> by derivedStateOf { totalTasks.filter { task -> task.status == TaskStatus.IN_PROGRESS } }

    val doneCardList: List<KanbanTask> by derivedStateOf { totalTasks.filter { task -> task.status == TaskStatus.DONE } }

    val progress by derivedStateOf {
        if (totalTasks.isEmpty()) 0.0 else doneCardList.size.toDouble() / totalTasks.size.toDouble()
    }

    val showDialog = mutableStateOf(false)

    // List로 반환하더라도 toMutableList()를 통해 캐스팅하면 원본 리스트에 대해서
    // 조작이 가능하다. 반환할 때 toList()를 사용해 새로운 리스트를 만들어 주면
    // 원본 리스트와는 다른 리스트로 반환되게 됨으로 조작이 차단된다.
    fun getTotalTasks(): List<KanbanTask> {
        return totalTasks.toList()
    }

    fun addTask(task: KanbanTask) {
        totalTasks.add(task)
    }

    fun changeStatus(
        status: TaskStatus,
        idx: Int,
    ) {
        totalTasks[idx] = totalTasks[idx].copy(inputStatus = status)
    }

    fun getTasksByStatus(status: TaskStatus): List<KanbanTask> {
        return when (status) {
            TaskStatus.TO_DO -> todoCardList
            TaskStatus.IN_PROGRESS -> inProgressCardList
            TaskStatus.DONE -> doneCardList
        }
    }
}
