package woowacourse.kanban.board.task.domain

import kotlin.test.Test
import kotlin.test.assertIs
import org.assertj.core.api.Assertions.assertThat

class KanbanProjectTest {
    @Test
    fun `보드 ID로 보드를 조회할 수 있다`() {
        val board1 = KanbanBoard(
            boardId = 0,
            title = "보드1",
        )
        val board2 = KanbanBoard(
            boardId = 1,
            title = "보드2",
        )
        val project = KanbanProject(
            projectTitle = "프로젝트",
            boards = listOf(
                board1,
                board2,
            ),
        )

        assertThat(project.getBoard(0)!!.title).isEqualTo("보드1")
        assertThat(project.getBoard(1)!!.title).isEqualTo("보드2")
    }

    @Test
    fun `잘못된 보드 ID로 조회하면 null을 반환한다`() {
        val board1 = KanbanBoard(
            boardId = 0,
            title = "보드1",
        )
        val board2 = KanbanBoard(
            boardId = 1,
            title = "보드2",
        )
        val project = KanbanProject(
            projectTitle = "프로젝트",
            boards = listOf(
                board1,
                board2,
            ),
        )

        assertThat(project.getBoard(10)).isNull()
    }

    @Test
    fun `특정 보드에 새로운 카드를 추가하면 새로운 Project를 반환한다`() {
        val board1 = KanbanBoard(
            boardId = 0,
            title = "보드1",
        )
        val board2 = KanbanBoard(
            boardId = 1,
            title = "보드2",
        )
        val project = KanbanProject(
            projectTitle = "프로젝트",
            boards = listOf(
                board1,
                board2,
            ),
        )

        val addCard = KanbanCard(
            title = "제목",
            assigneeName = "담당자",
            status = KanbanStatus.TO_DO,
        )
        val projectResult = project.addBoardCard(
            boardId = 0,
            card = addCard,
        )

        val newProject = assertIs<KanbanProjectResult.Success>(projectResult)
        val searchBoard = newProject.project.getBoard(0)
        assertThat(newProject).isNotNull()
        assertThat(searchBoard?.cards[0]?.title).isEqualTo("제목")
        assertThat(board1.cards.size).isEqualTo(0)
    }
}
