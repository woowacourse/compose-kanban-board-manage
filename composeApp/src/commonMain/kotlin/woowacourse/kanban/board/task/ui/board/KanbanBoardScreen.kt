package woowacourse.kanban.board.task.ui.board

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import woowacourse.kanban.board.task.domain.KanbanCard
import woowacourse.kanban.board.task.domain.KanbanProject
import woowacourse.kanban.board.task.domain.KanbanStatus
import woowacourse.kanban.board.task.domain.TaskMockData
import woowacourse.kanban.board.task.ui.project.KanbanProjectState
import woowacourse.kanban.board.task.ui.project.RememberKanbanProjectState
import woowacourse.kanban.board.theme.BoardBackground
import woowacourse.kanban.board.theme.SnackBarBackground

@Composable
fun KanbanBoardScreen(
    kanbanProjectState: KanbanProjectState,
    modifier: Modifier = Modifier,
    getIsDropTarget: (KanbanStatus) -> Boolean = { false },
    onBoundsChanged: (KanbanStatus, Rect) -> Unit = { _, _ -> },
    onTaskDragStart: (KanbanCard) -> Unit = {},
    onTaskDragChange: (Offset) -> Unit = {},
    onTaskDragEnd: () -> Unit = {},
    onTaskDragCancel: () -> Unit = {},
) {
    val currentBoard = kanbanProjectState.kanbanBoard
    if (currentBoard != null) {
        Column(
            modifier = modifier,
        ) {
            KanbanBoardHeader(
                modifier = Modifier.padding(
                    vertical = 16.dp,
                    horizontal = 24.dp,
                ),
                title = currentBoard.title,
                doneCount = currentBoard.doneCount,
                totalCount = currentBoard.totalCount,
                progress = currentBoard.progress,
                onCreateClick = { kanbanProjectState.isShowCreateModal = true },
            )

            KanbanBoardContent(
                kanbanProjectState = kanbanProjectState,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(BoardBackground)
                    .padding(24.dp),
                getIsDropTarget = getIsDropTarget,
                onBoundsChanged = onBoundsChanged,
                onTaskDragStart = onTaskDragStart,
                onTaskDragChange = onTaskDragChange,
                onTaskDragEnd = onTaskDragEnd,
                onTaskDragCancel = onTaskDragCancel,
            )
        }
    }
}

@Composable
fun SnackBarCard(message: String, onDismiss: () -> Unit, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .width(344.dp)
            .height(48.dp)
            .background(
                color = SnackBarBackground,
                shape = RoundedCornerShape(4.dp),
            )
            .padding(
                vertical = 14.dp,
                horizontal = 16.dp,
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = message,
            fontSize = 14.sp,
            color = Color.White,
        )

        Icon(
            imageVector = Icons.Default.Close,
            modifier = modifier.size(24.dp).clickable(onClick = { onDismiss() }),
            contentDescription = "스낵바 닫기",
            tint = Color.White,
        )
    }
}

@Preview(
    widthDp = 1300,
    heightDp = 900,
)
@Composable
private fun KanbanBoardScreenPreview() {
    KanbanBoardScreen(
        kanbanProjectState = RememberKanbanProjectState(
            coroutineScope = rememberCoroutineScope(),
            kanbanProject = KanbanProject(
                projectTitle = "4주차 미션 보드",
                boards = TaskMockData.boards,
            ),
        ),
    )
}
