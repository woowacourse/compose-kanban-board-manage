package woowacourse.kanban.board.task.ui.board

import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.runComposeUiTest
import kotlin.test.Test
import woowacourse.kanban.board.task.domain.KanbanProject
import woowacourse.kanban.board.task.domain.TaskMockData
import woowacourse.kanban.board.task.ui.project.RememberKanbanProjectState

@OptIn(ExperimentalTestApi::class)
class KanbanBoardScreenTest {

    @Test
    fun `새 태스크 생성 버튼 클릭 시 Dialog가 생성된다`() = runComposeUiTest {
        setContent {
            val kanbanProjectState = RememberKanbanProjectState(
                coroutineScope = rememberCoroutineScope(),
                kanbanProject = KanbanProject(
                    projectTitle = "4주차 미션 보드",
                    boards = TaskMockData.boards,
                ),
            )
            KanbanBoardScreen(
                kanbanProjectState = kanbanProjectState,
            )
        }

        onNodeWithText("새 태스크 생성").performClick()

        onNodeWithText("제목 *").assertIsDisplayed()
    }

    @Test
    fun `Dialog를 닫고 다시 열었을 때 이전 입력값이 초기화된다`() = runComposeUiTest {
        setContent {
            val kanbanProjectState = RememberKanbanProjectState(
                coroutineScope = rememberCoroutineScope(),
                kanbanProject = KanbanProject(
                    projectTitle = "4주차 미션 보드",
                    boards = TaskMockData.boards,
                ),
            )
            KanbanBoardScreen(
                kanbanProjectState = kanbanProjectState,
            )
        }

        onNodeWithText("새 태스크 생성").performClick()
        onNodeWithText("태스크 제목을 입력하세요").performTextInput("제목")
        onNodeWithText("취소").performClick()

        onNodeWithText("새 태스크 생성").performClick()
        onNodeWithText("제목").assertDoesNotExist()
        onNodeWithText("태스크 제목을 입력하세요").assertIsDisplayed()
    }

    @Test
    fun `카드 추가 성공 시 스낵바가 생성된다`() = runComposeUiTest {
        setContent {
            val kanbanProjectState = RememberKanbanProjectState(
                coroutineScope = rememberCoroutineScope(),
                kanbanProject = KanbanProject(
                    projectTitle = "4주차 미션 보드",
                    boards = TaskMockData.boards,
                ),
            )
            KanbanBoardScreen(
                kanbanProjectState = kanbanProjectState,
            )
        }

        onNodeWithText("새 태스크 생성").performClick()
        onNodeWithText("태스크 제목을 입력하세요").performTextInput("제목")
        onNodeWithText("생성").performClick()

        onNodeWithText("새로운 태스크가 추가되었습니다.").assertIsDisplayed()
    }

    @Test
    fun `일정 시간이 경과하면 스낵바가 화면에서 사라진다`() = runComposeUiTest {
        setContent {
            val kanbanProjectState = RememberKanbanProjectState(
                coroutineScope = rememberCoroutineScope(),
                kanbanProject = KanbanProject(
                    projectTitle = "4주차 미션 보드",
                    boards = TaskMockData.boards,
                ),
            )
            KanbanBoardScreen(
                kanbanProjectState = kanbanProjectState,
            )
        }

        onNodeWithText("새 태스크 생성").performClick()
        onNodeWithText("태스크 제목을 입력하세요").performTextInput("제목")
        onNodeWithText("생성").performClick()
        onNodeWithText("새로운 태스크가 추가되었습니다.").assertIsDisplayed()

        mainClock.advanceTimeBy(5000L)

        onNodeWithText("새로운 태스크가 추가되었습니다.").assertDoesNotExist()
    }
}
