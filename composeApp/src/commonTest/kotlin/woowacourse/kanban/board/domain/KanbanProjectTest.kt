package woowacourse.kanban.board.domain

import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import woowacourse.kanban.board.domain.model.Assignee
import woowacourse.kanban.board.domain.model.KanbanProject
import woowacourse.kanban.board.domain.model.KanbanResult
import woowacourse.kanban.board.domain.model.Status
import woowacourse.kanban.board.domain.model.Tags
import woowacourse.kanban.board.domain.model.Task

class KanbanProjectTest {

    /*
        1. addTask()
            1-1. 태스크 추가 시 기존 해당 태스크가 추가된 프로젝트를 반환한다

        2. deleteTask()
            2-1. 태스크 Id를 받아 해당 태스크를 제거한 프로젝트를 반환한다.
     */

    @Test
    fun `태스크 추가 시 해당 태스크가 포함된 새로운 프로젝트를 반환한다`() {
        val project = KanbanProject(name = "프로젝트")
        val newTask = Task(
            title = "태스크",
            tags = Tags(),
            status = Status.TODO,
        )
        val result = (project.addTask(newTask) as KanbanResult.Success).data
        assertContains(result.tasks, newTask)
    }

    @Test
    fun `태스크 삭제 시 해당 태스크를 제외한 새로운 프로젝트를 반환한다`() {
        val targetId = "1"

        val project = KanbanProject(
            name = "프로젝트",
            tasks = listOf(
                Task(
                    id = targetId,
                    title = "태스크",
                    tags = Tags(),
                    status = Status.TODO,
                ),
            ),
        )

        val result = (project.deleteTask("1") as KanbanResult.Success).data
        assertTrue(result.tasks.all { it.id != targetId })
    }

    @Test
    fun `존재하지 않는 태스크 삭제 시 에러가 발생한다`() {
        val given = "1"

        val project = KanbanProject(
            name = "프로젝트",
            tasks = listOf(
                Task(
                    id = "2",
                    title = "태스크",
                    tags = Tags(),
                    status = Status.TODO,
                ),
            ),
        )

        val result = project.deleteTask(given)

        assert(result is KanbanResult.Failure)
    }

    @Test
    fun `태스크 수정 시 해당 태스크를 수정한 새로운 프로젝트를 반환한다`() {
        val targetId = "1"

        val project = KanbanProject(
            name = "프로젝트",
            tasks = listOf(
                Task(
                    id = targetId,
                    title = "태스크",
                    tags = Tags(),
                    status = Status.TODO,
                ),
            ),
        )
        val newTask = Task(
            id = targetId,
            title = "수정된 태스크",
            tags = Tags(),
            status = Status.TODO,
        )

        val result = (
            project.editTask(
                targetId,
                newTask,
            ) as KanbanResult.Success
            ).data
        assertEquals(result.tasks.find { it.id == targetId }?.title, "수정된 태스크")
    }

    @Test
    fun `존재하지 않는 태스크 수정 시 에러가 발생한다`() {
        val given = "1"

        val project = KanbanProject(
            name = "프로젝트",
            tasks = listOf(
                Task(
                    id = "2",
                    title = "태스크",
                    tags = Tags(),
                    status = Status.TODO,
                ),
            ),
        )
        val newTask = Task(title = "수정", tags = Tags(), status = Status.TODO)

        val result = project.editTask(given, newTask)

        assert(result is KanbanResult.Failure)
    }

    @Test
    fun `태스크 상태 변경 시 해당 태스크의 상태를 수정한 새로운 프로젝트를 반환한다`() {
        val project = KanbanProject(
            name = "프로젝트",
            tasks = listOf(
                Task(
                    id = "1",
                    title = "태스크",
                    tags = Tags(),
                    status = Status.TODO,
                    assignee = Assignee("다이노"),
                ),
            ),
        )
        val result = (project.updateStatus("1", Status.IN_PROGRESS) as KanbanResult.Success).data
        assertEquals(result.tasks.find { it.id == "1" }?.status, Status.IN_PROGRESS)
    }

    @Test
    fun `존재하지 않는 태스크의 상태 변경 시 에러가 발생한다`() {
        val given = "1"

        val project = KanbanProject(
            name = "프로젝트",
            tasks = listOf(
                Task(
                    id = "2",
                    title = "태스크",
                    tags = Tags(),
                    status = Status.TODO,
                    assignee = Assignee("다이노"),
                ),
            ),
        )
        val result = project.updateStatus(given, Status.IN_PROGRESS)
        assert(result is KanbanResult.Failure)
    }
}
