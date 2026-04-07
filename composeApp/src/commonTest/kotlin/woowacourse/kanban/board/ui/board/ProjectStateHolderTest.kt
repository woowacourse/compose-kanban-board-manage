package woowacourse.kanban.board.ui.board

import kotlin.test.Test
import kotlin.test.assertEquals
import woowacourse.kanban.board.domain.model.Assignee
import woowacourse.kanban.board.domain.model.KanbanProject
import woowacourse.kanban.board.domain.model.Status
import woowacourse.kanban.board.domain.model.Tags
import woowacourse.kanban.board.domain.model.Task

class ProjectStateHolderTest {

    @Test
    fun `태스크 추가 성공 시 프로젝트에 태스크가 추가된다`() {
        val holder = ProjectStateHolder(
            listOf(
                KanbanProject(name = "프로젝트"),
            ),
        )
        holder.addTask("title", "description", emptyList(), null, Status.TODO)

        assertEquals(holder.currentProject.tasks.size, 1)
    }

    @Test
    fun `태스크 수정 시 프로젝트에 수정된 태스크가 반영된다`() {
        val task = Task("1", "title", "description", Tags(), null, Status.TODO)
        val holder = ProjectStateHolder(
            listOf(
                KanbanProject(name = "프로젝트", tasks = listOf(task)),
            ),
        )
        holder.selectedTask = task
        holder.editTask("edit", "description", emptyList(), null, Status.TODO)

        assertEquals(holder.currentProject.tasks.first().title, "edit")
    }

    @Test
    fun `태스크 삭제 시 프로젝트에 해당 태스크가 삭제된다`() {
        val task = Task("1", "title", "description", Tags(), null, Status.TODO)
        val holder = ProjectStateHolder(
            listOf(
                KanbanProject(name = "프로젝트", tasks = listOf(task)),
            ),
        )
        holder.selectedTask = task
        holder.deleteTask()

        assertEquals(holder.currentProject.tasks.size, 0)
    }

    @Test
    fun `태스크 상태 변경 시 프로젝트에 해당 내용이 반영된다`() {
        val task = Task("1", "title", "description", Tags(), Assignee("다이노"), Status.TODO)
        val holder = ProjectStateHolder(
            listOf(
                KanbanProject(name = "프로젝트", tasks = listOf(task)),
            ),
        )
        holder.changeTaskStatus(task, Status.IN_PROGRESS)

        assertEquals(holder.currentProject.tasks.first().status, Status.IN_PROGRESS)
    }

    @Test
    fun `프로젝트 변경 시 현재 프로젝트가 변경된다`() {
        val task = Task("1", "title", "description", Tags(), Assignee("다이노"), Status.TODO)
        val holder = ProjectStateHolder(
            listOf(
                KanbanProject(id = "1", name = "프로젝트1", tasks = listOf(task)),
                KanbanProject(id = "2", name = "프로젝트2"),
            ),
        )
        holder.changeProject("2")
        assertEquals(holder.currentProjectId, "2")
    }
}
