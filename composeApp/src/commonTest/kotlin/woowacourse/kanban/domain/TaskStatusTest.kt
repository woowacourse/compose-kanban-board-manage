package woowacourse.kanban.domain

import kotlin.test.assertEquals
import org.junit.Test

class TaskStatusTest {

    private val assignee = Assignee(Nickname("아오"))

    @Test
    fun `TO_DO에서 IN_PROGRESS로 변경 가능하다`() {
        // given : TO_DO 상태와 담당자가 주어진다
        val status = TaskStatus.TO_DO

        // when : IN_PROGRESS로 변경 가능 여부를 확인할 때
        val result = status.isChangeable(TaskStatus.IN_PROGRESS, assignee)

        // then : Changeable이어야 한다
        assertEquals(ChangeableResult.Changeable, result)
    }

    @Test
    fun `TO_DO에서 담당자 없이 IN_PROGRESS로 변경하면 NotAssigned`() {
        // given : TO_DO 상태와 담당자가 없는 경우가 주어진다
        val status = TaskStatus.TO_DO

        // when : 담당자 없이 IN_PROGRESS로 변경할 때
        val result = status.isChangeable(TaskStatus.IN_PROGRESS, null)

        // then : NotAssigned여야 한다
        assertEquals(ChangeableResult.NotAssigned, result)
    }

    @Test
    fun `TO_DO에서 REVIEW로 변경 불가능하다`() {
        // given : TO_DO 상태가 주어진다
        val status = TaskStatus.TO_DO

        // when : REVIEW로 변경할 때
        val result = status.isChangeable(TaskStatus.REVIEW, assignee)

        // then : NotChangeable이어야 한다
        assertEquals(ChangeableResult.NotChangeable, result)
    }

    @Test
    fun `TO_DO에서 DONE으로 변경 불가능하다`() {
        // given : TO_DO 상태가 주어진다
        val status = TaskStatus.TO_DO

        // when : DONE으로 변경할 때
        val result = status.isChangeable(TaskStatus.DONE, assignee)

        // then : NotChangeable이어야 한다
        assertEquals(ChangeableResult.NotChangeable, result)
    }

    @Test
    fun `IN_PROGRESS에서 TO_DO로 변경 가능하다`() {
        // given : IN_PROGRESS 상태가 주어진다
        val status = TaskStatus.IN_PROGRESS

        // when : TO_DO로 변경할 때
        val result = status.isChangeable(TaskStatus.TO_DO, assignee)

        // then : Changeable이어야 한다
        assertEquals(ChangeableResult.Changeable, result)
    }

    @Test
    fun `IN_PROGRESS에서 REVIEW로 변경 가능하다`() {
        // given : IN_PROGRESS 상태가 주어진다
        val status = TaskStatus.IN_PROGRESS

        // when : REVIEW로 변경할 때
        val result = status.isChangeable(TaskStatus.REVIEW, assignee)

        // then : Changeable이어야 한다
        assertEquals(ChangeableResult.Changeable, result)
    }

    @Test
    fun `IN_PROGRESS에서 DONE으로 변경 불가능하다`() {
        // given : IN_PROGRESS 상태가 주어진다
        val status = TaskStatus.IN_PROGRESS

        // when : DONE으로 변경할 때
        val result = status.isChangeable(TaskStatus.DONE, assignee)

        // then : NotChangeable이어야 한다
        assertEquals(ChangeableResult.NotChangeable, result)
    }

    @Test
    fun `REVIEW에서 IN_PROGRESS로 변경 가능하다`() {
        // given : REVIEW 상태가 주어진다
        val status = TaskStatus.REVIEW

        // when : IN_PROGRESS로 변경할 때
        val result = status.isChangeable(TaskStatus.IN_PROGRESS, assignee)

        // then : Changeable이어야 한다
        assertEquals(ChangeableResult.Changeable, result)
    }

    @Test
    fun `REVIEW에서 DONE으로 변경 가능하다`() {
        // given : REVIEW 상태가 주어진다
        val status = TaskStatus.REVIEW

        // when : DONE으로 변경할 때
        val result = status.isChangeable(TaskStatus.DONE, assignee)

        // then : Changeable이어야 한다
        assertEquals(ChangeableResult.Changeable, result)
    }

    @Test
    fun `REVIEW에서 TO_DO로 변경 불가능하다`() {
        // given : REVIEW 상태가 주어진다
        val status = TaskStatus.REVIEW

        // when : TO_DO로 변경할 때
        val result = status.isChangeable(TaskStatus.TO_DO, assignee)

        // then : NotChangeable이어야 한다
        assertEquals(ChangeableResult.NotChangeable, result)
    }

    @Test
    fun `DONE에서 TO_DO로 변경 가능하다`() {
        // given : DONE 상태가 주어진다
        val status = TaskStatus.DONE

        // when : TO_DO로 변경할 때
        val result = status.isChangeable(TaskStatus.TO_DO, assignee)

        // then : Changeable이어야 한다
        assertEquals(ChangeableResult.Changeable, result)
    }

    @Test
    fun `DONE에서 IN_PROGRESS로 변경 불가능하다`() {
        // given : DONE 상태가 주어진다
        val status = TaskStatus.DONE

        // when : IN_PROGRESS로 변경할 때
        val result = status.isChangeable(TaskStatus.IN_PROGRESS, assignee)

        // then : NotChangeable이어야 한다
        assertEquals(ChangeableResult.NotChangeable, result)
    }

    @Test
    fun `TO_DO와 IN_PROGRESS는 삭제 가능하다`() {
        // given : TO_DO와 IN_PROGRESS 상태가 주어진다
        // when & then : isRemovable이 true여야 한다
        assertEquals(true, TaskStatus.TO_DO.isRemovable)
        assertEquals(true, TaskStatus.IN_PROGRESS.isRemovable)
    }

    @Test
    fun `REVIEW와 DONE은 삭제 불가능하다`() {
        // given : REVIEW와 DONE 상태가 주어진다
        // when & then : isRemovable이 false여야 한다
        assertEquals(false, TaskStatus.REVIEW.isRemovable)
        assertEquals(false, TaskStatus.DONE.isRemovable)
    }
}
