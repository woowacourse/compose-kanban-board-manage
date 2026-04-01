package woowacourse.kanban.board.domain

import kotlin.test.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Test
import woowacourse.kanban.board.ui.stateholder.BoardState
import woowacourse.kanban.domain.Assignee
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
                    assignee = Assignee(Nickname("아오")),
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
                assignee = Assignee(Nickname("아오")),
                id = 0,
            ),
            status = TaskStatus.IN_PROGRESS,
        )

        val project = KanbanProject(mutableListOf(task))
        val state = BoardState(project)

        state.changeStatus(taskId = 0, status = TaskStatus.TO_DO)

        assertEquals(TaskStatus.TO_DO, state.getTasksByStatus(TaskStatus.TO_DO).first().status)
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
                    assignee = Assignee(Nickname("아오")),
                    id = 0,
                ),
                status = TaskStatus.DONE,
            ),
            KanbanTask(
                data = BoardData(
                    title = Title("제목"),
                    content = "내용",
                    tags = Tags(),
                    assignee = Assignee(Nickname("아오")),
                    id = 1,
                ),
                status = TaskStatus.REVIEW,
            ),
        )
        val project = KanbanProject(tasks)
        val state = BoardState(project)

        // when : 각  태스크를 지울 때
        val resultDone = state.deleteTask(0)
        val resultReview = state.deleteTask(1)

        // then : 삭제가 실패해야 한다
        assertEquals(TaskReturnType.NOT_DELETABLE, resultDone)
        assertEquals(TaskReturnType.NOT_DELETABLE, resultReview)
    }

    @Test
    fun `To Do 상태의 태스크를 Review, Done으로 바꾸려면 실패한다`() = runTest {
        // given : To Do 상태의 태스크목록을 가진 프로젝트와 스테이트홀더가 제공된다
        val tasks = listOf(
            KanbanTask(
                data = BoardData(
                    title = Title("제목"),
                    content = "내용",
                    tags = Tags(),
                    assignee = Assignee(Nickname("아오")),
                    id = 0,
                ),
                status = TaskStatus.TO_DO,
            ),
        )
        val project = KanbanProject(tasks)
        val state = BoardState(project)

        // when : 상태를 Review와 Done으로 바꿀 때
        val resultReview = state.changeStatus(0, TaskStatus.REVIEW)
        val resultDone = state.changeStatus(0, TaskStatus.DONE)

        // then : 상태 변경이 실패해야 한다
        assertEquals(TaskReturnType.NOT_UPDATABLE, resultReview)
        assertEquals(TaskReturnType.NOT_UPDATABLE, resultDone)
    }

    @Test
    fun `In Progress 상태의 태스크를 Done으로 바꾸려면 실패한다`() = runTest {
        // given : In Progress 상태의 태스크목록을 가진 프로젝트와 스테이트홀더가 제공된다
        val tasks = listOf(
            KanbanTask(
                data = BoardData(
                    title = Title("제목"),
                    content = "내용",
                    tags = Tags(),
                    assignee = Assignee(Nickname("아오")),
                    id = 0,
                ),
                status = TaskStatus.IN_PROGRESS,
            ),
        )
        val project = KanbanProject(tasks)
        val state = BoardState(project)

        // when : 상태를 Done으로 바꿀 때
        val resultDone = state.changeStatus(0, TaskStatus.DONE)

        // then : 상태 변경이 실패해야 한다
        assertEquals(TaskReturnType.NOT_UPDATABLE, resultDone)
    }

    @Test
    fun `Review 상태의 태스크를 To Do로 바꾸려면 실패한다`() = runTest {
        // given : Review 상태의 태스크목록을 가진 프로젝트와 스테이트홀더가 제공된다
        val tasks = listOf(
            KanbanTask(
                data = BoardData(
                    title = Title("제목"),
                    content = "내용",
                    tags = Tags(),
                    assignee = Assignee(Nickname("아오")),
                    id = 0,
                ),
                status = TaskStatus.REVIEW,
            ),
        )
        val project = KanbanProject(tasks)
        val state = BoardState(project)

        // when : 상태를 To Do로 바꿀 때
        val resultToDo = state.changeStatus(0, TaskStatus.TO_DO)

        // then : 상태 변경이 실패해야 한다
        assertEquals(TaskReturnType.NOT_UPDATABLE, resultToDo)
    }

    @Test
    fun `Done 상태의 태스크를 In Progress, Review으로 바꾸려면 실패한다`() = runTest {
        // given : Done 상태의 태스크목록을 가진 프로젝트와 스테이트홀더가 제공된다
        val tasks = listOf(
            KanbanTask(
                data = BoardData(
                    title = Title("제목"),
                    content = "내용",
                    tags = Tags(),
                    assignee = Assignee(Nickname("아오")),
                    id = 0,
                ),
                status = TaskStatus.DONE,
            ),
        )
        val project = KanbanProject(tasks)
        val state = BoardState(project)

        // when : 상태를 In Progress와 Review로 바꿀 때
        val resultInProgress = state.changeStatus(0, TaskStatus.IN_PROGRESS)
        val resultReview = state.changeStatus(0, TaskStatus.REVIEW)

        // then : 상태 변경이 실패해야 한다
        assertEquals(TaskReturnType.NOT_UPDATABLE, resultInProgress)
        assertEquals(TaskReturnType.NOT_UPDATABLE, resultReview)
    }
}
