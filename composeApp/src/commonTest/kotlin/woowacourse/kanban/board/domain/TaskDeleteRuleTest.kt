package woowacourse.kanban.board.domain

import kotlin.test.Test
import org.assertj.core.api.Assertions.assertThat

private const val DEFAULT_TITLE = "테스크 제목"
private const val DEFAULT_DESCRIPTION = "테스크 설명"
private const val DEFAULT_NAME = "다이노"

class TaskDeleteRuleTest {
    
    @Test
    fun `To Do 상태의 태스크는 삭제 가능하다`() {
        val task = Task(
            title = DEFAULT_TITLE,
            description = DEFAULT_DESCRIPTION,
            status = Status.TODO,
            nickname = DEFAULT_NAME,
        )
        val board = KanbanBoard(title = "테스트 보드")
            .addTask(task)

        val updatedBoard = board.deleteTask(task)
        assertThat(updatedBoard.taskList).isEmpty()
    }

    @Test
    fun `In Progress 상태의 태스크는 삭제 가능하다`() {
        val task = Task(
            title = DEFAULT_TITLE,
            description = DEFAULT_DESCRIPTION,
            status = Status.IN_PROGRESS,
            nickname = DEFAULT_NAME,
        )
        val board = KanbanBoard(title = "테스트 보드")
            .addTask(task)

        val updatedBoard = board.deleteTask(task)
        assertThat(updatedBoard.taskList).isEmpty()
    }

    @Test
    fun `Review 상태의 태스크는 삭제할 수 없다`() {
        val task = Task(
            title = DEFAULT_TITLE,
            description = DEFAULT_DESCRIPTION,
            status = Status.REVIEW,
            nickname = DEFAULT_NAME,
        )
        val board = KanbanBoard(title = "테스트 보드")
            .addTask(task)

        // Review 상태에서는 삭제 불가능 규칙
        assertThat(task.status == Status.REVIEW).isTrue()
    }

    @Test
    fun `Done 상태의 태스크는 삭제할 수 없다`() {
        val task = Task(
            title = DEFAULT_TITLE,
            description = DEFAULT_DESCRIPTION,
            status = Status.DONE,
            nickname = DEFAULT_NAME,
        )
        val board = KanbanBoard(title = "테스트 보드")
            .addTask(task)

        // Done 상태에서는 삭제 불가능 규칙
        assertThat(task.status == Status.DONE).isTrue()
    }
}
