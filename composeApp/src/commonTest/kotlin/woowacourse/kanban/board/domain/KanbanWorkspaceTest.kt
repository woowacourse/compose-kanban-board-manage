package woowacourse.kanban.board.domain

import kotlin.test.Test
import woowacourse.kanban.board.domain.model.Assignee
import woowacourse.kanban.board.domain.model.KanbanProject
import woowacourse.kanban.board.domain.model.KanbanResult
import woowacourse.kanban.board.domain.model.KanbanWorkspace
import woowacourse.kanban.board.domain.model.Status
import woowacourse.kanban.board.domain.model.Tags
import woowacourse.kanban.board.domain.model.Task

class KanbanWorkspaceTest {

    @Test
    fun `태스크 추가 성공 시 Result Success를 반환한다`() {
        val workspace = KanbanWorkspace(mutableListOf(KanbanProject("1", "프로젝트")))

        val result = workspace.addTask("타이틀", "내용", emptyList(), null, Status.TODO, "1")

        assert(result is KanbanResult.Success)
    }

    @Test
    fun `태스트 추가 성공 시 프로젝트 리스트에 반영된다`() {
        val workspace = KanbanWorkspace(mutableListOf(KanbanProject("1", "프로젝트")))
        workspace.addTask("타이틀", "내용", emptyList(), null, Status.TODO, "1")

        assert(workspace.projectTasks.find { it.id == "1" }!!.tasks.size == 1)
    }

    @Test
    fun `태스크 추가 실패 시 Result Failure를 반환한다`() {
        val workspace = KanbanWorkspace(mutableListOf(KanbanProject("1", "프로젝트")))

        val result = workspace.addTask("타이틀", "내용", emptyList(), null, Status.TODO, "2")

        assert(result is KanbanResult.Failure)
    }

    @Test
    fun `태스트 추가 실패 시 프로젝트 리스트에 반영되지 않는다`() {
        val workspace = KanbanWorkspace(mutableListOf(KanbanProject("1", "프로젝트")))

        workspace.addTask("타이틀", "내용", emptyList(), null, Status.TODO, "2")

        assert(workspace.projectTasks.find { it.id == "1" }!!.tasks.isEmpty())
    }

    @Test
    fun `태스크 삭제 성공 시 Result Success를 반환한다`() {
        val target = Task("1", "태스크", tags = Tags(), status = Status.TODO)
        val workspace =
            KanbanWorkspace(mutableListOf(KanbanProject("1", "프로젝트", listOf(target))))

        val result = workspace.deleteTask("1", target)

        assert(result is KanbanResult.Success)
    }

    @Test
    fun `태스크 삭제 성공 시 프로젝트 리스트에 반영된다`() {
        val target = Task("1", "태스크", tags = Tags(), status = Status.TODO)
        val workspace =
            KanbanWorkspace(mutableListOf(KanbanProject("1", "프로젝트", listOf(target))))

        workspace.deleteTask("1", target)

        assert(workspace.projectTasks.find { it.id == "1" }!!.tasks.isEmpty())
    }

    @Test
    fun `태스크 삭제 실패 시 Result Failure를 반환한다`() {
        val target = Task("1", "태스크", tags = Tags(), status = Status.TODO)
        val workspace =
            KanbanWorkspace(mutableListOf(KanbanProject("1", "프로젝트", listOf(target))))

        val result = workspace.deleteTask("2", target)

        assert(result is KanbanResult.Failure)
    }

    @Test
    fun `태스트 삭제 실패 시 프로젝트 리스트에 반영되지 않는다`() {
        val target = Task("1", "태스크", tags = Tags(), status = Status.TODO)
        val workspace =
            KanbanWorkspace(mutableListOf(KanbanProject("1", "프로젝트", listOf(target))))

        workspace.deleteTask("2", target)
        assert(workspace.projectTasks.find { it.id == "1" }!!.tasks.size == 1)
    }

    @Test
    fun `태스크 수정 성공 시 Result Success를 반환한다`() {
        val target = Task("1", "태스크", tags = Tags(), status = Status.TODO)
        val workspace =
            KanbanWorkspace(mutableListOf(KanbanProject("1", "프로젝트", listOf(target))))

        val result = workspace.editTask("1", target, "수정", "내용", emptyList(), null, Status.TODO)
        assert(result is KanbanResult.Success)
    }

    @Test
    fun `태스크 수정 성공 시 프로젝트 리스트에 반영된다`() {
        val target = Task("1", "태스크", tags = Tags(), status = Status.TODO)
        val workspace =
            KanbanWorkspace(mutableListOf(KanbanProject("1", "프로젝트", listOf(target))))

        workspace.editTask("1", target, "수정", "내용", emptyList(), null, Status.TODO)

        assert(workspace.projectTasks.find { it.id == "1" }!!.tasks.find { it.id == "1" }!!.title == "수정")
    }

    @Test
    fun `태스크 수정 실패 시 Result Failure를 반환한다`() {
        val target = Task("1", "태스크", tags = Tags(), status = Status.TODO)
        val workspace =
            KanbanWorkspace(mutableListOf(KanbanProject("1", "프로젝트", listOf(target))))

        val result = workspace.editTask("2", target, "수정", "내용", emptyList(), null, Status.TODO)

        assert(result is KanbanResult.Failure)
    }

    @Test
    fun `태스크 수정 실패 시 프로젝트 리스트에 반영되지 않는다`() {
        val target = Task("1", "태스크", tags = Tags(), status = Status.TODO)
        val workspace =
            KanbanWorkspace(mutableListOf(KanbanProject("1", "프로젝트", listOf(target))))

        workspace.editTask("2", target, "수정", "내용", emptyList(), null, Status.TODO)

        assert(workspace.projectTasks.find { it.id == "1" }!!.tasks.find { it.id == "1" }!!.title == "태스크")
    }

    @Test
    fun `태스크 상태 변환 성공 시 Result Success를 반환한다`() {
        val target = Task("1", "태스크", tags = Tags(), status = Status.TODO, assignee = Assignee("assignee"))
        val workspace =
            KanbanWorkspace(mutableListOf(KanbanProject("1", "프로젝트", listOf(target))))

        val result = workspace.updateTaskStatus("1", target, Status.IN_PROGRESS)

        assert(result is KanbanResult.Success)
    }

    @Test
    fun `태스크 상태 변환 성공 시 프로젝트 리스트에 반영된다`() {
        val target = Task("1", "태스크", tags = Tags(), status = Status.TODO, assignee = Assignee("assignee"))
        val workspace =
            KanbanWorkspace(mutableListOf(KanbanProject("1", "프로젝트", listOf(target))))

        workspace.updateTaskStatus("1", target, Status.IN_PROGRESS)

        assert(workspace.projectTasks.find { it.id == "1" }!!.tasks.find { it.id == "1" }!!.status == Status.IN_PROGRESS)
    }

    @Test
    fun `태스크 상태 변환 실패 시 Result Failure를 반환한다`() {
        val target = Task("1", "태스크", tags = Tags(), status = Status.TODO, assignee = Assignee("assignee"))
        val workspace =
            KanbanWorkspace(mutableListOf(KanbanProject("1", "프로젝트", listOf(target))))

        val result = workspace.updateTaskStatus("2", target, Status.IN_PROGRESS)

        assert(result is KanbanResult.Failure)
    }

    @Test
    fun `태스크 상태 변환 실패 시 프로젝트 리스트에 반영되지 않는다`() {
        val target = Task("1", "태스크", tags = Tags(), status = Status.TODO, assignee = Assignee("assignee"))
        val workspace =
            KanbanWorkspace(mutableListOf(KanbanProject("1", "프로젝트", listOf(target))))

        workspace.updateTaskStatus("2", target, Status.IN_PROGRESS)

        assert(workspace.projectTasks.find { it.id == "1" }!!.tasks.find { it.id == "1" }!!.status == Status.TODO)
    }
}
