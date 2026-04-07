package woowacourse.kanban.board.component.dialog

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.runComposeUiTest
import kotlin.test.Test
import woowacourse.kanban.board.model.DialogStatus
import woowacourse.kanban.board.model.Status
import woowacourse.kanban.board.state.KanbanBoardState

@OptIn(ExperimentalTestApi::class)
class TaskDialogTest {

    @Test
    fun `모달의 상태가 CREATE일 경우 생성, 취소 버튼이 존재해야 한다`() = runComposeUiTest {

        val kanbanBoardState = KanbanBoardState()

        setContent {
            TaskDialog(
                boardDataState = kanbanBoardState.boardDataState,
                dialogStatus = DialogStatus.CREATE,
                onTaskCreate = {},
                onEditTask = {},
                onDeleteTask = {},
                onDismissRequest = {},
            )
        }

        onNodeWithText("생성").assertExists()
        onNodeWithText("취소").assertExists()
    }

    @Test
    fun `모달의 상태가 EDIT이고, 보드 데이터의 상태가 TODO일 경우 수정, 삭제, 취소 버튼이 존재해야 한다`() = runComposeUiTest {
        val kanbanBoardState = KanbanBoardState()

        setContent {
            TaskDialog(
                boardDataState = kanbanBoardState.boardDataState,
                dialogStatus = DialogStatus.EDIT,
                onTaskCreate = {},
                onEditTask = {},
                onDeleteTask = {},
                onDismissRequest = {},
            )
        }

        onNodeWithText("수정").assertExists()
        onNodeWithText("삭제").assertExists()
        onNodeWithText("취소").assertExists()
    }

    @Test
    fun `보드 데이터의 상태가 TODO가 아닐 경우 담당자의 없음 버튼이 존재하지 않는다`() = runComposeUiTest {
        val boardDataState = KanbanBoardState().boardDataState

        setContent {
            boardDataState.statusValue = Status.IN_PROGRESS

            TaskDialog(
                boardDataState = boardDataState,
                dialogStatus = DialogStatus.CREATE,
                onTaskCreate = {},
                onEditTask = {},
                onDeleteTask = {},
                onDismissRequest = {},
            )
        }

        onNodeWithText("없음").assertDoesNotExist()
    }

    @Test
    fun `모달의 상태가 EDIT이고, 보드 데이터의 상태가 REVIEW일 경우 담당자의 없음 버튼이 존재하지 않는다`() = runComposeUiTest {
        val boardDataState = KanbanBoardState().boardDataState

        setContent {
            boardDataState.statusValue = Status.REVIEW

            TaskDialog(
                boardDataState = boardDataState,
                dialogStatus = DialogStatus.EDIT,
                onTaskCreate = {},
                onEditTask = {},
                onDeleteTask = {},
                onDismissRequest = {},
            )
        }

        onNodeWithText("없음").assertDoesNotExist()
    }
}
