package woowacourse.kanban.board.task.ui.project

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import woowacourse.kanban.board.task.domain.KanbanBoard
import woowacourse.kanban.board.task.domain.KanbanCard
import woowacourse.kanban.board.task.domain.KanbanError
import woowacourse.kanban.board.task.domain.KanbanProject
import woowacourse.kanban.board.task.domain.KanbanProjectResult
import woowacourse.kanban.board.task.domain.KanbanStatus

@Composable
fun RememberKanbanProjectState(coroutineScope: CoroutineScope, kanbanProject: KanbanProject): KanbanProjectState {
    val snackbarHostState = remember { SnackbarHostState() }
    return remember(kanbanProject) {
        KanbanProjectState(
            snackbarHostState = snackbarHostState,
            coroutineScope = coroutineScope,
            kanbanProject = kanbanProject,
        )
    }
}

@Stable
class KanbanProjectState(val snackbarHostState: SnackbarHostState, val coroutineScope: CoroutineScope, kanbanProject: KanbanProject) {
    var selectedBoard by mutableIntStateOf(0)

    var kanbanProject by mutableStateOf(
        kanbanProject,
    )

    val kanbanBoard: KanbanBoard?
        get() = kanbanProject.getBoard(selectedBoard)

    var draggedTask by mutableStateOf<KanbanCard?>(null)
    var currentDragPosition by mutableStateOf<Offset?>(null)
    val columnBounds = mutableStateMapOf<KanbanStatus, Rect>()

    var isShowCreateModal by mutableStateOf(false)

    var isShowEditModal by mutableStateOf(false)
    var editingCard by mutableStateOf<KanbanCard?>(null)

    fun onSelectBoard(index: Int) {
        selectedBoard = index
    }

    fun showEditModal(card: KanbanCard) {
        editingCard = card
        isShowEditModal = true
    }

    fun onAddCard(boardId: Int, card: KanbanCard) {
        val newProject = kanbanProject.addBoardCard(
            boardId,
            card,
        )
        if (newProject is KanbanProjectResult.Success) kanbanProject = newProject.project
    }

    fun onCreate(card: KanbanCard) {
        onAddCard(selectedBoard, card)
        isShowCreateModal = false
        coroutineScope.launch {
            snackbarHostState.currentSnackbarData?.dismiss()
            snackbarHostState.showSnackbar(
                message = "새로운 태스크가 추가되었습니다.",
                duration = SnackbarDuration.Short,
            )
        }
    }

    fun onEdit(kanbanCard: KanbanCard) {
        val editCard = editingCard ?: return
        val projectResult = kanbanProject.updateCard(
            boardId = selectedBoard,
            cardId = editCard.id,
            card = kanbanCard,
        )
        when (projectResult) {
            is KanbanProjectResult.Failure -> {
                val message = getErrorMessage(projectResult.error)
                coroutineScope.launch {
                    snackbarHostState.currentSnackbarData?.dismiss()
                    snackbarHostState.showSnackbar(
                        message = message,
                        duration = SnackbarDuration.Short,
                    )
                }
            }
            is KanbanProjectResult.Success -> {
                kanbanProject = projectResult.project
                isShowEditModal = false
                coroutineScope.launch {
                    snackbarHostState.currentSnackbarData?.dismiss()
                    snackbarHostState.showSnackbar(
                        message = "태스크가 수정되었습니다.",
                        duration = SnackbarDuration.Short,
                    )
                }
            }
        }
    }

    fun onDelete() {
        val editCard = editingCard ?: return
        val projectResult = kanbanProject.deleteCard(
            boardId = selectedBoard,
            cardId = editCard.id,
        )
        when (projectResult) {
            is KanbanProjectResult.Failure -> {
                val message = getErrorMessage(projectResult.error)
                coroutineScope.launch {
                    snackbarHostState.currentSnackbarData?.dismiss()
                    snackbarHostState.showSnackbar(
                        message = message,
                        duration = SnackbarDuration.Short,
                    )
                }
            }
            is KanbanProjectResult.Success -> {
                kanbanProject = projectResult.project
                isShowEditModal = false
                coroutineScope.launch {
                    snackbarHostState.currentSnackbarData?.dismiss()
                    snackbarHostState.showSnackbar(
                        message = "태스크가 삭제되었습니다.",
                        duration = SnackbarDuration.Short,
                    )
                }
            }
        }
    }

    fun onUpdateStatus(card: KanbanCard, targetStatus: KanbanStatus) {
        val updateProject = kanbanProject.updateCardStatus(
            boardId = selectedBoard,
            cardId = card.id,
            status = targetStatus,
        )
        when (updateProject) {
            is KanbanProjectResult.Failure -> {
                val message = getErrorMessage(updateProject.error)
                coroutineScope.launch {
                    snackbarHostState.currentSnackbarData?.dismiss()

                    snackbarHostState.showSnackbar(
                        message = message,
                        duration = SnackbarDuration.Short,
                    )
                }
            }
            is KanbanProjectResult.Success -> {
                coroutineScope.launch {
                    snackbarHostState.currentSnackbarData?.dismiss()

                    snackbarHostState.showSnackbar(
                        message = "태스크가 수정되었습니다.",
                        duration = SnackbarDuration.Short,
                    )
                }
            }
        }
        if (updateProject is KanbanProjectResult.Success) kanbanProject = updateProject.project
    }

    private fun getErrorMessage(kanbanError: KanbanError) = when (kanbanError) {
        KanbanError.INVALID_TRANSITION -> "해당 상태로 옮길 수 없습니다."
        KanbanError.ASSIGNEE_REQUIRED -> "담당자를 지정해야 상태를 옮길 수 있습니다."
        KanbanError.DELETION_NOT_ALLOWED -> "해당 상태에서는 태스크 삭제가 불가합니다."
        KanbanError.KANBAN_NOT_FOUND -> "카드를 찾을 수 없습니다."
    }
}
