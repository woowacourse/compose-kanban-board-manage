@file:Suppress("NonAsciiCharacters")

package woowacourse.kanban.board.domain

import org.assertj.core.api.Assertions.assertThat
import woowacourse.kanban.board.domain.dialog.Status
import woowacourse.kanban.board.fixture.createKanbanTask
import woowacourse.kanban.board.fixture.createKanbanTasks
import kotlin.test.Test

class KanbanBoardTest {
    @Test
    fun `칸반 태스크의 id 값들을 주었을 때_칸반 보드 내부의 태스크 리스트가 정상적으로 호출된다`() {
        // Given: 3개의 태스크를 생성하고, 해당 id 들은 각각 0L, 1L, 2L 이다.
        val tasks = createKanbanTasks(requiredTaskCount = 3, startId = 0L)
        val kanbanIds = listOf(0L, 1L, 2L)

        // When: 칸반보드 내부에 태스크 리스트를 주입한다.
        val kanbanBoard = KanbanBoard(
            tasks = tasks,
        )

        // Then: 칸반 태스크의 id 값을 주었을 때 해당 리스트를 정확히 반환한다.
        assertThat(kanbanBoard.getTasks(kanbanIds)).isEqualTo(tasks)
    }

    @Test
    fun `칸반 보드 태스크를 생성했을 때 리스트에 추가된다`() {
        // Given: 칸반 보드 태스크를 세 개 생성한 후, 새로운 추가할 태스크 준비한다.
        val kanbanBoard = KanbanBoard(tasks = createKanbanTasks(requiredTaskCount = 3, startId = 0L))
        val kanbanIds = listOf(0L, 1L, 2L, 3L)
        val newTask = createKanbanTask(description = "새로운 태스크")

        // When: 새로운 태스크를 추가한다.
        kanbanBoard.createTask(
            task = newTask,
        )

        // Then: "새로운 태스크"라는 설명을 가진 태스크가 1개 존재한다.
        assertThat(kanbanBoard.getTasks(kanbanIds).count { it.description == "새로운 태스크" }).isEqualTo(1)
    }

    @Test
    fun `칸반 보드 태스크의 상태를 수정했을 때 상태가 반영된다`() {
        // Given: status 기본값이 'Status.TO_DO'인 3개의 태스크를 생성하여 칸반 보드에 주입한다.
        val tasks = createKanbanTasks(requiredTaskCount = 3, startId = 0L)
        val kanbanBoard = KanbanBoard(tasks = tasks)
        val kanbanIds = listOf(0L, 1L, 2L)

        // When: 첫 번째 태스크의 상태를 'Status.IN_PROGRESS'
        kanbanBoard.changeTaskStatus(tasks.first(), Status.IN_PROGRESS)

        // Then: 현재 상태가 'Status.IN_PROGRESS'인 칸반태스크는 한 개이다.
        assertThat(kanbanBoard.getTasks(kanbanIds).count { it.status == Status.IN_PROGRESS }).isEqualTo(1)
    }
}
