package woowacourse.kanban.board.task.domain

import kotlin.test.assertFailsWith
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class KanbanProjectTest {
    private fun createProjectWithCard(status: KanbanStatus): KanbanProject {
        return KanbanProject(
            projectTitle = "프로젝트",
            boards = listOf(
                KanbanBoard(
                    boardId = 0,
                    title = "보드1",
                    cards = listOf(
                        KanbanCard(
                            id = "1",
                            title = "제목",
                            assigneeName = "담당자",
                            status = status,
                        ),
                    ),
                ),
            ),
        )
    }

    @Test
    fun `보드 ID로 보드를 조회할 수 있다`() {
        val project = KanbanProject(
            projectTitle = "프로젝트",
            boards = listOf(
                KanbanBoard(boardId = 0, title = "보드1"),
                KanbanBoard(boardId = 1, title = "보드2"),
            ),
        )

        assertThat(project.getBoard(0)?.title).isEqualTo("보드1")
        assertThat(project.getBoard(1)?.title).isEqualTo("보드2")
    }

    @Test
    fun `잘못된 보드 ID로 조회하면 null을 반환한다`() {
        val project = KanbanProject(
            projectTitle = "프로젝트",
            boards = listOf(
                KanbanBoard(boardId = 0, title = "보드1"),
                KanbanBoard(boardId = 1, title = "보드2"),
            ),
        )

        assertThat(project.getBoard(10)).isNull()
    }

    @Test
    fun `특정 보드에 새로운 카드를 추가하면 새로운 Project를 반환한다`() {
        val project = KanbanProject(
            projectTitle = "프로젝트",
            boards = listOf(
                KanbanBoard(boardId = 0, title = "보드1"),
                KanbanBoard(boardId = 1, title = "보드2"),
            ),
        )

        val newProject = project.addCard(
            boardId = 0,
            title = "제목",
            content = "",
            assigneeName = "담당자",
            tags = listOf("컴포넌트","성능"),
            status = KanbanStatus.TO_DO
        )

        assertThat(newProject).isNotNull()
        assertThat(newProject?.getBoard(0)?.cards).hasSize(1)
        assertThat(newProject?.getBoard(0)?.cards?.single()?.title).isEqualTo("제목")
        assertThat(newProject?.getBoard(0)?.cards?.single()?.id).isNotBlank()
        assertThat(project.getBoard(0)?.cards).isEmpty()
    }

    @Test
    fun `특정 보드의 카드를 업데이트하면 새로운 Project를 반환한다`() {
        val project = KanbanProject(
            projectTitle = "프로젝트",
            boards = listOf(
                KanbanBoard(
                    boardId = 0,
                    title = "보드1",
                    cards = listOf(
                        KanbanCard(
                            id = "1",
                            title = "제목",
                            assigneeName = "담당자",
                            status = KanbanStatus.TO_DO,
                        ),
                    ),
                ),
            ),
        )

        val updateProject = project.updateCardStatus(
            boardId = 0,
            cardId = "1",
            status = KanbanStatus.IN_PROGRESS,
        )

        assertThat(updateProject?.getBoard(0)?.getCardByStatus(KanbanStatus.TO_DO)).isEmpty()
        assertThat(updateProject?.getBoard(0)?.getCardByStatus(KanbanStatus.IN_PROGRESS)).hasSize(1)
    }

    // 허용하지 않는 경우
    @Test
    fun `Review 상태의 카드를 삭제하려고 하면 예외가 발생한다`() {
        val project = createProjectWithCard(KanbanStatus.REVIEW)

        assertFailsWith<IllegalArgumentException> {
            project.deleteCard(0, "1")
        }
    }

    @Test
    fun `Done 상태의 카드를 삭제하려고 하면 예외가 발생한다`() {
        val project = createProjectWithCard(KanbanStatus.DONE)

        assertFailsWith<IllegalArgumentException> {
            project.deleteCard(0, "1")
        }
    }

    // 허용하는 경우
    @Test
    fun `To Do 상태의 카드는 삭제할 수 있다`() {
        val project = createProjectWithCard(KanbanStatus.TO_DO)

        val updatedProject = project.deleteCard(0, "1")

        assertThat(updatedProject?.getBoard(0)?.cards).isEmpty()
    }
}
