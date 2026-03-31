package woowacourse.kanban.board.ui.screen.board

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kanbanboard.composeapp.generated.resources.Res
import kanbanboard.composeapp.generated.resources.kanban_board_subtitle
import kanbanboard.composeapp.generated.resources.kanban_board_title
import org.jetbrains.compose.resources.stringResource
import woowacourse.kanban.board.domain.KanbanBoard
import woowacourse.kanban.board.domain.KanbanProject
import woowacourse.kanban.board.domain.KanbanTask
import woowacourse.kanban.board.domain.Status
import woowacourse.kanban.board.ui.component.board.CardGroup
import woowacourse.kanban.board.ui.component.board.KanbanBoardTopAppBar
import woowacourse.kanban.board.ui.component.board.sidebar.SideBar
import woowacourse.kanban.board.ui.component.dialog.TaskDialog

@Composable
fun KanbanBoardScreen(
    modifier: Modifier = Modifier,
    projects: List<KanbanProject> = listOf(KanbanProject("Compose1")),
    kanbanBoard: KanbanBoard = KanbanBoard(),
) {
    val kanbanBoardState = remember {
        KanbanBoardState(
            kanbanBoard = kanbanBoard,
            projects = projects,
        )
    }
    val snackBarHostState = remember { SnackbarHostState() }
    var snackbarMessage by remember { mutableStateOf<SnackbarMessage?>(null) }

    val totalCount = kanbanBoardState.getTotalCount()
    val completeCount = kanbanBoardState.getCompleteCount()
    val progress = if (totalCount == 0) 0f else completeCount.toFloat() / totalCount.toFloat()
    val progressPercent = (progress * 100).toInt()

    LaunchedEffect(key1 = snackbarMessage) {
        snackbarMessage?.let { message ->
            snackBarHostState.showSnackbar(
                message = message.text,
                withDismissAction = true,
                duration = SnackbarDuration.Short,
            )
            snackbarMessage = null
        }
    }

    KanbanBoardContent(
        projectTitles = kanbanBoardState.getProjectsTitles(),
        projectSelectedIndex = kanbanBoardState.selectedProjectIndex,
        cards = kanbanBoardState.tasks,
        completeCount = completeCount,
        totalCount = totalCount,
        progress = progress,
        progressPercent = progressPercent,
        isNewTaskDialog = kanbanBoardState.isNewTaskDialog,
        modifier = modifier,
        onNewTaskClick = {
            kanbanBoardState.showNewTaskDialog()
        },
        onDismissClick = {
            kanbanBoardState.hideNewTaskDialog()
        },
        snackHost = snackBarHostState,
        onCreateClick = {
            kanbanBoardState.addTask(it)
            kanbanBoardState.hideNewTaskDialog()
            snackbarMessage = SnackbarMessage.TASK_CREATED
        },
        updateSelectedProjectIndex = { kanbanBoardState.updateSelectedProjectIndex(it) },
        onMoveTask = { task, targetStatus ->
            kanbanBoardState.moveTask(task, targetStatus)
            snackbarMessage = SnackbarMessage.TASK_MOVED
        },
    )
}

@Composable
private fun KanbanBoardContent(
    projectTitles: List<String>,
    projectSelectedIndex: Int,
    cards: List<KanbanTask>,
    completeCount: Int,
    totalCount: Int,
    progress: Float,
    progressPercent: Int,
    isNewTaskDialog: Boolean,
    snackHost: SnackbarHostState,
    modifier: Modifier = Modifier,
    onNewTaskClick: () -> Unit,
    onDismissClick: () -> Unit,
    onCreateClick: (KanbanTask) -> Unit,
    onMoveTask: (KanbanTask, Status) -> Unit,
    updateSelectedProjectIndex: (Int) -> Unit,
) {
    // drag
    var draggedTask by remember { mutableStateOf<KanbanTask?>(null) }
    var currentDragPosition by remember { mutableStateOf<Offset?>(null) }
    val columnBounds = remember { mutableStateMapOf<Status, Rect>() }

    Scaffold(
        modifier = modifier,
        snackbarHost = { SnackbarHost(hostState = snackHost) },
        containerColor = Color.White,
    ) { innerPadding ->
        Row(
            modifier = Modifier
                .padding(innerPadding),
        ) {
            SideBar(
                title = stringResource(Res.string.kanban_board_title),
                subTitle = stringResource(Res.string.kanban_board_subtitle),
                titles = projectTitles,
                selectedIndex = projectSelectedIndex,
                onTitleClick = updateSelectedProjectIndex,
            )

            VerticalDivider(modifier = Modifier.fillMaxHeight())

            Column {
                KanbanBoardTopAppBar(
                    title = projectTitles[projectSelectedIndex],
                    progress = progress,
                    progressPercent = progressPercent,
                    completeCount = completeCount,
                    totalCount = totalCount,
                    onNewTaskClick = onNewTaskClick,
                )

                CardGroup(
                    cards = cards,
                    modifier = Modifier
                        .padding(24.dp),
                    getIsDropTarget = { status ->
                        currentDragPosition?.let { columnBounds[status]?.contains(it) == true } ?: false
                    },
                    onBoundsChanged = { rect, status ->
                        columnBounds[status] = rect
                    },
                    onTaskDragStart = { task ->
                        draggedTask = task
                    },
                    onTaskDragChange = { pos ->
                        currentDragPosition = pos
                    },
                    onTaskDragEnd = {
                        val dropPosition = currentDragPosition ?: return@CardGroup
                        val targetStatus = columnBounds.entries
                            .firstOrNull { (_, rect) -> rect.contains(dropPosition) }?.key

                        draggedTask?.let { task ->
                            if (task.status != targetStatus) {
                                onMoveTask(task, targetStatus ?: return@let)
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
            if (isNewTaskDialog) {
                TaskDialog(
                    onDismissClick = onDismissClick,
                    onCreateClick = onCreateClick,
                )
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 1551, heightDp = 909)
@Composable
private fun KanbanBoardContentPreview() {
    val task = KanbanTask(
        title = "LazyColumn 컴포넌트 구현",
        description = "세로 스크롤 가능한 리스트 컴포넌트를 만들고 성능 최적화를 적용합니다.",
        tags = listOf("컴포넌트", "성능"),
        status = Status.TO_DO,
        assignee = "다이노",
    )
    val cards = listOf(
        task.copy(id = 0L),
        task.copy(id = 1L, status = Status.TO_DO),
        task.copy(id = 2L, status = Status.IN_PROGRESS),
        task.copy(id = 3L, status = Status.DONE),
        task.copy(id = 4L, status = Status.DONE),
        task.copy(id = 5L, status = Status.DONE),
    )

    KanbanBoardContent(
        projectTitles = listOf("1", "2"),
        projectSelectedIndex = 0,
        cards = cards,
        completeCount = 3,
        totalCount = 6,
        progress = 0.5f,
        progressPercent = 50,
        isNewTaskDialog = false,
        modifier = Modifier.fillMaxSize(),
        onNewTaskClick = { },
        onCreateClick = { },
        snackHost = remember { SnackbarHostState() },
        onDismissClick = { },
        updateSelectedProjectIndex = { },
        onMoveTask = { _, _ -> },
    )
}
