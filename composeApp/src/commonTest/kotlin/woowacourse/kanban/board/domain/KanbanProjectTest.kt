package woowacourse.kanban.board.domain

import kotlin.test.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Test
import woowacourse.kanban.board.ui.stateholder.BoardState
import woowacourse.kanban.domain.BoardData
import woowacourse.kanban.domain.KanbanTask
import woowacourse.kanban.domain.Nickname
import woowacourse.kanban.domain.Tags
import woowacourse.kanban.domain.TaskStatus
import woowacourse.kanban.domain.Title

class KanbanProjectTest {
    @Test
    fun `새 태스크를 생성했을 때 현재 프로젝트에 삽입되어야 한다`() = runTest {
        val boardState = BoardState(KanbanProject(mutableListOf()))

        boardState.addTask(
            KanbanTask(
                data = BoardData(
                    title = Title("제목"),
                    content = "내용",
                    tags = Tags(),
                    nickname = Nickname("아오"),
                ),
                status = TaskStatus.DONE,
            ),
        )

        assertEquals(1, boardState.totalTaskCount)
    }

    @Test
    fun `태스크의 상태를 변경 할 수 있어야 한다`() = runTest {
        var task = KanbanTask(
            data = BoardData(
                title = Title("제목"),
                content = "내용",
                tags = Tags(),
                nickname = Nickname("아오"),
                id = 0,
            ),
            status = TaskStatus.IN_PROGRESS,
        )

        val project = KanbanProject(mutableListOf(task))
        val state = BoardState(project)

        state.changeStatus(taskId = 0, status = TaskStatus.DONE)

        assertEquals(TaskStatus.DONE, state.getTasksByStatus(TaskStatus.DONE).first().status)
    }

    @Test
    fun `Review, Done 상태인 태스크를 삭제하려고 하면 실패한다`() = runTest {
        // given : Review, Done 상태의 태스크목록을 가진 프로젝트와 스테이트홀더가 제공된다
        val tasks = listOf<KanbanTask>(
            KanbanTask(
                data = BoardData(
                    title = Title("제목"),
                    content = "내용",
                    tags = Tags(),
                    nickname = Nickname("아오"),
                    id = 0,
                ),
                status = TaskStatus.DONE,
            ),
            KanbanTask(
                data = BoardData(
                    title = Title("제목"),
                    content = "내용",
                    tags = Tags(),
                    nickname = Nickname("아오"),
                    id = 1,
                ),
                status = TaskStatus.REVIEW,
            ),
        )
        val project = KanbanProject(tasks)
        val state = BoardState(project)
        // when : 각  태스크를 지울 때

        val resultDone: Boolean = state.deleteTask(0)

        val resultReview: Boolean = state.deleteTask(1)

        // then : 삭제가 실패해야 한다
        assertEquals(false, resultDone)
        assertEquals(false, resultReview)
    }
}
