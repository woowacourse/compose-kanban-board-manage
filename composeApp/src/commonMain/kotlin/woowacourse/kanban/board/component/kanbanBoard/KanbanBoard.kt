package woowacourse.kanban.board.component.kanbanBoard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import kotlin.time.Duration.Companion.milliseconds
import kotlinx.coroutines.delay
import woowacourse.kanban.board.component.dialog.layout.TaskCreateDialog
import woowacourse.kanban.board.domain.KanbanBoard
import woowacourse.kanban.board.domain.Status
import woowacourse.kanban.board.domain.Task
import woowacourse.kanban.board.state.KanbanBoardState
import woowacourse.kanban.board.state.MoveTaskStatusResult
import woowacourse.kanban.board.state.DialogMode
import woowacourse.kanban.board.theme.StatusColor

@Composable
fun KanbanBoard(
    kanbanBoard: KanbanBoard,
    modifier: Modifier = Modifier,
    onAddTask: (Task) -> Unit = {},
    onEditTask: (Task) -> Unit = {},
    onDeleteTask: (Task) -> Boolean = { true },
    onMoveTaskStatus: (String, Status) -> MoveTaskStatusResult = { _, _ -> MoveTaskStatusResult.SUCCESS },
) {
    val statuses = remember { Status.entries }
    val names = remember { listOf("다이노", "페임스") }
    val state = remember { KanbanBoardState() }


    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize().background(color = Color.White),
        ) {
            KanbanBoardTitleBar(
                title = kanbanBoard.title,
                progress = kanbanBoard.progress(),
                doneCount = kanbanBoard.doneCount(),
                totalStatusCount = kanbanBoard.totalStatusCount(),
                onCreateClick = {
                    state.showDialog = true
                    state.dialogState.mode = DialogMode.CREATE

                                },
            )
            Row(
                modifier = Modifier.padding(24.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Status.entries.forEach { status ->
                    StatusCardManageBox(
                        boardList = kanbanBoard.getStatusTask(status),
                        status = status,
                        statusColor = StatusColor.getStatusColor(status),
                        getIsDropTarget = {
                            state.currentDragPosition?.let { state.columnBounds[status]?.contains(it) } ?: false
                        },
                        onBoundsChanged = { rect -> state.columnBounds[status] = rect },
                        onTaskDragStart = { task ->
                            state.draggedTaskId = task.id
                            state.draggedTaskSourceStatus = task.status
                            state.draggedTaskNickname = task.nickname
                        },
                        onTaskDragChange = { pos -> state.currentDragPosition = pos },
                        onTaskDragEnd = {
                            state.onTaskDragEnd(state) { taskId, targetStatus ->
                                val result = onMoveTaskStatus(taskId, targetStatus)
                                state.onTaskMoveResult(result)
                            }
                        },
                        onTaskDragCancel = {
                            state.currentDragPosition = null
                            state.draggedTaskId = null
                            state.draggedTaskNickname = null
                            state.draggedTaskSourceStatus = null
                        },
                        onTaskClick = { task ->
                            state.showDialog = true
                            state.dialogState.mode = DialogMode.EDIT
                            state.dialogState.loadTaskData(task)
                        },
                    )
                }
            }
            DialogIfVisible(
                state = state,
                statuses = statuses,
                names = names,
                onAddTask = onAddTask,
                onEditTask = onEditTask,
                onDeleteTask = onDeleteTask
            )
        }
        CreateAlertSnackBarVisible(
            state = state,
            modifier = Modifier.align(alignment = Alignment.BottomCenter),
        )
    }
}

//Dialog 표시 여부 책임 분리
@Composable
private fun DialogIfVisible(
    state: KanbanBoardState,
    statuses:List<Status>,
    names:List<String>,
    onAddTask: (Task) -> Unit,
    onEditTask: (Task) -> Unit,
    onDeleteTask: (Task) -> Boolean,
){

     if (state.showDialog) {
         Dialog(
             onDismissRequest = { state.showDialog = false },
         ) {
             TaskCreateDialog(
                 statuses = statuses,
                 names = names,
                 onTaskCreate = {
                     task -> onAddTask(task)
                     closeDialogAndShowSnackBar(state) { state.onTaskCreated() }
                 },
                 onEditTask = {
                     task -> onEditTask(task)
                     closeDialogAndShowSnackBar(state) { state.onTaskEdited() }

                 },
                 onDeleteTask = {
                     task -> val isDeleted = onDeleteTask(task)
                     closeDialogAndShowSnackBar(state) { state.onTaskDeleted(isDeleted) }
                 },
                 onShowSnackbar = { message ->
                     state.showSnackBar(message)
                 },
                 onDismissRequest = {
                     state.showDialog = false
                     state.dialogState.resetDialog()
                 },
                 dialogState = state.dialogState,
             )
         }
     }
}

//스낵바 표시 여부 책임 분리
@Composable
private fun CreateAlertSnackBarVisible(
    state: KanbanBoardState,
    modifier: Modifier = Modifier,
){
    LaunchedEffect(state.snackBarState.isVisible) {
        if (state.snackBarState.isVisible) {
            delay(7000.milliseconds)
            state.hideSnackBar()
        }
    }
    if (state.snackBarState.isVisible) CreateAlertSnackBar(
        modifier = modifier
            .clip(shape = RoundedCornerShape(4.dp))
            .background(color = Color(0xFF322F35))
            .padding(start = 16.dp)
            .size(width = 344.dp, height = 48.dp),
        text = state.snackBarState.text,
        onClick = { state.hideSnackBar() },
    )
}

private fun closeDialogAndShowSnackBar( state: KanbanBoardState, onSnackBar: () -> Unit) {
    state.showDialog = false
    state.dialogState.resetDialog()
    onSnackBar()
}

@Preview(showBackground = true, widthDp = 1200, heightDp = 800)
@Composable
private fun KanbanBoardPreview() {
    KanbanBoard(kanbanBoard = KanbanBoard(title = "Compose1"))
}
