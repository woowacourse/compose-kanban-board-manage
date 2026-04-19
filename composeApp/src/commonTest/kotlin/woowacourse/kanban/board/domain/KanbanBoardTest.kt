package woowacourse.kanban.board.domain

import kotlin.test.Test
import org.assertj.core.api.Assertions.assertThat
import woowacourse.kanban.board.domain.dialog.Status
import woowacourse.kanban.board.domain.result.BoardResult

class KanbanBoardTest {
    @Test
    fun `칸반 보드 태스크를 생성했을 때 리스트에 추가된다`() {
        // Given
        val projects = listOf(
            KanbanProject(title = "안녕"),

        )
        var kanbanBoard = KanbanBoard(projects = projects)

        // When
        val newTask = KanbanTask(
            id = 4L,
            title = "새로운 기능 구현",
            description = "이 기능은 매우 중요합니다.",
            tags = listOf("긴급", "백엔드"),
            status = Status.TO_DO,
            assignee = "별터",
        )
        kanbanBoard = kanbanBoard.addTask(
            projectIndex = 0,
            task = newTask,
        )

        // Then
        assertThat(kanbanBoard.getProject(0).getTasks().count { it.assignee == "별터" }).isEqualTo(1)
    }

    @Test
    fun `칸반 보드 태스크의 상태를 수정했을 때 상태가 반영된다`() {
        val task = KanbanTask(
            id = 4L,
            title = "새로운 기능 구현",
            description = "이 기능은 매우 중요합니다.",
            tags = listOf("긴급", "백엔드"),
            status = Status.TO_DO,
            assignee = "별터",
        )

        val projects = listOf(
            KanbanProject(
                title = "안녕",
                tasks = listOf(
                    task,
                ),
            ),

        )

        var kanbanBoard = KanbanBoard(projects = projects)

        // When
        val result = kanbanBoard.changeTaskStatus(projectIndex = 0, task = task, newStatus = Status.IN_PROGRESS) as BoardResult.Success
        kanbanBoard = result.board

        // Then
        assertThat(kanbanBoard.getProject(0).getTasksByStatus(Status.TO_DO).size).isEqualTo(0)
        assertThat(kanbanBoard.getProject(0).getTasksByStatus(Status.IN_PROGRESS).size).isEqualTo(1)
    }

    @Test
    fun `칸반 보드의 project들을 반환한다`() {
        val task = KanbanTask(
            id = 4L,
            title = "새로운 기능 구현",
            description = "이 기능은 매우 중요합니다.",
            tags = listOf("긴급", "백엔드"),
            status = Status.TO_DO,
            assignee = "별터",
        )

        val projects = listOf(
            KanbanProject(
                title = "안녕",
                tasks = listOf(
                    task,
                ),
            ),
        )

        val kanbanBoard = KanbanBoard(projects = projects)
        val boardProjects = kanbanBoard.getProjectList()

        assertThat(boardProjects).isEqualTo(projects)
    }

    @Test
    fun `칸반 보드의 project의 제목들을 반환한다`() {
        val task = KanbanTask(
            id = 4L,
            title = "새로운 기능 구현",
            description = "이 기능은 매우 중요합니다.",
            tags = listOf("긴급", "백엔드"),
            status = Status.TO_DO,
            assignee = "별터",
        )

        val projects = listOf(
            KanbanProject(
                title = "안녕",
                tasks = listOf(
                    task,
                ),
            ),
        )

        val kanbanBoard = KanbanBoard(projects = projects)
        val boardProjects = kanbanBoard.getProjectTitles()

        assertThat(boardProjects).isEqualTo(listOf("안녕"))
    }
}
