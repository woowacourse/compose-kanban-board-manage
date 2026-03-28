@file:Suppress("NonAsciiCharacters")

package woowacourse.kanban.board.ui.screen.board

import org.assertj.core.api.Assertions.assertThat
import woowacourse.kanban.board.domain.KanbanBoard
import woowacourse.kanban.board.domain.KanbanProject
import woowacourse.kanban.board.domain.dialog.Status
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
}
