package woowacourse.kanban.board.state

import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlinx.collections.immutable.toImmutableList
import org.assertj.core.api.Assertions.assertThat
import woowacourse.kanban.board.component.state.WorkSpaceStateHolder
import woowacourse.kanban.board.model.identifier.IdentifierGenerator
import woowacourse.kanban.board.model.project.Project
import woowacourse.kanban.board.model.project.ProjectFactory
import woowacourse.kanban.board.model.taskcard.Description
import woowacourse.kanban.board.model.taskcard.Profile
import woowacourse.kanban.board.model.taskcard.Status
import woowacourse.kanban.board.model.taskcard.Tag
import woowacourse.kanban.board.model.taskcard.Tags
import woowacourse.kanban.board.model.taskcard.TaskCard
import woowacourse.kanban.board.model.taskcard.Title
import woowacourse.kanban.board.model.workspace.WorkSpace

class WorkSpaceStateHolderTest {
    private lateinit var workSpaceStateHolder: WorkSpaceStateHolder
    private lateinit var projectFactory: ProjectFactory
    private lateinit var identifierGenerator: IdentifierGenerator

    @BeforeTest
    fun setUp() {
        identifierGenerator = FixedIdentifierGenerator()
        projectFactory = ProjectFactory(identifierGenerator)
        workSpaceStateHolder = WorkSpaceStateHolder(
            WorkSpace(
                listOf<Project>(
                    projectFactory.create("Compose1", listOf<TaskCard>().toImmutableList()),
                    projectFactory.create("Compose2", listOf<TaskCard>().toImmutableList()),
                    projectFactory.create("Compose3너무너무긴문장은말줄임표로표시합니다", listOf<TaskCard>().toImmutableList()),
                ).toImmutableList()
            )
        )
    }

    @Test
    fun `Todo TaskCard를 추가하면 todoList에 저장된다`() {
        val data = createData(Status.TODO)
        workSpaceStateHolder = workSpaceStateHolder.addTask(data)
        assertThat(workSpaceStateHolder.selectedProject?.filterTasksbyStatus(Status.TODO)).contains(data)
    }

    @Test
    fun `Progress TaskCard를 추가하면 progressList에 저장된다`() {
        val data = createData(Status.PROGRESS)
        workSpaceStateHolder = workSpaceStateHolder.addTask(data)
        assertThat(workSpaceStateHolder.selectedProject?.filterTasksbyStatus(Status.PROGRESS)).contains(data)
    }

    @Test
    fun `Done TaskCard를 추가하면 doneList에 저장된다`() {
        val data = createData(Status.DONE)
        workSpaceStateHolder = workSpaceStateHolder.addTask(data)
        assertThat(workSpaceStateHolder.selectedProject?.filterTasksbyStatus(Status.DONE)).contains(data)
    }

    @Test
    fun `프로젝트를 선택하면 선택된 프로젝트가 변경된다`() {
        val nextProject = workSpaceStateHolder.workSpace.projects[1]

        workSpaceStateHolder = workSpaceStateHolder.selectProject(nextProject)

        assertThat(workSpaceStateHolder.selectedProject?.id).isEqualTo(nextProject.id)
    }

    @Test
    fun `선택된 프로젝트의 태스크 상태를 변경할 수 있다`() {
        val task = createData(Status.TODO)
        workSpaceStateHolder = workSpaceStateHolder.addTask(task)

        workSpaceStateHolder = workSpaceStateHolder.updateTaskStatus(task.id, Status.PROGRESS)

        val foundTask = workSpaceStateHolder.selectedProject?.findTaskById(task.id)
        assertThat(foundTask?.status).isEqualTo(Status.PROGRESS)
    }

    @Test
    fun `선택된 프로젝트의 태스크를 수정할 수 있다`() {
        val task = createData(Status.TODO)
        workSpaceStateHolder = workSpaceStateHolder.addTask(task)
        val updatedTask = TaskCard(
            id = task.id,
            title = Title("수정된 업무"),
            description = Description("수정된 설명"),
            tags = Tags(value = listOf(Tag("수정")).toImmutableList()),
            status = Status.PROGRESS,
            profile = Profile("페임스")
        )

        workSpaceStateHolder = workSpaceStateHolder.updateTask(task.id, updatedTask)

        val foundTask = workSpaceStateHolder.selectedProject?.findTaskById(task.id)
        assertThat(foundTask?.title?.value).isEqualTo("수정된 업무")
        assertThat(foundTask?.description?.value).isEqualTo("수정된 설명")
        assertThat(foundTask?.status).isEqualTo(Status.PROGRESS)
        assertThat(foundTask?.profile?.nickname).isEqualTo("페임스")
    }

    @Test
    fun `선택된 프로젝트의 태스크를 삭제할 수 있다`() {
        val task = createData(Status.TODO)
        workSpaceStateHolder = workSpaceStateHolder.addTask(task)

        workSpaceStateHolder = workSpaceStateHolder.deleteTask(task.id)

        assertThat(workSpaceStateHolder.selectedProject?.findTaskById(task.id)).isNull()
    }

    @Test
    fun `4개 업무 중 2개를 완료했을 때 완료율은 50%로 계산된다`() {
        val task1 = createData(Status.DONE)
        val task2 = createData(Status.TODO)
        val task3 = createData(Status.TODO)

        workSpaceStateHolder = workSpaceStateHolder.addTask(task1)
        workSpaceStateHolder = workSpaceStateHolder.addTask(task1)
        workSpaceStateHolder = workSpaceStateHolder.addTask(task2)
        workSpaceStateHolder = workSpaceStateHolder.addTask(task3)

        assertThat(workSpaceStateHolder.selectedProject?.calculateDoneRate()).isEqualTo(0.50f)
    }

    @Test
    fun `진행 상태가 모두 다른 3개 업무가 등록되면 totalTasks는 3으로 계산된다`() {
        val task1 = createData(Status.TODO)
        val task2 = createData(Status.PROGRESS)
        val task3 = createData(Status.DONE)

        workSpaceStateHolder = workSpaceStateHolder.addTask(task1)
        workSpaceStateHolder = workSpaceStateHolder.addTask(task2)
        workSpaceStateHolder = workSpaceStateHolder.addTask(task3)

        assertThat(workSpaceStateHolder.selectedProject?.allTasksCount).isEqualTo(3)
    }

    @Test
    fun `등록된 업무가 0개일 때 완료율은 0%으로 계산된다`() {
        assertThat(workSpaceStateHolder.selectedProject?.calculateDoneRate()).isEqualTo(0.0f)
    }

    @Test
    fun `3개 업무 중 0개를 완료했을 때 완료율은 0%으로 계산된다`() {
        val task1 = createData(Status.TODO)
        val task2 = createData(Status.TODO)
        val task3 = createData(Status.TODO)

        workSpaceStateHolder = workSpaceStateHolder.addTask(task1)
        workSpaceStateHolder = workSpaceStateHolder.addTask(task2)
        workSpaceStateHolder = workSpaceStateHolder.addTask(task3)

        assertThat(workSpaceStateHolder.selectedProject?.calculateDoneRate()).isEqualTo(0.0f)
    }

    private fun createData(status: Status): TaskCard {
        return TaskCard(
            id = identifierGenerator.next(),
            title = Title(value = "업무1"),
            description = Description(""),
            tags = Tags(value = listOf(Tag("컴포넌트")).toImmutableList()),
            status = status,
            profile = Profile("다이노")
        )
    }

    private class FixedIdentifierGenerator : IdentifierGenerator {
        private var sequence = 0

        override fun next(): String {
            sequence += 1
            return "id-$sequence"
        }
    }
}
