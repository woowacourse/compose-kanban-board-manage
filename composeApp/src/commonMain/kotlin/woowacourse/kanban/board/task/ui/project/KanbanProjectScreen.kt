package woowacourse.kanban.board.task.ui.project

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.launch
import woowacourse.kanban.board.task.domain.KanbanBoard
import woowacourse.kanban.board.task.domain.KanbanCard
import woowacourse.kanban.board.task.domain.KanbanProject
import woowacourse.kanban.board.task.domain.KanbanStatus
import woowacourse.kanban.board.task.domain.TaskMockData
import woowacourse.kanban.board.task.ui.board.KanbanBoardScreen

@Composable
fun KanbanProjectScreen(modifier: Modifier = Modifier) {
    var draggedTask by remember { mutableStateOf<KanbanCard?>(null) }
    var currentDragPosition by remember { mutableStateOf<Offset?>(null) }
    val columnBounds = remember { mutableStateMapOf<KanbanStatus, Rect>() }
    var selectedBoardId by remember { mutableIntStateOf(0) }
    var kanbanProject by remember {
        mutableStateOf(
            KanbanProject(
                projectTitle = "4주차 미션 보드",
                boards = TaskMockData.boards,
            ),
        )
    }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    var kanbanBoard by remember { mutableStateOf<KanbanBoard?>(null) }

    LaunchedEffect(selectedBoardId) {
        kanbanBoard = kanbanProject.getBoard(selectedBoardId)
    }

    kanbanBoard?.let { board ->
        Row(modifier = modifier) {
            KanbanProjectSideBar(
                modifier = Modifier.fillMaxHeight(),
                title = kanbanProject.projectTitle,
                boardTitle = kanbanProject.getBoardTitles(),
                selected = selectedBoardId,
                onClick = { index ->
                    selectedBoardId = index
                },
            )
            KanbanBoardScreen(
                kanbanBoard = board,
                onAddCard = { title, content, assigneeName, tags, status ->
                    val newProject = kanbanProject.addCard(
                        boardId = selectedBoardId,
                        title = title,
                        content = content,
                        assigneeName = assigneeName,
                        tags = tags,
                        status = status,
                    )
                    if (newProject != null) kanbanProject = newProject
                },
                onEditCard = { id, title, content, assigneeName, tags, status ->
                    val newProject = kanbanProject.updateCard(
                        boardId = selectedBoardId,
                        cardId = id,
                        title = title,
                        content = content,
                        assigneeName = assigneeName,
                        tags = tags,
                        status = status,
                    )
                    if (newProject != null) kanbanProject = newProject
                },
                onDeleteCard = { id ->
                    val newProject = kanbanProject.deleteCard(
                        boardId = selectedBoardId,
                        cardId = id,
                    )
                    if (newProject != null) kanbanProject = newProject
                },
                getIsDropTarget = { status ->
                    currentDragPosition?.let { columnBounds[status]?.contains(it) } ?: false
                },
                onBoundsChanged = { status, rect -> columnBounds[status] = rect },
                onTaskDragStart = { task -> draggedTask = task },
                onTaskDragChange = { pos -> currentDragPosition = pos },
                onTaskDragEnd = {
                    val dropPosition = currentDragPosition ?: return@KanbanBoardScreen
                    val targetStatus = columnBounds.entries
                        .firstOrNull { (_, rect) -> rect.contains(dropPosition) }
                        ?.key

                    draggedTask?.let { task ->
                        if (targetStatus != null && task.status != targetStatus) {
                            try {
                                val updateProject = kanbanProject.updateCardStatus(
                                    boardId = selectedBoardId,
                                    cardId = task.id,
                                    status = targetStatus,
                                )
                                if (updateProject != null) {
                                    kanbanProject = updateProject
                                }
                                scope.launch {
                                    snackbarHostState.showSnackbar(
                                        message = "태스크가 이동되었습니다.",
                                        duration = SnackbarDuration.Short,
                                    )
                                }
                            } catch (e: IllegalArgumentException) {
                                scope.launch {
                                    snackbarHostState.showSnackbar(
                                        message = e.message ?: "상태를 변경할 수 없습니다.",
                                        duration = SnackbarDuration.Short,
                                    )
                                }
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
                snackbarHostState = snackbarHostState,
                scope = scope,
            )
        }
    }
}

@Preview(widthDp = 1500)
@Composable
private fun KanbanProjectScreenPreview() {
    KanbanProjectScreen()
}
