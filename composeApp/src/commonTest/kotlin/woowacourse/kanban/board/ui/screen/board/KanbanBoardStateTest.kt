@file:Suppress("NonAsciiCharacters")

package woowacourse.kanban.board.ui.screen.board

import org.assertj.core.api.Assertions.assertThat
import woowacourse.kanban.board.domain.KanbanBoard
import woowacourse.kanban.board.domain.KanbanProject
import woowacourse.kanban.board.domain.Status
import woowacourse.kanban.board.fixture.createKanbanTask
import kotlin.test.Test

class KanbanBoardStateTest {
    @Test
    fun `새로운 index로 Project를 찾을 때 그에 맞게 tasks가 변경된다`() {
        // Given: 각기 다른 태스크를 가진 프로젝트 2개를 생성한다.
        val task1 = createKanbanTask(id = 0L)
        val task2 = createKanbanTask(id = 1L)
        val kanbanBoard = KanbanBoard(listOf(task1, task2))
        val projects = listOf(
            KanbanProject(title = "1번 프로젝트", taskIds = listOf(0L)),
            KanbanProject(title = "2번 프로젝트", taskIds = listOf(1L)),
        )
        val kanbanBoardState = KanbanBoardState(kanbanBoard, projects)

        // When: 2번 프로젝트를 선택한다.
        kanbanBoardState.updateSelectedProjectIndex(1)

        // Then: kanbanBoardState 의 tasks 가 2번 프로젝트의 tasks 를 잘 불러온다.
        assertThat(kanbanBoardState.tasks).isEqualTo(listOf(task2))
    }

    @Test
    fun `새로운 태스크를 추가하면, tasks에 태스크가 추가된다`() {
        // Given: 프로젝트 한 개와 추가할 태스크 한 개를 생성한다.
        val kanbanBoard = KanbanBoard(listOf(createKanbanTask(id = 0L)))
        val projects = listOf(
            KanbanProject(title = "프로젝트", taskIds = listOf(0L)),
        )
        val kanbanBoardState = KanbanBoardState(kanbanBoard, projects)
        val newTask = createKanbanTask(id = 1L)

        // When: 새로운 태스크를 추가한다.
        kanbanBoardState.addTask(newTask)

        // Then: tasks 에 새로운 태스크가 포함되어 있다.
        assertThat(kanbanBoardState.tasks).contains(newTask)
    }

    @Test
    fun `태스크 상태를 변경하면, 해당 변경사항이 반영된다`() {
        // Given: 태스크 상태가 TO_DO인 kanbanBoardState 를 준비한다.
        val task = createKanbanTask(id = 0L)
        val kanbanBoard = KanbanBoard(listOf(task))
        val projects = listOf(
            KanbanProject(title = "프로젝트", taskIds = listOf(0L)),
        )
        val kanbanBoardState = KanbanBoardState(kanbanBoard, projects)

        // When: 태스크 상태를 IN_PROGRESS로 변경한다.
        kanbanBoardState.moveTask(task = task, targetStatus = Status.IN_PROGRESS)

        // Then: tasks 내부에 IN_PROGRESS 인 태스크가 한 개 존재한다.
        assertThat(kanbanBoardState.tasks.count { it.status == Status.IN_PROGRESS }).isEqualTo(1)
    }

    @Test
    fun `getProjectsTitles()로 프로젝트의 제목들을 불러온다`() {
        // Given: 프로젝트 1번, 2번의 이름을 가진 칸반보드를 생성한다.
        val kanbanBoard = KanbanBoard()
        val projects = listOf(
            KanbanProject(title = "프로젝트 1번"),
            KanbanProject(title = "프로젝트 2번"),
        )
        val kanbanBoardState = KanbanBoardState(kanbanBoard, projects)

        // When: 프로젝트의 제목들을 불러온다.
        val result = kanbanBoardState.getProjectsTitles()

        // Then: 불러온 결과값의 개수가 2개이고, 각각 '프로젝트 1번', '프로젝트 2번'을 포함한다.
        assertThat(result.count()).isEqualTo(2)
        assertThat(result).contains("프로젝트 1번")
        assertThat(result).contains("프로젝트 2번")
    }

    @Test
    fun `getCompleteCount()로 완료된 태스크의 개수를 셀 수 있다`() {
        // Given: 프로젝트 1번에는 DONE 0개, 프로젝트 2번에는 DONE 1개를 생성한다.
        val task1 = createKanbanTask(id = 0L, status = Status.IN_PROGRESS)
        val task2 = createKanbanTask(id = 1L, status = Status.IN_PROGRESS)
        val task3 = createKanbanTask(id = 2L, status = Status.DONE)
        val kanbanBoard = KanbanBoard(listOf(task1, task2, task3))
        val projects = listOf(
            KanbanProject(title = "프로젝트 1번", taskIds = listOf(0L)),
            KanbanProject(title = "프로젝트 2번", taskIds = listOf(1L, 2L)),
        )
        val kanbanBoardState = KanbanBoardState(kanbanBoard, projects)

        // When: result1 에는 프로젝트 1의 DONE의 개수를, result2는 프로젝트 2의 DONE의 개수를 불러온다.
        kanbanBoardState.updateSelectedProjectIndex(0)
        val result1 = kanbanBoardState.getCompleteCount()
        kanbanBoardState.updateSelectedProjectIndex(1)
        val result2 = kanbanBoardState.getCompleteCount()

        // Then: result1 에는 DONE이 0개, result2에는 DONE이 1개 뜬다.
        assertThat(result1).isEqualTo(0)
        assertThat(result2).isEqualTo(1)
    }

    @Test
    fun `getTotalCount()로 총 태스크의 개수를 불러올 수 있다`() {
        // Given: 프로젝트 1번에는 Task 1개, 프로젝트 2번에는 Task 2개를 생성한다.
        val task1 = createKanbanTask(id = 0L, status = Status.TO_DO)
        val task2 = createKanbanTask(id = 1L, status = Status.IN_PROGRESS)
        val task3 = createKanbanTask(id = 2L, status = Status.DONE)
        val kanbanBoard = KanbanBoard(listOf(task1, task2, task3))
        val projects = listOf(
            KanbanProject(title = "프로젝트 1번", taskIds = listOf(0L)),
            KanbanProject(title = "프로젝트 2번", taskIds = listOf(1L, 2L)),
        )
        val kanbanBoardState = KanbanBoardState(kanbanBoard, projects)

        // When: result1에는 프로젝트 1의 Task의 개수를, result2는 프로젝트 2의 Task의 개수를 불러온다.
        kanbanBoardState.updateSelectedProjectIndex(0)
        val result1 = kanbanBoardState.getTotalCount()
        kanbanBoardState.updateSelectedProjectIndex(1)
        val result2 = kanbanBoardState.getTotalCount()

        // Then: result1에는 Task 1개, result2에는 Task 2개가 뜬다.
        assertThat(result1).isEqualTo(1)
        assertThat(result2).isEqualTo(2)
    }
}
