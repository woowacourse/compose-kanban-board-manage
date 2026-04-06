package woowacourse.kanban.board.model

import kanbanboard.composeapp.generated.resources.Res
import kanbanboard.composeapp.generated.resources.profile
import kotlinx.collections.immutable.toImmutableList
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import woowacourse.kanban.board.fixture.TaskCardDataFixture
import woowacourse.kanban.board.model.project.MoveResult
import woowacourse.kanban.board.model.taskcard.Assignee
import woowacourse.kanban.board.model.taskcard.Status
import woowacourse.kanban.board.model.taskcard.TaskCardData
import woowacourse.kanban.board.model.taskcard.TaskDescription
import woowacourse.kanban.board.model.taskcard.TaskTag
import woowacourse.kanban.board.model.taskcard.TaskTags
import woowacourse.kanban.board.model.taskcard.TaskTitle
import kotlin.test.Test

class TaskCardDataTest {

    private lateinit var todoTask: TaskCardData

    @Before
    fun setUp() {
        todoTask = TaskCardDataFixture.create(status = Status.TODO)
    }

    @Test
    fun `TaskCardData의 taskTitle을 수정할 수 있다`() {
        val updateTaskCardData = TaskCardData(
            taskTitle = TaskTitle("수정수정"),
            taskDescription = todoTask.taskDescription,
            taskTags = todoTask.taskTags,
            status = todoTask.status,
            assignee = todoTask.assignee,
        )
        val updatedData = todoTask.updateData(
            updateTaskCardData = updateTaskCardData,
        )
        assertThat(updatedData.taskTitle).isEqualTo(TaskTitle("수정수정"))
    }

    @Test
    fun `TaskCardData의 taskDescription을 수정할 수 있다`() {
        val updateTaskCardData = TaskCardData(
            taskTitle = todoTask.taskTitle,
            taskDescription = TaskDescription("설명수정했어요"),
            taskTags = todoTask.taskTags,
            status = todoTask.status,
            assignee = todoTask.assignee,
        )
        val updatedData = todoTask.updateData(
            updateTaskCardData = updateTaskCardData,
        )
        assertThat(updatedData.taskDescription).isEqualTo(TaskDescription("설명수정했어요"))
    }

    @Test
    fun `TaskCardData의 taskTags를 수정할 수 있다`() {
        val updatedTags = TaskTags(listOf(TaskTag("d")).toImmutableList())
        val updateTaskCardData = TaskCardData(
            taskTitle = todoTask.taskTitle,
            taskDescription = todoTask.taskDescription,
            taskTags = updatedTags,
            status = todoTask.status,
            assignee = todoTask.assignee,
        )
        val updatedData = todoTask.updateData(
            updateTaskCardData = updateTaskCardData,
        )
        assertThat(updatedData.taskTags).isEqualTo(updatedTags)
    }

    @Test
    fun `TaskCardData의 status를 수정할 수 있다`() {
        val updateTaskCardData = TaskCardData(
            taskTitle = todoTask.taskTitle,
            taskDescription = todoTask.taskDescription,
            taskTags = todoTask.taskTags,
            status = Status.PROGRESS,
            assignee = todoTask.assignee,
        )
        val updatedData = todoTask.updateData(
            updateTaskCardData = updateTaskCardData,
        )
        assertThat(updatedData.status).isEqualTo(Status.PROGRESS)
    }

    @Test
    fun `TaskCardData의 assignee를 수정할 수 있다`() {
        val newAssignee = Assignee("호이", Res.drawable.profile)
        val updateTaskCardData = TaskCardData(
            taskTitle = todoTask.taskTitle,
            taskDescription = TaskDescription("설명수정했어요"),
            taskTags = todoTask.taskTags,
            status = todoTask.status,
            assignee = newAssignee,
        )
        val updatedData = todoTask.updateData(
            updateTaskCardData = updateTaskCardData,
        )
        assertThat(updatedData.assignee).isEqualTo(newAssignee)
    }

    @Test
    fun `담당자가 존재하지 않는 TODO 태스크를 IN PROGRESS로 이동 가능한지 확인하면 MoveResult가 NO ASSIGNEE를 반환한다`() {
        val task = TaskCardDataFixture.create(status = Status.TODO, assigneeName = null)
        val moveResult = task.isTaskStatusUpdateAvailable(Status.PROGRESS)
        assertThat(moveResult).isEqualTo(MoveResult.NO_ASSIGNEE)
    }

    @Test
    fun `담당자가 존재하지 않는 TODO 태스크를 REVIEW로 이동 가능한지 확인하면 MoveResult가 NO ASSIGNEE를 반환한다`() {
        val task = TaskCardDataFixture.create(status = Status.TODO, assigneeName = null)
        val moveResult = task.isTaskStatusUpdateAvailable(Status.REVIEW)
        assertThat(moveResult).isEqualTo(MoveResult.NO_ASSIGNEE)
    }

    @Test
    fun `담당자가 존재하지 않는 TODO 태스크를 DONE으로 이동 가능한지 확인하면 MoveResult가 NO ASSIGNEE를 반환한다`() {
        val task = TaskCardDataFixture.create(status = Status.TODO, assigneeName = null)
        val moveResult = task.isTaskStatusUpdateAvailable(Status.DONE)
        assertThat(moveResult).isEqualTo(MoveResult.NO_ASSIGNEE)
    }

    @Test
    fun `담당자가 존재하는 TODO 태스크를 IN PROGRESS로 이동 가능한지 확인하면 MoveResult가 SUCCESS를 반환한다`() {
        val task = TaskCardDataFixture.create(status = Status.TODO)
        val moveResult = task.isTaskStatusUpdateAvailable(Status.PROGRESS)
        assertThat(moveResult).isEqualTo(MoveResult.SUCCESS)
    }

    @Test
    fun `담당자가 존재하는 TODO 태스크를 REVIEW로 이동 가능한지 확인하면 MoveResult가 INVALID_MOVE를 반환한다`() {
        val task = TaskCardDataFixture.create(status = Status.TODO)
        val moveResult = task.isTaskStatusUpdateAvailable(Status.REVIEW)
        assertThat(moveResult).isEqualTo(MoveResult.INVALID_MOVE)
    }

    @Test
    fun `담당자가 존재하는 TODO 태스크를 DONE으로 이동 가능한지 확인하면 MoveResult가 INVALID_MOVE를 반환한다`() {
        val task = TaskCardDataFixture.create(status = Status.TODO)
        val moveResult = task.isTaskStatusUpdateAvailable(Status.DONE)
        assertThat(moveResult).isEqualTo(MoveResult.INVALID_MOVE)
    }

    @Test
    fun `담당자가 존재하는 INPROGRESS 태스크를 TODO로 이동 가능한지 확인하면 MoveResult가 SUCCESS를 반환한다`() {
        val task = TaskCardDataFixture.create(status = Status.PROGRESS)
        val moveResult = task.isTaskStatusUpdateAvailable(Status.TODO)
        assertThat(moveResult).isEqualTo(MoveResult.SUCCESS)
    }

    @Test
    fun `담당자가 존재하는 INPROGRESS 태스크를 REVIEW로 이동 가능한지 확인하면 MoveResult가 SUCCESS를 반환한다`() {
        val task = TaskCardDataFixture.create(status = Status.PROGRESS)
        val moveResult = task.isTaskStatusUpdateAvailable(Status.REVIEW)
        assertThat(moveResult).isEqualTo(MoveResult.SUCCESS)
    }

    @Test
    fun `담당자가 존재하는 INPROGRESS 태스크를 DONE로 이동 가능한지 확인하면 MoveResult가 INVALID_MOVE를 반환한다`() {
        val task = TaskCardDataFixture.create(status = Status.PROGRESS)
        val moveResult = task.isTaskStatusUpdateAvailable(Status.DONE)
        assertThat(moveResult).isEqualTo(MoveResult.INVALID_MOVE)
    }

    @Test
    fun `담당자가 존재하는 REVIEW 태스크를 TODO로 이동 가능한지 확인하면 MoveResult가 INVALID_MOVE를 반환한다`() {
        val task = TaskCardDataFixture.create(status = Status.REVIEW)
        val moveResult = task.isTaskStatusUpdateAvailable(Status.TODO)
        assertThat(moveResult).isEqualTo(MoveResult.INVALID_MOVE)
    }

    @Test
    fun `담당자가 존재하는 REVIEW 태스크를 PROGRESS로 이동 가능한지 확인하면 MoveResult가 SUCCESS를 반환한다`() {
        val task = TaskCardDataFixture.create(status = Status.REVIEW)
        val moveResult = task.isTaskStatusUpdateAvailable(Status.PROGRESS)
        assertThat(moveResult).isEqualTo(MoveResult.SUCCESS)
    }

    @Test
    fun `담당자가 존재하는 REVIEW 태스크를 DONE으로 이동 가능한지 확인하면 MoveResult가 SUCCESS를 반환한다`() {
        val task = TaskCardDataFixture.create(status = Status.REVIEW)
        val moveResult = task.isTaskStatusUpdateAvailable(Status.DONE)
        assertThat(moveResult).isEqualTo(MoveResult.SUCCESS)
    }

    @Test
    fun `담당자가 존재하는 DONE 태스크를 TODO로 이동 가능한지 확인하면 MoveResult가 SUCCESS를 반환한다`() {
        val task = TaskCardDataFixture.create(status = Status.DONE)
        val moveResult = task.isTaskStatusUpdateAvailable(Status.TODO)
        assertThat(moveResult).isEqualTo(MoveResult.SUCCESS)
    }

    @Test
    fun `담당자가 존재하는 DONE 태스크를 PROGRESS로 이동 가능한지 확인하면 MoveResult가 INVALID_MOVE를 반환한다`() {
        val task = TaskCardDataFixture.create(status = Status.DONE)
        val moveResult = task.isTaskStatusUpdateAvailable(Status.PROGRESS)
        assertThat(moveResult).isEqualTo(MoveResult.INVALID_MOVE)
    }

    @Test
    fun `담당자가 존재하는 DONE 태스크를 REVIEW로 이동 가능한지 확인하면 MoveResult가 INVALID_MOVE를 반환한다`() {
        val task = TaskCardDataFixture.create(status = Status.DONE)
        val moveResult = task.isTaskStatusUpdateAvailable(Status.REVIEW)
        assertThat(moveResult).isEqualTo(MoveResult.INVALID_MOVE)
    }

    @Test
    fun `담당자가 존재하지 않는 TODO 태스크를 IN PROGRESS로 이동할 수 없다`() {
        val task = TaskCardDataFixture.create(status = Status.TODO, assigneeName = null)
        val moveResult = task.isTaskStatusUpdateAvailable(Status.PROGRESS)
        val updatedTask = task.updateTaskStatus(
            moveResult = moveResult,
            targetStatus = Status.PROGRESS,
        )
        assertThat(updatedTask.status).isEqualTo(Status.TODO)
    }

    @Test
    fun `담당자가 존재하지 않는 TODO 태스크를 REVIEW으로 이동할 수 없다`() {
        val task = TaskCardDataFixture.create(status = Status.TODO, assigneeName = null)
        val moveResult = task.isTaskStatusUpdateAvailable(Status.REVIEW)
        val updatedTask = task.updateTaskStatus(
            moveResult = moveResult,
            targetStatus = Status.REVIEW,
        )
        assertThat(updatedTask.status).isEqualTo(Status.TODO)
    }

    @Test
    fun `담당자가 존재하지 않는 TODO 태스크를 DONE으로 이동할 수 없다`() {
        val task = TaskCardDataFixture.create(status = Status.TODO, assigneeName = null)
        val moveResult = task.isTaskStatusUpdateAvailable(Status.DONE)
        val updatedTask = task.updateTaskStatus(
            moveResult = moveResult,
            targetStatus = Status.DONE,
        )
        assertThat(updatedTask.status).isEqualTo(Status.TODO)
    }

    @Test
    fun `담당자가 존재하는 TODO 태스크를 IN PROGRESS로 이동할 수 있다 `() {
        val task = TaskCardDataFixture.create(status = Status.TODO)
        val moveResult = task.isTaskStatusUpdateAvailable(Status.PROGRESS)
        val updatedTask = task.updateTaskStatus(
            moveResult = moveResult,
            targetStatus = Status.PROGRESS,
        )
        assertThat(updatedTask.status).isEqualTo(Status.PROGRESS)
    }

    @Test
    fun `담당자가 존재하는 TODO 태스크를 REVIEW로 이동할 수 없다`() {
        val task = TaskCardDataFixture.create(status = Status.TODO)
        val moveResult = task.isTaskStatusUpdateAvailable(Status.REVIEW)
        val updatedTask = task.updateTaskStatus(
            moveResult = moveResult,
            targetStatus = Status.REVIEW,
        )
        assertThat(updatedTask.status).isEqualTo(Status.TODO)
    }

    @Test
    fun `담당자가 존재하는 TODO 태스크를 DONE으로 이동할 수 없다`() {
        val task = TaskCardDataFixture.create(status = Status.TODO)
        val moveResult = task.isTaskStatusUpdateAvailable(Status.DONE)
        val updatedTask = task.updateTaskStatus(
            moveResult = moveResult,
            targetStatus = Status.DONE,
        )
        assertThat(updatedTask.status).isEqualTo(Status.TODO)
    }

    @Test
    fun `담당자가 존재하는 PROGRESS 태스크를 TODO로 이동할 수 있다`() {
        val task = TaskCardDataFixture.create(status = Status.PROGRESS)
        val moveResult = task.isTaskStatusUpdateAvailable(Status.TODO)
        val updatedTask = task.updateTaskStatus(
            moveResult = moveResult,
            targetStatus = Status.TODO,
        )
        assertThat(updatedTask.status).isEqualTo(Status.TODO)
    }

    @Test
    fun `담당자가 존재하는 PROGRESS 태스크를 REVIEW로 이동할 수 있다`() {
        val task = TaskCardDataFixture.create(status = Status.PROGRESS)
        val moveResult = task.isTaskStatusUpdateAvailable(Status.REVIEW)
        val updatedTask = task.updateTaskStatus(
            moveResult = moveResult,
            targetStatus = Status.REVIEW,
        )
        assertThat(updatedTask.status).isEqualTo(Status.REVIEW)
    }

    @Test
    fun `담당자가 존재하는 PROGRESS 태스크를 DONE으로 이동할 수 없다`() {
        val task = TaskCardDataFixture.create(status = Status.PROGRESS)
        val moveResult = task.isTaskStatusUpdateAvailable(Status.DONE)
        val updatedTask = task.updateTaskStatus(
            moveResult = moveResult,
            targetStatus = Status.DONE,
        )
        assertThat(updatedTask.status).isEqualTo(Status.PROGRESS)
    }

    @Test
    fun `담당자가 존재하는 REVIEW 태스크를 DONE으로 이동할 수 있다`() {
        val task = TaskCardDataFixture.create(status = Status.REVIEW)
        val moveResult = task.isTaskStatusUpdateAvailable(Status.DONE)
        val updatedTask = task.updateTaskStatus(
            moveResult = moveResult,
            targetStatus = Status.DONE,
        )
        assertThat(updatedTask.status).isEqualTo(Status.DONE)
    }

    @Test
    fun `담당자가 존재하는 REVIEW 태스크를 PROGRESS로 이동할 수 있다`() {
        val task = TaskCardDataFixture.create(status = Status.REVIEW)
        val moveResult = task.isTaskStatusUpdateAvailable(Status.PROGRESS)
        val updatedTask = task.updateTaskStatus(
            moveResult = moveResult,
            targetStatus = Status.PROGRESS,
        )
        assertThat(updatedTask.status).isEqualTo(Status.PROGRESS)
    }

    @Test
    fun `담당자가 존재하는 REVIEW 태스크를 TODO로 이동할 수 없다`() {
        val task = TaskCardDataFixture.create(status = Status.REVIEW)
        val moveResult = task.isTaskStatusUpdateAvailable(Status.TODO)
        val updatedTask = task.updateTaskStatus(
            moveResult = moveResult,
            targetStatus = Status.TODO,
        )
        assertThat(updatedTask.status).isEqualTo(Status.REVIEW)
    }

    @Test
    fun `담당자가 존재하는 DONE 태스크를 TODO로 이동할 수 있다`() {
        val task = TaskCardDataFixture.create(status = Status.DONE)
        val moveResult = task.isTaskStatusUpdateAvailable(Status.TODO)
        val updatedTask = task.updateTaskStatus(
            moveResult = moveResult,
            targetStatus = Status.TODO,
        )
        assertThat(updatedTask.status).isEqualTo(Status.TODO)
    }

    @Test
    fun `담당자가 존재하는 DONE 태스크를 PROGRESS로 이동할 수 없다`() {
        val task = TaskCardDataFixture.create(status = Status.DONE)
        val moveResult = task.isTaskStatusUpdateAvailable(Status.PROGRESS)
        val updatedTask = task.updateTaskStatus(
            moveResult = moveResult,
            targetStatus = Status.PROGRESS,
        )
        assertThat(updatedTask.status).isEqualTo(Status.DONE)
    }

    @Test
    fun `담당자가 존재하는 DONE 태스크를 REVIEW로 이동할 수 없다`() {
        val task = TaskCardDataFixture.create(status = Status.DONE)
        val moveResult = task.isTaskStatusUpdateAvailable(Status.REVIEW)
        val updatedTask = task.updateTaskStatus(
            moveResult = moveResult,
            targetStatus = Status.REVIEW,
        )
        assertThat(updatedTask.status).isEqualTo(Status.DONE)
    }
}
