package woowacourse.kanban.board.state

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import woowacourse.kanban.board.domain.Status
import kotlin.collections.component1
import kotlin.collections.component2

class KanbanBoardState {

    var showDialog by mutableStateOf(false)
    var snackBarState by mutableStateOf(SnackBarState())
    var dialogState by mutableStateOf(DialogState())
    var draggedTaskId by mutableStateOf<String?>(null)
    var currentDragPosition by mutableStateOf<Offset?>(null)
    val columnBounds = mutableStateMapOf<Status, Rect>()
    var draggedTaskSourceStatus by mutableStateOf<Status?>(null)

    var draggedTaskNickname by mutableStateOf<String?>(null)

    fun onTaskDragEnd(state: KanbanBoardState, onMoveTaskStatus: (String, Status) -> Unit) {
        val dropPosition = state.currentDragPosition ?: run {
            resetDragState()
            return
        }
        val targetStatus = state.columnBounds.entries
            .firstOrNull { (_, rect) -> rect.contains(dropPosition) }?.key
            ?: run {
                resetDragState()
                return
            }

        val taskId = state.draggedTaskId

        if (taskId == null) {
            resetDragState()
            return
        }

        onMoveTaskStatus(taskId, targetStatus)
        resetDragState()
    }

    fun onTaskCreated() {
        showSnackBar("새로운 태스크가 생성되었습니다.")
    }

    fun onTaskEdited() {
        showSnackBar("태스크가 수정되었습니다.")
    }

    fun onTaskDeleted(isDeleted: Boolean) {
        if (isDeleted) {
            showSnackBar("태스크가 삭제되었습니다.")
        }
        else {
            showSnackBar("해당 상태에서는 태스크 삭제가 불가합니다.")
        }
    }

    fun onTaskMoveResult(result: MoveTaskStatusResult) {
        when (result) {
            MoveTaskStatusResult.SUCCESS -> showSnackBar("태스크가 이동되었습니다.")
            MoveTaskStatusResult.ASSIGNEE_REQUIRED -> showSnackBar("담당자를 지정해야 상태를 옮길 수 있습니다.")
            MoveTaskStatusResult.INVALID_TRANSITION -> showSnackBar("해당 상태로 옮길 수 없습니다.")
        }
    }

    fun hideSnackBar() {
        snackBarState = SnackBarState(isVisible = false)
    }

    fun showSnackBar(message: String) {
        snackBarState = SnackBarState(
            isVisible = true,
            text = message,
        )
    }

    private fun resetDragState() {
        currentDragPosition = null
        draggedTaskId = null
        draggedTaskSourceStatus = null
        draggedTaskNickname = null
    }
}
