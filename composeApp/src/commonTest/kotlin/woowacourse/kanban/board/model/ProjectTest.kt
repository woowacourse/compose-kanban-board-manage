package woowacourse.kanban.board.model

import kotlinx.collections.immutable.immutableListOf
import kotlinx.collections.immutable.toImmutableList
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import woowacourse.kanban.board.fixture.TaskCardDataFixture
import woowacourse.kanban.board.model.project.Project
import woowacourse.kanban.board.model.taskcard.Status
import woowacourse.kanban.board.model.taskcard.TaskCardData
import kotlin.test.Test
import kotlin.test.assertTrue

class ProjectTest {
    private lateinit var project: Project
    private lateinit var todoTask: TaskCardData
    private lateinit var progressTask: TaskCardData
    private lateinit var reviewTask: TaskCardData
    private lateinit var doneTask: TaskCardData

    @Before
    fun setUp() {
        project = Project(
            title = "테스트 프로젝트",
            initialTasks = listOf<TaskCardData>().toImmutableList(),
        )
        todoTask = TaskCardDataFixture.create(status = Status.TODO)
        progressTask = TaskCardDataFixture.create(status = Status.PROGRESS)
        reviewTask = TaskCardDataFixture.create(status = Status.REVIEW)
        doneTask = TaskCardDataFixture.create(status = Status.DONE)
    }

    @Test
    fun `입력한 id를 가진 태스크 카드가 변경값으로 입력한 status로 변경된다`() {
        val taskCardData = TaskCardDataFixture.create(
            id = "테스트",
            status = Status.TODO,
        )
        project.addTask(taskCardData)
        project.tryMoveTaskStatus("테스트", Status.PROGRESS)
        assertThat(project.todoTasks.size).isEqualTo(0)
        assertThat(project.progressTasks.size).isEqualTo(1)
    }

    @Test
    fun `찾고자 하는 태스크 카드의 id값을 넣었을 때 해당 id 값을 가진 TaskCardData를 찾을 수 있다`() {
        val task = TaskCardDataFixture.create(
            id = "테스트",
            status = Status.TODO,
        )
        val project = Project(
            title = "테스트 프로젝트",
            initialTasks = immutableListOf<TaskCardData>(
                task,
            ),
        )
        assertTrue { project.findTaskById("테스트") == task }
    }

    @Test
    fun `Todo TaskCardData를 추가하면 todoTasks에 저장된다`() {
        project.addTask(todoTask)
        assertThat(project.todoTasks).contains(todoTask)
    }

    @Test
    fun `Progress TaskCardData를 추가하면 progressTasks에 저장된다`() {
        project.addTask(progressTask)
        assertThat(project.progressTasks).contains(progressTask)
    }

    @Test
    fun `Review TaskCardData를 추가하면 reviewTasks에 저장된다`() {
        project.addTask(reviewTask)
        assertThat(project.reviewTasks).contains(reviewTask)
    }

    @Test
    fun `Done TaskCardData를 추가하면 doneTasks에 저장된다`() {
        project.addTask(doneTask)
        assertThat(project.doneTasks).contains(doneTask)
    }

    @Test
    fun `4개 업무 중 2개를 완료했을 때 완료율은 50%로 계산된다`() {
        project.addTask(TaskCardDataFixture.create(status = Status.DONE))
        project.addTask(TaskCardDataFixture.create(status = Status.DONE))
        project.addTask(TaskCardDataFixture.create(status = Status.REVIEW))
        project.addTask(TaskCardDataFixture.create(status = Status.PROGRESS))

        assertThat(project.calculateDoneRate()).isEqualTo(0.50f)
    }

    @Test
    fun `진행 상태가 모두 다른 4개 업무가 등록되면 totalTasks는 4으로 계산된다`() {
        project.addTask(todoTask)
        project.addTask(doneTask)
        project.addTask(progressTask)
        project.addTask(reviewTask)
        assertThat(project.allTasksCount).isEqualTo(4)
    }

    @Test
    fun `등록된 업무가 0개일 때 완료율은 0%으로 계산된다`() {
        assertThat(project.calculateDoneRate()).isEqualTo(0.0f)
    }

    @Test
    fun `3개 업무 중 0개를 완료했을 때 완료율은 0%으로 계산된다`() {
        project.addTask(progressTask)
        project.addTask(reviewTask)
        project.addTask(todoTask)

        assertThat(project.calculateDoneRate()).isEqualTo(0.0f)
    }

    @Test
    fun `To do 상태의 task를 삭제할 수 있다`() {
        project.addTask(todoTask)
        assertThat(project.todoTasks).contains(todoTask)
        project.deleteTaskById(todoTask.id)
        assertThat(project.todoTasks).doesNotContain(todoTask)
    }

    @Test
    fun `In Progress 상태의 task를 삭제할 수 있다`() {
        project.addTask(progressTask)
        assertThat(project.progressTasks).contains(progressTask)
        project.deleteTaskById(progressTask.id)
        assertThat(project.progressTasks).doesNotContain(progressTask)
    }

    @Test
    fun `Review 상태의 task는 삭제를 시도해도 삭제를 할 수 없다`() {
        val task = TaskCardDataFixture.create(status = Status.REVIEW)
        project.addTask(task)
        assertThat(project.reviewTasks).contains(task)
        project.deleteTaskById(task.id)
        assertThat(project.reviewTasks).contains(task)
    }

    @Test
    fun `Done 상태의 task는 삭제를 시도해도 삭제를 할 수 없다`() {
        val task = TaskCardDataFixture.create(status = Status.DONE)
        project.addTask(task)
        assertThat(project.doneTasks).contains(task)
        project.deleteTaskById(task.id)
        assertThat(project.doneTasks).contains(task)
    }
}
