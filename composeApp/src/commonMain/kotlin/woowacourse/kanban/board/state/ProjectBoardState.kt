package woowacourse.kanban.board.state

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import woowacourse.kanban.board.domain.KanbanBoard
import woowacourse.kanban.board.domain.Status
import woowacourse.kanban.board.domain.Task

class ProjectBoardState {
    var kanbanBoards by mutableStateOf(
        listOf(
            KanbanBoard(title = "Compose1"),
            KanbanBoard(title = "Compose2"),
            KanbanBoard(title = "Compose3너무너무너무너무너무너무너무너무"),
        )
    )
    var selectedIndex by mutableStateOf(0)

    val selectedKanbanBoardTask get() = kanbanBoards[selectedIndex]

    // 보드 선택
    fun selectedOnValueChange(kanbanBoard: KanbanBoard) {
        selectedIndex = kanbanBoards.indexOf(kanbanBoard)
    }

    fun isKanbanBoardTaskSelected(kanbanBoard: KanbanBoard): Boolean =
        kanbanBoards.indexOf(kanbanBoard) == selectedIndex

    fun addTask(task: Task) {
        updateSelectedBoard { it.addTask(task) }
    }

    fun editTask(task: Task) {
        val beforeBoard = selectedKanbanBoardTask
        val currentTask = beforeBoard.taskList.find { it.id == task.id } ?: return

        updateSelectedBoard { it.updateTask(task) }

        if (currentTask.status != task.status) {
            updateSelectedBoard { it.moveTaskStatus(task.id, task.status) }
        }
    }

    fun deleteTask(task: Task): Boolean {
        val beforeBoard = selectedKanbanBoardTask
        updateSelectedBoard { it.deleteTask(task) }
        val afterBoard = selectedKanbanBoardTask

        return beforeBoard != afterBoard
    }

    fun moveTaskStatus(taskId: String, targetStatus: Status): MoveTaskStatusResult {
        val beforeBoard = selectedKanbanBoardTask
        val sourceTask = beforeBoard.taskList.find { it.id == taskId }

        updateSelectedBoard { it.moveTaskStatus(taskId, targetStatus) }
        val afterBoard = selectedKanbanBoardTask

        return resolveMoveResult(
            beforeBoard = beforeBoard,
            afterBoard = afterBoard,
            sourceTask = sourceTask,
            targetStatus = targetStatus,
        )
    }

    private fun updateSelectedBoard(update: (KanbanBoard) -> KanbanBoard) {
        kanbanBoards = kanbanBoards.mapIndexed { index, board ->
            if (index == selectedIndex) update(board) else board
        }
    }
}
