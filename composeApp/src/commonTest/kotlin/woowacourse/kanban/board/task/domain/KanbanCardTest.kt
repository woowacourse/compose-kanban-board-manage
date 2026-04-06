package woowacourse.kanban.board.task.domain

import kotlin.test.assertFailsWith
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class KanbanCardTest {
    private fun createCard(status: KanbanStatus, assigneeName: String? = "담당자 1"): KanbanCard {
        return KanbanCard(
            title = "칸반제목 1",
            assigneeName = assigneeName,
            status = status,
        )
    }

    @Test
    fun `KanbanCard의 Status가 변경된다`() {
        val kanbanCard = createCard(KanbanStatus.TO_DO)

        val updateKanbanCard = kanbanCard.updateStatus(next = KanbanStatus.IN_PROGRESS)

        assertThat(updateKanbanCard.status).isEqualTo(KanbanStatus.IN_PROGRESS)
    }

    @Test
    fun `담당자가 없으면 To Do에서 In Progress로 옮길 수 없다`() {
        val kanbanCard = createCard(
            status = KanbanStatus.TO_DO,
            assigneeName = null,
        )

        assertFailsWith<IllegalArgumentException> {
            kanbanCard.updateStatus(KanbanStatus.IN_PROGRESS)
        }
    }

    @Test
    fun `In Progress 상태의 KanbanCard는 Review 상태로 변경할 수 있다`() {
        val kanbanCard = createCard(KanbanStatus.IN_PROGRESS)

        val updatedKanbanCard = kanbanCard.updateStatus(KanbanStatus.REVIEW)

        assertThat(updatedKanbanCard.status).isEqualTo(KanbanStatus.REVIEW)
    }

    @Test
    fun `Review 상태의 KanbanCard는 Done 상태로 변경할 수 있다`() {
        val kanbanCard = createCard(KanbanStatus.REVIEW)

        val updatedKanbanCard = kanbanCard.updateStatus(KanbanStatus.DONE)

        assertThat(updatedKanbanCard.status).isEqualTo(KanbanStatus.DONE)
    }

    @Test
    fun `Done 상태의 KanbanCard는 To Do 상태로 변경할 수 있다`() {
        val kanbanCard = createCard(KanbanStatus.DONE)

        val updatedKanbanCard = kanbanCard.updateStatus(KanbanStatus.TO_DO)

        assertThat(updatedKanbanCard.status).isEqualTo(KanbanStatus.TO_DO)
    }

    @Test
    fun `To Do 상태의 KanbanCard는 Review 상태로 변경할 수 없다`() {
        val kanbanCard = createCard(KanbanStatus.TO_DO)

        assertFailsWith<IllegalArgumentException> {
            kanbanCard.updateStatus(KanbanStatus.REVIEW)
        }
    }

    @Test
    fun `In Progress 상태의 KanbanCard는 Done 상태로 변경할 수 없다`() {
        val kanbanCard = createCard(KanbanStatus.IN_PROGRESS)

        assertFailsWith<IllegalArgumentException> {
            kanbanCard.updateStatus(KanbanStatus.DONE)
        }
    }

    @Test
    fun `Review 상태의 KanbanCard는 To Do 상태로 변경할 수 없다`() {
        val kanbanCard = createCard(KanbanStatus.REVIEW)

        assertFailsWith<IllegalArgumentException> {
            kanbanCard.updateStatus(KanbanStatus.TO_DO)
        }
    }

    @Test
    fun `Done 상태의 KanbanCard는 Review 상태로 변경할 수 없다`() {
        val kanbanCard = createCard(KanbanStatus.DONE)

        assertFailsWith<IllegalArgumentException> {
            kanbanCard.updateStatus(KanbanStatus.REVIEW)
        }
    }

    @Test
    fun `제목에 비어있거나 공백이 입력되면 에러가 발생한다`() {
        assertFailsWith<IllegalArgumentException> {
            KanbanCard(
                title = "",
                assigneeName = "바드",
                status = KanbanStatus.TO_DO,
            )
        }
        assertFailsWith<IllegalArgumentException> {
            KanbanCard(
                title = "      ",
                assigneeName = "바드",
                status = KanbanStatus.TO_DO,
            )
        }
    }

    @Test
    fun `태그의 개수가 5개 이상이면 에러가 발생한다`() {
        assertFailsWith<IllegalArgumentException> {
            KanbanCard(
                title = "제목이름",
                assigneeName = "바드",
                tags = listOf("태그1", "태그2", "태그3", "태그4", "태그5", "태그6"),
                status = KanbanStatus.TO_DO,
            )
        }
    }

    @Test
    fun `태그가 5글자 이상이면 에러가 발생한다`() {
        assertFailsWith<IllegalArgumentException> {
            KanbanCard(
                title = "제목 이름",
                assigneeName = "바드",
                tags = listOf("긴 태그이름입니다."),
                status = KanbanStatus.TO_DO,
            )
        }
    }

    @Test
    fun `정상 테스트`() {
        val card = KanbanCard(
            title = "제목 이름",
            assigneeName = "바드",
            tags = listOf("태그1", "태그2", "태그3"),
            content = "칸반 카드 내용",
            status = KanbanStatus.TO_DO,
        )

        assertThat(card.title).isEqualTo("제목 이름")
        assertThat(card.assigneeName).isEqualTo("바드")
        assertThat(card.tags).containsExactly("태그1", "태그2", "태그3")
        assertThat(card.content).isEqualTo("칸반 카드 내용")
    }

    @Test
    fun `'ToDo' 상태의 'KanbanCard'는 담당자 미지정을 허용한다`() {
        val kanbanCard = createCard(
            status = KanbanStatus.TO_DO,
            assigneeName = null,
        )

        assertThat(kanbanCard.assigneeName).isNull()
        assertThat(kanbanCard.status).isEqualTo(KanbanStatus.TO_DO)
    }
}
