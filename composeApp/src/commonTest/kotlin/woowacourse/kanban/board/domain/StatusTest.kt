package woowacourse.kanban.board.domain

import kotlin.test.Test
import woowacourse.kanban.board.domain.model.KanbanResult
import woowacourse.kanban.board.domain.model.Status

class StatusTest {

    @Test
    fun `To-Do에서 담당자가 있을 경우 In-Progress 전이가 가능하다`() {
        Status.TODO.validateTransition(Status.IN_PROGRESS, true)
    }

    @Test
    fun `To-Do에서 담당자가 없는 상태로 상태 전이 시 에러가 발생한다`() {
        val result = Status.TODO.validateTransition(Status.IN_PROGRESS, false)

        assert(result is KanbanResult.Failure)
    }

    @Test
    fun `In-Progress에서 to-do 상태로 전이가 가능하다`() {
        Status.IN_PROGRESS.validateTransition(Status.TODO, true)
    }

    @Test
    fun `In-Progress에서 Review 상태로 전이가 가능하다`() {
        Status.IN_PROGRESS.validateTransition(Status.REVIEW, true)
    }

    @Test
    fun `In-Progress에서 Done 상태로 전이 시 에러가 발생한다`() {
        val result = Status.IN_PROGRESS.validateTransition(Status.DONE, true)
        assert(result is KanbanResult.Failure)
    }

    @Test
    fun `Review에서 In-Progress로 전이가 가능하다`() {
        Status.REVIEW.validateTransition(Status.IN_PROGRESS, true)
    }

    @Test
    fun `Review에서 Done 상태로 전이가 가능하다`() {
        Status.REVIEW.validateTransition(Status.DONE, true)
    }

    @Test
    fun `Review에서 to-do 상태로 전이 시 에러가 발생한다`() {
        val result = Status.REVIEW.validateTransition(Status.TODO, true)
        assert(result is KanbanResult.Failure)
    }

    @Test
    fun `Done에서 TODO 상태로 전이가 가능하다`() {
        Status.DONE.validateTransition(Status.TODO, true)
    }
}
