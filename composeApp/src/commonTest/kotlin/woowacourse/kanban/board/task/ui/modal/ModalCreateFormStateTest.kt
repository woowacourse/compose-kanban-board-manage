package woowacourse.kanban.board.task.ui.modal

import kotlin.test.BeforeTest
import kotlin.test.Test
import org.assertj.core.api.Assertions.assertThat
import woowacourse.kanban.board.task.domain.KanbanCardError
import woowacourse.kanban.board.task.domain.TaskMockData

class ModalCreateFormStateTest {
    private lateinit var state: ModalCreateFormState

    @BeforeTest
    fun setUp() {
        state = ModalCreateFormState(TaskMockData.assignees)
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
    fun `태그 형식이 올바르지 않으면 유효하지 않다 (쉼표만 있는 경우)`() {
        state.tag = "태그1,,태그2"
        state.validate()
        assertThat(state.isValidTag).isFalse()
        assertThat(state.validTag).isEqualTo(KanbanCardError.TAG_FORMAT)
    }
}
