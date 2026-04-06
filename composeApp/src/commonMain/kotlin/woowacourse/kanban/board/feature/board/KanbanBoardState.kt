package woowacourse.kanban.board.feature.board

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import woowacourse.kanban.board.domain.KanbanBoard
import woowacourse.kanban.board.domain.KanbanTask
import woowacourse.kanban.board.domain.TaskStatus
import woowacourse.kanban.board.feature.board.component.dialog.model.TaskFormResult

@Stable
class KanbanBoardState(initialBoard: KanbanBoard = KanbanBoard()) {
    var kanbanBoard by mutableStateOf(initialBoard)
        private set
    var isTaskDialogVisible by mutableStateOf(false)
        private set
    var selectedTask by mutableStateOf<KanbanTask?>(null)
        private set

    private val _events = MutableSharedFlow<KanbanBoardEvent>(extraBufferCapacity = 64)
    val events: SharedFlow<KanbanBoardEvent> = _events.asSharedFlow()

    fun showTaskDialog() {
        isTaskDialogVisible = true
    }

    fun hideTaskDialog() {
        isTaskDialogVisible = false
    }

    fun showEditDialog(task: KanbanTask) {
        selectedTask = task
    }

    fun hideEditDialog() {
        selectedTask = null
    }

    fun moveTask(task: KanbanTask, targetStatus: TaskStatus) {
        when {
            !task.status.isTransitionableTo(targetStatus) -> {
                _events.tryEmit(KanbanBoardEvent.TaskTransitionFailed)
            }
            targetStatus.isAssigneeRequired && task.crewName == null -> {
                _events.tryEmit(KanbanBoardEvent.TaskAssigneeMissing)
            }
            else -> {
                kanbanBoard = kanbanBoard.moveTask(task.id, targetStatus)
                _events.tryEmit(KanbanBoardEvent.TaskMoved)
            }
        }
    }

    fun addTask(result: TaskFormResult) {
        runCatching {
            val newTask = KanbanTask(
                title = result.title,
                description = result.description,
                tags = result.tags,
                status = result.status,
                crewName = result.assignee,
            )
            kanbanBoard = kanbanBoard.addTask(newTask)
            hideTaskDialog()
        }.onSuccess {
            _events.tryEmit(KanbanBoardEvent.TaskAdded)
        }.onFailure {
            _events.tryEmit(KanbanBoardEvent.TaskAddFailed)
        }
    }

    fun editTask(task: KanbanTask, result: TaskFormResult) {
        runCatching {
            val updatedTask = task.copy(
                title = result.title,
                description = result.description,
                tags = result.tags,
                status = result.status,
                crewName = result.assignee,
            )
            kanbanBoard = kanbanBoard.updateTask(updatedTask)
            hideEditDialog()
        }.onSuccess {
            _events.tryEmit(KanbanBoardEvent.TaskEdited)
        }.onFailure {
            _events.tryEmit(KanbanBoardEvent.TaskEditFailed)
        }
    }

    fun deleteTask(task: KanbanTask) {
        if (!task.status.isDeletable) {
            _events.tryEmit(KanbanBoardEvent.TaskDeleteFailed)
            return
        }
        kanbanBoard = kanbanBoard.deleteTask(task.id)
        hideEditDialog()
        _events.tryEmit(KanbanBoardEvent.TaskDeleted)
    }
}

@Composable
fun rememberKanbanBoardState(): KanbanBoardState = remember { KanbanBoardState() }
