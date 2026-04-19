package woowacourse.kanban.board.ui.screen

import kotlin.test.Test
import org.assertj.core.api.Assertions.assertThat
import woowacourse.kanban.board.domain.DeleteError
import woowacourse.kanban.board.domain.EditError
import woowacourse.kanban.board.domain.KanbanBoard
import woowacourse.kanban.board.domain.KanbanProject
import woowacourse.kanban.board.domain.KanbanTask
import woowacourse.kanban.board.domain.dialog.Status
import woowacourse.kanban.board.ui.screen.board.DeleteUiEvent
import woowacourse.kanban.board.ui.screen.board.EditUiEvent
import woowacourse.kanban.board.ui.screen.board.KanbanBoardState

class KanbanBoardStateTest {
    @Test
    fun `프로젝트 이동 시 프로젝트에 맞는 태스크만 전달한다`() {
        // Given
        val task = KanbanTask(
            id = 1L,
            title = "안녕",
            assignee = "볼트",
            status = Status.TO_DO,
        )
        val projects = listOf(
            KanbanProject(
                title = "안녕",
                tasks = listOf(
                    KanbanTask(
                        id = 0L,
                        title = "안녕",
                        assignee = "볼트",
                        status = Status.TO_DO,
                    ),
                    KanbanTask(
                        id = 2L,
                        title = "안녕",
                        assignee = "볼트",
                        status = Status.TO_DO,
                    ),
                ),
            ),
            KanbanProject(
                title = "잘가",
                tasks = listOf(task),
            ),
        )
        val kanbanBoard = KanbanBoard(projects = projects)

        val kanbanBoardState = KanbanBoardState(kanbanBoard = kanbanBoard)

        // When
        kanbanBoardState.updateSelectedProjectIndex(1)

        // Then
        assertThat(kanbanBoardState.getProjectTasksByStatus(Status.TO_DO)).isEqualTo(listOf(task))
    }

    @Test
    fun `태스크가 프로젝트에 정상적으로 추가된다`() {
        // Given
        val task = KanbanTask(
            id = 1L,
            title = "안녕",
            assignee = "볼트",
            status = Status.TO_DO,
        )

        val projects = listOf(
            KanbanProject(
                title = "안녕",
                tasks = listOf(),
            ),
            KanbanProject(
                title = "잘가",
                tasks = listOf(
                    KanbanTask(
                        id = 0L,
                        title = "안녕",
                        assignee = "볼트",
                        status = Status.TO_DO,
                    ),
                    KanbanTask(
                        id = 2L,
                        title = "안녕",
                        assignee = "볼트",
                        status = Status.TO_DO,
                    ),
                ),
            ),
        )

        val kanbanBoard = KanbanBoard(projects = projects)

        val kanbanBoardState = KanbanBoardState(kanbanBoard = kanbanBoard)

        // When
        kanbanBoardState.addTask(task)

        // Then
        assertThat(kanbanBoardState.getProjectTasksByStatus(Status.TO_DO)).isEqualTo(listOf(task))
    }

    @Test
    fun `태스크의 ToDo에서 InProgress로 상태가 변경된다`() {
        // Given
        val task = KanbanTask(
            id = 1L,
            title = "안녕",
            assignee = "볼트",
            status = Status.TO_DO,
        )

        val projects = listOf(KanbanProject(title = "안녕", tasks = listOf(task)))
        val kanbanBoard = KanbanBoard(projects = projects)

        val kanbanBoardState = KanbanBoardState(kanbanBoard = kanbanBoard)

        // When
        kanbanBoardState.moveTask(taskId = task.id, targetStatus = Status.IN_PROGRESS)

        // Then
        assertThat(kanbanBoardState.getProjectTasksByStatus(Status.IN_PROGRESS).size).isEqualTo(1)
        assertThat(kanbanBoardState.getProjectTasksByStatus(Status.TO_DO).size).isEqualTo(0)
    }

    @Test
    fun `태스크의 ToDo에서 Review로 상태로 변경 시 예외가 발생한다`() {
        // Given
        val task = KanbanTask(
            id = 1L,
            title = "안녕",
            assignee = "볼트",
            status = Status.TO_DO,
        )

        val projects = listOf(KanbanProject(title = "안녕", tasks = listOf(task)))
        val kanbanBoard = KanbanBoard(projects = projects)

        val kanbanBoardState = KanbanBoardState(kanbanBoard = kanbanBoard)

        // When & Then
        assertThat(kanbanBoardState.moveTask(taskId = task.id, targetStatus = Status.REVIEW))
            .isEqualTo(EditUiEvent.Error(EditError.INVALID_STATUS))
    }

    @Test
    fun `태스크의 ToDo에서 Done으로 변경 시 예외가 발생한다`() {
        // Given
        val task = KanbanTask(
            id = 1L,
            title = "안녕",
            assignee = "볼트",
            status = Status.TO_DO,
        )

        val projects = listOf(KanbanProject(title = "안녕", tasks = listOf(task)))
        val kanbanBoard = KanbanBoard(projects = projects)

        val kanbanBoardState = KanbanBoardState(kanbanBoard = kanbanBoard)

        // When & Then
        assertThat(kanbanBoardState.moveTask(taskId = task.id, targetStatus = Status.DONE))
            .isEqualTo(EditUiEvent.Error(EditError.INVALID_STATUS))
    }

    @Test
    fun `태스크의 task의 담당자가 없으면 예외가 발생한다`() {
        // Given
        val task = KanbanTask(
            id = 1L,
            title = "안녕",
            status = Status.TO_DO,
        )

        val projects = listOf(KanbanProject(title = "안녕", tasks = listOf(task)))
        val kanbanBoard = KanbanBoard(projects = projects)

        val kanbanBoardState = KanbanBoardState(kanbanBoard = kanbanBoard)

        // When & Then
        assertThat(kanbanBoardState.moveTask(taskId = task.id, targetStatus = Status.IN_PROGRESS))
            .isEqualTo(EditUiEvent.Error(EditError.UNASSIGNED))
    }

    @Test
    fun `태스크의 InProgress에서 Review로 상태가 변경된다`() {
        // Given
        val task = KanbanTask(
            id = 1L,
            title = "안녕",
            assignee = "볼트",
            status = Status.IN_PROGRESS,
        )

        val projects = listOf(KanbanProject(title = "안녕", tasks = listOf(task)))
        val kanbanBoard = KanbanBoard(projects = projects)

        val kanbanBoardState = KanbanBoardState(kanbanBoard = kanbanBoard)

        // When
        kanbanBoardState.moveTask(taskId = task.id, targetStatus = Status.REVIEW)

        // Then
        assertThat(kanbanBoardState.getProjectTasksByStatus(Status.REVIEW).size).isEqualTo(1)
        assertThat(kanbanBoardState.getProjectTasksByStatus(Status.IN_PROGRESS).size).isEqualTo(0)
    }

    @Test
    fun `태스크의 InProgress에서 ToDo로 상태가 변경된다`() {
        // Given
        val task = KanbanTask(
            id = 1L,
            title = "안녕",
            assignee = "볼트",
            status = Status.IN_PROGRESS,
        )

        val projects = listOf(KanbanProject(title = "안녕", tasks = listOf(task)))
        val kanbanBoard = KanbanBoard(projects = projects)

        val kanbanBoardState = KanbanBoardState(kanbanBoard = kanbanBoard)

        // When
        kanbanBoardState.moveTask(taskId = task.id, targetStatus = Status.TO_DO)

        // Then
        assertThat(kanbanBoardState.getProjectTasksByStatus(Status.TO_DO).size).isEqualTo(1)
        assertThat(kanbanBoardState.getProjectTasksByStatus(Status.IN_PROGRESS).size).isEqualTo(0)
    }

    @Test
    fun `태스크의 InProgress에서 Done로 상태로 변경 시 예외가 발생한다`() {
        // Given
        val task = KanbanTask(
            id = 1L,
            title = "안녕",
            assignee = "볼트",
            status = Status.IN_PROGRESS,
        )

        val projects = listOf(KanbanProject(title = "안녕", tasks = listOf(task)))
        val kanbanBoard = KanbanBoard(projects = projects)

        val kanbanBoardState = KanbanBoardState(kanbanBoard = kanbanBoard)

        // When & Then
        assertThat(kanbanBoardState.moveTask(taskId = task.id, targetStatus = Status.DONE))
            .isEqualTo(EditUiEvent.Error(EditError.INVALID_STATUS))
    }

    @Test
    fun `태스크의 Review에서 Done으로 상태가 변경된다`() {
        // Given
        val task = KanbanTask(
            id = 1L,
            title = "안녕",
            assignee = "볼트",
            status = Status.REVIEW,
        )

        val projects = listOf(KanbanProject(title = "안녕", tasks = listOf(task)))
        val kanbanBoard = KanbanBoard(projects = projects)

        val kanbanBoardState = KanbanBoardState(kanbanBoard = kanbanBoard)

        // When
        kanbanBoardState.moveTask(taskId = task.id, targetStatus = Status.DONE)

        // Then
        assertThat(kanbanBoardState.getProjectTasksByStatus(Status.DONE).size).isEqualTo(1)
        assertThat(kanbanBoardState.getProjectTasksByStatus(Status.REVIEW).size).isEqualTo(0)
    }

    @Test
    fun `태스크의 Review에서 InProgress로 상태가 변경된다`() {
        // Given
        val task = KanbanTask(
            id = 1L,
            title = "안녕",
            assignee = "볼트",
            status = Status.REVIEW,
        )

        val projects = listOf(KanbanProject(title = "안녕", tasks = listOf(task)))
        val kanbanBoard = KanbanBoard(projects = projects)

        val kanbanBoardState = KanbanBoardState(kanbanBoard = kanbanBoard)

        // When
        kanbanBoardState.moveTask(taskId = task.id, targetStatus = Status.IN_PROGRESS)

        // Then
        assertThat(kanbanBoardState.getProjectTasksByStatus(Status.IN_PROGRESS).size).isEqualTo(1)
        assertThat(kanbanBoardState.getProjectTasksByStatus(Status.REVIEW).size).isEqualTo(0)
    }

    @Test
    fun `태스크의 Review에서 ToDo로 상태로 변경 시 예외가 발생한다`() {
        // Given
        val task = KanbanTask(
            id = 1L,
            title = "안녕",
            assignee = "볼트",
            status = Status.REVIEW,
        )

        val projects = listOf(KanbanProject(title = "안녕", tasks = listOf(task)))
        val kanbanBoard = KanbanBoard(projects = projects)

        val kanbanBoardState = KanbanBoardState(kanbanBoard = kanbanBoard)

        // When & Then
        assertThat(kanbanBoardState.moveTask(taskId = task.id, targetStatus = Status.TO_DO))
            .isEqualTo(EditUiEvent.Error(EditError.INVALID_STATUS))
    }

    @Test
    fun `태스크의 Done에서 ToDo로 상태가 변경된다`() {
        // Given
        val task = KanbanTask(
            id = 1L,
            title = "안녕",
            assignee = "볼트",
            status = Status.DONE,
        )

        val projects = listOf(KanbanProject(title = "안녕", tasks = listOf(task)))
        val kanbanBoard = KanbanBoard(projects = projects)

        val kanbanBoardState = KanbanBoardState(kanbanBoard = kanbanBoard)

        // When
        kanbanBoardState.moveTask(taskId = task.id, targetStatus = Status.TO_DO)

        // Then
        assertThat(kanbanBoardState.getProjectTasksByStatus(Status.TO_DO).size).isEqualTo(1)
        assertThat(kanbanBoardState.getProjectTasksByStatus(Status.DONE).size).isEqualTo(0)
    }

    @Test
    fun `태스크의 Done에서 Review로 상태로 변경 시 예외가 발생한다`() {
        // Given
        val task = KanbanTask(
            id = 1L,
            title = "안녕",
            assignee = "볼트",
            status = Status.DONE,
        )

        val projects = listOf(KanbanProject(title = "안녕", tasks = listOf(task)))
        val kanbanBoard = KanbanBoard(projects = projects)

        val kanbanBoardState = KanbanBoardState(kanbanBoard = kanbanBoard)

        assertThat(kanbanBoardState.moveTask(taskId = task.id, targetStatus = Status.REVIEW))
            .isEqualTo(EditUiEvent.Error(EditError.INVALID_STATUS))
    }

    @Test
    fun `태스크의 Done에서 InProgress으로 변경 시 예외가 발생한다`() {
        // Given
        val task = KanbanTask(
            id = 1L,
            title = "안녕",
            assignee = "볼트",
            status = Status.DONE,
        )

        val projects = listOf(KanbanProject(title = "안녕", tasks = listOf(task)))
        val kanbanBoard = KanbanBoard(projects = projects)

        val kanbanBoardState = KanbanBoardState(kanbanBoard = kanbanBoard)

        // When & Then
        assertThat(kanbanBoardState.moveTask(taskId = task.id, targetStatus = Status.IN_PROGRESS))
            .isEqualTo(EditUiEvent.Error(EditError.INVALID_STATUS))
    }

    @Test
    fun `상태가 ToDo일 때 해당하는 태스크가 삭제된다`() {
        // Given
        val task = KanbanTask(
            id = 1L,
            title = "안녕",
            assignee = "볼트",
            status = Status.TO_DO,
        )

        val projects = listOf(KanbanProject(title = "안녕", tasks = listOf(task)))
        val kanbanBoard = KanbanBoard(projects = projects)

        val kanbanBoardState = KanbanBoardState(kanbanBoard = kanbanBoard)

        // When
        kanbanBoardState.deleteTask(task = task)

        // Then
        assertThat(kanbanBoardState.getProjectTasksByStatus(Status.TO_DO).size).isEqualTo(0)
    }

    @Test
    fun `상태가 InProgress일 때 해당하는 태스크가 삭제된다`() {
        // Given
        val task = KanbanTask(
            id = 1L,
            title = "안녕",
            assignee = "볼트",
            status = Status.IN_PROGRESS,
        )

        val projects = listOf(KanbanProject(title = "안녕", tasks = listOf(task)))
        val kanbanBoard = KanbanBoard(projects = projects)

        val kanbanBoardState = KanbanBoardState(kanbanBoard = kanbanBoard)

        // When
        kanbanBoardState.deleteTask(task = task)

        // Then
        assertThat(kanbanBoardState.getProjectTasksByStatus(Status.TO_DO).size).isEqualTo(0)
    }

    @Test
    fun `상태가 Review일 때 해당하는 태스크를 삭제시 예외가 발생한다`() {
        // Given
        val task = KanbanTask(
            id = 1L,
            title = "안녕",
            assignee = "볼트",
            status = Status.REVIEW,
        )

        val projects = listOf(KanbanProject(title = "안녕", tasks = listOf(task)))
        val kanbanBoard = KanbanBoard(projects = projects)

        val kanbanBoardState = KanbanBoardState(kanbanBoard = kanbanBoard)

        // When & Then
        assertThat(kanbanBoardState.deleteTask(task = task)).isEqualTo(DeleteUiEvent.Error(DeleteError.FAILED))
    }

    @Test
    fun `상태가 Done일 때 해당하는 태스크를 삭제시 예외가 발생한다`() {
        // Given
        val task = KanbanTask(
            id = 1L,
            title = "안녕",
            assignee = "볼트",
            status = Status.DONE,
        )

        val projects = listOf(KanbanProject(title = "안녕", tasks = listOf(task)))
        val kanbanBoard = KanbanBoard(projects = projects)

        val kanbanBoardState = KanbanBoardState(kanbanBoard = kanbanBoard)

        // When & Then
        assertThat(kanbanBoardState.deleteTask(task = task)).isEqualTo(DeleteUiEvent.Error(DeleteError.FAILED))
    }

    @Test
    fun `해당하는 태스크가 수정된다`() {
        // Given
        val task = KanbanTask(
            id = 1L,
            title = "안녕",
            assignee = "볼트",
            status = Status.TO_DO,
        )
        val newTask = task.copy(title = "반가워", status = Status.IN_PROGRESS)

        val projects = listOf(KanbanProject(title = "안녕", tasks = listOf(task)))
        val kanbanBoard = KanbanBoard(projects = projects)

        val kanbanBoardState = KanbanBoardState(kanbanBoard = kanbanBoard)

        // When
        kanbanBoardState.editTask(task = newTask)

        // Then
        assertThat(
            kanbanBoardState.getProjectTasksByStatus(status = Status.IN_PROGRESS)
                .first()
                .title,
        ).isEqualTo("반가워")
    }

    @Test
    fun `프로젝트 타이틀 리스트를 정상적으로 반환한다`() {
        // Given & When
        val titles = listOf("안녕", "잘가")
        val projects = titles.map { KanbanProject(title = it) }

        val kanbanBoardState = KanbanBoardState(kanbanBoard = KanbanBoard(projects = projects))

        // Then
        assertThat(kanbanBoardState.getProjectsTitles()).isEqualTo(titles)
    }
}
