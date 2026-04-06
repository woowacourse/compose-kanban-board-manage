package woowacourse.kanban.board.task.domain

import kotlin.test.assertFailsWith
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class KanbanBoardTest {
    @Test
    fun `boardId가 음수이면 KanbanBoard를 생성할 수 없다`() {
        assertFailsWith<IllegalArgumentException> {
            KanbanBoard(
                boardId = -1,
                title = "보드",
            )
        }
    }

    @Test
    fun `칸반 카드를 추가하면 새로운 보드를 반환한다`() {
        val board = KanbanBoard(
            boardId = 1,
            title = "보드",
        )
        val card = createKanbanCard(KanbanStatus.TO_DO)

        val newBoard = board.addCard(card)

        assertThat(newBoard.cards).hasSize(1)
        assertThat(newBoard.cards.first().title).isEqualTo("제목")
        assertThat(board.cards).isEmpty()
    }

    @Test
    fun `칸반 보드의 전체 카드 수와 Done 카드 수를 반환한다`() {
        val board = createKanbanBoard(
            cards = listOf(
                createKanbanCard(KanbanStatus.TO_DO),
                createKanbanCard(KanbanStatus.IN_PROGRESS),
                createKanbanCard(KanbanStatus.DONE),
            ),
        )

        assertThat(board.totalCount).isEqualTo(3)
        assertThat(board.doneCount).isEqualTo(1)
    }

    @Test
    fun `칸반 상태에 따라 카드 리스트를 반환한다`() {
        val board = createKanbanBoard(
            cards = listOf(
                createKanbanCard(KanbanStatus.TO_DO),
                createKanbanCard(KanbanStatus.TO_DO),
                createKanbanCard(KanbanStatus.IN_PROGRESS),
                createKanbanCard(KanbanStatus.DONE),
                createKanbanCard(KanbanStatus.DONE),
            ),
        )

        assertThat(board.getCardByStatus(KanbanStatus.TO_DO)).hasSize(2)
        assertThat(board.getCardByStatus(KanbanStatus.IN_PROGRESS)).hasSize(1)
        assertThat(board.getCardByStatus(KanbanStatus.DONE)).hasSize(2)
    }

    @Test
    fun `칸반 카드를 업데이트하면 변경된 보드를 반환한다`() {
        val todoCard = createKanbanCard(
            status = KanbanStatus.TO_DO,
            id = "1",
        )
        val board = createKanbanBoard(cards = listOf(todoCard))

        val updatedBoard = board.updateCardStatus(
            cardId = todoCard.id,
            status = KanbanStatus.IN_PROGRESS,
        )

        assertThat(updatedBoard?.getCardByStatus(KanbanStatus.TO_DO)).isEmpty()
        assertThat(updatedBoard?.getCardByStatus(KanbanStatus.IN_PROGRESS)).hasSize(1)
        assertThat(updatedBoard?.getCardByStatus(KanbanStatus.IN_PROGRESS)?.first()?.status).isEqualTo(KanbanStatus.IN_PROGRESS)
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

    private fun createKanbanCard(status: KanbanStatus, id: String = "1") = KanbanCard(
        id = id,
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
