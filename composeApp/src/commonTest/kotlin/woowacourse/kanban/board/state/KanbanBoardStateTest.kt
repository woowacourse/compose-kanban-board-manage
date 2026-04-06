package woowacourse.kanban.board.state

import kotlin.test.Test
import org.assertj.core.api.Assertions.assertThat
import woowacourse.kanban.board.domain.Status
import woowacourse.kanban.board.domain.Task

private const val DEFAULT_TITLE = "테스트 태스크"
private const val DEFAULT_NAME = "다이노"
private const val NO_ASSIGNEE = "없음"

class KanbanBoardStateTest {
    
    @Test
    fun `To Do 상태는 담당자 없음 선택 가능하다`() {
        assertThat(Status.TODO).isEqualTo(Status.TODO)
    }

    @Test
    fun `허용되지 않은 상태 전이는 차단된다`() {
        val state = KanbanBoardState()
        var moveTaskCalled = false

        state.draggedTaskSourceStatus = Status.IN_PROGRESS
        state.draggedTaskId = "task123"
        state.currentDragPosition = androidx.compose.ui.geometry.Offset(100f, 100f)
        state.columnBounds[Status.IN_PROGRESS] = androidx.compose.ui.geometry.Rect(0f, 50f, 200f, 150f)

        state.onTaskDragEnd(state) { _, _ ->
            moveTaskCalled = true
        }

        assertThat(moveTaskCalled).isFalse()
    }

    @Test
    fun `상태 이동 성공 시 스낵바 메시지 표시된다`() {
        val state = KanbanBoardState()

        state.draggedTaskSourceStatus = Status.TODO
        state.draggedTaskNickname = DEFAULT_NAME
        state.draggedTaskId = "task-123"
        state.currentDragPosition = androidx.compose.ui.geometry.Offset(100f, 100f)
        state.columnBounds[Status.IN_PROGRESS] = androidx.compose.ui.geometry.Rect(0f, 50f, 200f, 150f)

        state.onTaskDragEnd(state) { _, _ -> }

        assertThat(state.snackBarState.isVisible).isTrue()
        assertThat(state.snackBarState.text).contains("태스크가 이동되었습니다.")
    }
}
