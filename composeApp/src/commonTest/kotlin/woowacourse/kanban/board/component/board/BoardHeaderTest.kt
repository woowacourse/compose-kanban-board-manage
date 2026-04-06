package woowacourse.kanban.board.component.board

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.runComposeUiTest
import kotlin.test.Test
import org.assertj.core.api.Assertions.assertThat
import woowacourse.kanban.board.component.ComponentText

@OptIn(ExperimentalTestApi::class)
class BoardHeaderTest {

    @Test
    fun `새 태스크 생성 버튼 클릭 시 onClickCreateTask가 실행된다`() = runComposeUiTest {
        var clicked = false

        setContent {
            TaskBoardHeader(
                title = "타이틀",
                doneRate = 0f,
                doneTasks = 0,
                totalTasks = 0,
                onClickCreateTask = { clicked = true },
            )
        }

        onNodeWithText(ComponentText.BOARD_TASK_CREATE_BUTTON)
            .performClick()

        assertThat(clicked).isTrue
    }

    @Test
    fun `완료된 태스크가 없을 때 0%가 출력된다`() = runComposeUiTest {

        setContent {
            TaskBoardHeader(
                title = "타이틀",
                doneRate = 0f,
                doneTasks = 0,
                totalTasks = 10,
                onClickCreateTask = {}
            )
        }

        onNodeWithText(ComponentText.BOARD_HEADER_PROGRESS, substring = true)
            .assertTextContains("0%", substring = true)
    }

    @Test
    fun `10개 업무 중 5개 완료된 업무가 있을때 50%가 출력된다`() = runComposeUiTest {
        setContent {
            TaskBoardHeader(
                title = "타이틀",
                doneRate = 0.5f,
                doneTasks = 5,
                totalTasks = 10,
                onClickCreateTask = {}
            )
        }

        onNodeWithText(ComponentText.BOARD_HEADER_PROGRESS, substring = true)
            .assertTextContains("50%", substring = true)
    }

    @Test
    fun `20개 중 10개 업무가 완료이면 완료 업무수가 10으로 출력된다`() = runComposeUiTest {
        setContent {
            TaskBoardHeader(
                title = "타이틀",
                doneRate = 0.5f,
                doneTasks = 10,
                totalTasks = 20,
                onClickCreateTask = {}
            )
        }

        onNodeWithText("완료율: 50% (10/20)").assertIsDisplayed()
    }

    @Test
    fun `등록된 태스크가 10개면 10이 출력된다`() = runComposeUiTest {
        setContent {
            TaskBoardHeader(
                title = "타이틀",
                doneRate = 0.5f,
                doneTasks = 5,
                totalTasks = 10,
                onClickCreateTask = {}
            )
        }

        onNodeWithText("완료율: 50% (5/10)").assertIsDisplayed()
    }
}
