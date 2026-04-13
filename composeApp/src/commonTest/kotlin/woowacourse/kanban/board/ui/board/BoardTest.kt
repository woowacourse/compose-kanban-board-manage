package woowacourse.kanban.board.ui.board

import androidx.compose.runtime.remember
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.runComposeUiTest
import kotlin.test.Test
import woowacourse.kanban.board.domain.Author
import woowacourse.kanban.board.domain.Task
import woowacourse.kanban.board.domain.TaskState
import woowacourse.kanban.board.ui.board.state.ProjectState
import woowacourse.kanban.board.ui.board.state.ProjectsStateHolder

@OptIn(ExperimentalTestApi::class)
class BoardTest {
    val projects = listOf(
        ProjectState(
            name = "Compose1",
            initialTasks = mutableListOf(
                Task(
                    title = "title",
                    taskState = TaskState.TO_DO,
                    author = Author.User("다이노"),
                ),
            ),
        ),
        ProjectState(name = "Compose2"),
        ProjectState(name = "Compose3너무너무길다란이름"),
    )

    @Test
    fun `새 태스크 생성 버튼을 클릭하면 새 태스크 생성 모달이 노출된다`() = runComposeUiTest {
        // given
        setContent {
            Board(
                project = projects.first(),
                onTaskCreated = {},
                onTaskUpdated = { _, _ -> },
                onTaskDeleted = { },
                onTaskStateChange = { _, _ -> },
            )
        }

        // when
        onNodeWithText("새 태스크 생성").performClick()

        // then
        onNodeWithText("태스크 제목을 입력하세요", useUnmergedTree = true).assertExists()
    }

    @Test
    fun `새 태스크 생성 모달의 x 아이콘을 클릭하면 모달이 닫힌다`() = runComposeUiTest {
        // given
        setContent {
            Board(
                project = projects.first(),
                onTaskCreated = {},
                onTaskUpdated = { _, _ -> },
                onTaskDeleted = { },
                onTaskStateChange = { _, _ -> },
            )
        }

        // when
        onNodeWithText("새 태스크 생성").performClick()
        onNodeWithContentDescription("닫기").performClick()

        // then
        onNodeWithText("태스크 제목을 입력하세요", useUnmergedTree = true).assertDoesNotExist()
    }

    @Test
    fun `유효한 입력 후 생성 버튼을 클릭하면 새 태스크가 노출된다`() = runComposeUiTest {
        // given
        setContent {
            val stateHolder = remember { ProjectsStateHolder(projects) }
            val selectedProject = stateHolder.selectedProject

            Board(
                project = selectedProject ?: projects.first(),
                onTaskCreated = { stateHolder.selectedProject?.addTask(it) },
                onTaskUpdated = { _, _ -> },
                onTaskDeleted = { },
                onTaskStateChange = { _, _ -> },
            )
        }

        // when
        onNodeWithText("새 태스크 생성").performClick()
        onNodeWithText("태스크 제목을 입력하세요").performTextInput("title2")
        onNodeWithText("생성").performClick()

        // then
        onNodeWithText("title2").assertExists()
    }

    @Test
    fun `새로운 태스크가 추가되면 Snackbar를 노출한다`() = runComposeUiTest {
        // given
        setContent {
            Board(
                project = projects.first(),
                onTaskCreated = {},
                onTaskUpdated = { _, _ -> },
                onTaskDeleted = { },
                onTaskStateChange = { _, _ -> },
            )
        }

        // when
        onNodeWithText("새 태스크 생성").performClick()
        onNodeWithText("태스크 제목을 입력하세요").performTextInput("title")
        onNodeWithText("생성").performClick()

        // then
        onNodeWithText("새로운 태스크가 추가되었습니다.").assertIsDisplayed()
    }

    @Test
    fun `DONE 상태의 태스크를 추가하면 완료율이 50으로 변경된다`() = runComposeUiTest {
        // given
        setContent {
            val stateHolder = remember { ProjectsStateHolder(projects) }
            val selectedProject = stateHolder.selectedProject

            Board(
                project = selectedProject ?: projects.first(),
                onTaskCreated = { stateHolder.selectedProject?.addTask(it) },
                onTaskUpdated = { _, _ -> },
                onTaskDeleted = { },
                onTaskStateChange = { _, _ -> },
            )
        }

        // when
        onNodeWithText("완료율: 0% (0/1)").assertExists()
        onNodeWithText("새 태스크 생성").performClick()
        onNodeWithText("태스크 제목을 입력하세요").performTextInput("title2")
        onNode(hasText("Done") and hasClickAction()).performClick()
        onNodeWithText("생성").performClick()

        // then
        onNodeWithText("완료율: 50% (1/2)").assertExists()
    }

    @Test
    fun `태스크의 상태를 변경하면 Snackbar를 노출한다`() = runComposeUiTest {
        // given
        setContent {
            val stateHolder = remember { ProjectsStateHolder(projects) }

            Board(
                project = projects.first(),
                onTaskCreated = { stateHolder.selectedProject?.addTask(it) },
                onTaskUpdated = { _, _ -> },
                onTaskDeleted = { },
                onTaskStateChange = { idx, targetStatus ->
                    stateHolder.selectedProject?.changeTaskState(idx, targetStatus)
                },
            )
        }

        val targetColumnBounds = onNodeWithTag(TaskState.IN_PROGRESS.name).fetchSemanticsNode().boundsInRoot

        // when
        onNodeWithText("title").performTouchInput {
            down(center)
            advanceEventTime(1000)
            moveTo(targetColumnBounds.center)
            up()
        }

        // then
        onNodeWithText("태스크가 이동되었습니다.").assertIsDisplayed()
    }

    @Test
    fun `태스크를 수정하면 Snackbar를 노출한다`() = runComposeUiTest {
        // given
        setContent {
            val stateHolder = remember { ProjectsStateHolder(projects) }

            Board(
                project = projects.first(),
                onTaskCreated = { stateHolder.selectedProject?.addTask(it) },
                onTaskUpdated = { taskId, task ->
                    stateHolder.selectedProject?.updateTask(taskId, task)
                },
                onTaskDeleted = { taskId ->
                    stateHolder.selectedProject?.deleteTask(taskId)
                },
                onTaskStateChange = { idx, targetStatus ->
                    stateHolder.selectedProject?.changeTaskState(idx, targetStatus)
                },
            )
        }

        // when
        onNodeWithText("title").performClick()
        onNode(hasText("수정") and hasClickAction()).performClick()

        // then
        onNodeWithText("태스크가 수정되었습니다.").assertIsDisplayed()
    }

    @Test
    fun `태스크를 삭제하면 Snackbar를 노출한다`() = runComposeUiTest {
        // given
        setContent {
            val stateHolder = remember { ProjectsStateHolder(projects) }

            Board(
                project = projects.first(),
                onTaskCreated = { stateHolder.selectedProject?.addTask(it) },
                onTaskUpdated = { taskId, task ->
                    stateHolder.selectedProject?.updateTask(taskId, task)
                },
                onTaskDeleted = { taskId ->
                    stateHolder.selectedProject?.deleteTask(taskId)
                },
                onTaskStateChange = { idx, targetStatus ->
                    stateHolder.selectedProject?.changeTaskState(idx, targetStatus)
                },
            )
        }

        // when
        onNodeWithText("title").performClick()
        onNodeWithText("삭제").performClick()

        // then
        onNodeWithText("태스크가 삭제되었습니다.").assertIsDisplayed()
    }
}
