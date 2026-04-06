package woowacourse.kanban.board.component.board

import kotlin.test.Test
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import woowacourse.kanban.board.domain.KanbanBoard
import woowacourse.kanban.board.domain.KanbanTask
import woowacourse.kanban.board.domain.TaskStatus
import woowacourse.kanban.board.feature.board.KanbanBoardEvent
import woowacourse.kanban.board.feature.board.KanbanBoardState
import woowacourse.kanban.board.feature.board.component.dialog.model.TaskFormResult

@OptIn(ExperimentalCoroutinesApi::class)
class KanbanBoardStateTest {

    @Test
    fun `초기 상태에서는 보드가 비어있고 다이얼로그가 보이지 않는다`() {
        // Given & When
        val state = KanbanBoardState()

        // Then
        assertThat(state.kanbanBoard.tasks).isEmpty()
        assertThat(state.isTaskDialogVisible).isFalse()
    }

    @Test
    fun `초기 보드 상태를 주입하면 해당 상태로 초기화된다`() {
        // Given
        val initialBoard = KanbanBoard()
        val state = KanbanBoardState(initialBoard)

        // Then
        assertThat(state.kanbanBoard).isEqualTo(initialBoard)
    }

    @Test
    fun `showTaskDialog를 호출하면 다이얼로그가 보이는 상태가 된다`() {
        // Given
        val state = KanbanBoardState()

        // When
        state.showTaskDialog()

        // Then
        assertThat(state.isTaskDialogVisible).isTrue()
    }

    @Test
    fun `hideTaskDialog를 호출하면 다이얼로그가 보이지 않는 상태가 된다`() {
        // Given
        val state = KanbanBoardState()
        state.showTaskDialog()

        // When
        state.hideTaskDialog()

        // Then
        assertThat(state.isTaskDialogVisible).isFalse()
    }

    @Test
    fun `addTask 성공 시 TaskAdded 이벤트가 방출된다`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        val state = KanbanBoardState()
        val events = mutableListOf<KanbanBoardEvent>()
        backgroundScope.launch { state.events.collect { events.add(it) } }

        // When
        state.addTask(
            TaskFormResult(
                title = "새로운 태스크",
                description = null,
                tags = emptyList(),
                status = TaskStatus.TODO,
                assignee = "다이노",
            ),
        )

        // Then
        assertThat(events).containsExactly(KanbanBoardEvent.TaskAdded)
    }

    @Test
    fun `addTask를 호출하면 보드에 태스크가 추가되고 다이얼로그가 닫힌다`() {
        // Given
        val state = KanbanBoardState()
        state.showTaskDialog()

        // When
        state.addTask(
            TaskFormResult(
                title = "새로운 태스크",
                description = "태스크 설명",
                tags = emptyList(),
                status = TaskStatus.TODO,
                assignee = "다이노",
            ),
        )

        // Then
        assertThat(state.kanbanBoard.tasks).hasSize(1)
        val addedTask = state.kanbanBoard.tasks.first()
        assertThat(addedTask.title).isEqualTo("새로운 태스크")
        assertThat(addedTask.description).isEqualTo("태스크 설명")
        assertThat(addedTask.status).isEqualTo(TaskStatus.TODO)
        assertThat(addedTask.crewName).isEqualTo("다이노")
        assertThat(state.isTaskDialogVisible).isFalse()
    }

    @Test
    fun `moveTask를 호출하면 해당 태스크의 상태가 변경된다`() {
        // Given
        val task = KanbanTask(title = "태스크", status = TaskStatus.TODO, crewName = "다이노")
        val state = KanbanBoardState(KanbanBoard(listOf(task)))

        // When
        state.moveTask(task, TaskStatus.IN_PROGRESS)

        // Then
        assertThat(state.kanbanBoard.tasks.first().status).isEqualTo(TaskStatus.IN_PROGRESS)
    }

    @Test
    fun `moveTask 성공 시 TaskMoved 이벤트가 방출된다`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        val task = KanbanTask(title = "태스크", status = TaskStatus.TODO, crewName = "다이노")
        val state = KanbanBoardState(KanbanBoard(listOf(task)))
        val events = mutableListOf<KanbanBoardEvent>()
        backgroundScope.launch { state.events.collect { events.add(it) } }

        // When
        state.moveTask(task, TaskStatus.IN_PROGRESS)

        // Then
        assertThat(events).containsExactly(KanbanBoardEvent.TaskMoved)
    }

    @Test
    fun `유효하지 않은 전이를 시도하면 TaskTransitionFailed 이벤트가 방출된다`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        val task = KanbanTask(title = "태스크", status = TaskStatus.TODO, crewName = "다이노")
        val state = KanbanBoardState(KanbanBoard(listOf(task)))
        val events = mutableListOf<KanbanBoardEvent>()
        backgroundScope.launch { state.events.collect { events.add(it) } }

        // When
        state.moveTask(task, TaskStatus.DONE)

        // Then
        assertThat(events).containsExactly(KanbanBoardEvent.TaskTransitionFailed)
        assertThat(state.kanbanBoard.tasks.first().status).isEqualTo(TaskStatus.TODO)
    }

    @Test
    fun `담당자 없이 담당자 필수 상태로 이동하면 TaskAssigneeMissing 이벤트가 방출된다`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        val task = KanbanTask(title = "태스크", status = TaskStatus.TODO, crewName = null)
        val state = KanbanBoardState(KanbanBoard(listOf(task)))
        val events = mutableListOf<KanbanBoardEvent>()
        backgroundScope.launch { state.events.collect { events.add(it) } }

        // When
        state.moveTask(task, TaskStatus.IN_PROGRESS)

        // Then
        assertThat(events).containsExactly(KanbanBoardEvent.TaskAssigneeMissing)
        assertThat(state.kanbanBoard.tasks.first().status).isEqualTo(TaskStatus.TODO)
    }

    @Test
    fun `editTask 성공 시 태스크가 수정되고 TaskEdited 이벤트가 방출된다`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        val task = KanbanTask(title = "기존 제목", status = TaskStatus.TODO, crewName = "다이노")
        val state = KanbanBoardState(KanbanBoard(listOf(task)))
        val events = mutableListOf<KanbanBoardEvent>()
        backgroundScope.launch { state.events.collect { events.add(it) } }
        state.showEditDialog(task)

        // When
        state.editTask(
            task,
            TaskFormResult(
                title = "수정된 제목",
                description = null,
                tags = emptyList(),
                status = TaskStatus.TODO,
                assignee = "다이노",
            ),
        )

        // Then
        assertThat(state.kanbanBoard.tasks.first().title).isEqualTo("수정된 제목")
        assertThat(state.selectedTask).isNull()
        assertThat(events).containsExactly(KanbanBoardEvent.TaskEdited)
    }

    @Test
    fun `editTask에서 유효한 전이로 상태를 변경하면 태스크 상태가 변경되고 TaskEdited 이벤트가 방출된다`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        val task = KanbanTask(title = "태스크", status = TaskStatus.TODO, crewName = "다이노")
        val state = KanbanBoardState(KanbanBoard(listOf(task)))
        val events = mutableListOf<KanbanBoardEvent>()
        backgroundScope.launch { state.events.collect { events.add(it) } }
        state.showEditDialog(task)

        // When
        state.editTask(
            task,
            TaskFormResult(
                title = "태스크",
                description = null,
                tags = emptyList(),
                status = TaskStatus.IN_PROGRESS,
                assignee = "다이노",
            ),
        )

        // Then
        assertThat(state.kanbanBoard.tasks.first().status).isEqualTo(TaskStatus.IN_PROGRESS)
        assertThat(state.selectedTask).isNull()
        assertThat(events).containsExactly(KanbanBoardEvent.TaskEdited)
    }

    @Test
    fun `editTask에서 유효하지 않은 전이를 시도하면 TaskEditFailed 이벤트가 방출된다`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        val task = KanbanTask(title = "태스크", status = TaskStatus.TODO, crewName = "다이노")
        val state = KanbanBoardState(KanbanBoard(listOf(task)))
        val events = mutableListOf<KanbanBoardEvent>()
        backgroundScope.launch { state.events.collect { events.add(it) } }

        // When
        state.editTask(
            task,
            TaskFormResult(
                title = "태스크",
                description = null,
                tags = emptyList(),
                status = TaskStatus.DONE,
                assignee = "다이노",
            ),
        )

        // Then
        assertThat(events).containsExactly(KanbanBoardEvent.TaskEditFailed)
        assertThat(state.kanbanBoard.tasks.first().status).isEqualTo(TaskStatus.TODO)
    }

    @Test
    fun `deleteTask 호출 시 태스크가 삭제되고 TaskDeleted 이벤트가 방출된다`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        val task = KanbanTask(title = "태스크", status = TaskStatus.TODO, crewName = "다이노")
        val state = KanbanBoardState(KanbanBoard(listOf(task)))
        val events = mutableListOf<KanbanBoardEvent>()
        backgroundScope.launch { state.events.collect { events.add(it) } }
        state.showEditDialog(task)

        // When
        state.deleteTask(task)

        // Then
        assertThat(state.kanbanBoard.tasks).isEmpty()
        assertThat(state.selectedTask).isNull()
        assertThat(events).containsExactly(KanbanBoardEvent.TaskDeleted)
    }

    @Test
    fun `삭제 불가 상태의 태스크를 삭제하면 TaskDeleteFailed 이벤트가 방출된다`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        val task = KanbanTask(title = "태스크", status = TaskStatus.REVIEW, crewName = "다이노")
        val state = KanbanBoardState(KanbanBoard(listOf(task)))
        val events = mutableListOf<KanbanBoardEvent>()
        backgroundScope.launch { state.events.collect { events.add(it) } }

        // When
        state.deleteTask(task)

        // Then
        assertThat(events).containsExactly(KanbanBoardEvent.TaskDeleteFailed)
        assertThat(state.kanbanBoard.tasks).hasSize(1)
    }
}
