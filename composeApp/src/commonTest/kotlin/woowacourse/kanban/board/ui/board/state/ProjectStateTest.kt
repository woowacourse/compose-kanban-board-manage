package woowacourse.kanban.board.ui.board.state

import kotlin.test.Test
import org.assertj.core.api.Assertions.assertThat
import woowacourse.kanban.board.domain.Author
import woowacourse.kanban.board.domain.Task
import woowacourse.kanban.board.domain.TaskState

class ProjectStateTest {

    @Test
    fun `TODO 2개, INPROGRESS 1개, DONE 2개인 경우 각 상태별 태스크의 개수는 2, 1, 2가 반환한다`() {
        // given
        val project = ProjectState(
            name = "",
            initialTasks = mutableListOf(
                Task(title = "title1", taskState = TaskState.TO_DO, author = Author.User("다이노")),
                Task(title = "title2", taskState = TaskState.TO_DO, author = Author.User("다이노")),
                Task(title = "title3", taskState = TaskState.IN_PROGRESS, author = Author.User("다이노")),
                Task(title = "title4", taskState = TaskState.DONE, author = Author.User("다이노")),
                Task(title = "title5", taskState = TaskState.DONE, author = Author.User("다이노")),
            ),
        )

        // when
        val toDoCount = project.countByState(TaskState.TO_DO)
        val inProgressCount = project.countByState(TaskState.IN_PROGRESS)
        val doneCount = project.countByState(TaskState.DONE)

        // then
        assertThat(toDoCount).isEqualTo(2)
        assertThat(inProgressCount).isEqualTo(1)
        assertThat(doneCount).isEqualTo(2)
    }

    @Test
    fun `Tasks가 비어 있는 경우 TODO의 개수는 0개이다`() {
        // given
        val project = ProjectState(name = "")

        // when
        val toDoCount = project.countByState(TaskState.TO_DO)

        // then
        assertThat(toDoCount).isEqualTo(0)
    }

    @Test
    fun `TODO 2개, DONE 2개인 경우 완료된 일의 비율은 50이다`() {
        // given
        val project = ProjectState(
            name = "",
            initialTasks = mutableListOf(
                Task(title = "title1", taskState = TaskState.TO_DO, author = Author.User("다이노")),
                Task(title = "title2", taskState = TaskState.TO_DO, author = Author.User("다이노")),
                Task(title = "title4", taskState = TaskState.DONE, author = Author.User("다이노")),
                Task(title = "title5", taskState = TaskState.DONE, author = Author.User("다이노")),
            ),
        )

        // when
        val completedRate = project.completedRate

        // then
        assertThat(completedRate).isEqualTo(50)
    }

    @Test
    fun `Tasks가 비어있는 경우 완료된 일의 비율은 0이다`() {
        // given
        val project = ProjectState(name = "")

        // when
        val completedRate = project.completedRate

        // then
        assertThat(completedRate).isEqualTo(0)
    }

    @Test
    fun `TODO 2개, DONE 2개인 경우 TODO 상태의 태스크는 2개이고 첫 번째 태스크의 제목은 title1이다`() {
        // given
        val project = ProjectState(
            name = "",
            initialTasks = mutableListOf(
                Task(title = "title1", taskState = TaskState.TO_DO, author = Author.User("다이노")),
                Task(title = "title2", taskState = TaskState.TO_DO, author = Author.User("다이노")),
                Task(title = "title4", taskState = TaskState.DONE, author = Author.User("다이노")),
                Task(title = "title5", taskState = TaskState.DONE, author = Author.User("다이노")),
            ),
        )

        // when
        val toDoTasks = project.getTasksByState(TaskState.TO_DO)

        // then
        assertThat(toDoTasks.size).isEqualTo(2)
        assertThat(toDoTasks.first().title).isEqualTo("title1")
    }

    @Test
    fun `Tasks가 비어 있는 경우 TODO 상태의 태스크는 0개이다`() {
        // given
        val project = ProjectState(name = "")

        // when
        val toDoTasks = project.getTasksByState(TaskState.TO_DO)

        // then
        assertThat(toDoTasks.size).isEqualTo(0)
    }

    @Test
    fun `태스크를 추가하면 Tasks에 추가된다`() {
        // given
        val project = ProjectState(name = "")

        // when
        project.addTask(Task(title = "title", taskState = TaskState.TO_DO, author = Author.User("다이노")))

        // then
        assertThat(project.tasks.size).isEqualTo(1)
    }

    @Test
    fun `태스크의 상태를 변경하면 Tasks에 변경된 상태가 반영된다`() {
        // given
        val project = ProjectState(
            name = "",
            initialTasks = mutableListOf(
                Task(
                    title = "title1",
                    taskState = TaskState.TO_DO,
                    author = Author.User("다이노"),
                ),
            ),
        )

        // when
        project.changeTaskState(project.tasks.first().id, TaskState.DONE)

        // then
        assertThat(project.tasks.first().taskState).isEqualTo(TaskState.DONE)
    }

    @Test
    fun `태스크를 수정하면 Tasks에 수정된 태스크가 반영된다`() {
        // given
        val project = ProjectState(
            name = "",
            initialTasks = mutableListOf(
                Task(
                    title = "title1",
                    taskState = TaskState.TO_DO,
                    author = Author.User("다이노"),
                ),
            ),
        )

        // when
        project.updateTask(project.tasks.first().id, Task(title = "title2", taskState = TaskState.DONE, author = Author.User("다이노")))

        // then
        assertThat(project.tasks.first().title).isEqualTo("title2")
    }
}
