package woowacourse.kanban.board.domain.validate

import kotlin.test.Test
import org.assertj.core.api.Assertions.assertThat
import woowacourse.kanban.board.domain.DeleteError
import woowacourse.kanban.board.domain.dialog.Status
import woowacourse.kanban.board.domain.validator.TaskDeleteValidator

class TaskDeleteValidatorTest {
    @Test
    fun `Todo, In Progress 상태를 삭제 검증할 경우 null을 반환한다`() {
        val error = TaskDeleteValidator.validateDelete(Status.TO_DO)
        val error2 = TaskDeleteValidator.validateDelete(Status.IN_PROGRESS)

        assertThat(error).isEqualTo(null)
        assertThat(error2).isEqualTo(null)
    }

    @Test
    fun `Review, Done 상태를 삭제 검증할 경우 에러를 반환한다`() {
        val error = TaskDeleteValidator.validateDelete(Status.REVIEW)
        val error2 = TaskDeleteValidator.validateDelete(Status.DONE)

        assertThat(error).isEqualTo(DeleteError.FAILED)
        assertThat(error2).isEqualTo(DeleteError.FAILED)
    }
}
