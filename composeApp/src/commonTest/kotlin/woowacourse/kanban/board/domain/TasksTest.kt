package woowacourse.kanban.board.domain

import kotlin.test.Test
import org.assertj.core.api.Assertions.assertThat

class TasksTest {

    @Test
    fun `TODO 2개, INPROGRESS 1개, DONE 2개인 경우 각 상태별 태스크의 개수는 2, 1, 2가 반환한다`() {
        // given
        val tasks = Tasks(
            listOf(
                Task(title = "title1", taskState = TaskState.TO_DO),
                Task(title = "title2", taskState = TaskState.TO_DO),
                Task(title = "title3", taskState = TaskState.IN_PROGRESS),
                Task(title = "title4", taskState = TaskState.DONE),
                Task(title = "title5", taskState = TaskState.DONE),
            ),
        )

        // when
        val toDoCount = tasks.countByState(TaskState.TO_DO)
        val inProgressCount = tasks.countByState(TaskState.IN_PROGRESS)
        val doneCount = tasks.countByState(TaskState.DONE)

        // then
        assertThat(toDoCount).isEqualTo(2)
        assertThat(inProgressCount).isEqualTo(1)
        assertThat(doneCount).isEqualTo(2)
    }

    @Test
    fun `Tasks가 비어 있는 경우 TODO의 개수는 0개이다`() {
        // given
        val tasks = Tasks(emptyList())

        // when
        val toDoCount = tasks.countByState(TaskState.TO_DO)

        // then
        assertThat(toDoCount).isEqualTo(0)
    }

    @Test
    fun `TODO 2개, DONE 2개인 경우 완료된 일의 비율은 50이다`() {
        // given
        val tasks = Tasks(
            listOf(
                Task(title = "title1", taskState = TaskState.TO_DO),
                Task(title = "title2", taskState = TaskState.TO_DO),
                Task(title = "title4", taskState = TaskState.DONE),
                Task(title = "title5", taskState = TaskState.DONE),
            ),
        )

        // when
        val completedRate = tasks.completedRate()

        // then
        assertThat(completedRate).isEqualTo(50)
    }

    @Test
    fun `Tasks가 비어있는 경우 완료된 일의 비율은 0이다`() {
        // given
        val tasks = Tasks(emptyList())

        // when
        val completedRate = tasks.completedRate()

        // then
        assertThat(completedRate).isEqualTo(0)
    }

    @Test
    fun `TODO 2개, DONE 2개인 경우 TODO 상태의 태스크는 2개이고 첫 번째 태스크의 제목은 title1이다`() {
        // given
        val tasks = Tasks(
            listOf(
                Task(title = "title1", taskState = TaskState.TO_DO),
                Task(title = "title2", taskState = TaskState.TO_DO),
                Task(title = "title4", taskState = TaskState.DONE),
                Task(title = "title5", taskState = TaskState.DONE),
            ),
        )

        // when
        val toDoTasks = tasks.getTasksByState(TaskState.TO_DO)

        // then
        assertThat(toDoTasks.size).isEqualTo(2)
        assertThat(toDoTasks.first().title).isEqualTo("title1")
    }

    @Test
    fun `Tasks가 비어 있는 경우 TODO 상태의 태스크는 0개이다`() {
        // given
        val tasks = Tasks(emptyList())

        // when
        val toDoTasks = tasks.getTasksByState(TaskState.TO_DO)

        // then
        assertThat(toDoTasks.size).isEqualTo(0)
    }

    @Test
    fun `Task를 추가하면 Tasks의 개수가 늘어나고 해당 Task를 포함한다`() {
        // given
        val tasks = Tasks(emptyList())
        val newTask = Task(title = "새로운 할 일", taskState = TaskState.TO_DO)

        // when
        val updatedTasks = tasks.addTask(newTask)

        // then
        assertThat(updatedTasks.items).hasSize(1)
        assertThat(updatedTasks.items).contains(newTask)
    }

    @Test
    fun `Task의 상태를 수정하면 해당 ID를 가진 Task만 업데이트된다`() {
        // given
        val task1 = Task(title = "할 일 1", taskState = TaskState.TO_DO)
        val task2 = Task(title = "할 일 2", taskState = TaskState.TO_DO)
        val tasks = Tasks(listOf(task1, task2))
        val updatedTask1 = task1.copy(taskState = TaskState.DONE)

        // when
        val resultTasks = tasks.fixStatus(updatedTask1)

        // then
        assertThat(resultTasks.getTasksByState(TaskState.DONE)).containsExactly(updatedTask1)
        assertThat(resultTasks.getTasksByState(TaskState.TO_DO)).containsExactly(task2)
    }
}
