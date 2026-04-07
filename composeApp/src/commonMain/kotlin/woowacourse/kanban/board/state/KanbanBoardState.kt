package woowacourse.kanban.board.state

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import kotlin.time.Duration.Companion.milliseconds
import kotlinx.coroutines.delay
import woowacourse.kanban.board.constant.SnackbarMessage
import woowacourse.kanban.board.model.BoardData
import woowacourse.kanban.board.model.DialogStatus
import woowacourse.kanban.board.model.MoveStatus
import woowacourse.kanban.board.model.Status

class KanbanBoardState {

    var showCreateDialog by mutableStateOf(false)
    var showEditDialog by mutableStateOf(false)
    var isShowSnackBar by mutableStateOf(false)

    var snackbarMessage by mutableStateOf("")

    var draggedTask by mutableStateOf<BoardData?>(null)
    var currentDragPosition by mutableStateOf<Offset?>(null)
    val columnBounds = mutableStateMapOf<Status, Rect>()

    var boardDataState by mutableStateOf(BoardDataState())

    var dialogStatus by mutableStateOf(DialogStatus.CREATE)

    fun onCreateClick() {
        dialogStatus = DialogStatus.CREATE
        boardDataState = BoardDataState()
        showCreateDialog = true
    }

    fun onTaskCreate() {
        onDismissRequest()
        snackbarMessage = SnackbarMessage.CREATE_SUCCESS_MESSAGE
        onShowSnackBar()
    }

    fun onEditTask() {
        showEditDialog = false
        snackbarMessage = SnackbarMessage.EDIT_SUCCESS_MESSAGE
        onShowSnackBar()
    }

    fun onDeleteTask() {
        showEditDialog = false
        snackbarMessage = SnackbarMessage.DELETE_SUCCESS_MESSAGE
        onShowSnackBar()
    }

    fun onNotDeleteTask() {
        showEditDialog = false
        snackbarMessage = SnackbarMessage.DELETE_FAILED_MESSAGE
        onShowSnackBar()
    }

    fun onDismissRequest() {
        if (showCreateDialog) showCreateDialog = false else showEditDialog = false
    }

    fun onShowSnackBar() {
        isShowSnackBar = true
    }

    suspend fun showSnackBar() {
        delay(3000.milliseconds)
        isShowSnackBar = false
    }

    fun onSnackBarCancelClick() {
        isShowSnackBar = false
    }

    fun getIsDropTarget(status: Status): Boolean {
        return currentDragPosition?.let { columnBounds[status]?.contains(it) } ?: false
    }

    fun onBoundsChanged(rect: Rect, status: Status) {
        columnBounds[status] = rect
    }

    fun onTaskDragStart(task: BoardData) {
        draggedTask = task
    }

    fun onTaskDragChange(pos: Offset) {
        currentDragPosition = pos
    }

    fun onTaskDragEnd(onMoveBoardDataStatus: (BoardData, Status) -> Unit) {
        val dropPosition = currentDragPosition ?: return
        val targetStatus = columnBounds.entries
            .firstOrNull { (_, rect) -> rect.contains(dropPosition) }?.key

        draggedTask?.let { task ->
            if (targetStatus != null && task.status != targetStatus) {
                snackbarMessage = when (task.getMoveStatus(targetStatus)) {
                    MoveStatus.SUCCESS -> {
                        SnackbarMessage.MOVE_SUCCESS_MESSAGE
                    }

                    MoveStatus.FAILED -> {
                        SnackbarMessage.MOVE_FAILED_MESSAGE
                    }

                    MoveStatus.IN_PROGRESS -> {
                        SnackbarMessage.MOVE_IN_PROGRESS_FAILED_MESSAGE
                    }
                }
                onMoveBoardDataStatus(task, targetStatus)
                isShowSnackBar = true
            }
        }
        currentDragPosition = null
        draggedTask = null
    }

    fun onTaskDragCancel() {
        currentDragPosition = null
        draggedTask = null
    }

    fun onCardClick(boardData: BoardData) {
        boardDataState = BoardDataState(
            id = boardData.id,
            title = boardData.title,
            description = boardData.description,
            tags = boardData.tags,
            status = boardData.status,
            name = boardData.nickname,
        )
        dialogStatus = DialogStatus.EDIT
        showEditDialog = true
    }
}
