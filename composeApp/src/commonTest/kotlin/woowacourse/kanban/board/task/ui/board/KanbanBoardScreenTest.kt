package woowacourse.kanban.board.task.ui.board

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.runComposeUiTest
import kotlin.test.Test
import woowacourse.kanban.board.task.domain.KanbanBoard
import woowacourse.kanban.board.task.domain.KanbanCard
import woowacourse.kanban.board.task.domain.KanbanStatus

@OptIn(ExperimentalTestApi::class)
class KanbanBoardScreenTest {
    private fun createBoardWithCard(status: KanbanStatus): KanbanBoard {
        return KanbanBoard(
            boardId = 0,
            title = "compose",
            cards = listOf(
                KanbanCard(
                    id = "1",
                    title = "test1",
                    status = status,
                    assigneeName = "조디악",
                ),
            ),
        )
    }

    @Test
    fun `새 태스크 생성 버튼 클릭 시 Dialog가 생성된다`() = runComposeUiTest {
        setContent {
            val scope = rememberCoroutineScope()
            KanbanBoardScreen(
                onAddCard = { _, _, _, _, _ -> },
                onEditCard = { _, _, _, _, _, _ -> },
                onDeleteCard = {},
                kanbanBoard = KanbanBoard(
                    boardId = 0,
                    title = "compose",
                    cards = listOf(),
                ),
                snackbarHostState = remember { SnackbarHostState() },
                scope = scope,
            )
        }

        onNodeWithText("새 태스크 생성").performClick()

        onNodeWithText("제목 *").assertIsDisplayed()
    }

    @Test
    fun `Dialog를 닫고 다시 열었을 때 이전 입력값이 초기화된다`() = runComposeUiTest {
        setContent {
            val scope = rememberCoroutineScope()
            KanbanBoardScreen(
                onAddCard = { _, _, _, _, _ -> },
                onEditCard = { _, _, _, _, _, _ -> },
                onDeleteCard = {},
                kanbanBoard = KanbanBoard(
                    boardId = 0,
                    title = "compose",
                    cards = listOf(),
                ),
                snackbarHostState = remember { SnackbarHostState() },
                scope = scope,
            )
        }

        onNodeWithText("새 태스크 생성").performClick()
        onNodeWithText("태스크 제목을 입력하세요").performTextInput("제목")
        onNodeWithText("취소").performClick()

        onNodeWithText("새 태스크 생성").performClick()
        onAllNodesWithText("제목").assertCountEquals(0)
        onNodeWithText("태스크 제목을 입력하세요").assertIsDisplayed()
    }

    @Test
    fun `카드 추가 성공 시 스낵바가 생성된다`() = runComposeUiTest {
        setContent {
            val scope = rememberCoroutineScope()
            KanbanBoardScreen(
                onAddCard = { _, _, _, _, _ -> },
                onEditCard = { _, _, _, _, _, _ -> },
                onDeleteCard = {},
                kanbanBoard = KanbanBoard(
                    boardId = 0,
                    title = "compose",
                    cards = listOf(),
                ),
                snackbarHostState = remember { SnackbarHostState() },
                scope = scope,
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
            val scope = rememberCoroutineScope()
            KanbanBoardScreen(
                onAddCard = { _, _, _, _, _ -> },
                onEditCard = { _, _, _, _, _, _ -> },
                onDeleteCard = {},
                kanbanBoard = KanbanBoard(
                    boardId = 0,
                    title = "compose",
                    cards = listOf(),
                ),
                snackbarHostState = remember { SnackbarHostState() },
                scope = scope,
            )
        }

        onNodeWithText("새 태스크 생성").performClick()
        onNodeWithText("태스크 제목을 입력하세요").performTextInput("제목")
        onNodeWithText("생성").performClick()
        onNodeWithText("새로운 태스크가 추가되었습니다.").assertIsDisplayed()

        mainClock.advanceTimeBy(5000L)

        onAllNodesWithText("새로운 태스크가 추가되었습니다.").assertCountEquals(0)
    }

    @Test
    fun `Review or Done 상태에서 태스크를 삭제하려고 할 때 스낵 바 노출`() = runComposeUiTest {
        setContent {
            val scope = rememberCoroutineScope()
            KanbanBoardScreen(
                onAddCard = { _, _, _, _, _ -> },
                onEditCard = { _, _, _, _, _, _ -> },
                onDeleteCard = {
                    throw IllegalArgumentException("해당 상태에서는 태스크 삭제가 불가합니다.")
                },
                kanbanBoard = createBoardWithCard(KanbanStatus.REVIEW),
                snackbarHostState = remember { SnackbarHostState() },
                scope = scope,
            )
        }

        onNodeWithText("test1").performClick()
        onNodeWithText("삭제").performClick()

        onNodeWithText("해당 상태에서는 태스크 삭제가 불가합니다.").assertIsDisplayed()
    }

    @Test
    fun `태스크가 수정되었을 때 스낵바 노출`() = runComposeUiTest {
        setContent {
            val scope = rememberCoroutineScope()
            KanbanBoardScreen(
                onAddCard = { _, _, _, _, _ -> },
                onEditCard = { _, _, _, _, _, _ -> },
                onDeleteCard = {},
                kanbanBoard = createBoardWithCard(KanbanStatus.TO_DO),
                snackbarHostState = remember { SnackbarHostState() },
                scope = scope,
            )
        }

        onNodeWithText("test1").performClick()
        onNodeWithText("수정").performClick()

        onNodeWithText("태스크가 수정되었습니다.").assertIsDisplayed()
    }

    @Test
    fun `태스크가 삭제되었을 때 스낵바 노출`() = runComposeUiTest {
        setContent {
            val scope = rememberCoroutineScope()
            KanbanBoardScreen(
                onAddCard = { _, _, _, _, _ -> },
                onEditCard = { _, _, _, _, _, _ -> },
                onDeleteCard = {},
                kanbanBoard = createBoardWithCard(KanbanStatus.TO_DO),
                snackbarHostState = remember { SnackbarHostState() },
                scope = scope,
            )
        }

        onNodeWithText("test1").performClick()
        onNodeWithText("삭제").performClick()

        onNodeWithText("태스크가 삭제되었습니다.").assertIsDisplayed()
    }
}
