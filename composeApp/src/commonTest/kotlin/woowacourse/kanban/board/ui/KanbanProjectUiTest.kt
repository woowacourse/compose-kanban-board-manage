package woowacourse.kanban.board.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.runComposeUiTest
import androidx.compose.ui.unit.dp
import kotlin.test.Test
import kotlinx.coroutines.launch
import woowacourse.kanban.board.ui.constant.MockData
import woowacourse.kanban.board.ui.constant.SnackBarText
import woowacourse.kanban.board.ui.stateholder.BoardState
import woowacourse.kanban.domain.TaskStatus

@OptIn(ExperimentalTestApi::class)
class KanbanProjectUiTest {

    @Test
    fun `프로젝트를 선택하면 프로젝트에 저장되어 있는 태스크들이 표시되어야 한다`() = runComposeUiTest {
        // given : 칸반 페이지가 주어지고 프로젝트 리스트는 칸반 페이지 내부에 MockData로 설정 되어 있다
        setContent {
            KanbanPage(
                projects = MockData.MOCK_PROJECTS,
                assignees = MockData.ASSIGNEES,
            )
        }

        // when : 프로젝트 버튼을 눌렀을 때
        onNodeWithText("Compose2").performClick()
        waitForIdle()

        // then : 프로젝트에 저장되어 있는 태스크들이 표시되어야 한다
        onNodeWithTag("In Progress")
            .onChildren()
            .assertCountEquals(3)
    }

    @Test
    fun `프로젝트를 선택하면 보드의 제목이 변경되어야 한다`() = runComposeUiTest {
        // given : 칸반 페이지가 주어지고 프로젝트 리스트는 칸반 페이지 내부에 MockData로 설정 되어 있다
        setContent {
            KanbanPage(
                projects = MockData.MOCK_PROJECTS,
                assignees = MockData.ASSIGNEES,
            )
        }

        // when : 가장 처음 프로젝트의 제목이 표시되고 다른 프로젝트 버튼을 눌렀을 때
        onNodeWithTag("headerTitle").assert(hasText("Compose1"))

        onNodeWithText("Compose2").performClick()
        waitForIdle()

        // then : 보드의 제목이 선택된 프로젝트의 이름으로 변경되어야 한다
        onNodeWithTag("headerTitle").assert(hasText("Compose2"))
    }

    @Test
    fun `사이드바에 프로젝트 리스트가 출력되어야 한다`() = runComposeUiTest {
        // given : 목 데이터가 주어진다
        val mock =
            MockData.MOCK_PROJECTS

        // when : 사이드바에 프로젝트 리스트가 표시될 때
        setContent {
            KanbanSidebar(
                projects = mock,
                onClick = { },
                selectedProjectIndex = 0,
            )
        }

        // then : 입력된 프로젝트들의 제목이 사이드바에 표시된다
        onNodeWithText("Compose1").assertExists()
        onNodeWithText("Compose2").assertExists()
        onNodeWithText("compose3 너무너무 길어진 프로젝트 이름").assertExists()
    }

    @Test
    fun `상태를 변경 했을 때 스낵바가 출력되어야 한다`() = runComposeUiTest {
        // given : snackBarHostState를 설정한 Scaffold와 BoardAction가 주어진다.
        lateinit var state: BoardState

        setContent {
            val scope = rememberCoroutineScope()
            val snackBarHostState = remember { SnackbarHostState() }

            val project = MockData.MOCK_PROJECTS.first()
            state = BoardState(
                project,
            )

            Scaffold(
                snackbarHost = {
                    SnackbarHost(snackBarHostState, modifier = Modifier.offset(y = (-50).dp)) { data ->
                        KanbanSnackBar(data)
                    }
                },
            ) { innerPadding ->
                Box(modifier = Modifier.padding(innerPadding))
            }

            // when : 상태 변경 함수를 호출했을 때
            state.changeStatus(
                taskId = 0,
                status = TaskStatus.DONE,
            )
            scope.launch { snackBarHostState.showSnackbar(SnackBarText.EDIT_TASK) }
        }

        // then : "태스크가 이동되었습니다" 스낵바가 출력되어야 한다.
        awaitIdle()
        onNodeWithText(SnackBarText.EDIT_TASK).assertExists()
    }
}
