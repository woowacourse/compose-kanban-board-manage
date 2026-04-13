package woowacourse.kanban.board.ui.board.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import woowacourse.kanban.board.domain.Task
import woowacourse.kanban.board.domain.TaskState
import woowacourse.kanban.board.ui.board.state.ProjectState
import woowacourse.kanban.board.ui.taskcard.TaskCards
import woowacourse.kanban.board.ui.theme.DoneBorder
import woowacourse.kanban.board.ui.theme.DoneContent
import woowacourse.kanban.board.ui.theme.DoneTitle
import woowacourse.kanban.board.ui.theme.InProgressBorder
import woowacourse.kanban.board.ui.theme.InProgressContent
import woowacourse.kanban.board.ui.theme.InProgressTitle
import woowacourse.kanban.board.ui.theme.ReviewBorder
import woowacourse.kanban.board.ui.theme.ReviewContent
import woowacourse.kanban.board.ui.theme.ReviewTitle
import woowacourse.kanban.board.ui.theme.ToDoBorder
import woowacourse.kanban.board.ui.theme.ToDoContent
import woowacourse.kanban.board.ui.theme.ToDoTitle

@Composable
fun KanbanBoardContent(
    project: ProjectState,
    onTaskStateChange: (Task, TaskState) -> Unit,
    onClick: (Task) -> Unit,
    modifier: Modifier = Modifier,
) {
    var draggedTask by remember { mutableStateOf<Task?>(null) }
    var currentDragPosition by remember { mutableStateOf<Offset?>(null) }
    val columnBounds = remember { mutableStateMapOf<TaskState, Rect>() }

    fun clearDragState() {
        currentDragPosition = null
        draggedTask = null
    }

    Row(
        modifier = modifier.padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        TaskState.entries.forEach { taskState ->
            StateTasks(
                project,
                taskState,
                taskState.titleColor(),
                taskState.contentColor(),
                taskState.borderColor(),
                onClick = onClick,
                modifier = Modifier.testTag(taskState.name),
                getIsDropTarget = {
                    currentDragPosition?.let { columnBounds[taskState]?.contains(it) } ?: false
                },
                onBoundsChanged = { rect -> columnBounds[taskState] = rect },
                onTaskDragStart = { task -> draggedTask = task },
                onTaskDragChange = { pos -> currentDragPosition = pos },
                onTaskDragEnd = {
                    val dragPosition = currentDragPosition ?: return@StateTasks
                    val targetStatus = columnBounds.entries.firstOrNull { (_, rect) -> rect.contains(dragPosition) }?.key

                    draggedTask?.let { task ->
                        if (targetStatus != null && task.taskState != targetStatus) {
                            onTaskStateChange(task, targetStatus)
                        }
                    }

                    clearDragState()
                },
                onTaskDragCancel = {
                    clearDragState()
                },
            )
        }
    }
}

@Composable
private fun StateTasks(
    project: ProjectState,
    taskState: TaskState,
    titleColor: Color,
    contentColor: Color,
    borderColor: Color,
    onClick: (Task) -> Unit,
    modifier: Modifier = Modifier,
    getIsDropTarget: () -> Boolean = { false },
    onBoundsChanged: (Rect) -> Unit = {},
    onTaskDragStart: (Task) -> Unit = {},
    onTaskDragChange: (Offset) -> Unit = {},
    onTaskDragEnd: () -> Unit = {},
    onTaskDragCancel: () -> Unit = {},
) {
    val isDropTarget by remember { derivedStateOf { getIsDropTarget() } }
    val lastBoundsHolder = remember { mutableStateOf<Rect?>(null) }

    OutlinedCard(
        colors = CardDefaults.cardColors(
            containerColor = contentColor,

        ),
        border = BorderStroke(0.5.dp, borderColor),
        modifier = modifier
            .size(width = 310.dp, height = 748.dp)
            .onGloballyPositioned {
                val newBounds = it.boundsInWindow()
                if (newBounds != lastBoundsHolder.value) {
                    lastBoundsHolder.value = newBounds
                    onBoundsChanged(newBounds)
                }
            }
            .then(
                if (isDropTarget) modifier.border(2.dp, Color.Black, RoundedCornerShape(16.dp)) else modifier,
            ),

    ) {
        StateTasksTitle(titleColor, taskState, project)
        TaskCards(
            project.getTasksByState(taskState),
            onClick = onClick,
            onTaskDragStart = onTaskDragStart,
            onTaskDragChange = onTaskDragChange,
            onTaskDragEnd = onTaskDragEnd,
            onTaskDragCancel = onTaskDragCancel,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
        )
    }
}

@Composable
private fun StateTasksTitle(titleColor: Color, taskState: TaskState, project: ProjectState, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(titleColor)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = taskState.toText(),
            fontWeight = FontWeight.W600,
            fontSize = 16.sp,
        )
        Box(
            modifier = Modifier
                .size(width = 29.dp, height = 24.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(Color.White),
        ) {
            Text(
                text = project.countByState(taskState).toString(),
                fontWeight = FontWeight.W500,
                fontSize = 14.sp,
                modifier = Modifier.align(Alignment.Center),
            )
        }
    }
}

fun TaskState.toText(): String = when (this) {
    TaskState.TO_DO -> "To Do"
    TaskState.IN_PROGRESS -> "In Progress"
    TaskState.REVIEW -> "Review"
    TaskState.DONE -> "Done"
}

private fun TaskState.titleColor(): Color = when (this) {
    TaskState.TO_DO -> ToDoTitle
    TaskState.IN_PROGRESS -> InProgressTitle
    TaskState.REVIEW -> ReviewTitle
    TaskState.DONE -> DoneTitle
}

private fun TaskState.contentColor(): Color = when (this) {
    TaskState.TO_DO -> ToDoContent
    TaskState.IN_PROGRESS -> InProgressContent
    TaskState.REVIEW -> ReviewContent
    TaskState.DONE -> DoneContent
}

private fun TaskState.borderColor(): Color = when (this) {
    TaskState.TO_DO -> ToDoBorder
    TaskState.IN_PROGRESS -> InProgressBorder
    TaskState.REVIEW -> ReviewBorder
    TaskState.DONE -> DoneBorder
}
