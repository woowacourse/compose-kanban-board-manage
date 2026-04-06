package woowacourse.kanban.board.component

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.SemanticsActions
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.isEditable
import androidx.compose.ui.test.isRoot
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performMouseInput
import androidx.compose.ui.test.performSemanticsAction
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.printToLog
import androidx.compose.ui.test.runComposeUiTest
import androidx.compose.ui.unit.Density
import kanbanboard.composeapp.generated.resources.Res
import kanbanboard.composeapp.generated.resources.profile
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import org.junit.Before
import woowacourse.kanban.board.component.sample.ProjectPreviewData
import woowacourse.kanban.board.component.workspace.WorkSpace
import woowacourse.kanban.board.fixture.TaskCardDataFixture
import woowacourse.kanban.board.model.project.Project
import woowacourse.kanban.board.model.taskcard.Assignee
import woowacourse.kanban.board.model.taskcard.Status
import kotlin.test.Test

@OptIn(ExperimentalTestApi::class)
class WorkSpaceTest {

    private lateinit var projects: ImmutableList<Project>
    private lateinit var assignees: ImmutableList<Assignee>

    private fun createProject(status: Status, title: String) =
        listOf(
            Project(
                initialTasks = listOf(
                    TaskCardDataFixture.create(
                        title = title,
                        status = status,
                    ),
                ).toImmutableList(),
                title = "프로젝트",
            ),
        ).toImmutableList()

    @Before
    fun setUp() {
        projects = ProjectPreviewData().values.toImmutableList()
        assignees = listOf(
            Assignee("다이노", Res.drawable.profile),
            Assignee("페임스", Res.drawable.profile),
        ).toImmutableList()
    }

    @Test
    fun `사이드바의 탭을 누르면 헤더 타이틀이 프로젝트 이름으로 변경된다`() = runComposeUiTest {
        setContent {
            WorkSpace(
                projects = projects,
                assignees = assignees,
            )
        }
        onAllNodesWithText("Compose1").assertCountEquals(2)
        onNodeWithText("Compose2").performClick()
        onAllNodesWithText("Compose1").assertCountEquals(1)
        onAllNodesWithText("Compose2").assertCountEquals(2)
    }

    @Test
    fun `새 태스크 생성 버튼을 누르면 다이얼로그가 열린다`() = runComposeUiTest {
        setContent {
            WorkSpace(
                projects = projects,
                assignees = assignees,
            )
        }
        onNodeWithText(ComponentText.BOARD_TASK_CREATE_BUTTON).performClick()
        onNodeWithText(ComponentText.TITLE_PLACEHOLDER).assertIsDisplayed()
    }

    @Test
    fun `다이얼로그 내 취소 버튼을 누르면 다이얼로그가 닫힌다`() = runComposeUiTest {
        setContent {
            WorkSpace(
                projects = projects,
                assignees = assignees,
            )
        }
        onNodeWithText(ComponentText.BOARD_TASK_CREATE_BUTTON).performClick()
        onNodeWithText(ComponentText.TITLE_PLACEHOLDER).assertIsDisplayed()
        onNodeWithText(ComponentText.CANCEL_BUTTON).performClick()
        onNodeWithText(ComponentText.TITLE_PLACEHOLDER).assertDoesNotExist()
    }

    @Test
    fun `다이얼로그 헤더의 x 버튼을 누르면 다이얼로그가 닫힌다`() = runComposeUiTest {
        setContent {
            WorkSpace(
                projects = projects,
                assignees = assignees,
            )
        }
        onNodeWithText(ComponentText.BOARD_TASK_CREATE_BUTTON).performClick()
        onNodeWithText(ComponentText.TITLE_PLACEHOLDER).assertIsDisplayed()
        onNodeWithContentDescription("닫기").performClick()
        onNodeWithText(ComponentText.TITLE_PLACEHOLDER).assertDoesNotExist()
    }

    @Test
    fun `모달을 통해 생성한 태스크 카드가 출력된다`() = runComposeUiTest {
        setContent {
            WorkSpace(
                projects = projects,
                assignees = assignees,
            )
        }
        onNodeWithText(ComponentText.BOARD_TASK_CREATE_BUTTON).performClick()
        onAllNodes(isEditable())[0].performClick()
        onAllNodes(isEditable())[0].performTextInput("테스트에용")
        onAllNodes(hasText(ComponentText.CREATE_BUTTON))[0].performSemanticsAction(SemanticsActions.OnClick)
        onNodeWithText("테스트에용").assertExists()
    }

    @Test
    fun `태스크 카드를 생성하면 스낵바가 출력된다`() = runComposeUiTest {
        setContent {
            WorkSpace(
                projects = projects,
                assignees = assignees,
            )
        }
        onNodeWithText(ComponentText.BOARD_TASK_CREATE_BUTTON).performClick()
        onAllNodes(isEditable())[0].performClick()
        onAllNodes(isEditable())[0].performTextInput("테스트에용")
        onAllNodes(hasText(ComponentText.CREATE_BUTTON))[0].performSemanticsAction(SemanticsActions.OnClick)
        onNodeWithText(ComponentText.BOARD_TASK_CREATE_SNACKBAR).assertIsDisplayed()
    }

    @Test
    fun `TODO 태스크 카드를 삭제 시도하면 삭제 성공 스낵바가 출력된다`() = runComposeUiTest {
        val project = createProject(
            status = Status.TODO,
            title = "이거누르세요태스크카드에요"
        )
        setContent {
            CompositionLocalProvider(
                LocalDensity provides Density(
                    density = 0.1f,
                ),
            ) {
                WorkSpace(
                    projects = project,
                    assignees = assignees,
                )
            }
        }
        onNodeWithText("이거누르세요태스크카드에요").performClick()
        waitForIdle()
        onNodeWithText("삭제").performClick()
        waitForIdle()
        onNodeWithText(ComponentText.BOARD_TASK_DELETE_SUCCESS_SNACKBAR).assertIsDisplayed()
    }

    @Test
    fun `TODO 태스크 카드를 삭제 시도하면 보드에서 해당 태스크 카드가 삭제된다`() = runComposeUiTest {
        val project = createProject(
            status = Status.TODO,
            title = "이거누르세요태스크카드에요"
        )
        setContent {
            CompositionLocalProvider(
                LocalDensity provides Density(
                    density = 0.1f,
                ),
            ) {
                WorkSpace(
                    projects = project,
                    assignees = assignees,
                )
            }
        }
        onNodeWithText("이거누르세요태스크카드에요").assertIsDisplayed()
        onNodeWithText("이거누르세요태스크카드에요").performClick()
        waitForIdle()
        onNodeWithText("삭제").performClick()
        waitForIdle()
        onNodeWithText("이거누르세요태스크카드에요").assertDoesNotExist()
    }

    @Test
    fun `IN PROGRESS 태스크 카드를 삭제 시도하면 삭제 성공 스낵바가 출력된다`() = runComposeUiTest {
        val project = createProject(
            status = Status.PROGRESS,
            title = "이거누르세요태스크카드에요"
        )
        setContent {
            CompositionLocalProvider(
                LocalDensity provides Density(
                    density = 0.1f,
                ),
            ) {
                WorkSpace(
                    projects = project,
                    assignees = assignees,
                )
            }
        }
        onNodeWithText("이거누르세요태스크카드에요").performClick()
        waitForIdle()
        onNodeWithText("삭제").performClick()
        waitForIdle()
        onNodeWithText(ComponentText.BOARD_TASK_DELETE_SUCCESS_SNACKBAR).assertIsDisplayed()
    }

    @Test
    fun `IN PROGRESS 태스크 카드를 삭제 시도하면 보드에서 해당 태스크 카드가 삭제된다`() = runComposeUiTest {
        val project = createProject(
            status = Status.PROGRESS,
            title = "이거누르세요태스크카드에요"
        )
        setContent {
            CompositionLocalProvider(
                LocalDensity provides Density(
                    density = 0.1f,
                ),
            ) {
                WorkSpace(
                    projects = project,
                    assignees = assignees,
                )
            }
        }
        onNodeWithText("이거누르세요태스크카드에요").assertIsDisplayed()
        onNodeWithText("이거누르세요태스크카드에요").performClick()
        waitForIdle()
        onNodeWithText("삭제").performClick()
        waitForIdle()
        onNodeWithText("이거누르세요태스크카드에요").assertDoesNotExist()
    }

    @Test
    fun `REVIEW 태스크 카드를 삭제 시도하면 삭제 실패 스낵바가 출력된다`() = runComposeUiTest {
        val project = createProject(
            status = Status.REVIEW,
            title = "이거누르세요태스크카드에요"
        )
        setContent {
            CompositionLocalProvider(
                LocalDensity provides Density(
                    density = 0.1f,
                ),
            ) {
                WorkSpace(
                    projects = project,
                    assignees = assignees,
                )
            }
        }
        onNodeWithText("이거누르세요태스크카드에요").performClick()
        waitForIdle()
        onNodeWithText("삭제").performClick()
        waitForIdle()
        onNodeWithText(ComponentText.BOARD_TASK_DELETE_FAILED_SNACKBAR).assertIsDisplayed()
    }

    @Test
    fun `Review 태스크 카드를 삭제 시도하면 보드에서 해당 태스크 카드가 삭제되지 않는다`() = runComposeUiTest {
        val project = createProject(
            status = Status.REVIEW,
            title = "이거누르세요태스크카드에요"
        )
        setContent {
            CompositionLocalProvider(
                LocalDensity provides Density(
                    density = 0.1f,
                ),
            ) {
                WorkSpace(
                    projects = project,
                    assignees = assignees,
                )
            }
        }
        onNodeWithText("이거누르세요태스크카드에요").assertIsDisplayed()
        onNodeWithText("이거누르세요태스크카드에요").performClick()
        waitForIdle()
        onNodeWithText("삭제").performClick()
        waitForIdle()
        onNodeWithText("이거누르세요태스크카드에요").assertIsDisplayed()
    }

    @Test
    fun `DONE 태스크 카드를 삭제 시도하면 삭제 실패 스낵바가 출력된다`() = runComposeUiTest {
        val project = createProject(
            status = Status.DONE,
            title = "이거누르세요태스크카드에요"
        )
        setContent {
            CompositionLocalProvider(
                LocalDensity provides Density(
                    density = 0.1f,
                ),
            ) {
                WorkSpace(
                    projects = project,
                    assignees = assignees,
                )
            }
        }
        onNodeWithText("이거누르세요태스크카드에요").performClick()
        waitForIdle()
        onNodeWithText("삭제").performClick()
        waitForIdle()
        onNodeWithText(ComponentText.BOARD_TASK_DELETE_FAILED_SNACKBAR).assertIsDisplayed()
    }

    @Test
    fun `Done 태스크 카드를 삭제 시도하면 보드에서 해당 태스크 카드가 삭제되지 않는다`() = runComposeUiTest {
        val project = createProject(
            status = Status.DONE,
            title = "이거누르세요태스크카드에요"
        )
        setContent {
            CompositionLocalProvider(
                LocalDensity provides Density(
                    density = 0.1f,
                ),
            ) {
                WorkSpace(
                    projects = project,
                    assignees = assignees,
                )
            }
        }
        onNodeWithText("이거누르세요태스크카드에요").assertIsDisplayed()
        onNodeWithText("이거누르세요태스크카드에요").performClick()
        waitForIdle()
        onNodeWithText("삭제").performClick()
        waitForIdle()
        onNodeWithText("이거누르세요태스크카드에요").assertIsDisplayed()
    }

    @Test
    fun `태스크 카드를 수정하면 수정 스낵바가 출력된다`() = runComposeUiTest {
        val project = createProject(
            status = Status.DONE,
            title = "이거누르세요태스크카드에요"
        )
        setContent {
            CompositionLocalProvider(
                LocalDensity provides Density(
                    density = 0.1f,
                ),
            ) {
                WorkSpace(
                    projects = project,
                    assignees = assignees,
                )
            }
        }
        onNodeWithText("이거누르세요태스크카드에요").performClick()
        waitForIdle()
        onAllNodes(isEditable())[0].performClick()
        onAllNodes(isEditable())[0].performTextClearance()
        onAllNodes(isEditable())[0].performTextInput("제목을수정했어용")
        onAllNodes(isRoot())[1].printToLog("TREE_DIALOG")
        onNodeWithText("수정").performClick()
        waitForIdle()
        onNodeWithText(ComponentText.BOARD_TASK_EDIT_SNACKBAR).assertIsDisplayed()
        onNodeWithText("제목을수정했어용").assertIsDisplayed()
    }

    @Test
    fun `태스크 카드의 상태 이동이 성공하면 이동 성공 스낵바가 출력된다`() = runComposeUiTest {
        val project = createProject(
            status = Status.REVIEW,
            title = "이거누르세요태스크카드에요"
        )
        setContent {
            CompositionLocalProvider(
                LocalDensity provides Density(
                    density = 0.1f,
                ),
            ) {
                WorkSpace(
                    projects = project,
                    assignees = assignees,
                )
            }
        }
        onNodeWithText("이거누르세요태스크카드에요")
            .performMouseInput {
                moveTo(center)
                press()
                moveBy(Offset(x = 300f, y = 0f))
                release()
            }
        onNodeWithText(ComponentText.BOARD_TASK_MOVE_SUCCESS_SNACKBAR).assertExists()
    }
}
