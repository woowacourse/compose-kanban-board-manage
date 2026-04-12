package woowacourse.kanban.card.model

import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Assert.assertThrows
import org.junit.Test
import woowacourse.kanban.domain.task.Done
import woowacourse.kanban.domain.task.InProgress
import woowacourse.kanban.domain.task.Review
import woowacourse.kanban.domain.task.TaskRules
import woowacourse.kanban.domain.task.TaskStatus
import woowacourse.kanban.domain.task.Todo

class TaskRulesTest {
    @Test
    fun `todo의 isDeletable은 true다`() {
        val taskRules: TaskRules = Todo()

        assertTrue(taskRules.isDeletable)
    }

    @Test
    fun `todo는 inProgress로 이동할 수 있다`() {
        val taskRules: TaskRules = Todo()

        assertTrue(taskRules.moveTo(TaskStatus.IN_PROGRESS) is InProgress)
    }

    @Test
    fun `todo는 review로 이동하면 예외가 발생한다`() {
        val taskRules: TaskRules = Todo()

        assertThrows(IllegalStateException::class.java) {
            taskRules.moveTo(TaskStatus.REVIEW)
        }
    }

    @Test
    fun `todo는 done으로 이동하면 예외가 발생한다`() {
        val taskRules: TaskRules = Todo()

        assertThrows(IllegalStateException::class.java) {
            taskRules.moveTo(TaskStatus.DONE)
        }
    }

    @Test
    fun `todo의 requireAssignee는 false다`() {
        val taskRules: TaskRules = Todo()

        assertFalse(taskRules.requireAssignee)
    }

    @Test
    fun `inProgress의 isDeletable은 true다`() {
        val taskRules: TaskRules = InProgress()

        assertTrue(taskRules.isDeletable)
    }

    @Test
    fun `inProgress는 todo로 이동할 수 있다`() {
        val taskRules: TaskRules = InProgress()

        assertTrue(taskRules.moveTo(TaskStatus.TO_DO) is Todo)
    }

    @Test
    fun `inProgress는 review로 이동할 수 있다`() {
        val taskRules: TaskRules = InProgress()

        assertTrue(taskRules.moveTo(TaskStatus.REVIEW) is Review)
    }

    @Test
    fun `inProgress는 done으로 이동하면 예외가 발생한다`() {
        val taskRules: TaskRules = InProgress()

        assertThrows(IllegalStateException::class.java) {
            taskRules.moveTo(TaskStatus.DONE)
        }
    }

    @Test
    fun `inProgress의 requireAssignee는 true다`() {
        val taskRules: TaskRules = InProgress()

        assertTrue(taskRules.requireAssignee)
    }

    @Test
    fun `review의 isDeletable은 false다`() {
        val taskRules: TaskRules = Review()

        assertFalse(taskRules.isDeletable)
    }

    @Test
    fun `review는 todo로 이동하면 예외가 발생한다`() {
        val taskRules: TaskRules = Review()

        assertThrows(IllegalStateException::class.java) {
            taskRules.moveTo(TaskStatus.TO_DO)
        }
    }

    @Test
    fun `review는 inProgress로 이동할 수 있다`() {
        val taskRules: TaskRules = Review()

        assertTrue(taskRules.moveTo(TaskStatus.IN_PROGRESS) is InProgress)
    }

    @Test
    fun `review는 done으로 이동할 수 있다`() {
        val taskRules: TaskRules = Review()

        assertTrue(taskRules.moveTo(TaskStatus.DONE) is Done)
    }

    @Test
    fun `review의 requireAssignee는 true다`() {
        val taskRules: TaskRules = Review()

        assertTrue(taskRules.requireAssignee)
    }

    @Test
    fun `done의 isDeletable은 false다`() {
        val taskRules: TaskRules = Done()

        assertFalse(taskRules.isDeletable)
    }

    @Test
    fun `done은 todo로 이동할 수 있다`() {
        val taskRules: TaskRules = Done()

        assertTrue(taskRules.moveTo(TaskStatus.TO_DO) is Todo)
    }

    @Test
    fun `done은 inProgress로 이동하면 예외가 발생한다`() {
        val taskRules: TaskRules = Done()

        assertThrows(IllegalStateException::class.java) {
            taskRules.moveTo(TaskStatus.IN_PROGRESS)
        }
    }

    @Test
    fun `done은 review로 이동하면 예외가 발생한다`() {
        val taskRules: TaskRules = Done()

        assertThrows(IllegalStateException::class.java) {
            taskRules.moveTo(TaskStatus.REVIEW)
        }
    }

    @Test
    fun `Done의 requireAssignee는 true다`() {
        val taskRules: TaskRules = Done()

        assertTrue(taskRules.requireAssignee)
    }
}
