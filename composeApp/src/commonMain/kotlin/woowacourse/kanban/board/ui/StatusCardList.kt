package woowacourse.kanban.board.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import woowacourse.kanban.Colors
import woowacourse.kanban.card.ui.DragWrapper
import woowacourse.kanban.card.ui.KanbanCard
import woowacourse.kanban.domain.KanbanTask
import woowacourse.kanban.domain.TaskStatus

private val TaskStatus.displayName: String
    get() = when (this) {
        TaskStatus.TO_DO -> "To Do"
        TaskStatus.IN_PROGRESS -> "In Progress"
        TaskStatus.DONE -> "Done"
    }

private val TaskStatus.titleColor: Color
    get() = when (this) {
        TaskStatus.TO_DO -> Colors.StatusBgToDo
        TaskStatus.IN_PROGRESS -> Colors.StatusBgInProgress
        TaskStatus.DONE -> Colors.StatusBgDone
    }

private val TaskStatus.bgColor: Color
    get() = when (this) {
        TaskStatus.TO_DO -> Colors.StatusListBgToDo
        TaskStatus.IN_PROGRESS -> Colors.StatusListBgInProgress
        TaskStatus.DONE -> Colors.StatusListBgDone
    }

private val TaskStatus.borderColor: Color
    get() = when (this) {
        TaskStatus.TO_DO -> Colors.StatusListBorderToDo
        TaskStatus.IN_PROGRESS -> Colors.StatusListBorderInProgress
        TaskStatus.DONE -> Colors.StatusListBorderDone
    }

@Composable
fun StatusCardList(
    tasks: List<KanbanTask>,
    status: TaskStatus,
    modifier: Modifier = Modifier,
    getIsDropTarget: () -> Boolean = { false },
    onBoundsChanged: (Rect) -> Unit = {},
    onTaskDragStart: (KanbanTask) -> Unit = {},
    onTaskDragChange: (Offset) -> Unit = {},
    onTaskDragEnd: () -> Unit = {},
    onTaskDragCancel: () -> Unit = {},
) {
    val isDropTarget by remember { derivedStateOf { getIsDropTarget() } }
    val lastBoundsHolder = remember { mutableStateOf<Rect?>(null) }

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(status.bgColor)
            .border(
                color = status.borderColor,
                width = 1.dp,
                shape = RoundedCornerShape(10.dp),
            ).onGloballyPositioned {
                val newBounds = it.boundsInWindow()
                if (newBounds != lastBoundsHolder.value) {
                    lastBoundsHolder.value = newBounds
                    onBoundsChanged(newBounds)
                }
            }
            .then(

                if (isDropTarget)
                    modifier.border(2.dp, Colors.DropTargetBorder, RoundedCornerShape(12.dp))
                else modifier,
            ),

    ) {
        Box(
            modifier = Modifier
                .background(status.titleColor)
                .fillMaxWidth(),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp),
            ) {
                Text(
                    status.displayName,
                    color = Colors.OnNeutral,
                    fontWeight = FontWeight.W600,
                    fontSize = 16.sp,
                )
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(width = 29.dp, height = 24.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Colors.Surface),
                ) {
                    Text(
                        tasks.size.toString(),
                        color = Colors.OnSurface,
                    )
                }
            }
        }
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .padding(vertical = 12.dp)
                .fillMaxHeight()
                .fillMaxWidth()
                .testTag(status.displayName),

        ) {
            items(count = tasks.size, key = { tasks[it].data.id }) {
                DragWrapper(
                    onDragStart = {
                        onTaskDragStart(tasks[it])
                    },
                    onDragChange = onTaskDragChange,
                    onDragEnd = onTaskDragEnd,
                    onDragCancel = onTaskDragCancel,
                ) {
                    KanbanCard(
                        tasks[it].data,
                    )
                }
            }
        }
    }
}

@Composable
@Preview
private fun StatusCardListPreview() {
    StatusCardList(
        tasks = emptyList(),
        status = TaskStatus.TO_DO,
    )
}
