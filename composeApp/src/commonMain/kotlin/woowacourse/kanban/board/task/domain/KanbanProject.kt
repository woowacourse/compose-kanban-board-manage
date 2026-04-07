package woowacourse.kanban.board.task.domain

import java.util.UUID

data class KanbanProject(
    val id: String = UUID.randomUUID().toString(),
    val projectTitle: String,
    val boards: List<KanbanBoard> = emptyList(),
) {
    val boardTitles: List<String> = boards.map { it.title }
    fun getBoard(boardId: Int): KanbanBoard? = boards.find { it.boardId == boardId }

    fun updateCardStatus(boardId: Int, cardId: String, status: KanbanStatus): KanbanProjectResult {
        val targetBoard = getBoard(boardId) ?: return KanbanProjectResult.Failure.NotFound(type = "Board", id = boardId.toString())
        return when (val boardResult = targetBoard.updateCardStatus(cardId = cardId, status = status)) {
            is KanbanBoardResult.Failure -> boardResult.toProjectFailure()
            is KanbanBoardResult.Success -> {
                val newBoard = boards.map {
                    if (it.boardId == boardId) boardResult.board else it
                }
                KanbanProjectResult.Success(copy(boards = newBoard))
            }
        }
    }

    fun updateCard(boardId: Int, cardId: String, card: KanbanCard): KanbanProjectResult {
        val targetBoard = getBoard(boardId) ?: return KanbanProjectResult.Failure.NotFound(type = "Board", id = boardId.toString())
        return when (val boardResult = targetBoard.updateCard(cardId = cardId, updatedCard = card)) {
            is KanbanBoardResult.Failure -> boardResult.toProjectFailure()
            is KanbanBoardResult.Success -> {
                val newBoard = boards.map {
                    if (it.boardId == boardId) boardResult.board else it
                }
                KanbanProjectResult.Success(copy(boards = newBoard))
            }
        }
    }

    fun addBoardCard(boardId: Int, card: KanbanCard): KanbanProjectResult {
        val targetBoard = getBoard(boardId) ?: return KanbanProjectResult.Failure.NotFound(type = "Board", id = boardId.toString())
        return when (val boardResult = targetBoard.addCard(card)) {
            is KanbanBoardResult.Failure -> boardResult.toProjectFailure()
            is KanbanBoardResult.Success -> {
                val newBoard = boards.map {
                    if (it.boardId == boardId) boardResult.board else it
                }
                KanbanProjectResult.Success(copy(boards = newBoard))
            }
        }
    }

    fun deleteCard(boardId: Int, cardId: String): KanbanProjectResult {
        val targetBoard = getBoard(boardId) ?: return KanbanProjectResult.Failure.NotFound(type = "Board", id = boardId.toString())
        return when (val boardResult = targetBoard.deleteCard(cardId)) {
            is KanbanBoardResult.Failure -> boardResult.toProjectFailure()
            is KanbanBoardResult.Success -> {
                val newBoard = boards.map {
                    if (it.boardId == boardId) boardResult.board else it
                }
                KanbanProjectResult.Success(copy(boards = newBoard))
            }
        }
    }

    private fun KanbanBoardResult.Failure.toProjectFailure(): KanbanProjectResult.Failure {
        return when (this) {
            is KanbanBoardResult.Failure.AssigneeRequired -> KanbanProjectResult.Failure.AssigneeRequired(this.status)
            is KanbanBoardResult.Failure.DeletionNotAllowed -> KanbanProjectResult.Failure.DeletionNotAllowed(this.status)
            is KanbanBoardResult.Failure.InvalidTransition ->
                KanbanProjectResult.Failure.InvalidTransition(status = this.status, toStatus = this.toStatus)
            is KanbanBoardResult.Failure.NotFound -> KanbanProjectResult.Failure.NotFound(type = this.type, id = this.id)
        }
    }
}
