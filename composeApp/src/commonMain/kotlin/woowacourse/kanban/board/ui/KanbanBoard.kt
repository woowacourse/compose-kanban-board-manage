package woowacourse.kanban.board.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import woowacourse.kanban.board.ui.constant.MockData
import woowacourse.kanban.board.ui.stateholder.BoardState
import woowacourse.kanban.create.ui.TaskCreateDialog
import woowacourse.kanban.domain.Assignee
import woowacourse.kanban.domain.KanbanTask
import woowacourse.kanban.domain.TaskStatus

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun KanbanBoard(
    boardState: BoardState,
    projectTitle: String,
    assignees: List<Assignee>,
    modifier: Modifier = Modifier,
    onTaskCreated: (KanbanTask) -> Unit = {},
    onStatusChanged: (TaskStatus, Int) -> Unit = { _, _ -> },
    selectedStatuses: List<TaskStatus> = TaskStatus.entries,
) {

    var draggedTask by remember { mutableStateOf<KanbanTask?>(null) }
    var currentDragPosition by remember { mutableStateOf<Offset?>(null) }
    val columnBounds = remember { mutableStateMapOf<TaskStatus, Rect>() }

    Column(modifier = modifier) {
        KanbanBoardHeader(
            progress = boardState.progress,
            doneTaskCount = boardState.doneCardList.size,
            totalTaskCount = boardState.totalTaskCount,
            onClick = { boardState.showDialog.value = true },
            headerTitle = projectTitle,
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth(1f),
        ) {
            selectedStatuses.forEach { status ->
                StatusCardList(
                    tasks = boardState.getTasksByStatus(status),
                    status = status,
                    modifier = Modifier.weight(1f),
                    getIsDropTarget = {
                        currentDragPosition?.let { columnBounds[status]?.contains(it) }
                            ?: false
                    },
                    onBoundsChanged = { rect -> columnBounds[status] = rect },
                    onTaskDragStart = { task ->
                        draggedTask = task
                    },
                    onTaskDragChange = { pos -> currentDragPosition = pos },
                    onTaskDragEnd = {
                        val dropPosition = currentDragPosition
                            ?: return@StatusCardList
                        val targetStatus = columnBounds.entries
                            .firstOrNull { (_, rect) -> rect.contains(dropPosition) }?.key

                        draggedTask?.let { task ->
                            if (targetStatus != null && task.status != targetStatus) {
                                val idx = boardState.getTotalTasks().indexOfFirst { it.data.id == task.data.id }
                                if (idx != -1) {
                                    onStatusChanged(targetStatus, idx)
                                }
                            }
                        }
                        currentDragPosition = null
                        draggedTask = null
                    },
                    onTaskDragCancel = {
                        currentDragPosition = null
                        draggedTask = null
                    },
                )
            }
        }
    }

    if (boardState.showDialog.value) {
        TaskCreateDialog(
            onDismiss = { boardState.showDialog.value = false },
            onCreateTask = { task ->
                onTaskCreated(task)
            },
            assignees = assignees,
            modifier = Modifier,
        )
    }
}

@Preview(widthDp = 1500, heightDp = 800)
@Composable
fun KanbanBoardPreview() {
    KanbanBoard(
        BoardState(initTasks = emptyList()),
        assignees = MockData.ASSIGNEES,
        projectTitle = "",
    )
}
