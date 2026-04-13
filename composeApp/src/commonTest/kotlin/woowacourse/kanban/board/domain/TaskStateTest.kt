package woowacourse.kanban.board.domain

import kotlin.test.Test
import org.assertj.core.api.Assertions.assertThat

class TaskStateTest {

    @Test
    fun `TODO에서 In Progress로 전이할 수 있다`() {
        // when
        val result = TaskState.isAvailableTransition(TaskState.TO_DO, TaskState.IN_PROGRESS)

        // then
        assertThat(result).isTrue
    }

    @Test
    fun `TODO에서 Review로 전이할 수 없다`() {
        // when
        val result = TaskState.isAvailableTransition(TaskState.TO_DO, TaskState.REVIEW)

        // then
        assertThat(result).isFalse
    }

    @Test
    fun `TODO에서 Done으로 전이할 수 없다`() {
        // when
        val result = TaskState.isAvailableTransition(TaskState.TO_DO, TaskState.DONE)

        // then
        assertThat(result).isFalse
    }

    @Test
    fun `InProgress에서 TODO로 전이할 수 있다`() {
        // when
        val result = TaskState.isAvailableTransition(TaskState.IN_PROGRESS, TaskState.TO_DO)

        // then
        assertThat(result).isTrue
    }

    @Test
    fun `InProgress에서 Review로 전이할 수 있다`() {
        // when
        val result = TaskState.isAvailableTransition(TaskState.IN_PROGRESS, TaskState.REVIEW)

        // then
        assertThat(result).isTrue
    }

    @Test
    fun `InProgress에서 Done로 전이할 수 없다`() {
        // when
        val result = TaskState.isAvailableTransition(TaskState.IN_PROGRESS, TaskState.DONE)

        // then
        assertThat(result).isFalse
    }

    @Test
    fun `Review에서 InProgress로 전이할 수 있다`() {
        // when
        val result = TaskState.isAvailableTransition(TaskState.REVIEW, TaskState.IN_PROGRESS)

        // then
        assertThat(result).isTrue
    }

    @Test
    fun `Review에서 Done으로 전이할 수 있다`() {
        // when
        val result = TaskState.isAvailableTransition(TaskState.REVIEW, TaskState.DONE)

        // then
        assertThat(result).isTrue
    }

    @Test
    fun `Review에서 ToDo로 전이할 수 없다`() {
        // when
        val result = TaskState.isAvailableTransition(TaskState.REVIEW, TaskState.TO_DO)

        // then
        assertThat(result).isFalse
    }

    @Test
    fun `Done에서 ToDo로 전이할 수 있다`() {
        // when
        val result = TaskState.isAvailableTransition(TaskState.DONE, TaskState.TO_DO)

        // then
        assertThat(result).isTrue
    }

    @Test
    fun `Done에서 InProgress로 전이할 수 없다`() {
        // when
        val result = TaskState.isAvailableTransition(TaskState.DONE, TaskState.IN_PROGRESS)

        // then
        assertThat(result).isFalse
    }

    @Test
    fun `Done에서 Review로 전이할 수 없다`() {
        // when
        val result = TaskState.isAvailableTransition(TaskState.DONE, TaskState.REVIEW)

        // then
        assertThat(result).isFalse
    }
}
