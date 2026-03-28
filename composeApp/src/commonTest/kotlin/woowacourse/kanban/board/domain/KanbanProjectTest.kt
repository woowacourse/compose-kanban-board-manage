package woowacourse.kanban.board.domain

import kotlin.test.Test
import org.assertj.core.api.Assertions.assertThat

class KanbanProjectTest {
    @Test
    fun `태스크 ID 리스트를 반환한다`() {
        // Given
        val taskIds = listOf(1L, 2L)

        // When
        val kanbanProject = KanbanProject(
            title = "안녕",
            taskIds = taskIds,
        )

        // Then
        assertThat(kanbanProject.getTaskIds()).isEqualTo(taskIds)
    }

    @Test
    fun `태스크 ID가 잘 들어간다`() {
        // Given
        val kanbanProject = KanbanProject("안녕")

        // When
        kanbanProject.addTaskId(1L)

        // Then
        assertThat(kanbanProject.getTaskIds().contains(1L)).isTrue
        assertThat(kanbanProject.getTaskIds().contains(0L)).isFalse
    }
}
