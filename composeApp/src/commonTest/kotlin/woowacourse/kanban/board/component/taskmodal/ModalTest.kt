package woowacourse.kanban.board.component.taskmodal

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.SemanticsActions
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import kanbanboard.composeapp.generated.resources.Res
import kanbanboard.composeapp.generated.resources.profile
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import woowacourse.kanban.board.Blue50
import woowacourse.kanban.board.component.ComponentText
import woowacourse.kanban.board.component.workspace.rememberModalState
import woowacourse.kanban.board.fixture.TaskCardDataFixture
import woowacourse.kanban.board.model.taskcard.Assignee
import woowacourse.kanban.board.model.taskcard.TaskTag
import kotlin.test.Test

@OptIn(ExperimentalTestApi::class)
class ModalTest {

    private lateinit var assignees: ImmutableList<Assignee>
    private lateinit var createModalTitle: @Composable () -> Unit
    private lateinit var editModalTitle: @Composable () -> Unit

    @Before
    fun setUp() {
        assignees = listOf(
            Assignee("다이노", Res.drawable.profile),
            Assignee("페임스", Res.drawable.profile),
        ).toImmutableList()

        createModalTitle = {
            Text(
                text = ComponentText.CREATE_MODAL_HEADER_LABEL,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
            )
        }

        editModalTitle = {
            Text(
                text = ComponentText.EDIT_MODAL_HEADER_LABEL,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
            )
        }
    }

    @Test
    fun `초기 상태에서 생성 버튼이 비활성화된다`() = runComposeUiTest {
        setContent {
            val modalState = rememberModalState(assignees)
            TaskModal(
                assignees = assignees,
                onClickClose = {},
                title = createModalTitle,
                footerButtonSection = {
                    TaskModalFooterButton(
                        enabled = modalState.isTaskTitleValid &&
                                modalState.isTaskTagsValid &&
                                modalState.isTaskAssigneeValid,
                        containerColor = Blue50,
                        text = ComponentText.CREATE_BUTTON,
                        onClick = {},
                    )
                },
                modalState = modalState,
            )
        }
        onNodeWithText(ComponentText.CREATE_BUTTON).assertIsNotEnabled()
    }

    @Test
    fun `제목을 입력하면 생성 버튼이 활성화된다`() = runComposeUiTest {
        setContent {
            val modalState = rememberModalState(assignees)
            TaskModal(
                assignees = assignees,
                onClickClose = {},
                title = createModalTitle,
                footerButtonSection = {
                    TaskModalFooterButton(
                        enabled = modalState.isTaskTitleValid &&
                                modalState.isTaskTagsValid &&
                                modalState.isTaskAssigneeValid,
                        containerColor = Blue50,
                        text = ComponentText.CREATE_BUTTON,
                        onClick = {},
                    )
                },
                modalState = modalState,
            )
        }
        onNodeWithText(ComponentText.TITLE_PLACEHOLDER).performTextInput("하이")
        onNodeWithText(ComponentText.CREATE_BUTTON).assertIsEnabled()
    }

    @Test
    fun `제목을 입력하고 태그에 ,,을 연속으로 입력하면 생성 버튼이 비활성화된다`() = runComposeUiTest {
        setContent {
            val modalState = rememberModalState(assignees)
            TaskModal(
                assignees = assignees,
                onClickClose = {},
                title = createModalTitle,
                footerButtonSection = {
                    TaskModalFooterButton(
                        enabled = modalState.isTaskTitleValid &&
                                modalState.isTaskTagsValid &&
                                modalState.isTaskAssigneeValid,
                        containerColor = Blue50,
                        text = ComponentText.CREATE_BUTTON,
                        onClick = {},
                    )
                },
                modalState = modalState,
            )
        }
        onNodeWithText(ComponentText.TITLE_PLACEHOLDER).performTextInput("하이")
        onNodeWithText(ComponentText.CREATE_BUTTON).assertIsEnabled()
        onNodeWithText(ComponentText.TAG_PLACEHOLDER).performTextInput("태그,,태그2")
        onNodeWithText(ComponentText.CREATE_BUTTON).assertIsNotEnabled()
    }

    @Test
    fun `제목을 입력한 뒤 모두 지우면 생성 버튼이 비활성화된다`() = runComposeUiTest {
        setContent {
            val modalState = rememberModalState(assignees)
            TaskModal(
                assignees = assignees,
                onClickClose = {},
                title = createModalTitle,
                footerButtonSection = {
                    TaskModalFooterButton(
                        enabled = modalState.isTaskTitleValid &&
                                modalState.isTaskTagsValid &&
                                modalState.isTaskAssigneeValid,
                        containerColor = Blue50,
                        text = ComponentText.CREATE_BUTTON,
                        onClick = {},
                    )
                },
                modalState = modalState,
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
            val modalState = rememberModalState(assignees)
            TaskModal(
                assignees = assignees,
                onClickClose = { close = true },
                title = createModalTitle,
                footerButtonSection = {
                    TaskModalFooterButton(
                        enabled = modalState.isTaskTitleValid &&
                                modalState.isTaskTagsValid &&
                                modalState.isTaskAssigneeValid,
                        containerColor = Blue50,
                        text = ComponentText.CREATE_BUTTON,
                        onClick = {},
                    )
                },
                modalState = modalState,
            )
        }
        onNodeWithContentDescription("닫기").performClick()
        assertThat(close).isTrue()
    }

    @Test
    fun `취소 버튼을 누르면 onClickClose가 호출된다`() = runComposeUiTest {
        var close = false
        setContent {
            val modalState = rememberModalState(assignees)
            TaskModal(
                assignees = assignees,
                onClickClose = { close = true },
                title = createModalTitle,
                footerButtonSection = {
                    TaskModalFooterButton(
                        enabled = modalState.isTaskTitleValid &&
                                modalState.isTaskTagsValid &&
                                modalState.isTaskAssigneeValid,
                        containerColor = Blue50,
                        text = ComponentText.CREATE_BUTTON,
                        onClick = {},
                    )
                },
                modalState = modalState,
            )
        }
        onNodeWithText(ComponentText.CANCEL_BUTTON).performSemanticsAction(SemanticsActions.OnClick)
        assertThat(close).isTrue()
    }

    @Test
    fun `제목을 입력하고 생성 버튼을 누르면 onClickTaskCreate가 호출된다`() = runComposeUiTest {
        var create = false
        setContent {
            val modalState = rememberModalState(assignees)
            TaskModal(
                assignees = assignees,
                onClickClose = { },
                title = createModalTitle,
                footerButtonSection = {
                    TaskModalFooterButton(
                        enabled = modalState.isTaskTitleValid &&
                                modalState.isTaskTagsValid &&
                                modalState.isTaskAssigneeValid,
                        containerColor = Blue50,
                        text = ComponentText.CREATE_BUTTON,
                        onClick = { create = true },
                    )
                },
                modalState = modalState,
            )
        }
        onAllNodes(isEditable())[0].performTextInput("하이")
        onNodeWithText(ComponentText.CREATE_BUTTON).performSemanticsAction(SemanticsActions.OnClick)
        assertThat(create).isTrue()
    }

    @Test
    fun `수정 모달을 열면 수정할 태스크 데이터가 입력되어 있다`() = runComposeUiTest {
        val taskCardData = TaskCardDataFixture.create(
            title = "수정태스크",
            taskDescription = "수정할 태스크의 설명입니다",
            tags = listOf(TaskTag(value = "태그3")).toImmutableList(),
        )
        setContent {
            val modalState = rememberModalState(assignees)
            TaskModal(
                assignees = assignees,
                onClickClose = { },
                title = editModalTitle,
                footerButtonSection = {
                    EditModalFooterButtons(
                        isButtonEnabled = modalState.isFormValid,
                        onDeleteClick = {},
                        onEditClick = {},
                    )
                },
                data = taskCardData,
                modalState = modalState,
            )
        }

        onNodeWithText("수정태스크").assertIsDisplayed()
        onNodeWithText("수정할 태스크의 설명입니다").assertIsDisplayed()
        onNodeWithText("태그3").assertIsDisplayed()
    }
}
