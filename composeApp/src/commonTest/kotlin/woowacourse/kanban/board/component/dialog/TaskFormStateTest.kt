package woowacourse.kanban.board.component.dialog

import kotlin.test.Test
import org.assertj.core.api.Assertions.assertThat
import woowacourse.kanban.board.domain.KanbanTask
import woowacourse.kanban.board.domain.TaskStatus
import woowacourse.kanban.board.feature.board.component.dialog.TaskFormState

class TaskFormStateTest {

    @Test
    fun `초기 상태에서는 에러가 발생하지 않는다`() {
        // Given & When
        val state = TaskFormState()

        // Then
        assertThat(state.isTitleError).isFalse()
        assertThat(state.isTagCountError).isFalse()
        assertThat(state.isTagFormatError).isFalse()
        assertThat(state.isConfirmButtonEnabled).isFalse()
    }

    @Test
    fun `제목 입력을 시도했고, 값이 비어있으면 isTitleError가 true가 된다`() {
        // Given
        val state = TaskFormState()

        // When
        state.title = ""
        state.isTitleDirty = true

        // Then
        assertThat(state.isTitleError).isTrue()
        assertThat(state.isConfirmButtonEnabled).isFalse()
    }

    @Test
    fun `올바른 제목과 태그를 입력하면 생성 버튼이 활성화된다`() {
        // Given
        val state = TaskFormState()

        // When
        state.title = "정상 제목"
        state.tagValue = "버그, 긴급"
        state.isTitleDirty = true

        // Then
        assertThat(state.isTitleError).isFalse()
        assertThat(state.isTagCountError).isFalse()
        assertThat(state.isTagFormatError).isFalse()
        assertThat(state.isConfirmButtonEnabled).isTrue()
    }

    @Test
    fun `태그 개수가 6개 이상이면 isTagCountError가 true가 된다`() {
        // Given
        val state = TaskFormState()

        // When
        state.title = "제목"
        state.tagValue = "1, 2, 3, 4, 5, 6"

        // Then
        assertThat(state.isTagCountError).isTrue()
        assertThat(state.isTagFormatError).isFalse()
        assertThat(state.isConfirmButtonEnabled).isFalse()
    }

    @Test
    fun `태그 중 5자를 초과하는 것이 있으면 isTagFormatError가 true가 된다`() {
        // Given
        val state = TaskFormState()

        // When
        state.title = "제목"
        state.tagValue = "정상, 여섯글자태그"

        // Then
        assertThat(state.isTagCountError).isFalse()
        assertThat(state.isTagFormatError).isTrue()
        assertThat(state.isConfirmButtonEnabled).isFalse()
    }

    @Test
    fun `담당자가 필요한 상태에서 담당자 없이 입력하면 확인 버튼이 비활성화된다`() {
        // Given
        val state = TaskFormState()

        // When
        state.title = "제목"
        state.selectedStatus = TaskStatus.IN_PROGRESS

        // Then
        assertThat(state.isAssigneeRequired).isTrue()
        assertThat(state.isAssigneeError).isTrue()
        assertThat(state.isConfirmButtonEnabled).isFalse()
    }

    @Test
    fun `담당자가 필요한 상태에서 담당자를 입력하면 확인 버튼이 활성화된다`() {
        // Given
        val state = TaskFormState()

        // When
        state.title = "제목"
        state.selectedStatus = TaskStatus.IN_PROGRESS
        state.selectedAssignee = "다이노"

        // Then
        assertThat(state.isAssigneeRequired).isTrue()
        assertThat(state.isAssigneeError).isFalse()
        assertThat(state.isConfirmButtonEnabled).isTrue()
    }

    @Test
    fun `TODO 상태에서는 담당자 없이도 확인 버튼이 활성화된다`() {
        // Given
        val state = TaskFormState()

        // When
        state.title = "제목"

        // Then
        assertThat(state.isAssigneeRequired).isFalse()
        assertThat(state.isConfirmButtonEnabled).isTrue()
    }

    @Test
    fun `task로 생성하면 해당 태스크의 값으로 초기화된다`() {
        // Given
        val task = KanbanTask(
            title = "기존 제목",
            description = "기존 설명",
            status = TaskStatus.IN_PROGRESS,
            crewName = "다이노",
        )

        // When
        val state = TaskFormState(task = task)

        // Then
        assertThat(state.title).isEqualTo("기존 제목")
        assertThat(state.description).isEqualTo("기존 설명")
        assertThat(state.selectedStatus).isEqualTo(TaskStatus.IN_PROGRESS)
        assertThat(state.selectedAssignee).isEqualTo("다이노")
        assertThat(state.isConfirmButtonEnabled).isTrue()
    }
}
