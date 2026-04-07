package woowacourse.kanban.board.component.kanbanBoard

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.runComposeUiTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import woowacourse.kanban.board.component.dialog.TaskDialog
import woowacourse.kanban.board.model.DialogStatus
import woowacourse.kanban.board.state.KanbanBoardState

@OptIn(ExperimentalTestApi::class)
class CreateAlertSnackBarTest {

    @Test
    fun `카드가 생성됐을 경우 스낵바가 나타난다`() = runComposeUiTest {
        val kanbanBoardState = KanbanBoardState()

        setContent {
            TaskDialog(
                boardDataState = kanbanBoardState.boardDataState,
                dialogStatus = DialogStatus.CREATE,
                onTaskCreate = { kanbanBoardState.onTaskCreate() },
                onEditTask = { kanbanBoardState.onEditTask() },
                onDeleteTask = { kanbanBoardState.onDeleteTask() },
                onDismissRequest = { kanbanBoardState.onDismissRequest() },
            )
        }
        onNodeWithText("태스크 제목을 입력하세요").performTextInput("제목입력")
        onNodeWithText("제목입력").assertExists()
        onNodeWithText("생성").performClick()

        assertThat(kanbanBoardState.isShowSnackBar).isEqualTo(true)
    }

    @Test
    fun `스낵바의 닫기 버튼을 눌렀을 경우 스낵바가 사라진다`() = runComposeUiTest {
        var isShowSnackBar = true

        setContent {
            CreateAlertSnackBar(
                text = "새로운 태스크가 추가되었습니다.",
                onClick = {
                    isShowSnackBar = false
                },
            )
        }

        onNodeWithContentDescription("닫기 버튼").performClick()
        assertThat(isShowSnackBar).isEqualTo(false)
    }
}
