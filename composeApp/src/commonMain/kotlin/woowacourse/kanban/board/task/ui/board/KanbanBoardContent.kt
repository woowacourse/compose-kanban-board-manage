package woowacourse.kanban.board.task.ui.board

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kanbanboard.composeapp.generated.resources.Res
import kanbanboard.composeapp.generated.resources.status_Done
import kanbanboard.composeapp.generated.resources.status_In_Progress
import kanbanboard.composeapp.generated.resources.status_review
import kanbanboard.composeapp.generated.resources.status_to_do
import org.jetbrains.compose.resources.stringResource
import woowacourse.kanban.board.task.domain.KanbanCard
import woowacourse.kanban.board.task.domain.KanbanProject
import woowacourse.kanban.board.task.domain.KanbanStatus
import woowacourse.kanban.board.task.domain.TaskMockData
import woowacourse.kanban.board.task.ui.card.KanbanCardItem
import woowacourse.kanban.board.task.ui.project.KanbanProjectState
import woowacourse.kanban.board.task.ui.project.RememberKanbanProjectState
import woowacourse.kanban.board.theme.DoneColumnBorder
import woowacourse.kanban.board.theme.DoneColumnContentBackground
import woowacourse.kanban.board.theme.DoneColumnHeaderBackground
import woowacourse.kanban.board.theme.InProgressColumnBorder
import woowacourse.kanban.board.theme.InProgressColumnContentBackground
import woowacourse.kanban.board.theme.InProgressColumnHeaderBackground
import woowacourse.kanban.board.theme.ReviewColumnBorder
import woowacourse.kanban.board.theme.ReviewColumnContentBackground
import woowacourse.kanban.board.theme.ReviewColumnHeaderBackground
import woowacourse.kanban.board.theme.TodoColumnBorder
import woowacourse.kanban.board.theme.TodoColumnContentBackground
import woowacourse.kanban.board.theme.TodoColumnHeaderBackground

@Composable
fun KanbanBoardContent(
    kanbanProjectState: KanbanProjectState,
    modifier: Modifier = Modifier,
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
                kanbanProjectState = kanbanProjectState,
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

data class ColumnColors(val headerColor: Color, val backgroundColor: Color, val borderColor: Color)

@Composable
private fun KanbanBoardStatusColumn(
    status: KanbanStatus,
    kanbanProjectState: KanbanProjectState,
    modifier: Modifier = Modifier,
    getIsDropTarget: (KanbanStatus) -> Boolean = { false },
    onBoundsChanged: (KanbanStatus, Rect) -> Unit = { _, _ -> },
    onTaskDragStart: (KanbanCard) -> Unit = {},
    onTaskDragChange: (Offset) -> Unit = {},
    onTaskDragEnd: () -> Unit = {},
    onTaskDragCancel: () -> Unit = {},
) {
    var cardWindowPosition by remember { mutableStateOf(Offset.Zero) }

    val (title, color) = when (status) {
        KanbanStatus.TO_DO -> stringResource(Res.string.status_to_do) to ColumnColors(
            headerColor = TodoColumnHeaderBackground,
            backgroundColor = TodoColumnContentBackground,
            borderColor = TodoColumnBorder,
        )

        KanbanStatus.IN_PROGRESS -> stringResource(Res.string.status_In_Progress) to ColumnColors(
            headerColor = InProgressColumnHeaderBackground,
            backgroundColor = InProgressColumnContentBackground,
            borderColor = InProgressColumnBorder,
        )

        KanbanStatus.REVIEW -> stringResource(Res.string.status_review) to ColumnColors(
            headerColor = ReviewColumnHeaderBackground,
            backgroundColor = ReviewColumnContentBackground,
            borderColor = ReviewColumnBorder,
        )

        KanbanStatus.DONE -> stringResource(Res.string.status_Done) to ColumnColors(
            headerColor = DoneColumnHeaderBackground,
            backgroundColor = DoneColumnContentBackground,
            borderColor = DoneColumnBorder,
        )
    }

    val isDropTarget by remember { derivedStateOf { getIsDropTarget(status) } }
    val lastBoundsHolder = remember { mutableStateOf<Rect?>(null) }

    val cards = kanbanProjectState.kanbanBoard?.getCardByStatus(status) ?: emptyList()

    Column(
        modifier = modifier.fillMaxHeight().width(320.dp).clip(RoundedCornerShape(10.dp)).border(
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
                if (isDropTarget) modifier.border(2.dp, Color.Black, RoundedCornerShape(12.dp)) else modifier,
            ),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().background(color = color.headerColor).padding(
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
                key = { it.id },
                items = cards,
            ) { card ->
                KanbanCardItem(
                    modifier = Modifier
                        .width(286.dp)
                        .background(
                            Color.White,
                            RoundedCornerShape(10.dp),
                        )
                        .border(
                            Dp.Hairline,
                            Color.Gray,
                            RoundedCornerShape(10.dp),
                        )
                        .clickable(
                            onClick = {
                                kanbanProjectState.showEditModal(card)
                            },
                        )
                        .onGloballyPositioned { cardWindowPosition = it.positionInWindow() }
                        .pointerInput(Unit) {
                            detectDragGestures(
                                onDragStart = { onTaskDragStart(card) },
                                onDrag = { change, _ ->
                                    change.consume()
                                    onTaskDragChange(cardWindowPosition + change.position)
                                },
                                onDragEnd = { onTaskDragEnd() },
                                onDragCancel = { onTaskDragCancel() },
                            )
                        },
                    title = card.title,
                    content = card.content,
                    tags = card.tags,
                    assigneeName = card.assigneeName,
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
private fun KanbanBodyPreview() {
    KanbanBoardContent(
        kanbanProjectState = RememberKanbanProjectState(
            coroutineScope = rememberCoroutineScope(),
            kanbanProject = KanbanProject(
                projectTitle = "4주차 미션 보드",
                boards = TaskMockData.boards,
            ),
        ),
    )
}
