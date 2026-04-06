package woowacourse.kanban.board.task.domain

data class KanbanBoard(val boardId: Int, val title: String, val cards: List<KanbanCard> = emptyList()) {
    init {
        require(boardId >= 0) { "보드 ID는 0 이상이어야 합니다." }
    }

    val totalCount: Int get() = cards.size
    val doneCount: Int get() = cards.count { it.status == KanbanStatus.DONE }

    fun getCardByStatus(status: KanbanStatus) = cards.filter { it.status == status }

    fun getCard(cardId: String): KanbanCard? = cards.find { it.id == cardId }

    fun addCard(card: KanbanCard): KanbanBoard = copy(cards = cards + card)

    fun updateCardStatus(cardId: String, status: KanbanStatus): KanbanBoard? {
        val targetCard = getCard(cardId) ?: return null
        val newCard = targetCard.updateStatus(status)

        val newCards = cards.map {
            if (it.id == cardId) newCard
            else it
        }
        return copy(cards = newCards)
    }

    fun updateCard(cardId: String, card: KanbanCard): KanbanBoard? {
        val targetCard = getCard(cardId) ?: return null
        val newCard = targetCard.update(card)

        val newCards = cards.map {
            if (it.id == cardId) newCard else it
        }
        return copy(cards = newCards)
    }

    fun deleteCard(cardId: String): KanbanBoard? {
        val targetCard = getCard(cardId) ?: return null
        targetCard.validateDeletable()
        return copy(cards = cards.filterNot { it.id == cardId })
    }
}
