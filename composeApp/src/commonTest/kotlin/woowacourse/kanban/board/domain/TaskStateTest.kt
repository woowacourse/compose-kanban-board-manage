package woowacourse.kanban.board.domain

import kotlin.test.Test
import org.assertj.core.api.Assertions.assertThatThrownBy
import woowacourse.kanban.board.exception.TransStateError
import woowacourse.kanban.board.exception.TransStateException

class TaskStateTest {
    @Test
    fun `ToDo에서 In Progress외 다른 상태로 전이를 시도할 경우 IllegalArgumentException을 반환한다`() {
        val state = TaskState.ToDo

        assertThatThrownBy { state.transferTo(TaskState.Review) }
            .isInstanceOf(TransStateException::class.java)

        assertThatThrownBy { state.transferTo(TaskState.Done) }
            .isInstanceOf(TransStateException::class.java)
    }

    @Test
    fun `In Progress에서 ToDo, Review외 다른 상태로 전이를 시도할 경우 IllegalArgumentException을 반환한다`() {
        val state = TaskState.InProgress

        assertThatThrownBy { state.transferTo(TaskState.Done) }
            .isInstanceOf(TransStateException::class.java)
    }

    @Test
    fun `Review에서 In Progress, Done 다른 상태로 전이를 시도할 경우 IllegalArgumentException을 반환한다`() {
        val state = TaskState.Review

        assertThatThrownBy { state.transferTo(TaskState.ToDo) }
            .isInstanceOf(TransStateException::class.java)
    }

    @Test
    fun `Done에서 ToDo외 다른 상태로 전이를 시도할 경우 TransStateException 반환한다`() {
        val state = TaskState.Done

        assertThatThrownBy { state.transferTo(TaskState.Review) }
            .isInstanceOf(TransStateException::class.java)

        assertThatThrownBy { state.transferTo(TaskState.InProgress) }
            .isInstanceOf(TransStateException::class.java)
    }
}
