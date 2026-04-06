package woowacourse.kanban.board.domain

import kotlin.test.Test
import kotlin.test.assertFailsWith
import org.assertj.core.api.Assertions.assertThat

class KanbanBoardTest {

    @Test
    fun `전체_태스크_중_완료된_태스크의_비율을_계산한다`() {
        // given
        val tasks = listOf(
            createTask(status = TaskStatus.TODO),
            createTask(status = TaskStatus.DONE),
        )
        val board = KanbanBoard(tasks)

        // when
        val actual = board.completionRate

        // then
        assertThat(actual).isEqualTo(0.5f)
    }

    @Test
    fun `태스크가_없을_때_완료율은_0퍼센트이다`() {
        // given
        val tasks = emptyList<KanbanTask>()
        val board = KanbanBoard(tasks)

        // when
        val actual = board.completionRate

        // then
        assertThat(actual).isEqualTo(0.0f)
    }

    @Test
    fun `상태에_따라_분류된_태스크_목록을_정확히_반환한다`() {
        // given
        val todoTask = createTask(status = TaskStatus.TODO)
        val doneTask = createTask(status = TaskStatus.DONE)
        val board = KanbanBoard(listOf(todoTask, doneTask))

        // when
        val actual = board.getTasksByStatus(TaskStatus.TODO)

        // then
        assertThat(actual).containsExactly(todoTask)
        assertThat(actual).doesNotContain(doneTask)
    }

    @Test
    fun `태스크를 추가하면 해당 태스크가 포함된 새 보드를 반환한다`() {
        // given
        val board = KanbanBoard()
        val task = createTask(status = TaskStatus.TODO)

        // when
        val updatedBoard = board.addTask(task)

        // then
        assertThat(updatedBoard.tasks).containsExactly(task)
    }

    @Test
    fun `태스크를 추가해도 원래 보드 객체는 변경되지 않는다`() {
        // given
        val board = KanbanBoard()
        val task = createTask(status = TaskStatus.TODO)

        // when
        val updatedBoard = board.addTask(task)

        // then
        assertThat(updatedBoard).isNotSameAs(board)
        assertThat(board.tasks).isEmpty()
    }

    @Test
    fun `태스크를 다른 컬럼으로 이동하면 해당 태스크의 상태만 변경된 새 보드를 반환한다`() {
        // given
        val task = createTask(status = TaskStatus.TODO)
        val board = KanbanBoard(listOf(task))

        // when
        val updatedBoard = board.moveTask(task.id, TaskStatus.IN_PROGRESS)

        // then
        assertThat(updatedBoard.tasks.first().status).isEqualTo(TaskStatus.IN_PROGRESS)
        assertThat(updatedBoard.tasks.first().id).isEqualTo(task.id)
    }

    @Test
    fun `태스크를 이동해도 원래 보드 객체는 변경되지 않는다`() {
        // given
        val task = createTask(status = TaskStatus.TODO)
        val board = KanbanBoard(listOf(task))

        // when
        val updatedBoard = board.moveTask(task.id, TaskStatus.IN_PROGRESS)

        // then
        assertThat(updatedBoard).isNotSameAs(board)
        assertThat(board.tasks.first().status).isEqualTo(TaskStatus.TODO)
    }

    @Test
    fun `존재하지 않는 ID로 이동을 시도하면 보드가 변경되지 않는다`() {
        // given
        val task = createTask(status = TaskStatus.TODO)
        val board = KanbanBoard(listOf(task))
        val nonExistentTask = createTask()

        // when
        val updatedBoard = board.moveTask(nonExistentTask.id, TaskStatus.DONE)

        // then
        assertThat(updatedBoard.tasks).isEqualTo(board.tasks)
    }

    @Test
    fun `동일한 상태로 이동을 시도하면 보드가 변경되지 않는다`() {
        // given
        val task = createTask(status = TaskStatus.TODO)
        val board = KanbanBoard(listOf(task))

        // when
        val updatedBoard = board.moveTask(task.id, TaskStatus.TODO)

        // then
        assertThat(updatedBoard.tasks).isEqualTo(board.tasks)
    }

    @Test
    fun `상태별_태스크_개수를_정확히_반환한다`() {
        // given
        val todoTask1 = createTask(status = TaskStatus.TODO)
        val todoTask2 = createTask(status = TaskStatus.TODO)
        val inProgressTask = createTask(status = TaskStatus.IN_PROGRESS)
        val board = KanbanBoard(listOf(todoTask1, todoTask2, inProgressTask))

        // when & then
        assertThat(board.getCountByStatus(TaskStatus.TODO)).isEqualTo(2)
        assertThat(board.getCountByStatus(TaskStatus.IN_PROGRESS)).isEqualTo(1)
        assertThat(board.getCountByStatus(TaskStatus.DONE)).isEqualTo(0)
    }

    @Test
    fun `담당자 없이 담당자 필수 상태로 이동을 시도하면 보드가 변경되지 않는다`() {
        // given
        val task = KanbanTask(title = "제목", status = TaskStatus.TODO, crewName = null)
        val board = KanbanBoard(listOf(task))

        // when
        val updatedBoard = board.moveTask(task.id, TaskStatus.IN_PROGRESS)

        // then
        assertThat(updatedBoard.tasks.first().status).isEqualTo(TaskStatus.TODO)
    }

    @Test
    fun `삭제 불가 상태의 태스크를 삭제 시도하면 보드가 변경되지 않는다`() {
        // given
        val task = KanbanTask(title = "제목", status = TaskStatus.REVIEW, crewName = "다이노")
        val board = KanbanBoard(listOf(task))

        // when
        val updatedBoard = board.deleteTask(task.id)

        // then
        assertThat(updatedBoard.tasks).hasSize(1)
    }

    @Test
    fun `태스크를 삭제하면 해당 태스크가 제거된 새 보드를 반환한다`() {
        // given
        val task = createTask(status = TaskStatus.TODO)
        val board = KanbanBoard(listOf(task))

        // when
        val updatedBoard = board.deleteTask(task.id)

        // then
        assertThat(updatedBoard.tasks).isEmpty()
    }

    @Test
    fun `태스크를 삭제해도 원래 보드 객체는 변경되지 않는다`() {
        // given
        val task = createTask(status = TaskStatus.TODO)
        val board = KanbanBoard(listOf(task))

        // when
        val updatedBoard = board.deleteTask(task.id)

        // then
        assertThat(updatedBoard).isNotSameAs(board)
        assertThat(board.tasks).hasSize(1)
    }

    @Test
    fun `존재하지 않는 ID로 삭제를 시도하면 보드가 변경되지 않는다`() {
        // given
        val task = createTask(status = TaskStatus.TODO)
        val board = KanbanBoard(listOf(task))
        val nonExistentTask = createTask()

        // when
        val updatedBoard = board.deleteTask(nonExistentTask.id)

        // then
        assertThat(updatedBoard.tasks).isEqualTo(board.tasks)
    }

    @Test
    fun `존재하지 않는 ID로 수정을 시도하면 보드가 변경되지 않는다`() {
        // given
        val task = createTask(title = "기존 제목", status = TaskStatus.TODO)
        val board = KanbanBoard(listOf(task))
        val nonExistentTask = createTask(title = "수정된 제목")

        // when
        val updatedBoard = board.updateTask(nonExistentTask)

        // then
        assertThat(updatedBoard.tasks.first().title).isEqualTo("기존 제목")
    }

    @Test
    fun `태스크를 수정하면 해당 태스크가 교체된 새 보드를 반환한다`() {
        // given
        val task = createTask(title = "기존 제목", status = TaskStatus.TODO)
        val board = KanbanBoard(listOf(task))
        val updatedTask = task.copy(title = "수정된 제목")

        // when
        val updatedBoard = board.updateTask(updatedTask)

        // then
        assertThat(updatedBoard.tasks.first().title).isEqualTo("수정된 제목")
        assertThat(updatedBoard.tasks.first().id).isEqualTo(task.id)
    }

    @Test
    fun `태스크를 수정해도 원래 보드 객체는 변경되지 않는다`() {
        // given
        val task = createTask(title = "기존 제목", status = TaskStatus.TODO)
        val board = KanbanBoard(listOf(task))

        // when
        val updatedBoard = board.updateTask(task.copy(title = "수정된 제목"))

        // then
        assertThat(updatedBoard).isNotSameAs(board)
        assertThat(board.tasks.first().title).isEqualTo("기존 제목")
    }

    @Test
    fun `updateTask로 유효한 전이를 시도하면 상태가 변경된다`() {
        // given
        val task = createTask(status = TaskStatus.TODO)
        val board = KanbanBoard(listOf(task))

        // when
        val updatedBoard = board.updateTask(task.copy(status = TaskStatus.IN_PROGRESS))

        // then
        assertThat(updatedBoard.tasks.first().status).isEqualTo(TaskStatus.IN_PROGRESS)
    }

    @Test
    fun `updateTask로 유효하지 않은 전이를 시도하면 예외가 발생한다`() {
        // given
        val task = createTask(status = TaskStatus.TODO)
        val board = KanbanBoard(listOf(task))

        // when & then
        assertFailsWith<IllegalArgumentException> {
            board.updateTask(task.copy(status = TaskStatus.DONE))
        }
    }

    @Test
    fun `updateTask로 담당자 없이 담당자 필수 상태로 변경하면 예외가 발생한다`() {
        // given
        val task = KanbanTask(title = "제목", status = TaskStatus.TODO, crewName = null)
        val board = KanbanBoard(listOf(task))

        // when & then
        assertFailsWith<IllegalArgumentException> {
            board.updateTask(task.copy(status = TaskStatus.IN_PROGRESS))
        }
    }

    private fun createTask(title: String = "테스트 제목", status: TaskStatus = TaskStatus.TODO, crewName: String? = "테스트 크루"): KanbanTask {
        return KanbanTask(
            title = title,
            status = status,
            crewName = crewName,
        )
    }
}
