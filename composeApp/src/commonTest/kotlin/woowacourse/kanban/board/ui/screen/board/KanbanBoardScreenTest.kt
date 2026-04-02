package woowacourse.kanban.board.ui.screen.board

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasText
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
import kotlin.test.Test

@Suppress("NonAsciiCharacters")
@OptIn(ExperimentalTestApi::class)
class KanbanBoardScreenTest {
    @Test
    fun `칸반보드 스크린의 카드홀더들이 정상적으로 화면에 표시된다`() = runComposeUiTest {
        setContent {
            CompositionLocalProvider(LocalDensity provides Density(0.1f)) {
                KanbanBoardScreen(
                    projects = listOf(KanbanProject("안녕")),
                )
            }
        }

        onNodeWithText("To Do").assertIsDisplayed()
        onNodeWithText("In Progress").assertIsDisplayed()
        onNodeWithText("Review").assertIsDisplayed()
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
        onNodeWithText(SnackbarMessage.TASK_CREATED.text).assertIsDisplayed()
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
        onNodeWithText("다이노").performClick()
        onNodeWithText("생성").performClick()
        onNodeWithText("새로운 태스크가 추가되었습니다.").assertIsDisplayed()
        onNodeWithContentDescription("닫기").performClick()

        // When
        val taskBounds = onNodeWithText("안녕하세요")
            .fetchSemanticsNode()
            .boundsInRoot

        val doneBounds = onNodeWithText("In Progress")
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
        onNodeWithText(SnackbarMessage.TASK_MOVED.text).assertIsDisplayed()
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
        onNodeWithText(SnackbarMessage.TASK_MOVED.text).assertDoesNotExist()
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

    @Test
    fun `태스크를 삭제했을 때 스낵바가 정상적으로 표시된다`() = runComposeUiTest {
        // Given: 태스크 한 개를 생성한다.
        setContent {
            CompositionLocalProvider(LocalDensity provides Density(0.1f)) {
                KanbanBoardScreen(
                    projects = listOf(KanbanProject("안녕")),
                )
            }
        }
        onNodeWithText("새 태스크 생성").performClick()
        onNodeWithText("태스크 제목을 입력하세요.").performTextInput("삭제할 태스크")
        onNodeWithText("생성").performClick()

        // When: 해당 태스크를 클릭한 후 삭제한다.
        onNodeWithText("삭제할 태스크").performClick()
        onNodeWithText("삭제").performClick()

        // Then: 삭제할 태스크 라는 이름을 가진 태스크가 사라지고, 스낵바가 표시된다.
        onNodeWithText("삭제할 태스크").assertDoesNotExist()
        onNodeWithText(SnackbarMessage.TASK_DELETED.text).assertIsDisplayed()
    }

    @Test
    fun `태스크를 수정했을 때 스낵바가 정상적으로 표시된다`() = runComposeUiTest {
        // Given: 태스크 한 개를 생성한다.
        setContent {
            CompositionLocalProvider(LocalDensity provides Density(0.1f)) {
                KanbanBoardScreen(
                    projects = listOf(KanbanProject("안녕")),
                )
            }
        }
        onNodeWithText("새 태스크 생성").performClick()
        onNodeWithText("태스크 제목을 입력하세요.").performTextInput("수정할 태스크")
        onNodeWithText("생성").performClick()

        // When: 해당 태스크를 클릭한 후 삭제한다.
        onNodeWithText("수정할 태스크").performClick()
        onNodeWithText("수정").performClick()

        // Then: 수정할 태스크 라는 이름을 가진 태스크가 여전히 존재하고, 스낵바가 표시된다.
        onNodeWithText("수정할 태스크").assertExists()
        onNodeWithText(SnackbarMessage.TASK_EDITED.text).assertIsDisplayed()
    }

    @Test
    fun `불가능한 상태 전이를 시도할 때 스낵바가 정상적으로 표시된다`() = runComposeUiTest {
        // Given: To Do 상태의 태스크 한 개를 생성한다.
        setContent {
            CompositionLocalProvider(LocalDensity provides Density(0.1f)) {
                KanbanBoardScreen(
                    projects = listOf(KanbanProject("안녕")),
                )
            }
        }
        onNodeWithText("새 태스크 생성").performClick()
        onNodeWithText("태스크 제목을 입력하세요.").performTextInput("안녕하세요")
        onNodeWithText("다이노").performClick()
        onNodeWithText("생성").performClick()
        onNodeWithText("새로운 태스크가 추가되었습니다.").assertIsDisplayed()
        onNodeWithContentDescription("닫기").performClick()

        // When: To Do에 있는 태스크를 Done 으로 이동한다.
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

        // Then: 이동 불가능 안내 메시지가 출력된다.
        onNodeWithText(SnackbarMessage.TASK_MOVE_NOT_ALLOWED.text).assertIsDisplayed()
    }

    @Test
    fun `담당자를 지정하지 않고 이동을 시도할 때 스낵바가 정상적으로 표시된다`() = runComposeUiTest {
        // Given: 담당자를 지정하지 않은 태스크 한 개를 생성한다.
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
        onNodeWithText(SnackbarMessage.TASK_CREATED.text).assertIsDisplayed()
        onNodeWithContentDescription("닫기").performClick()

        // When
        val taskBounds = onNodeWithText("안녕하세요")
            .fetchSemanticsNode()
            .boundsInRoot

        val doneBounds = onNodeWithText("In Progress")
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

        // Then: 담당자 지정 안내 메시지가 출력된다.
        onNodeWithText(SnackbarMessage.TASK_ASSIGNEE_REQUIRED.text).assertIsDisplayed()
    }

    @Test
    fun `삭제 불가능한 태스크의 삭제 버튼을 눌렀을 때 스낵바가 정상적으로 표시된다`() = runComposeUiTest {
        // Given: Done 상태의 태스크 한 개를 생성한다.
        setContent {
            CompositionLocalProvider(LocalDensity provides Density(0.1f)) {
                KanbanBoardScreen(
                    projects = listOf(KanbanProject("안녕")),
                )
            }
        }
        onNodeWithText("새 태스크 생성").performClick()
        onNodeWithText("태스크 제목을 입력하세요.").performTextInput("삭제할 태스크")
        onNode(hasText("Review") and hasClickAction()).performClick()
        onNodeWithText("다이노").performClick()
        onNodeWithText("생성").performClick()

        // When: 해당 태스크를 클릭한 후 삭제 버튼을 클릭한다.
        onNodeWithText("삭제할 태스크").performClick()
        onNodeWithText("삭제").performClick()

        // Then: 삭제할 태스크 라는 이름을 가진 태스크가 여전히 존재하고, 스낵바가 표시된다.
        onNodeWithText("삭제할 태스크").assertExists()
        onNodeWithText(SnackbarMessage.TASK_DELETE_NOT_ALLOWED.text).assertIsDisplayed()
    }

    @Test
    fun `태스크를 클릭해 태스크를 수정할 때 수정된 태스크가 화면에 잘 나타난다`() = runComposeUiTest {
        // Given: 태스크 한 개를 생성한다.
        setContent {
            CompositionLocalProvider(LocalDensity provides Density(0.1f)) {
                KanbanBoardScreen(
                    projects = listOf(KanbanProject("안녕")),
                )
            }
        }
        onNodeWithText("새 태스크 생성").performClick()
        onNodeWithText("태스크 제목을 입력하세요.").performTextInput("수정할 태스크")
        onNodeWithText("생성").performClick()

        // When: 해당 태스크를 클릭한 후 삭제 버튼을 클릭한다.
        onNodeWithText("수정할 태스크").performClick()
        onNodeWithText("태스크에 대한 자세한 설명을 입력하세요.").performTextInput("이것은 수정되었습니다.")
        onNodeWithText("수정").performClick()

        // Then: 수정할 태스크는 사라지고 수정된 태스크가 화면에 표시된다.
        onNodeWithText("이것은 수정되었습니다.").assertExists()
    }
}
