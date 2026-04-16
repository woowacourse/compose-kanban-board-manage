package woowacourse.kanban.board.domain

import kotlin.test.Test
import org.assertj.core.api.Assertions.assertThat

class TaskStateTest {
    @Test
    fun `ToDo에서 In Progress외 다른 상태로 전이를 시도할 경우 Failure를 반환한다`() {
        val state = TaskState.ToDo

        assertThat(state.transferTo(TaskState.Review)).isInstanceOf(DomainResult.Failure::class.java)
        assertThat(state.transferTo(TaskState.Done)).isInstanceOf(DomainResult.Failure::class.java)
    }

    @Test
    fun `In Progress에서 ToDo, Review외 다른 상태로 전이를 시도할 경우 Failure를 반환한다`() {
        val state = TaskState.InProgress

        assertThat(state.transferTo(TaskState.Done)).isInstanceOf(DomainResult.Failure::class.java)
    }

    @Test
    fun `Review에서 In Progress, Done 다른 상태로 전이를 시도할 경우 Failure를 반환한다`() {
        val state = TaskState.Review

        assertThat(state.transferTo(TaskState.ToDo)).isInstanceOf(DomainResult.Failure::class.java)
    }

    @Test
    fun `Done에서 ToDo외 다른 상태로 전이를 시도할 경우 Failure를 반환한다`() {
        val state = TaskState.Done

        assertThat(state.transferTo(TaskState.Review)).isInstanceOf(DomainResult.Failure::class.java)
        assertThat(state.transferTo(TaskState.InProgress)).isInstanceOf(DomainResult.Failure::class.java)
    }
}
