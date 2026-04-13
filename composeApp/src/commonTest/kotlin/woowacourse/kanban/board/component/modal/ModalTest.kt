package woowacourse.kanban.board.component.modal

import androidx.compose.ui.semantics.SemanticsActions
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.isEditable
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performSemanticsAction
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.runComposeUiTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import org.assertj.core.api.Assertions.assertThat
import woowacourse.kanban.board.component.util.ComponentText
import woowacourse.kanban.board.model.taskcard.Description
import woowacourse.kanban.board.model.taskcard.Profile
import woowacourse.kanban.board.model.taskcard.Status
import woowacourse.kanban.board.model.taskcard.Tag
import woowacourse.kanban.board.model.taskcard.Tags
import woowacourse.kanban.board.model.taskcard.TaskCard
import woowacourse.kanban.board.model.taskcard.Title

@OptIn(ExperimentalTestApi::class)
class ModalTest {

    private lateinit var profiles: ImmutableList<Profile>

    @BeforeTest
    fun setUp() {
        profiles = listOf(
            Profile("다이노"),
            Profile("페임스"),
        ).toImmutableList()
    }

    @Test
    fun `초기 상태에서 생성 버튼이 비활성화된다`() = runComposeUiTest {
        setContent {
            Modal(
                profiles = profiles,
                initialTask = null,
                onClickClose = {},
                onShowSnackbar = {},
                onCreateTask = {},
                onUpdateTask = { _, _ -> },
                onDeleteTask = {},
            )
        }

        onNodeWithText(ComponentText.CREATE_BUTTON).assertIsNotEnabled()
    }

    @Test
    fun `제목을 입력하면 생성 버튼이 활성화된다`() = runComposeUiTest {
        setContent {
            Modal(
                profiles = profiles,
                initialTask = null,
                onClickClose = {},
                onShowSnackbar = {},
                onCreateTask = {},
                onUpdateTask = { _, _ -> },
                onDeleteTask = {},
            )
        }

        onAllNodes(isEditable())[0].performTextInput("하이")
        onNodeWithText(ComponentText.CREATE_BUTTON).assertIsEnabled()
    }

    @Test
    fun `제목을 입력하고 태그에 ,,을 연속으로 입력하면 생성 버튼이 비활성화된다`() = runComposeUiTest {
        setContent {
            Modal(
                profiles = profiles,
                initialTask = null,
                onClickClose = {},
                onShowSnackbar = {},
                onCreateTask = {},
                onUpdateTask = { _, _ -> },
                onDeleteTask = {},
            )
        }

        onAllNodes(isEditable())[0].performTextInput("하이")
        onNodeWithText(ComponentText.CREATE_BUTTON).assertIsEnabled()
        onAllNodes(isEditable())[2].performTextInput("태그,,태그2")
        onNodeWithText(ComponentText.CREATE_BUTTON).assertIsNotEnabled()
    }

    @Test
    fun `제목을 입력한 뒤 모두 지우면 생성 버튼이 비활성화된다`() = runComposeUiTest {
        setContent {
            Modal(
                profiles = profiles,
                initialTask = null,
                onClickClose = {},
                onShowSnackbar = {},
                onCreateTask = {},
                onUpdateTask = { _, _ -> },
                onDeleteTask = {},
            )
        }

        onAllNodes(isEditable())[0].performTextInput("하이")
        onAllNodes(isEditable())[0].performTextClearance()
        waitForIdle()
        onNodeWithText(ComponentText.CREATE_BUTTON).assertIsNotEnabled()
    }

    @Test
    fun `Modal 헤더의 닫기 버튼을 누르면 onClickClose가 호출된다`() = runComposeUiTest {
        var close = false

        setContent {
            Modal(
                profiles = profiles,
                initialTask = null,
                onClickClose = { close = true },
                onShowSnackbar = {},
                onCreateTask = {},
                onUpdateTask = { _, _ -> },
                onDeleteTask = {},
            )
        }

        onNodeWithContentDescription("닫기").performClick()
        assertThat(close).isTrue()
    }

    @Test
    fun `취소 버튼을 누르면 onClickClose가 호출된다`() = runComposeUiTest {
        var close = false

        setContent {
            Modal(
                profiles = profiles,
                initialTask = null,
                onClickClose = { close = true },
                onShowSnackbar = {},
                onCreateTask = {},
                onUpdateTask = { _, _ -> },
                onDeleteTask = {},
            )
        }

        onNodeWithText(ComponentText.CANCEL_BUTTON).performSemanticsAction(SemanticsActions.OnClick)
        assertThat(close).isTrue()
    }

    @Test
    fun `제목을 입력하고 생성 버튼을 누르면 onCreateTask가 호출된다`() = runComposeUiTest {
        var create = false

        setContent {
            Modal(
                profiles = profiles,
                initialTask = null,
                onClickClose = {},
                onShowSnackbar = {},
                onCreateTask = { create = true },
                onUpdateTask = { _, _ -> },
                onDeleteTask = {},
            )
        }

        onAllNodes(isEditable())[0].performTextInput("하이")
        onNodeWithText(ComponentText.CREATE_BUTTON).performSemanticsAction(SemanticsActions.OnClick)
        assertThat(create).isTrue()
    }

    @Test
    fun `todo 상태에서는 담당자 없음 버튼이 보인다`() = runComposeUiTest {
        setContent {
            Modal(
                profiles = profiles,
                initialTask = null,
                onClickClose = {},
                onShowSnackbar = {},
                onCreateTask = {},
                onUpdateTask = { _, _ -> },
                onDeleteTask = {},
            )
        }

        onNodeWithText(Profile.NONE.nickname).assertExists()
    }

    @Test
    fun `review 상태에서 삭제를 누르면 삭제 불가 스낵바를 요청한다`() = runComposeUiTest {
        var snackbarMessage = ""
        var deleted = false

        setContent {
            Modal(
                profiles = profiles,
                initialTask = createTask(status = Status.REVIEW),
                onClickClose = {},
                onShowSnackbar = { snackbarMessage = it },
                onCreateTask = {},
                onUpdateTask = { _, _ -> },
                onDeleteTask = { deleted = true },
            )
        }

        onNodeWithText("삭제").performSemanticsAction(SemanticsActions.OnClick)

        assertThat(snackbarMessage).isEqualTo(ComponentText.BOARD_TASK_DELETE_DENIED_SNACKBAR)
        assertThat(deleted).isFalse()
    }

    @Test
    fun `done 상태에서 review 상태로 변경하려고 하면 전이 불가 스낵바를 요청한다`() = runComposeUiTest {
        var snackbarMessage = ""

        setContent {
            Modal(
                profiles = profiles,
                initialTask = createTask(status = Status.DONE),
                onClickClose = {},
                onShowSnackbar = { snackbarMessage = it },
                onCreateTask = {},
                onUpdateTask = { _, _ -> },
                onDeleteTask = {},
            )
        }

        onNodeWithText(ComponentText.STATE_BUTTON_REVIEW).performClick()

        assertThat(snackbarMessage).isEqualTo(ComponentText.BOARD_TASK_INVALID_STATUS_SNACKBAR)
    }

    @Test
    fun `담당자가 없는 todo 상태에서 in progress로 변경하려고 하면 담당자 지정 스낵바를 요청한다`() = runComposeUiTest {
        var snackbarMessage = ""

        setContent {
            Modal(
                profiles = profiles,
                initialTask = createTask(status = Status.TODO, profile = Profile.NONE),
                onClickClose = {},
                onShowSnackbar = { snackbarMessage = it },
                onCreateTask = {},
                onUpdateTask = { _, _ -> },
                onDeleteTask = {},
            )
        }

        onNodeWithText(ComponentText.STATE_BUTTON_PROGRESS).performClick()

        assertThat(snackbarMessage).isEqualTo(ComponentText.BOARD_TASK_REQUIRE_PROFILE_SNACKBAR)
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
