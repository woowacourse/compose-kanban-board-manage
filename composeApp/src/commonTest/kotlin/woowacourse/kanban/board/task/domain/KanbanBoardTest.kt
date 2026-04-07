package woowacourse.kanban.board.task.domain

import kotlin.test.assertIs
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class KanbanBoardTest {
    @Test
    fun `칸반 카드를 추가하면 새로운 보드를 반환한다`() {
        val board = KanbanBoard(
            boardId = 1,
            title = "보드",
        )
        val card = createKanbanCard(KanbanStatus.TO_DO)

        val boardResult = board.addCard(card)

        val newBoard = assertIs<KanbanBoardResult.Success>(boardResult)

        assertThat(newBoard.board.cards.size).isEqualTo(1)
        assertThat(newBoard.board.cards.first().title).isEqualTo("제목")
        assertThat(board.cards.size).isEqualTo(0)
        assertThat(board.cards.isEmpty()).isEqualTo(true)
    }

    @Test
    fun `칸반 보드의 전체 카드 수와 Done 카드 수를 반환한다`() {
        val cards = listOf(
            createKanbanCard(KanbanStatus.TO_DO),
            createKanbanCard(KanbanStatus.IN_PROGRESS),
            createKanbanCard(KanbanStatus.DONE),
        )

        val board = createKanbanBoard(cards)

        assertThat(board.totalCount).isEqualTo(3)
        assertThat(board.doneCount).isEqualTo(1)
    }

    @Test
    fun `칸반 상태에 따라 카드 리스트를 반환한다`() {
        val cards = listOf(
            createKanbanCard(KanbanStatus.TO_DO),
            createKanbanCard(KanbanStatus.TO_DO),
            createKanbanCard(KanbanStatus.IN_PROGRESS),
            createKanbanCard(KanbanStatus.DONE),
            createKanbanCard(KanbanStatus.DONE),
        )

        val board = createKanbanBoard(cards)

        assertThat(board.getCardByStatus(KanbanStatus.TO_DO).size).isEqualTo(2)
        assertThat(board.getCardByStatus(KanbanStatus.TO_DO).first().status).isEqualTo(KanbanStatus.TO_DO)
        assertThat(board.getCardByStatus(KanbanStatus.IN_PROGRESS).size).isEqualTo(1)
        assertThat(board.getCardByStatus(KanbanStatus.IN_PROGRESS).first().status).isEqualTo(KanbanStatus.IN_PROGRESS)
        assertThat(board.getCardByStatus(KanbanStatus.DONE).size).isEqualTo(2)
        assertThat(board.getCardByStatus(KanbanStatus.DONE).first().status).isEqualTo(KanbanStatus.DONE)
    }

    @Test
    fun `칸반 카드를 업데이트하면 변경된 보드르 반환한다`() {
        val toDoCards = createKanbanCard(KanbanStatus.TO_DO)

        val board = createKanbanBoard(cards = listOf(toDoCards))

        val boardResult = board.updateCardStatus(
            toDoCards.id,
            KanbanStatus.IN_PROGRESS,
        )

        val newBoard = assertIs<KanbanBoardResult.Success>(boardResult)

        assertThat(newBoard.board.getCardByStatus(KanbanStatus.TO_DO).size).isEqualTo(0)
        assertThat(newBoard.board.getCardByStatus(KanbanStatus.IN_PROGRESS).size).isEqualTo(1)
        assertThat(newBoard.board.getCardByStatus(KanbanStatus.IN_PROGRESS).first().status).isEqualTo(KanbanStatus.IN_PROGRESS)
    }

    @Test
    fun `잘못된 cardId를 조회시 null을 반환한다`() {
        val card = KanbanCard(
            id = "1",
            title = "제목",
            assigneeName = "담당자",
            status = KanbanStatus.TO_DO,
        )

        val board = createKanbanBoard(listOf(card))

        val searchCard = board.getCard("2")

        assertThat(searchCard).isNull()
    }

    private fun createKanbanCard(status: KanbanStatus) = KanbanCard(
        title = "제목",
        assigneeName = "담당자",
        status = status,
    )

    private fun createKanbanBoard(cards: List<KanbanCard>) = KanbanBoard(
        boardId = 1,
        title = "보드",
        cards = cards,
    )
}
