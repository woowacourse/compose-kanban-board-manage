package woowacourse.kanban.board.ui.board

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import woowacourse.kanban.board.domain.Project
import woowacourse.kanban.board.domain.Task
import woowacourse.kanban.board.domain.TaskState
import woowacourse.kanban.board.domain.Tasks

@OptIn(ExperimentalTestApi::class)
class BoardTest {

    @Test
    fun `새 태스크 생성 버튼을 클릭하면 새 태스크 생성 모달이 노출된다`() = runComposeUiTest {
        // given
        setContent {
            Board(
                projectName = "Compose Desktop 칸반 보드",
                tasks = Tasks(emptyList()),
                onTaskCreated = {},
                authors = listOf("다이노", "페임스"),
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
                projectName = "Compose Desktop 칸반 보드",
                tasks = Tasks(emptyList()),
                onTaskCreated = {},
                authors = listOf("다이노", "페임스"),
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
            var tasks by remember { mutableStateOf(Tasks(emptyList())) }

            Board(
                projectName = "Compose Desktop 칸반 보드",
                tasks = tasks,
                onTaskCreated = { tasks = tasks.addTask(it) },
                authors = listOf("다이노", "페임스"),
                onTaskStateChange = { _, _ -> },
            )
        }

        // when
        onNodeWithText("새 태스크 생성").performClick()
        onNodeWithText("태스크 제목을 입력하세요").performTextInput("title")
        onNodeWithText("생성").performClick()

        // then
        onNodeWithText("title", useUnmergedTree = true).assertExists()
    }

    @Test
    fun `새로운 태스크가 추가되면 Snackbar를 노출한다`() = runComposeUiTest {
        // given
        setContent {
            var tasks by remember { mutableStateOf(Tasks(emptyList())) }

            Board(
                projectName = "Compose Desktop 칸반 보드",
                tasks = tasks,
                onTaskCreated = { tasks = tasks.addTask(it) },
                authors = listOf("다이노", "페임스"),
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
    fun `DONE 상태의 태스크를 추가하면 완료율이 100으로 변경된다`() = runComposeUiTest {
        // given
        setContent {
            var tasks by remember { mutableStateOf(Tasks(emptyList())) }

            Board(
                projectName = "Compose Desktop 칸반 보드",
                tasks = tasks,
                onTaskCreated = { tasks = tasks.addTask(it) },
                authors = listOf("다이노", "페임스"),
                onTaskStateChange = { _, _ -> },
            )
        }

        // when
        onNodeWithText("완료율: 0% (0/0)").assertExists()
        onNodeWithText("새 태스크 생성").performClick()
        onNodeWithText("태스크 제목을 입력하세요").performTextInput("title")
        onNode(hasText("Done") and hasClickAction()).performClick()
        onNodeWithText("생성").performClick()

        // then
        onNodeWithText("완료율: 100% (1/1)").assertExists()
    }

    @Test
    fun `태스크의 상태를 변경하면 Snackbar를 노출한다`() = runComposeUiTest {
        // given
        val projects =
            listOf(
                Project(
                    name = "Compose1",
                    tasks = Tasks(emptyList()),
                ),
                Project(
                    name = "Compose2",
                    tasks = Tasks(emptyList()),
                ),
                Project(
                    name = "Compose3너무너무길다란이름",
                    tasks = Tasks(emptyList()),
                ),
            )

        setContent {
            var tasks by remember {
                mutableStateOf(
                    Tasks(
                        listOf(
                            Task(title = "title", taskState = TaskState.TO_DO),
                        ),
                    ),
                )
            }

            Board(
                projectName = "Compose Desktop 칸반 보드",
                tasks = tasks,
                onTaskCreated = { tasks = tasks.addTask(it) },
                authors = listOf("다이노", "페임스"),
                onTaskStateChange = { idx, targetStatus ->
                    val newTask = tasks.items[idx].copy(taskState = targetStatus)
                    tasks.updateTask(newTask)
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
}
