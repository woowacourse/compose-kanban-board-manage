package woowacourse.kanban.board.ui.board

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kanbanboard.composeapp.generated.resources.Res
import kanbanboard.composeapp.generated.resources.snackbar_unknown_error
import org.jetbrains.compose.resources.getString
import woowacourse.kanban.board.domain.model.KanbanProject
import woowacourse.kanban.board.domain.model.Status
import woowacourse.kanban.board.domain.model.Task
import woowacourse.kanban.board.ui.dialog.TaskCreateDialog

@Composable
fun KanbanBoardScreen(projectStateHolder: ProjectStateHolder) {
    var showDialog by remember { mutableStateOf(false) }
    val snackBarHostState = remember { SnackbarHostState() }

    var draggedTask by remember { mutableStateOf<Task?>(null) }
    var currentDragPosition by remember { mutableStateOf<Offset?>(null) }
    val columnBounds = remember { mutableStateMapOf<Status, Rect>() }

    LaunchedEffect(projectStateHolder.snackBarEvent?.id) {
        projectStateHolder.snackBarEvent?.let {
            snackBarHostState.showSnackbar(
                message = when {
                    it.message != null -> it.message
                    it.strRes != null -> getString(it.strRes)
                    else -> getString(Res.string.snackbar_unknown_error)
                },
                withDismissAction = true,
            )
        }
    }

    Box {
        if (showDialog) {
            TaskCreateDialog(
                onDismissRequest = {
                    showDialog = false
                    projectStateHolder.clearSelectedTask()
                },
                onCreate = { title, description, tags, status, assignee ->
                    projectStateHolder.addTask(title = title, description = description, tags = tags, assignee = assignee, status = status)
                    showDialog = false
                },
                onEdit = { title, description, tags, status, assignee ->
                    projectStateHolder.editTask(title, description, tags, assignee, status)
                    showDialog = false
                },
                onDelete = {
                    projectStateHolder.deleteTask()
                    showDialog = false
                },
                originTask = projectStateHolder.selectedTask,
            )
        }
        Row {
            ProjectSideBar(
                projects = projectStateHolder.projects,
                selectedProjectId = projectStateHolder.currentProject.id,
                onProjectSelect = { projectStateHolder.changeProject(it) },
                modifier = Modifier.width(255.dp).fillMaxHeight().semantics { contentDescription = "Project SideBar" },
            )
            VerticalDivider(modifier = Modifier.width(1.dp).background(Color(0xffE5E7EB)))
            TaskBoard(
                getIsDropTarget = { status ->
                    currentDragPosition?.let { columnBounds[status]?.contains(it) } ?: false
                },
                onBoundsChanged = { rect, status -> columnBounds[status] = rect },
                onTaskDragStart = { task -> draggedTask = task },
                onTaskDragChange = { pos -> currentDragPosition = pos },
                onTaskDragEnd = {
                    val dropPosition = currentDragPosition ?: return@TaskBoard
                    val targetStatus = columnBounds.entries
                        .firstOrNull { (_, rect) -> rect.contains(dropPosition) }?.key

                    draggedTask?.let { task ->
                        if (targetStatus != null && task.status != targetStatus) {
                            projectStateHolder.changeTaskStatus(task = task, newStatus = targetStatus)
                        }
                    }
                    currentDragPosition = null
                    draggedTask = null
                },
                onTaskDragCancel = {
                    currentDragPosition = null
                    draggedTask = null
                },
                projectTask = projectStateHolder.currentProject,
                onClickCreate = { showDialog = true },
                onClickTask = { task ->
                    projectStateHolder.selectedTask = task
                    showDialog = true
                },
                modifier = Modifier.semantics { contentDescription = "${projectStateHolder.currentProject.name} 화면" },
            )
        }

        SnackbarHost(
            hostState = snackBarHostState,
            modifier = Modifier.align(Alignment.BottomCenter),
        )
    }
}

@Composable
@Preview
private fun KanbanBoardScreenPreview() {
    KanbanBoardScreen(
        remember {
            ProjectStateHolder(
                initialProjects = listOf(KanbanProject(name = "project1"), KanbanProject(name = "project2")),
            )
        },
    )
}
