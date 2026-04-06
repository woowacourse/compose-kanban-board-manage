package woowacourse.kanban.board.task.ui.modal

import kotlin.test.BeforeTest
import kotlin.test.Test
import org.assertj.core.api.Assertions.assertThat
import woowacourse.kanban.board.task.domain.KanbanCard
import woowacourse.kanban.board.task.domain.KanbanCardError
import woowacourse.kanban.board.task.domain.KanbanStatus

class ModalCreateFormStateTest {
    private lateinit var state: ModalFormState

    @BeforeTest
    fun setUp() {
        state = ModalFormState()
    }

    @Test
    fun `빈 제목은 유효하지 않다`() {
        state.title = ""
        state.validate()

        assertThat(state.isValidTitle).isFalse()
        assertThat(state.validTitle).isEqualTo(KanbanCardError.TITLE_FORMAT)
    }

    @Test
    fun `제목이 비어있지 않으면 유효하다`() {
        state.title = "태스크 제목"
        state.validate()

        assertThat(state.isValidTitle).isTrue()
        assertThat(state.validTitle).isNull()
    }

    @Test
    fun `태그가 비어있으면 유효하다`() {
        state.tag = ""
        state.validate()

        assertThat(state.isValidTag).isTrue()
        assertThat(state.validTag).isNull()
    }

    @Test
    fun `태그가 5개 이하이고 각각 5자 이하이면 유효하다`() {
        state.tag = "태그1,태그2,태그3,태그4,태그5"
        state.validate()

        assertThat(state.isValidTag).isTrue()
        assertThat(state.validTag).isNull()
    }

    @Test
    fun `태그가 5개를 초과하면 유효하지 않다`() {
        state.tag = "1,2,3,4,5,6"
        state.validate()

        assertThat(state.isValidTag).isFalse()
        assertThat(state.validTag).isEqualTo(KanbanCardError.TAG_SIZE)
    }

    @Test
    fun `태그 중 하나라도 5자를 초과하면 유효하지 않다`() {
        state.tag = "정상태그,육글자태그임"
        state.validate()

        assertThat(state.isValidTag).isFalse()
        assertThat(state.validTag).isEqualTo(KanbanCardError.TAG_SIZE)
    }

    @Test
    fun `태그 형식이 올바르지 않으면 유효하지 않다`() {
        state.tag = "태그1,,태그2"
        state.validate()

        assertThat(state.isValidTag).isFalse()
        assertThat(state.validTag).isEqualTo(KanbanCardError.TAG_FORMAT)
    }

    @Test
    fun `담당자 없음 옵션을 선택하면 null 담당자로 변환된다`() {
        state.title = "태스크 제목"
        state.assignee = AssigneeOption(AssigneeOptionType.NONE)

        val card = state.toKanbanCard()

        assertThat(card.assigneeName).isNull()
    }

    @Test
    fun `담당자가 없는 카드를 수정할 때 없음 옵션으로 초기화된다`() {
        val card = KanbanCard(
            title = "태스크 제목",
            status = KanbanStatus.TO_DO,
            assigneeName = null,
        )

        val restoredState = ModalFormState.from(card)

        assertThat(restoredState.assignee.type).isEqualTo(AssigneeOptionType.NONE)
        assertThat(restoredState.assignee.name).isNull()
    }

    @Test
    fun `담당자가 있는 카드를 수정할 때 담당자 옵션으로 초기화된다`() {
        val card = KanbanCard(
            title = "태스크 제목",
            status = KanbanStatus.REVIEW,
            assigneeName = "조디악",
        )

        val restoredState = ModalFormState.from(card)

        assertThat(restoredState.assignee.type).isEqualTo(AssigneeOptionType.MEMBER)
        assertThat(restoredState.assignee.name).isEqualTo("조디악")
    }
}
