package woowacourse.kanban.board.component.board

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import woowacourse.kanban.board.component.extension.toBackgroundColor
import woowacourse.kanban.board.component.extension.toBorderColor
import woowacourse.kanban.board.component.extension.toFilterTask
import woowacourse.kanban.board.component.extension.toHeaderColor
import woowacourse.kanban.board.component.extension.toText
import woowacourse.kanban.board.component.sample.ProjectPreviewData
import woowacourse.kanban.board.component.taskcard.TaskCard as TaskCardItem
import woowacourse.kanban.board.model.project.Project
import woowacourse.kanban.board.model.taskcard.Description
import woowacourse.kanban.board.model.taskcard.Profile
import woowacourse.kanban.board.model.taskcard.Status
import woowacourse.kanban.board.model.taskcard.Tag
import woowacourse.kanban.board.model.taskcard.Tags
import woowacourse.kanban.board.model.taskcard.TaskCard
import woowacourse.kanban.board.model.taskcard.TaskCardPolicy
import woowacourse.kanban.board.model.taskcard.Title

@Composable
fun TaskColumnSection(
    project: Project,
    onMoveSnackBar: () -> Unit,
    onInvalidStatusMove: () -> Unit,
    onRequireProfileMove: () -> Unit,
    onUpdateTaskStatus: (String, Status) -> Unit,
    onTaskClick: (TaskCard) -> Unit,
    modifier: Modifier = Modifier,
) {
    var currentDragPosition by remember { mutableStateOf<Offset?>(null) }
    val columnBounds = remember { mutableStateMapOf<Status, Rect>() }
    var draggedTaskId by remember { mutableStateOf<String?>(null) }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(24.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Status.entries.forEach { status ->
            TaskColumn(
                status = status,
                tasks = status.toFilterTask(project),
                modifier = Modifier.weight(1f),
                getIsDropTarget = {
                    currentDragPosition?.let { columnBounds[status]?.contains(it) } ?: false
                },
                onBoundsChanged = { rect -> columnBounds[status] = rect },
                onTaskDragStart = { task -> draggedTaskId = task.id },
                onTaskDragChange = { pos -> currentDragPosition = pos },
                onTaskDragEnd = {
                    val dropPosition = currentDragPosition ?: return@TaskColumn
                    val targetStatus = columnBounds.entries
                        .firstOrNull { (_, rect) -> rect.contains(dropPosition) }?.key

                    draggedTaskId?.let { id ->
                        val task = project.findTaskById(id)
                        when {
                            task == null || targetStatus == null || task.status == targetStatus -> Unit
                            !TaskCardPolicy.canModifyStatus(task.status, targetStatus) -> {
                                onInvalidStatusMove()
                            }
                            TaskCardPolicy.requireProfile(targetStatus) && !task.profile.isAssigned -> {
                                onRequireProfileMove()
                            }
                            else -> {
                                onUpdateTaskStatus(id, targetStatus)
                                onMoveSnackBar()
                            }
                        }
                    }

                    currentDragPosition = null
                    draggedTaskId = null
                },
                onTaskDragCancel = {
                    currentDragPosition = null
                    draggedTaskId = null
                },
                onTaskClick = onTaskClick
            )
        }
        Spacer(
            modifier = Modifier.weight(.7f),
        )
    }
}

@Composable
private fun TaskColumn(
    status: Status,
    tasks: ImmutableList<TaskCard>,
    modifier: Modifier = Modifier,
    getIsDropTarget: () -> Boolean = { false },
    onBoundsChanged: (Rect) -> Unit = {},
    onTaskDragStart: (TaskCard) -> Unit = {},
    onTaskDragChange: (Offset) -> Unit = {},
    onTaskDragEnd: () -> Unit = {},
    onTaskDragCancel: () -> Unit = {},
    onTaskClick: (TaskCard) -> Unit,
) {
    val isDropTarget by remember { derivedStateOf { getIsDropTarget() } }
    val lastBoundsHolder = remember { mutableStateOf<Rect?>(null) }

    Box(
        modifier = modifier
            .onGloballyPositioned {
                val newBounds = it.boundsInWindow()
                if (newBounds != lastBoundsHolder.value) {
                    lastBoundsHolder.value = newBounds
                    onBoundsChanged(newBounds)
                }
            }
            .then(
                if (isDropTarget) modifier.border(2.dp, status.toBorderColor(), RoundedCornerShape(12.dp)) else modifier,
            )
            .clip(RoundedCornerShape(15.dp))
            .border(1.dp, status.toBorderColor(), shape = RoundedCornerShape(15.dp))
            .background(status.toBackgroundColor()),
    ) {
        Column {
            TaskColumnHeader(
                status = status,
                taskCount = tasks.size,
                modifier = Modifier.fillMaxWidth(),
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(15.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                items(tasks, key = { it.id }) { task ->
                    TaskCardItem(
                        data = task,
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { onTaskClick(task) },
                        onDragStart = { onTaskDragStart(task) },
                        onDragChange = onTaskDragChange,
                        onDragEnd = onTaskDragEnd,
                        onDragCancel = onTaskDragCancel,
                    )
                }
            }
        }
    }
}

@Composable
private fun TaskColumnHeader(
    status: Status,
    taskCount: Int,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .background(status.toHeaderColor())
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = status.toText(),
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
        )
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(15.dp))
                .background(Color.White),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = taskCount.toString(),
                color = Color.Black,
                fontSize = 14.sp,
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 2.dp),
                fontWeight = FontWeight.Normal,
            )
        }
    }
}

@Preview(widthDp = 600)
@Composable
private fun TaskColumnProgressHeaderPreview() {
    TaskColumnHeader(
        status = Status.PROGRESS,
        taskCount = 1,
    )
}

@Preview(widthDp = 600)
@Composable
private fun TaskColumnDoneHeaderPreview() {
    TaskColumnHeader(
        status = Status.DONE,
        taskCount = 1,
    )
}

@Preview(widthDp = 600)
@Composable
private fun TaskColumnTodoHeaderPreview() {
    TaskColumnHeader(
        status = Status.TODO,
        taskCount = 1,
    )
}

@Preview(heightDp = 400)
@Composable
private fun TaskColumnTodoPreview() {
    val tasks = listOf(
        TaskCard(
            id = "preview-todo-task",
            title = Title(value = "제목"),
            description = Description(value = "설명"),
            tags = Tags(value = listOf(Tag(value = "컴포넌트")).toImmutableList()),
            status = Status.PROGRESS,
            profile = Profile("다이노"),
        ),
    ).toImmutableList()

    TaskColumn(
        tasks = tasks,
        status = Status.TODO,
        onTaskClick = {},
    )
}

@Preview(heightDp = 400)
@Composable
private fun TaskColumnProgressPreview() {
    val tasks = listOf(
        TaskCard(
            id = "preview-progress-task",
            title = Title(value = "제목"),
            description = Description(value = "설명"),
            tags = Tags(value = listOf(Tag(value = "컴포넌트"), Tag("zjavh")).toImmutableList()),
            status = Status.PROGRESS,
            profile = Profile("다이노"),
        ),
    ).toImmutableList()

    TaskColumn(
        tasks = tasks,
        status = Status.PROGRESS,
        onTaskClick = {},
    )
}

@Preview(heightDp = 400)
@Composable
private fun TaskColumnDonePreview() {
    val tasks = listOf(
        TaskCard(
            id = "preview-done-task",
            title = Title(value = "제목"),
            description = Description(value = "설명"),
            tags = Tags(value = listOf(Tag(value = "컴포넌트")).toImmutableList()),
            status = Status.PROGRESS,
            profile = Profile("다이노"),
        ),
    ).toImmutableList()

    TaskColumn(
        tasks = tasks,
        status = Status.DONE,
        onTaskClick = {},
    )
}

@Preview(showBackground = true)
@Composable
private fun TaskColumnSectionPreview() {
    val project = ProjectPreviewData().values.first()

    MaterialTheme {
        TaskColumnSection(
            project = project,
            onMoveSnackBar = {},
            onInvalidStatusMove = {},
            onRequireProfileMove = {},
            onUpdateTaskStatus = { _, _ -> },
            onTaskClick = {},
        )
    }
}
