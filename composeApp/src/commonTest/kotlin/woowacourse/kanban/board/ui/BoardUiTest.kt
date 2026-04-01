package woowacourse.kanban.board.ui

import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.runComposeUiTest
import androidx.compose.ui.unit.dp
import kotlin.test.Test
import kotlinx.coroutines.launch
import woowacourse.kanban.board.domain.KanbanProject
import woowacourse.kanban.board.ui.constant.MockData
import woowacourse.kanban.board.ui.constant.SnackBarText
import woowacourse.kanban.board.ui.stateholder.BoardState

@OptIn(ExperimentalTestApi::class)
class BoardUiTest {

    @Test
    fun `새 태스크 생성 버튼을 누르면 생성 다이얼로그가 열려야 한다`() = runComposeUiTest {
        // given : 새 태스크 버튼이 주어진다
        setContent {
            val boardState = remember {
                BoardState(
                    KanbanProject(mutableListOf()),
                )
            }

            KanbanBoard(
                assignees = MockData.ASSIGNEES,
                boardState = boardState,
                projectTitle = "",
            )
        }

        // when : 새 태스크 버튼을 눌렀을 때
        onNodeWithText("새 태스크 생성").performClick()
        waitForIdle()
        // then : 생성 다이얼로그가 열려야 한다
        onNodeWithText("태스크 제목을 입력하세요").assertExists()
    }

    @Test
    fun `생성 다이얼로그에서 정상적인 값들을 입력 후 생성 버튼을 누르면 칸반 보드 리스트에 표시되어야 한다`() = runComposeUiTest {
        // given : 태스크 카드 정상 입력값이 주어진다
        setContent {
            val boardState = remember {
                BoardState(
                    KanbanProject(
                        mutableListOf(),
                    ),
                )
            }

            KanbanBoard(
                boardState = boardState,
                assignees = MockData.ASSIGNEES,
                projectTitle = "",
                onTaskCreated = { task ->
                    boardState.addTask(task)
                },
            )
        }

        // when : 생성 다이얼로그에서 정상적인 값을 입력 후 생성 버튼을 누를 때
        onNodeWithText("새 태스크 생성").performClick()
        waitForIdle()
        onNodeWithText("태스크 제목을 입력하세요").performTextInput("태스크제목")
        waitForIdle()
        onNodeWithText("생성").performClick()
        waitForIdle()

        // then : 칸반 보드에서 입력된 카드가 보여야 한다
        onNodeWithText("태스크제목").assertExists()
    }

    @Test
    fun `태스크 카드가 생성되고 스낵바가 출력되어야 한다`() = runComposeUiTest {
        // given : 태스크 카드 정상 입력값이 주어진다

        setContent {
            //  snackBarHostState를 외부에서 생성한 후 KanbanBoard로 주입하는 형식이기 때문에 해당 형식으로 바꿔야만 했음
            // KanbanBoard 안에서 스낵바를 출력하려면 Scaffold가 있는 부모인 KanbanPage의 snackBarHostState를 알아야 하는데
            // 그렇기 때문에 snackBarHostState를 파라미터로 받아야 했음
            val scope = rememberCoroutineScope()
            val snackBarHostState = remember { SnackbarHostState() }

            Scaffold(
                modifier = Modifier.size(2400.dp, 2400.dp),
                snackbarHost = {
                    SnackbarHost(snackBarHostState, modifier = Modifier.offset(y = (-50).dp)) { data ->
                        KanbanSnackBar(data)
                    }
                },
            ) { innerPadding ->
                val project = KanbanProject(mutableListOf())
                val boardState = BoardState(project)

                KanbanBoard(
                    assignees = MockData.ASSIGNEES,
                    boardState = boardState,
                    projectTitle = "",
                    modifier = Modifier.padding(innerPadding),
                    onTaskCreated = { task ->
                        scope.launch {
                            snackBarHostState.showSnackbar(SnackBarText.UPDATE_TASK)
                        }
                    },
                )
            }
        }

        // when : 생성 다이얼로그에서 정상적인 값을 입력 후 생성 버튼을 누를 때
        onNodeWithText("새 태스크 생성").performClick()
        waitForIdle()
        onNodeWithText("태스크 제목을 입력하세요").performTextInput("태스크제목")
        waitForIdle()
        onNodeWithText("생성").performClick()
        waitForIdle()

        // then : 칸반 보드 하단에 스낵바가 출력되어야 한다
        waitUntil(timeoutMillis = 5000) {
            onAllNodesWithText(SnackBarText.UPDATE_TASK)
                .fetchSemanticsNodes().isNotEmpty()
        }
    }
}
