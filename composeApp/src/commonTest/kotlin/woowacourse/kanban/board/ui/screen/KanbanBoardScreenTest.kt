package woowacourse.kanban.board.ui.screen

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.runComposeUiTest
import androidx.compose.ui.unit.Density
import woowacourse.kanban.board.domain.KanbanBoard
import woowacourse.kanban.board.domain.KanbanProject
import woowacourse.kanban.board.fixture.createKanbanTask
import woowacourse.kanban.board.ui.screen.board.KanbanBoardScreen
import kotlin.test.Test

@OptIn(ExperimentalTestApi::class)
class KanbanBoardScreenTest {
    @Test
    fun `칸반보드 스크린의 카드홀더들이 정상적으로 화면에 표시된다`() = runComposeUiTest {
        setContent {
            KanbanBoardScreen(
                projects = listOf(KanbanProject("안녕")),
            )
        }

        onNodeWithText("To Do").assertIsDisplayed()
        onNodeWithText("In Progress").assertIsDisplayed()
        onNodeWithText("Done").assertIsDisplayed()
    }

    @Test
    fun `새 태스크 버튼을 누르면 다이얼로그가 표시된다`() = runComposeUiTest {
        setContent {
            KanbanBoardScreen(
                projects = listOf(KanbanProject("안녕")),
            )
        }

        onNodeWithText("새 태스크 생성").performClick()
        onNodeWithText("제목 *").assertIsDisplayed()
    }

    @Test
    fun `태스크를 생성했을 때 칸반보드에 카드가 정상적으로 표시된다`() = runComposeUiTest {
        setContent {
            CompositionLocalProvider(LocalDensity provides Density(0.1f)) {
                KanbanBoardScreen(
                    projects = listOf(KanbanProject("안녕")),
                )
            }
        }

        onNodeWithText("새 태스크 생성").performClick()
        onNodeWithText("태스크 제목을 입력하세요.").performTextInput("안녕하세요")
        onNodeWithText("생성").performClick()
        onNodeWithText("5자 이내의 태그를 최대 5개까지 등록할 수 있습니다.").assertDoesNotExist()
        onNodeWithText("안녕하세요").assertIsDisplayed()
    }

    @Test
    fun `태스크를 생성했을 때 스낵바가 정상적으로 표시된다`() = runComposeUiTest {
        setContent {
            CompositionLocalProvider(LocalDensity provides Density(0.1f)) {
                KanbanBoardScreen(
                    projects = listOf(KanbanProject("안녕")),
                )
            }
        }

        onNodeWithText("새 태스크 생성").performClick()
        onNodeWithText("태스크 제목을 입력하세요.").performTextInput("안녕하세요")
        onNodeWithText("생성").performClick()
        onNodeWithText("새로운 태스크가 추가되었습니다.").assertIsDisplayed()
    }

    @Test
    fun `태스크를 이동했을 때 스낵바가 정상적으로 표시된다`() = runComposeUiTest {
        // Given
        setContent {
            CompositionLocalProvider(LocalDensity provides Density(0.1f)) {
                KanbanBoardScreen(
                    projects = listOf(KanbanProject("안녕")),
                )
            }
        }
        onNodeWithText("새 태스크 생성").performClick()
        onNodeWithText("태스크 제목을 입력하세요.").performTextInput("안녕하세요")
        onNodeWithText("생성").performClick()
        onNodeWithText("새로운 태스크가 추가되었습니다.").assertIsDisplayed()
        onNodeWithContentDescription("닫기").performClick()

        // When
        val taskBounds = onNodeWithText("안녕하세요")
            .fetchSemanticsNode()
            .boundsInRoot

        val doneBounds = onNodeWithText("Done")
            .fetchSemanticsNode()
            .boundsInRoot
        onNodeWithText("안녕하세요").performTouchInput {
            down(center)
            moveTo(
                position = Offset(
                    x = doneBounds.center.x - taskBounds.left,
                    y = doneBounds.center.y - taskBounds.top,
                ),
            )
            up()
        }

        // Then
        onNodeWithText("태스크가 이동되었습니다.").assertIsDisplayed()
    }

    @Test
    fun `태스크를 같은 상태로 이동했을 때 스낵바가 표시되지 않는다`() = runComposeUiTest {
        // Given
        setContent {
            CompositionLocalProvider(LocalDensity provides Density(0.1f)) {
                KanbanBoardScreen(
                    projects = listOf(KanbanProject("안녕")),
                )
            }
        }
        onNodeWithText("새 태스크 생성").performClick()
        onNodeWithText("태스크 제목을 입력하세요.").performTextInput("안녕하세요")
        onNodeWithText("생성").performClick()
        onNodeWithText("새로운 태스크가 추가되었습니다.").assertIsDisplayed()
        onNodeWithContentDescription("닫기").performClick()

        // When
        val taskBounds = onNodeWithText("안녕하세요")
            .fetchSemanticsNode()
            .boundsInRoot

        val todoBounds = onNodeWithText("To Do")
            .fetchSemanticsNode()
            .boundsInRoot

        onNodeWithText("안녕하세요").performTouchInput {
            down(center)
            moveTo(
                position = Offset(
                    x = todoBounds.center.x - taskBounds.left,
                    y = todoBounds.center.y - taskBounds.top,
                ),
            )
            up()
        }

        // Then
        onNodeWithText("태스크가 이동되었습니다.").assertDoesNotExist()
    }

    @Test
    fun `프로젝트에 알맞는 카드가 화면에 표시된다`() = runComposeUiTest {
        // Given: 각기 다른 타이틀을 가진 태스크 4개를 생성한다.
        val kanbanBoard = KanbanBoard(
            tasks = listOf(
                createKanbanTask(id = 0L, title = "안녕하세요"),
                createKanbanTask(id = 1L, title = "우아한테크코스"),
                createKanbanTask(id = 2L, title = "안드로이드"),
                createKanbanTask(id = 3L, title = "8기"),
            ),
        )
        // Given: '안녕', '잘가'의 타이틀을 가진 2개의 프로젝트를 생성한 후 각각 2개의 태스크를 추가한다.
        val kanbanProjects = listOf(KanbanProject("안녕"), KanbanProject("잘가"))
        kanbanProjects[0].addTaskId(0L)
        kanbanProjects[0].addTaskId(1L)
        kanbanProjects[1].addTaskId(2L)
        kanbanProjects[1].addTaskId(3L)
        setContent {
            CompositionLocalProvider(LocalDensity provides Density(0.1f)) {
                KanbanBoardScreen(
                    kanbanBoard = kanbanBoard,
                    projects = kanbanProjects,
                )
            }
        }
        onNodeWithText("잘가").performClick()

        // Then: '안녕' 프로젝트에 포함된 태스크는 보이지 않고 '잘가' 프로젝트에 포함된 태스크는 보인다.
        onNodeWithText("안녕하세요").assertDoesNotExist()
        onNodeWithText("우아한테크코스").assertDoesNotExist()
        onNodeWithText("안드로이드").assertIsDisplayed()
        onNodeWithText("8기").assertIsDisplayed()
    }
}
