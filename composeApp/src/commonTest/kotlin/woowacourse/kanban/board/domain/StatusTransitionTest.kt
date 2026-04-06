package woowacourse.kanban.board.domain

import kotlin.test.Test
import org.assertj.core.api.Assertions.assertThat

class StatusTransitionTest {
    @Test
    fun `To Do 상태에서 In Progress로 전이 가능하다`() {
        val allowed = Status.TODO.isStatusTransitionAllowed(Status.IN_PROGRESS)
        assertThat(allowed).isTrue()
    }

    @Test
    fun `To Do 상태에서 In Progress 이외 상태로는 전이 불가능하다`() {
        assertThat(Status.TODO.isStatusTransitionAllowed(Status.REVIEW)).isFalse()
        assertThat(Status.TODO.isStatusTransitionAllowed(Status.DONE)).isFalse()
        assertThat(Status.TODO.isStatusTransitionAllowed(Status.TODO)).isFalse()
    }

    @Test
    fun `In Progress 상태에서 To Do로 전이 가능하다`() {
        val allowed = Status.IN_PROGRESS.isStatusTransitionAllowed(Status.TODO)
        assertThat(allowed).isTrue()
    }

    @Test
    fun `In Progress 상태에서 Review로 전이 가능하다`() {
        val allowed = Status.IN_PROGRESS.isStatusTransitionAllowed(Status.REVIEW)
        assertThat(allowed).isTrue()
    }

    @Test
    fun `In Progress 상태에서 To Do, Review 이외 상태로는 전이 불가능하다`() {
        assertThat(Status.IN_PROGRESS.isStatusTransitionAllowed(Status.DONE)).isFalse()
        assertThat(Status.IN_PROGRESS.isStatusTransitionAllowed(Status.IN_PROGRESS)).isFalse()
    }

    @Test
    fun `Review 상태에서 In Progress로 전이 가능하다`() {
        val allowed = Status.REVIEW.isStatusTransitionAllowed(Status.IN_PROGRESS)
        assertThat(allowed).isTrue()
    }

    @Test
    fun `Review 상태에서 Done으로 전이 가능하다`() {
        val allowed = Status.REVIEW.isStatusTransitionAllowed(Status.DONE)
        assertThat(allowed).isTrue()
    }

    @Test
    fun `Review 상태에서 In Progress, Done 이외 상태로는 전이 불가능하다`() {
        assertThat(Status.REVIEW.isStatusTransitionAllowed(Status.TODO)).isFalse()
        assertThat(Status.REVIEW.isStatusTransitionAllowed(Status.REVIEW)).isFalse()
    }

    @Test
    fun `Done 상태에서 To Do로만 전이 가능하다`() {
        val allowed = Status.DONE.isStatusTransitionAllowed(Status.TODO)
        assertThat(allowed).isTrue()
    }

    @Test
    fun `Done 상태에서 To Do 이외 상태로는 전이 불가능하다`() {
        assertThat(Status.DONE.isStatusTransitionAllowed(Status.IN_PROGRESS)).isFalse()
        assertThat(Status.DONE.isStatusTransitionAllowed(Status.REVIEW)).isFalse()
        assertThat(Status.DONE.isStatusTransitionAllowed(Status.DONE)).isFalse()
    }

    @Test
    fun `To Do에서 In Progress 이동은 isMoveTodoToInProgress로 감지된다`() {
        val allowed = Status.TODO.isMoveTodoToInProgress(Status.IN_PROGRESS)
        assertThat(allowed).isTrue()
    }

    @Test
    fun `To Do에서 In Progress 이외 이동은 isMoveTodoToInProgress로 감지되지 않는다`() {
        assertThat(Status.TODO.isMoveTodoToInProgress(Status.REVIEW)).isFalse()
        assertThat(Status.TODO.isMoveTodoToInProgress(Status.DONE)).isFalse()
        assertThat(Status.TODO.isMoveTodoToInProgress(Status.TODO)).isFalse()
    }

    @Test
    fun `To Do가 아닌 상태에서는 isMoveTodoToInProgress가 false를 반환한다`() {
        assertThat(Status.IN_PROGRESS.isMoveTodoToInProgress(Status.IN_PROGRESS)).isFalse()
        assertThat(Status.REVIEW.isMoveTodoToInProgress(Status.IN_PROGRESS)).isFalse()
        assertThat(Status.DONE.isMoveTodoToInProgress(Status.IN_PROGRESS)).isFalse()
    }
}
