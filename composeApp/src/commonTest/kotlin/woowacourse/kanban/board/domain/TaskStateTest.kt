package woowacourse.kanban.board.domain

import kotlin.test.Test
import org.assertj.core.api.Assertions.assertThatThrownBy
import woowacourse.kanban.board.exception.TransStateError
import woowacourse.kanban.board.exception.TransStateException

class TaskStateTest {
    @Test
    fun `ToDo에서 In Progress외 다른 상태로 전이를 시도할 경우 IllegalArgumentException을 반환한다`() {
        val task = Task(title = "test1", taskState = TaskState.TO_DO)
        val updatedTask = task.copy(taskState = TaskState.REVIEW)
        val updatedTask2 = task.copy(taskState = TaskState.DONE)

        val tasks = Tasks(listOf(task))

        assertThatThrownBy { tasks.updateTask(updatedTask) }
            .isInstanceOf(TransStateException::class.java)
            .extracting("error")
            .isEqualTo(TransStateError.CANT_TRANSFER)

        assertThatThrownBy { tasks.updateTask(updatedTask2) }
            .isInstanceOf(TransStateException::class.java)
            .extracting("error")
            .isEqualTo(TransStateError.CANT_TRANSFER)
    }

    @Test
    fun `In Progress에서 ToDo, Review외 다른 상태로 전이를 시도할 경우 IllegalArgumentException을 반환한다`() {
        val task = Task(title = "test1", taskState = TaskState.IN_PROGRESS)
        val updatedTask = task.copy(taskState = TaskState.DONE)

        val tasks = Tasks(listOf(task))

        assertThatThrownBy { tasks.updateTask(updatedTask) }
            .isInstanceOf(TransStateException::class.java)
            .extracting("error")
            .isEqualTo(TransStateError.CANT_TRANSFER)
    }

    @Test
    fun `Review에서 In Progress, Done 다른 상태로 전이를 시도할 경우 IllegalArgumentException을 반환한다`() {
        val task = Task(title = "test1", taskState = TaskState.REVIEW)
        val updatedTask = task.copy(taskState = TaskState.TO_DO)

        val tasks = Tasks(listOf(task))

        assertThatThrownBy { tasks.updateTask(updatedTask) }
            .isInstanceOf(TransStateException::class.java)
            .extracting("error")
            .isEqualTo(TransStateError.CANT_TRANSFER)
    }

    @Test
    fun `Done에서 ToDo외 다른 상태로 전이를 시도할 경우 TransStateException 반환한다`() {
        val task = Task(title = "test1", taskState = TaskState.DONE)
        val updatedTask = task.copy(taskState = TaskState.REVIEW)
        val updatedTask2 = task.copy(taskState = TaskState.IN_PROGRESS)

        val tasks = Tasks(listOf(task))

        assertThatThrownBy { tasks.updateTask(updatedTask) }
            .isInstanceOf(TransStateException::class.java)
            .extracting("error")
            .isEqualTo(TransStateError.CANT_TRANSFER)

        assertThatThrownBy { tasks.updateTask(updatedTask2) }
            .isInstanceOf(TransStateException::class.java)
            .extracting("error")
            .isEqualTo(TransStateError.CANT_TRANSFER)
    }
}
