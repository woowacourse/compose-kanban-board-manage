package woowacourse.kanban.board.task.domain

import kotlin.test.assertFailsWith
import kotlin.test.assertIs
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class KanbanCardTest {

    @Test
    fun `Status가 Todo에서 Inprogress로만 변경할 수 있다`() {
        val card = KanbanCard(
            title = "제목",
            assigneeName = "담당자",
            status = KanbanStatus.TO_DO,
        )
        val progressResult = card.updateStatus(KanbanStatus.IN_PROGRESS)
        val reviewResult = card.updateStatus(KanbanStatus.REVIEW)
        val doneResult = card.updateStatus(KanbanStatus.DONE)

        val progressCard = assertIs<KanbanCardResult.Success>(progressResult)
        val reviewCard = assertIs<KanbanCardResult.Failure>(reviewResult)
        val doneCard = assertIs<KanbanCardResult.Failure>(doneResult)

        assertThat(progressCard.card.status).isEqualTo(KanbanStatus.IN_PROGRESS)
        assertThat(reviewCard.error).isEqualTo(KanbanError.INVALID_TRANSITION)
        assertThat(doneCard.error).isEqualTo(KanbanError.INVALID_TRANSITION)
    }

    @Test
    fun `Status가 Todo에서 Inprogress로 변경할 때 담당자가 필요하다`() {
        val card = KanbanCard(
            title = "제목",
            assigneeName = null,
            status = KanbanStatus.TO_DO,
        )

        val progressResult = card.updateStatus(KanbanStatus.IN_PROGRESS)
        val reviewResult = card.updateStatus(KanbanStatus.REVIEW)
        val doneResult = card.updateStatus(KanbanStatus.DONE)

        val progressCard = assertIs<KanbanCardResult.Failure>(progressResult)
        val reviewCard = assertIs<KanbanCardResult.Failure>(reviewResult)
        val doneCard = assertIs<KanbanCardResult.Failure>(doneResult)

        assertThat(progressCard.error).isEqualTo(KanbanError.ASSIGNEE_REQUIRED)
        assertThat(reviewCard.error).isEqualTo(KanbanError.INVALID_TRANSITION)
        assertThat(doneCard.error).isEqualTo(KanbanError.INVALID_TRANSITION)
    }

    @Test
    fun `Status가 Inprogress에서 Todo, Review으로만 변경할 수 있다`() {
        val card = KanbanCard(
            title = "제목",
            assigneeName = "담당자",
            status = KanbanStatus.IN_PROGRESS,
        )

        val todoResult = card.updateStatus(KanbanStatus.TO_DO)
        val reviewResult = card.updateStatus(KanbanStatus.REVIEW)
        val doneResult = card.updateStatus(KanbanStatus.DONE)

        val todoCard = assertIs<KanbanCardResult.Success>(todoResult)
        val reviewCard = assertIs<KanbanCardResult.Success>(reviewResult)
        val doneCard = assertIs<KanbanCardResult.Failure>(doneResult)

        assertThat(todoCard.card.status).isEqualTo(KanbanStatus.TO_DO)
        assertThat(reviewCard.card.status).isEqualTo(KanbanStatus.REVIEW)
        assertThat(doneCard.error).isEqualTo(KanbanError.INVALID_TRANSITION)
    }

    @Test
    fun `Status가 Review에서 Inprogress, Done으로만 변경할 수 있다`() {
        val card = KanbanCard(
            title = "제목",
            assigneeName = "담당자",
            status = KanbanStatus.REVIEW,
        )

        val todoResult = card.updateStatus(KanbanStatus.TO_DO)
        val progressResult = card.updateStatus(KanbanStatus.IN_PROGRESS)
        val doneResult = card.updateStatus(KanbanStatus.DONE)

        val todoCard = assertIs<KanbanCardResult.Failure>(todoResult)
        val progressCard = assertIs<KanbanCardResult.Success>(progressResult)
        val doneCard = assertIs<KanbanCardResult.Success>(doneResult)

        assertThat(todoCard.error).isEqualTo(KanbanError.INVALID_TRANSITION)
        assertThat(progressCard.card.status).isEqualTo(KanbanStatus.IN_PROGRESS)
        assertThat(doneCard.card.status).isEqualTo(KanbanStatus.DONE)
    }

    @Test
    fun `Status가 Done에서 Todo로만 변경할 수 있다`() {
        val card = KanbanCard(
            title = "제목",
            assigneeName = "담당자",
            status = KanbanStatus.DONE,
        )

        val todoResult = card.updateStatus(KanbanStatus.TO_DO)
        val progressResult = card.updateStatus(KanbanStatus.IN_PROGRESS)
        val reviewResult = card.updateStatus(KanbanStatus.REVIEW)

        val todoCard = assertIs<KanbanCardResult.Success>(todoResult)
        val progressCard = assertIs<KanbanCardResult.Failure>(progressResult)
        val reviewCard = assertIs<KanbanCardResult.Failure>(reviewResult)

        assertThat(todoCard.card.status).isEqualTo(KanbanStatus.TO_DO)
        assertThat(progressCard.error).isEqualTo(KanbanError.INVALID_TRANSITION)
        assertThat(reviewCard.error).isEqualTo(KanbanError.INVALID_TRANSITION)
    }

    @Test
    fun `제목에 비어있거나 공백이 입력되면 에러가 발생한다`() {
        assertFailsWith<IllegalArgumentException> {
            KanbanCard(
                title = "",
                assigneeName = "바드",
                status = KanbanStatus.TO_DO,
            )
            KanbanCard(
                title = "      ",
                assigneeName = "바드",
                status = KanbanStatus.TO_DO,
            )
        }
    }

    @Test
    fun `태그의 개수가 5개 이상이면 에러가 발생`() {
        assertFailsWith<IllegalArgumentException> {
            KanbanCard(
                title = "제목이름",
                assigneeName = "바드",
                tags = listOf(
                    "태그1",
                    "태그2",
                    "태그3",
                    "태그4",
                    "태그5",
                    "태그6",
                ),
                status = KanbanStatus.TO_DO,
            )
        }
    }

    @Test
    fun `태그가 5글자 이상이면 에러가 발생`() {
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
        val title = "제목 이름"
        val assignee = "바드"
        val tags = listOf(
            "태그1",
            "태그2",
            "태그3",
        )
        val content = "칸반 카드 내용"

        val formInfo = KanbanCard(
            title = title,
            assigneeName = assignee,
            tags = tags,
            content = content,
            status = KanbanStatus.TO_DO,
        )

        assertThat(formInfo.title).isEqualTo("제목 이름")
        assertThat(formInfo.assigneeName).isEqualTo("바드")
        assertThat(formInfo.tags).isEqualTo(
            listOf(
                "태그1",
                "태그2",
                "태그3",
            ),
        )
        assertThat(formInfo.content).isEqualTo("칸반 카드 내용")
    }
}
