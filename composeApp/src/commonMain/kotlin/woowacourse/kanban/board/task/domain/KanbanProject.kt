package woowacourse.kanban.board.task.domain

data class KanbanProject(
    val projectTitle: String,
    val boards: List<KanbanBoard> = emptyList(),
) {
    fun getBoard(boardId: Int): KanbanBoard? = boards.find { it.boardId == boardId }

    fun getBoardTitles(): List<String> = boards.map { it.title }

    fun updateCardStatus(boardId: Int, cardId: String, status: KanbanStatus): KanbanProject? {
        val targetBoard = getBoard(boardId) ?: return null
        val updateBoard = targetBoard.updateCardStatus(cardId = cardId, status = status) ?: return null
        val newBoards = boards.map {
            if (it.boardId == boardId) updateBoard else it
        }
        return copy(boards = newBoards)
    }

    fun addCard(
        boardId: Int,
        title: String,
        content: String,
        assigneeName: String?,
        tags: List<String>,
        status: KanbanStatus,
    ): KanbanProject? {
        val card = KanbanCard(
            title = title,
            content = content,
            status = status,
            assigneeName = assigneeName,
            tags = tags,
        )
        val targetBoard = getBoard(boardId) ?: return null
        val newCard = KanbanCard.create(card = card)
        val addBoard = targetBoard.addCard(newCard)
        val newBoards = boards.map {
            if (it.boardId == boardId) addBoard else it
        }
        return copy(boards = newBoards)
    }

    fun updateCard(
        boardId: Int,
        cardId: String,
        title: String,
        content: String,
        assigneeName: String?,
        tags: List<String>,
        status: KanbanStatus,
    ): KanbanProject? {
        val card = KanbanCard(
            title = title,
            content = content,
            status = status,
            assigneeName = assigneeName,
            tags = tags,
        )
        val targetBoard = getBoard(boardId) ?: return null
        val updateBoard = targetBoard.updateCard(cardId = cardId, card = card) ?: return null
        val newBoards = boards.map {
            if (it.boardId == boardId) updateBoard else it
        }
        return copy(boards = newBoards)
    }

    fun deleteCard(boardId: Int, cardId: String): KanbanProject? {
        val targetBoard = getBoard(boardId) ?: return null
        val deleteBoard = targetBoard.deleteCard(cardId) ?: return null
        val newBoards = boards.map {
            if (it.boardId == boardId) deleteBoard else it
        }
        return copy(boards = newBoards)
    }
}
