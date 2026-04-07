package woowacourse.kanban.board.domain

import kotlin.test.assertEquals
import org.junit.Test
import woowacourse.kanban.domain.Assignee
import woowacourse.kanban.domain.BoardData
import woowacourse.kanban.domain.Nickname
import woowacourse.kanban.domain.Tags
import woowacourse.kanban.domain.TaskStatus
import woowacourse.kanban.domain.Title

class KanbanTaskTest {

    private fun createTask(
        status: TaskStatus,
        assignee: Assignee? = Assignee(Nickname("아오")),
    ): KanbanTask {
        return KanbanTask(
            data = BoardData(
                title = Title("제목"),
                content = "내용",
                tags = Tags(),
                assignee = assignee,
                id = 0,
            ),
            status = status,
        )
    }

    @Test
    fun `담당자가 있는 TO_DO 태스크를 IN_PROGRESS로 변경하면 성공한다`() {
        // given : 담당자가 있는 TO_DO 태스크가 주어진다
        val task = createTask(TaskStatus.TO_DO)

        // when : IN_PROGRESS로 변경할 때
        val result = task.changeStatus(TaskStatus.IN_PROGRESS)

        // then : 변경이 성공하고 상태가 IN_PROGRESS여야 한다
        assert(result is TaskChangeResult.Success)
        assertEquals(TaskStatus.IN_PROGRESS, (result as TaskChangeResult.Success).task.status)
    }

    @Test
    fun `담당자가 없는 TO_DO 태스크를 IN_PROGRESS로 변경하면 NotAssigned`() {
        // given : 담당자가 없는 TO_DO 태스크가 주어진다
        val task = createTask(TaskStatus.TO_DO, assignee = null)

        // when : IN_PROGRESS로 변경할 때
        val result = task.changeStatus(TaskStatus.IN_PROGRESS)

        // then : NotAssigned가 반환되어야 한다
        assertEquals(TaskChangeResult.NotAssigned, result)
    }

    @Test
    fun `TO_DO 태스크를 DONE으로 변경하면 NotChangeable`() {
        // given : TO_DO 태스크가 주어진다
        val task = createTask(TaskStatus.TO_DO)

        // when : DONE으로 변경할 때
        val result = task.changeStatus(TaskStatus.DONE)

        // then : NotChangeable이 반환되어야 한다
        assertEquals(TaskChangeResult.NotChangeable, result)
    }

    @Test
    fun `상태 변경 성공 시 원본 태스크의 상태는 변하지 않는다`() {
        // given : IN_PROGRESS 태스크가 주어진다
        val task = createTask(TaskStatus.IN_PROGRESS)

        // when : TO_DO로 변경할 때
        task.changeStatus(TaskStatus.TO_DO)

        // then : 원본 태스크의 상태는 IN_PROGRESS여야 한다
        assertEquals(TaskStatus.IN_PROGRESS, task.status)
    }
}
