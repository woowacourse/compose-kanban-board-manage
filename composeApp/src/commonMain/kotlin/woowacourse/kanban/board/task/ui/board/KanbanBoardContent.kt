package woowacourse.kanban.board.task.ui.board

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kanbanboard.composeapp.generated.resources.Res
import kanbanboard.composeapp.generated.resources.status_Done
import kanbanboard.composeapp.generated.resources.status_In_Progress
import kanbanboard.composeapp.generated.resources.status_To_Do
import kanbanboard.composeapp.generated.resources.status_review
import org.jetbrains.compose.resources.stringResource
import woowacourse.kanban.board.task.domain.KanbanBoard
import woowacourse.kanban.board.task.domain.KanbanCard
import woowacourse.kanban.board.task.domain.KanbanStatus
import woowacourse.kanban.board.task.ui.card.KanbanCardItem
import woowacourse.kanban.board.theme.Blue200
import woowacourse.kanban.board.theme.Blue50
import woowacourse.kanban.board.theme.Blue600
import woowacourse.kanban.board.theme.Green200
import woowacourse.kanban.board.theme.Green50
import woowacourse.kanban.board.theme.Green600
import woowacourse.kanban.board.theme.Orange600
import woowacourse.kanban.board.theme.Violet100
import woowacourse.kanban.board.theme.Violet200
import woowacourse.kanban.board.theme.Violet500
import woowacourse.kanban.board.theme.Yellow300
import woowacourse.kanban.board.theme.Yellow50

@Composable
fun KanbanBoardContent(
    kanbanBoard: KanbanBoard,
    modifier: Modifier = Modifier,
    onCardClick: (KanbanCard) -> Unit,
    getIsDropTarget: (KanbanStatus) -> Boolean = { false },
    onBoundsChanged: (KanbanStatus, Rect) -> Unit = { _, _ -> },
    onTaskDragStart: (KanbanCard) -> Unit = {},
    onTaskDragChange: (Offset) -> Unit = {},
    onTaskDragEnd: () -> Unit = {},
    onTaskDragCancel: () -> Unit = {},
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        KanbanStatus.entries.forEach { status ->
            KanbanBoardStatusColumn(
                status = status,
                cards = kanbanBoard.getCardByStatus(status),
                onCardClick = onCardClick,
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

private data class ColumnColors(val headerColor: Color, val backgroundColor: Color, val borderColor: Color)

@Composable
private fun KanbanBoardStatusColumn(
    status: KanbanStatus,
    cards: List<KanbanCard>,
    onCardClick: (KanbanCard) -> Unit,
    modifier: Modifier = Modifier,
    getIsDropTarget: (KanbanStatus) -> Boolean = { false },
    onBoundsChanged: (KanbanStatus, Rect) -> Unit = { _, _ -> },
    onTaskDragStart: (KanbanCard) -> Unit = {},
    onTaskDragChange: (Offset) -> Unit = {},
    onTaskDragEnd: () -> Unit = {},
    onTaskDragCancel: () -> Unit = {},
) {
    val (title, color) = when (status) {
        KanbanStatus.TO_DO -> stringResource(Res.string.status_To_Do) to ColumnColors(
            headerColor = Blue600,
            backgroundColor = Blue50,
            borderColor = Blue200,
        )
        KanbanStatus.IN_PROGRESS -> stringResource(Res.string.status_In_Progress) to ColumnColors(
            headerColor = Orange600,
            backgroundColor = Yellow50,
            borderColor = Yellow300,
        )
        KanbanStatus.REVIEW -> stringResource(Res.string.status_review) to ColumnColors(
            headerColor = Violet500,
            backgroundColor = Violet100,
            borderColor = Violet200,
        )
        KanbanStatus.DONE -> stringResource(Res.string.status_Done) to ColumnColors(
            headerColor = Green600,
            backgroundColor = Green50,
            borderColor = Green200,
        )
    }

    val isDropTarget by remember { derivedStateOf { getIsDropTarget(status) } }
    val lastBoundsHolder = remember { mutableStateOf<Rect?>(null) }

    Column(
        modifier = modifier
            .fillMaxHeight()
            .width(320.dp)
            .clip(RoundedCornerShape(10.dp))
            .border(
                width = 1.dp,
                color = color.borderColor,
                shape = RoundedCornerShape(10.dp),
            )
            .onGloballyPositioned {
                val newBounds = it.boundsInWindow()
                if (newBounds != lastBoundsHolder.value) {
                    lastBoundsHolder.value = newBounds
                    onBoundsChanged(status, newBounds)
                }
            }
            .then(
                if (isDropTarget) Modifier.border(2.dp, Color.Black, RoundedCornerShape(12.dp)) else Modifier,
            ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = color.headerColor)
                .padding(
                    vertical = 12.dp,
                    horizontal = 16.dp,
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White,
            )

            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(Color.White)
                    .padding(
                        horizontal = 10.dp,
                        vertical = 2.dp,
                    ),
            ) {
                Text(
                    text = "${cards.size}",
                    fontSize = 14.sp,
                )
            }
        }

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(color = color.backgroundColor)
                .padding(
                    start = 17.dp,
                    end = 17.dp,
                    top = 16.dp,
                ),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            items(
                items = cards,
                key = { it.id },
            ) { card ->
                KanbanCardItem(
                    kanbanCard = card,
                    onCardClick = onCardClick,
                    onDragStart = onTaskDragStart,
                    onDragChange = onTaskDragChange,
                    onDragEnd = onTaskDragEnd,
                    onDragCancel = onTaskDragCancel,
                )
            }
        }
    }
}

@Preview(
    widthDp = 1300,
    heightDp = 800,
)
@Composable
private fun KanbanBoardContentPreview() {
    KanbanBoardContent(
        kanbanBoard = KanbanBoard(
            boardId = 0,
            title = "보드",
            cards = listOf(
                createTempCard(KanbanStatus.TO_DO),
                createTempCard(KanbanStatus.IN_PROGRESS),
                createTempCard(KanbanStatus.DONE),
            ),
        ),
        onCardClick = {},
    )
}

private fun createTempCard(status: KanbanStatus) = KanbanCard(
    title = "제목",
    assigneeName = "담당자",
    tags = listOf("태그"),
    status = status,
)
