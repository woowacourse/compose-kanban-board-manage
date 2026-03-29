package woowacourse.kanban.board.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class ProjectTest {
    @Test
    fun `프로젝트에 새로운 태스크를 추가할 수 있다`() {
        // given
        val project = Project(
            name = "Compose1",
            tasks = Tasks(emptyList()),
        )
        val task = Task(
            title = "title",
            taskState = TaskState.TO_DO,
            author = "samuel"
        )

        // when
        val updatedProject = project.createNewTask(task)

        // then
        assertThat(updatedProject.tasks.items).contains(task)
    }

    @Test
    fun `태스크의 상태를 변경하면, 변경된 상태가 반영된 새로운 프로젝트가 반환된다`() {
        val project = Project(
            name = "name",
            tasks = Tasks(
                listOf(Task(title = "title", taskState = TaskState.TO_DO))
            )
        )

        val updatedProject = project.changeTaskState(0, TaskState.DONE)

        assertThat(updatedProject.tasks.items[0].taskState).isEqualTo(TaskState.DONE)
    }
}
