package woowacourse.kanban.board.component.modal.action

import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import org.assertj.core.api.Assertions.assertThat
import woowacourse.kanban.board.component.modal.state.ModalState
import woowacourse.kanban.board.component.util.ComponentText
import woowacourse.kanban.board.model.taskcard.Description
import woowacourse.kanban.board.model.taskcard.Profile
import woowacourse.kanban.board.model.taskcard.Status
import woowacourse.kanban.board.model.taskcard.Tag
import woowacourse.kanban.board.model.taskcard.Tags
import woowacourse.kanban.board.model.taskcard.TaskCard
import woowacourse.kanban.board.model.taskcard.Title

class ModalButtonActionFactoryTest {
    private lateinit var profiles: ImmutableList<Profile>

    @BeforeTest
    fun setUp() {
        profiles = listOf(
            Profile("다이노"),
            Profile("페임스"),
        ).toImmutableList()
    }

    @Test
    fun `상태 변경 액션은 전이할 수 없는 상태 변경을 거절한다`() {
        var modalState = ModalState(profiles, createTask(status = Status.DONE))
        var snackbarMessage = ""

        createFactory(
            modalState = modalState,
            initialTask = createTask(status = Status.DONE),
            onModalStateChange = { modalState = it },
            onShowSnackbar = { snackbarMessage = it },
        ).createStatusChangeAction(Status.REVIEW).execute()

        assertThat(snackbarMessage).isEqualTo(ComponentText.BOARD_TASK_INVALID_STATUS_SNACKBAR)
        assertThat(modalState.status).isEqualTo(Status.DONE)
    }

    @Test
    fun `상태 변경 액션은 담당자가 필요한 상태 변경 전에 담당자를 검증한다`() {
        val initialTask = createTask(status = Status.TODO, profile = Profile.NONE)
        var modalState = ModalState(profiles, initialTask)
        var snackbarMessage = ""

        createFactory(
            modalState = modalState,
            initialTask = initialTask,
            onModalStateChange = { modalState = it },
            onShowSnackbar = { snackbarMessage = it },
        ).createStatusChangeAction(Status.PROGRESS).execute()

        assertThat(snackbarMessage).isEqualTo(ComponentText.BOARD_TASK_REQUIRE_PROFILE_SNACKBAR)
        assertThat(modalState.status).isEqualTo(Status.TODO)
    }

    @Test
    fun `상태 변경 액션은 가능한 상태 변경이면 모달 상태를 변경한다`() {
        val initialTask = createTask(status = Status.TODO)
        var modalState = ModalState(profiles, initialTask)

        createFactory(
            modalState = modalState,
            initialTask = initialTask,
            onModalStateChange = { modalState = it },
        ).createStatusChangeAction(Status.PROGRESS).execute()

        assertThat(modalState.status).isEqualTo(Status.PROGRESS)
    }

    @Test
    fun `생성 액션은 입력이 유효할 때만 태스크를 생성한다`() {
        var modalState = ModalState(profiles, null)
        var createdTask: TaskCard? = null

        createFactory(
            modalState = modalState,
            initialTask = null,
            onModalStateChange = { modalState = it },
            onCreateTask = { createdTask = it },
        ).createTaskCreateAction().execute()

        assertThat(createdTask).isNull()

        modalState = modalState.copy(title = "업무")

        createFactory(
            modalState = modalState,
            initialTask = null,
            onModalStateChange = { modalState = it },
            onCreateTask = { createdTask = it },
        ).createTaskCreateAction().execute()

        assertThat(createdTask?.title?.value).isEqualTo("업무")
    }

    @Test
    fun `수정 액션은 입력이 유효할 때 기존 태스크를 수정한다`() {
        val initialTask = createTask(title = "기존 업무")
        var modalState = ModalState(profiles, initialTask).copy(
            title = "수정된 업무",
            description = "수정된 설명",
            status = Status.PROGRESS,
        )
        var updatedTaskId: String? = null
        var updatedTask: TaskCard? = null

        createFactory(
            modalState = modalState,
            initialTask = initialTask,
            onModalStateChange = { modalState = it },
            onUpdateTask = { id, task ->
                updatedTaskId = id
                updatedTask = task
            },
        ).createTaskModifyAction().execute()

        assertThat(updatedTaskId).isEqualTo(initialTask.id)
        assertThat(updatedTask?.title?.value).isEqualTo("수정된 업무")
        assertThat(updatedTask?.description?.value).isEqualTo("수정된 설명")
        assertThat(updatedTask?.status).isEqualTo(Status.PROGRESS)
    }

    @Test
    fun `삭제 액션은 삭제 불가능한 상태를 거절한다`() {
        val initialTask = createTask(status = Status.REVIEW)
        var modalState = ModalState(profiles, initialTask)
        var snackbarMessage = ""
        var deletedTaskId: String? = null

        createFactory(
            modalState = modalState,
            initialTask = initialTask,
            onModalStateChange = { modalState = it },
            onShowSnackbar = { snackbarMessage = it },
            onDeleteTask = { deletedTaskId = it },
        ).createTaskDeleteAction().execute()

        assertThat(snackbarMessage).isEqualTo(ComponentText.BOARD_TASK_DELETE_DENIED_SNACKBAR)
        assertThat(deletedTaskId).isNull()
    }

    @Test
    fun `삭제 액션은 삭제 가능한 상태면 태스크 삭제를 요청한다`() {
        val initialTask = createTask(status = Status.TODO)
        var modalState = ModalState(profiles, initialTask)
        var deletedTaskId: String? = null

        createFactory(
            modalState = modalState,
            initialTask = initialTask,
            onModalStateChange = { modalState = it },
            onDeleteTask = { deletedTaskId = it },
        ).createTaskDeleteAction().execute()

        assertThat(deletedTaskId).isEqualTo(initialTask.id)
    }

    private fun createFactory(
        modalState: ModalState,
        initialTask: TaskCard?,
        onModalStateChange: (ModalState) -> Unit = {},
        onShowSnackbar: (String) -> Unit = {},
        onCreateTask: (TaskCard) -> Unit = {},
        onUpdateTask: (String, TaskCard) -> Unit = { _, _ -> },
        onDeleteTask: (String) -> Unit = {},
    ): ModalButtonActionFactory {
        return ModalButtonActionFactory(
            modalState = modalState,
            initialTask = initialTask,
            onModalStateChange = onModalStateChange,
            buildTaskCard = { id ->
                TaskCard(
                    id = id ?: "created-task",
                    title = Title(modalState.title),
                    description = Description(modalState.description),
                    tags = Tags(Tag.parseAll(modalState.tags).toImmutableList()),
                    status = modalState.status,
                    profile = modalState.profile,
                )
            },
            onShowSnackbar = onShowSnackbar,
            onCreateTask = onCreateTask,
            onUpdateTask = onUpdateTask,
            onDeleteTask = onDeleteTask,
        )
    }

    private fun createTask(
        title: String = "업무1",
        status: Status = Status.TODO,
        profile: Profile = profiles.first(),
    ): TaskCard {
        return TaskCard(
            id = "task-$title-$status-${profile.nickname}",
            title = Title(title),
            description = Description("설명"),
            tags = Tags(listOf<Tag>().toImmutableList()),
            status = status,
            profile = profile,
        )
    }
}
