package woowacourse.kanban.board.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import java.util.UUID
import woowacourse.kanban.board.BoardState
import woowacourse.kanban.dialog.create.TaskCreateDialog
import woowacourse.kanban.dialog.edit.TaskEditDialog
import woowacourse.kanban.domain.project.KanbanProject
import woowacourse.kanban.domain.task.Assignee
import woowacourse.kanban.domain.task.KanbanTask
import woowacourse.kanban.domain.task.TaskStatus

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun KanbanBoard(
    project: KanbanProject,
    onProjectChanged: (KanbanProject) -> Unit = {},
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState = SnackbarHostState(),
) {
    val state = remember(project.title) { BoardState(project) }

    val currentProject = state.project

    var clickedTaskId by remember { mutableStateOf(null as UUID?) }
    var draggedTask by remember { mutableStateOf<KanbanTask?>(null) }
    var currentDragPosition by remember { mutableStateOf<Offset?>(null) }
    val columnBounds = remember { mutableStateMapOf<TaskStatus, Rect>() }

    LaunchedEffect(state.snackBarEvent?.id) {
        state.snackBarEvent?.let {
            snackbarHostState.showSnackbar(it.message)
        }
    }

    Column(modifier = modifier) {
        KanbanBoardHeader(
            progress = currentProject.value.getProgress(),
            doneTaskCount = currentProject.value.getTasksWithStatus(TaskStatus.DONE).size,
            totalTaskCount = currentProject.value.projectTasks.size,
            onClick = { state.toggleDialog() },
            headerTitle = currentProject.value.title,
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth(1f),
        ) {
            TaskStatus.entries.forEach { status ->
                StatusCardList(
                    tasks = currentProject.value.getTasksWithStatus(status),
                    status = status,
                    onCardClick = {
                        state.toggleDialog()
                        state.toggleEditTask()
                        clickedTaskId = it.data.id
                    },
                    modifier = Modifier.weight(1f),
                    getIsDropTarget = {
                        currentDragPosition?.let { columnBounds[status]?.contains(it) } ?: false
                    },
                    onBoundsChanged = { rect -> columnBounds[status] = rect },
                    onTaskDragStart = { task -> draggedTask = task },
                    onTaskDragChange = { pos -> currentDragPosition = pos },
                    onTaskDragEnd = {
                        val dropPosition = currentDragPosition ?: return@StatusCardList
                        val targetStatus = columnBounds.entries
                            .firstOrNull { (_, rect) -> rect.contains(dropPosition) }?.key

                        draggedTask?.let { task ->
                            if (targetStatus != null && task.status != targetStatus) {
                                val idx = currentProject.value.getTaskIndexWithId(task.data.id)
                                if (idx != -1) {
                                    state.changeTaskStatus(idx, targetStatus)
                                    onProjectChanged(state.project.value)
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

    if (state.showDialogValue()) {
        if (state.isEditTaskValue()) {
            TaskEditDialog(
                targetTask = state.getTaskWithId(clickedTaskId!!),
                onDismiss = {
                    state.toggleDialog()
                    state.toggleEditTask()
                },
                onDeleteTask = {
                    state.deleteTask(clickedTaskId!!)
                    onProjectChanged(state.project.value)
                },
                onEditTask = { taskCreator ->
                    state.editTask(clickedTaskId!!, taskCreator)
                    onProjectChanged(state.project.value)
                },
                assignees = if (state.getTaskWithId(clickedTaskId!!).status == TaskStatus.TO_DO) Assignee.entries
                else Assignee.entries - Assignee.NONE,
            )
        } else {
            TaskCreateDialog(
                onDismiss = { state.toggleDialog() },
                onCreateTask = { taskCreator ->
                    state.addTask(taskCreator)
                    onProjectChanged(state.project.value)
                },
                assignees = Assignee.entries,
                modifier = Modifier,
            )
        }
    }
}

@Preview(widthDp = 1500, heightDp = 800)
@Composable
fun KanbanBoardPreview() {
    KanbanBoard(
        project = KanbanProject(),
        onProjectChanged = { },
    )
}
