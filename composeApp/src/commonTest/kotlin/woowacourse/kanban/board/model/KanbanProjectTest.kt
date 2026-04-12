package woowacourse.kanban.board.model

import kotlin.test.assertEquals
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import woowacourse.kanban.domain.project.KanbanProject
import woowacourse.kanban.domain.task.Assignee
import woowacourse.kanban.domain.task.KanbanTask
import woowacourse.kanban.domain.task.Tags
import woowacourse.kanban.domain.task.TaskData
import woowacourse.kanban.domain.task.TaskStatus
import woowacourse.kanban.domain.task.Title

class KanbanProjectTest {
    @Test
    fun `새 태스크를 생성했을 때 현재 프로젝트에 삽입되어야 한다`() = runTest {
        var project = KanbanProject()

        project = project.addTask(
            KanbanTask(
                data = TaskData(
                    title = Title("제목"),
                    content = "내용",
                    tags = Tags(),
                    assignee = Assignee.NONE,
                ),
                status = TaskStatus.TO_DO,
            ),
        )

        assertEquals(1, project.projectTasks.size)
    }

    @Test
    fun `태스크가 없는 프로젝트의 진행률은 0이다`() = runTest {
        val project = KanbanProject()
        assertThat(project.getProgress()).isEqualTo(0.0)
    }

    @Test
    fun `완료된 태스크 비율에 따라 진행률이 정확히 계산되어야 한다`() = runTest {
        val todoTask = KanbanTask(
            data = TaskData(
                title = Title("제목"),
                content = "내용",
                tags = Tags(),
                assignee = Assignee.NONE,
            ),
            status = TaskStatus.TO_DO,
        )
        val doneTask = KanbanTask(
            data = TaskData(
                title = Title("제목"),
                content = "내용",
                tags = Tags(),
                assignee = Assignee.DINO,
            ),
            status = TaskStatus.DONE,
        )
        val project = KanbanProject(listOf(todoTask, doneTask))

        assertThat(project.getProgress()).isEqualTo(0.5)
    }

    @Test
    fun `태스크의 상태를 변경 할 수 있어야 한다`() = runTest {
        var project = KanbanProject(
            listOf(
                KanbanTask(
                    data = TaskData(
                        title = Title("제목"),
                        content = "내용",
                        tags = Tags(),
                        assignee = Assignee.DINO,
                    ),
                    status = TaskStatus.IN_PROGRESS,
                ),
            ),
        )

        project = project.changeTaskStatus(0, TaskStatus.REVIEW)

        assertEquals(TaskStatus.REVIEW, project.projectTasks[0].status)
    }

    @Test
    fun `태스크를 삭제할 수 있다`() = runTest {
        // given: 프로젝트에 TO_DO 상태인 Task가 있을 때
        val task = KanbanTask(
            data = TaskData(
                title = Title("제목"),
                content = "내용",
                tags = Tags(),
                assignee = Assignee.DINO,
            ),
            status = TaskStatus.TO_DO,
        )

        var project = KanbanProject(listOf(task))

        // when: IN_PROGRESS상태인 태스크를 삭제하면
        project = project.deleteTask(task.data.id)

        // 태스크가 삭제되고 true가 반환된다
        assertThat(project.projectTasks.size).isEqualTo(0)
    }

    @Test
    fun `id를 통해 task를 반환받을 수 있다`() = runTest {
        val todoTask = KanbanTask(
            data = TaskData(
                title = Title("제목"),
                content = "내용",
                tags = Tags(),
                assignee = Assignee.NONE,
            ),
            status = TaskStatus.TO_DO,
        )

        val targetId = todoTask.data.id

        val project = KanbanProject(listOf(todoTask))

        assertThat(project.getTaskWithID(targetId)).isEqualTo(todoTask)
    }

    @Test
    fun `id를 통해 task의 index를 반환받을 수 있다`() = runTest {
        val todoTask = KanbanTask(
            data = TaskData(
                title = Title("제목"),
                content = "내용",
                tags = Tags(),
                assignee = Assignee.NONE,
            ),
            status = TaskStatus.TO_DO,
        )

        val targetId = todoTask.data.id

        val project = KanbanProject(listOf(todoTask))

        assertThat(project.getTaskIndexWithId(targetId)).isEqualTo(0)
    }

    @Test
    fun `상태별로 태스크를 반환받을 수 있다`() = runTest {
        val todoTask = KanbanTask(
            data = TaskData(
                title = Title("제목"),
                content = "내용",
                tags = Tags(),
                assignee = Assignee.NONE,
            ),
            status = TaskStatus.TO_DO,
        )
        val doneTask = KanbanTask(
            data = TaskData(
                title = Title("제목"),
                content = "내용",
                tags = Tags(),
                assignee = Assignee.DINO,
            ),
            status = TaskStatus.DONE,
        )
        val project = KanbanProject(listOf(todoTask, doneTask))

        val todoTasks = project.getTasksWithStatus(TaskStatus.TO_DO)

        assertThat(todoTasks).hasSize(1)
        assertThat(todoTasks[0].status).isEqualTo(TaskStatus.TO_DO)
    }
}
