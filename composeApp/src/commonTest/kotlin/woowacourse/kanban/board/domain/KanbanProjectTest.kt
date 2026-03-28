@file:Suppress("NonAsciiCharacters")

package woowacourse.kanban.board.domain

import kotlin.test.Test
import org.assertj.core.api.Assertions.assertThat

class KanbanProjectTest {
    @Test
    fun `getTaskIds() 함수를 호출했을 때 태스크 ID 리스트를 반환한다`() {
        // Given: 두 개 id를 가진 id 리스트를 생성한다.
        val taskIds = listOf(1L, 2L)

        // When: 칸반프로젝트에 id 리스트를 주입한다.
        val kanbanProject = KanbanProject(
            title = "안녕",
            taskIds = taskIds,
        )

        // Then: 칸반프로젝트의 getTaskIds()의 반환값과 기존 taskIds와 일치한다.
        assertThat(kanbanProject.getTaskIds()).isEqualTo(taskIds)
    }

    @Test
    fun `addTaskId()로 태스크 ID를 추가했을 때 칸반프로젝트가 해당 태스크의 ID 를 갖고 있다`() {
        // Given: 기본 칸반 프로젝트 생성
        val kanbanProject = KanbanProject("안녕")

        // When: 1L 인 ID 추가
        kanbanProject.addTaskId(1L)

        // Then: 1L인 ID 는 갖고있지만, 0L인 ID 는 가지고 있지 않는다
        assertThat(kanbanProject.getTaskIds().contains(1L)).isTrue
        assertThat(kanbanProject.getTaskIds().contains(0L)).isFalse
    }
}
