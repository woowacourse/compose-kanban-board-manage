package woowacourse.kanban.board.domain.validate

import kotlin.test.Test
import org.assertj.core.api.Assertions.assertThat
import woowacourse.kanban.board.domain.EditError
import woowacourse.kanban.board.domain.dialog.Status
import woowacourse.kanban.board.domain.validator.TaskEditValidator

class TaskEditValidatorTest {
    @Test
    fun `ToDo 상태에서 In Progress상태로 수정 시, 담당자가 있을 경우 null을 반환한다`() {
        val error = TaskEditValidator.validateEditStatus(
            status = Status.TO_DO,
            newStatus = Status.IN_PROGRESS,
            isAssigned = true,
        )

        assertThat(error).isEqualTo(null)
    }

    @Test
    fun `ToDo 상태에서 In Progress상태로 수정 시, 담당자가 없을 경우 UNASSIGNED 에러를 반환한다`() {
        val error = TaskEditValidator.validateEditStatus(
            status = Status.TO_DO,
            newStatus = Status.IN_PROGRESS,
            isAssigned = false,
        )

        assertThat(error).isEqualTo(EditError.UNASSIGNED)
    }

    @Test
    fun `ToDo 상태에서 Review, Doen 상태로 수정 시, 담당자가 없을 경우 INVALID_STATUS 에러를 반환한다`() {
        val error = TaskEditValidator.validateEditStatus(
            status = Status.TO_DO,
            newStatus = Status.REVIEW,
            isAssigned = true,
        )

        val error2 = TaskEditValidator.validateEditStatus(
            status = Status.TO_DO,
            newStatus = Status.DONE,
            isAssigned = true,
        )

        assertThat(error).isEqualTo(EditError.INVALID_STATUS)
        assertThat(error2).isEqualTo(EditError.INVALID_STATUS)
    }

    @Test
    fun `In Progress상태에서 Todo, Review 상태로 수정 시, null을 반환한다`() {
        val error = TaskEditValidator.validateEditStatus(
            status = Status.IN_PROGRESS,
            newStatus = Status.TO_DO,
            isAssigned = true,
        )

        val error2 = TaskEditValidator.validateEditStatus(
            status = Status.IN_PROGRESS,
            newStatus = Status.REVIEW,
            isAssigned = true,
        )

        assertThat(error).isEqualTo(null)
        assertThat(error2).isEqualTo(null)
    }

    @Test
    fun `In Progress상태에서 Done 상태로 수정 시, INVALID_STATUS 에러를 반환한다`() {
        val error = TaskEditValidator.validateEditStatus(
            status = Status.IN_PROGRESS,
            newStatus = Status.DONE,
            isAssigned = true,
        )

        assertThat(error).isEqualTo(EditError.INVALID_STATUS)
    }

    @Test
    fun `Review 상태에서 In Progress, Done 상태로 수정 시, null을 반환한다`() {
        val error = TaskEditValidator.validateEditStatus(
            status = Status.REVIEW,
            newStatus = Status.IN_PROGRESS,
            isAssigned = true,
        )

        val error2 = TaskEditValidator.validateEditStatus(
            status = Status.REVIEW,
            newStatus = Status.DONE,
            isAssigned = true,
        )

        assertThat(error).isEqualTo(null)
        assertThat(error2).isEqualTo(null)
    }

    @Test
    fun `Review 상태에서 ToDo 상태로 수정 시, INVALID_STATUS 에러를 반환한다`() {
        val error = TaskEditValidator.validateEditStatus(
            status = Status.REVIEW,
            newStatus = Status.TO_DO,
            isAssigned = true,
        )

        assertThat(error).isEqualTo(EditError.INVALID_STATUS)
    }

    @Test
    fun `Done 상태에서 ToDo 상태로 수정 시, null을 반환한다`() {
        val error = TaskEditValidator.validateEditStatus(
            status = Status.DONE,
            newStatus = Status.TO_DO,
            isAssigned = true,
        )

        assertThat(error).isEqualTo(null)
    }

    @Test
    fun `Done 상태에서 In Progress, Review 상태로 수정 시, INVALID_STATUS 에러를 반환한다`() {
        val error = TaskEditValidator.validateEditStatus(
            status = Status.DONE,
            newStatus = Status.IN_PROGRESS,
            isAssigned = true,
        )

        val error2 = TaskEditValidator.validateEditStatus(
            status = Status.DONE,
            newStatus = Status.REVIEW,
            isAssigned = true,
        )

        assertThat(error).isEqualTo(EditError.INVALID_STATUS)
        assertThat(error2).isEqualTo(EditError.INVALID_STATUS)
    }
}
