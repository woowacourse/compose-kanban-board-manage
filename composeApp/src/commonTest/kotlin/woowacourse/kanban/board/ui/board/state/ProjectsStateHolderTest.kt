package woowacourse.kanban.board.ui.board.state

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import woowacourse.kanban.board.domain.Author
import woowacourse.kanban.board.domain.Task
import woowacourse.kanban.board.domain.TaskState

class ProjectsStateHolderTest {

    val projects = listOf(
        ProjectState(name = "Compose1"),
        ProjectState(name = "Compose2"),
        ProjectState(name = "Compose3너무너무길다란이름"),
    )

    @Test
    fun `ProjectsStateHolder를 생성하면 3개의 프로젝트 리스트가 저장된다`() {
        // when
        val stateHolder = ProjectsStateHolder(projects)

        // then
        assertThat(stateHolder.projects.size).isEqualTo(3)
    }

    @Test
    fun `ProjectsSTateHolder를 생성하면 프로젝트 리스트의 첫 번째 프로젝트가 선택된다`() {
        // when
        val stateHolder = ProjectsStateHolder(projects)

        // then
        assertThat(stateHolder.selectedProject).isEqualTo(projects.first())
    }

    @Test
    fun `프로젝트를 선택하면 해당 프로젝트가 선택된다`() {
        // given
        val stateHolder = ProjectsStateHolder(projects)

        // when
        stateHolder.selectProject(projects[1].id)

        // then
        assertThat(stateHolder.selectedProject).isEqualTo(projects[1])
    }

    @Test
    fun `첫 번째 프로젝트에 태스크를 추가하면 해당 프로젝트의 태스크 리스트에 추가된다`() {
        // given
        val stateHolder = ProjectsStateHolder(projects)

        // when
        stateHolder.selectedProject?.addTask(Task(title = "title", taskState = TaskState.TO_DO, author = Author.User("다이노")))

        // then
        assertThat(stateHolder.selectedProject?.tasks?.size).isEqualTo(1)
    }

    @Test
    fun `첫 번째 프로젝트의 첫 번째 태스크의 상태를 변경하면 해당 프로젝트의 태스크 리스트의 첫 번째 태스크의 상태가 변경된다`() {
        // given
        val stateHolder = ProjectsStateHolder(projects)
        stateHolder.selectedProject?.addTask(Task(title = "title", taskState = TaskState.TO_DO, author = Author.User("다이노")))

        // when
        stateHolder.selectedProject?.changeTaskState(stateHolder.selectedProject!!.tasks.first().id, TaskState.DONE)

        // then
        assertThat(stateHolder.selectedProject?.tasks?.first()?.taskState).isEqualTo(TaskState.DONE)
    }

    @Test
    fun `첫 번째 프로젝트의 첫 번째 태스크를 수정하면 해당 프로젝트의 태스크 리스트의 첫 번째 태스크가 수정된다`() {
        // given
        val stateHolder = ProjectsStateHolder(projects)
        val task = Task(title = "title", taskState = TaskState.TO_DO, author = Author.User("다이노"))
        stateHolder.selectedProject?.addTask(task)

        // when
        stateHolder.selectedProject?.updateTask(
            task.id,
            Task(
                id = task.id,
                title = "title2",
                taskState = TaskState.DONE,
                author = Author.User("다이노"),
            ),
        )

        // then
        assertThat(stateHolder.selectedProject?.tasks?.first()?.taskState).isEqualTo(TaskState.DONE)
    }
}
