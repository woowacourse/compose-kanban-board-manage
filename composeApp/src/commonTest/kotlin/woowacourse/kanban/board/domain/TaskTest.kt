package woowacourse.kanban.board.domain

import kotlin.test.Test
import org.assertj.core.api.Assertions.assertThat

class TaskTest {

    @Test
    fun `To Do 상태에서 담당자를 지정하지 않고 In Progress로 전이할 수 없다`() {
        // given
        val task = Task(
            title = "title",
            taskState = TaskState.TO_DO,
            author = Author.NONE,
        )

        // when
        val result = task.canChangeTaskState(TaskState.IN_PROGRESS)

        // then
        assertThat(result).isFalse
    }

    @Test
    fun `To Do 상태에서 담당자가 지정되어 있다면 In Progress로 전이할 수 있다`() {
        // given
        val task = Task(
            title = "title",
            taskState = TaskState.TO_DO,
            author = Author.User("다이노"),
        )

        // when
        val result = task.canChangeTaskState(TaskState.IN_PROGRESS)

        // then
        assertThat(result).isTrue
    }
}
