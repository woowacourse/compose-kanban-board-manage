package woowacourse.kanban.board.component.board

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.semantics.SemanticsActions
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.isEditable
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performSemanticsAction
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.runComposeUiTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import woowacourse.kanban.board.component.sample.ProjectPreviewData
import woowacourse.kanban.board.component.util.ComponentText
import woowacourse.kanban.board.model.project.Project
import woowacourse.kanban.board.model.taskcard.Profile

@OptIn(ExperimentalTestApi::class)
class BoardTest {
    private lateinit var project: Project
    private lateinit var profiles: ImmutableList<Profile>

    @BeforeTest
    fun setUp() {
        project = ProjectPreviewData().values.toImmutableList()[0]
        profiles = listOf(
            Profile("다이노"),
            Profile("페임스")
        ).toImmutableList()
    }

    @Test
    fun `새 태스크 생성 버튼을 누르면 다이얼로그가 열린다`() = runComposeUiTest {
        setContent {
            var currentProject by remember { mutableStateOf(project) }
            Board(
                project = currentProject,
                profiles = profiles,
                onCreateTask = { task -> currentProject = currentProject.addCard(task) },
                onUpdateTask = { id, task -> currentProject = currentProject.updateTask(id, task) },
                onDeleteTask = { id -> currentProject = currentProject.deleteTask(id) },
                onUpdateTaskStatus = { id, status -> currentProject = currentProject.updateTaskStatus(id, status) },
            )
        }
        onNodeWithText(ComponentText.BOARD_TASK_CREATE_BUTTON).performClick()
        onNodeWithText(ComponentText.TITLE_PLACEHOLDER).assertIsDisplayed()
    }

    @Test
    fun `다이얼로그 내 취소 버튼을 누르면 다이얼로그가 닫힌다`() = runComposeUiTest {
        setContent {
            var currentProject by remember { mutableStateOf(project) }
            Board(
                project = currentProject,
                profiles = profiles,
                onCreateTask = { task -> currentProject = currentProject.addCard(task) },
                onUpdateTask = { id, task -> currentProject = currentProject.updateTask(id, task) },
                onDeleteTask = { id -> currentProject = currentProject.deleteTask(id) },
                onUpdateTaskStatus = { id, status -> currentProject = currentProject.updateTaskStatus(id, status) },
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
            var currentProject by remember { mutableStateOf(project) }
            Board(
                project = currentProject,
                profiles = profiles,
                onCreateTask = { task -> currentProject = currentProject.addCard(task) },
                onUpdateTask = { id, task -> currentProject = currentProject.updateTask(id, task) },
                onDeleteTask = { id -> currentProject = currentProject.deleteTask(id) },
                onUpdateTaskStatus = { id, status -> currentProject = currentProject.updateTaskStatus(id, status) },
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
            var currentProject by remember { mutableStateOf(project) }
            Board(
                project = currentProject,
                profiles = profiles,
                onCreateTask = { task -> currentProject = currentProject.addCard(task) },
                onUpdateTask = { id, task -> currentProject = currentProject.updateTask(id, task) },
                onDeleteTask = { id -> currentProject = currentProject.deleteTask(id) },
                onUpdateTaskStatus = { id, status -> currentProject = currentProject.updateTaskStatus(id, status) },
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
            var currentProject by remember { mutableStateOf(project) }
            Board(
                project = currentProject,
                profiles = profiles,
                onCreateTask = { task -> currentProject = currentProject.addCard(task) },
                onUpdateTask = { id, task -> currentProject = currentProject.updateTask(id, task) },
                onDeleteTask = { id -> currentProject = currentProject.deleteTask(id) },
                onUpdateTaskStatus = { id, status -> currentProject = currentProject.updateTaskStatus(id, status) },
            )
        }
        onNodeWithText(ComponentText.BOARD_TASK_CREATE_BUTTON).performClick()
        onAllNodes(isEditable())[0].performClick()
        onAllNodes(isEditable())[0].performTextInput("테스트에용")
        onAllNodes(hasText(ComponentText.CREATE_BUTTON))[0].performSemanticsAction(SemanticsActions.OnClick)
        onNodeWithText(ComponentText.BOARD_TASK_CREATE_SNACKBAR).assertIsDisplayed()
    }
}
