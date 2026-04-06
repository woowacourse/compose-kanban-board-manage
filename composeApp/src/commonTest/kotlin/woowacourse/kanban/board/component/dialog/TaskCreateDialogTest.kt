package woowacourse.kanban.board.component.dialog

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.runComposeUiTest
import kotlin.test.Test
import woowacourse.kanban.board.component.dialog.layout.TaskCreateDialog
import woowacourse.kanban.board.domain.Status
import woowacourse.kanban.board.state.DialogMode
import woowacourse.kanban.board.state.DialogState

@OptIn(ExperimentalTestApi::class)
class TaskCreateDialogTest {

    @Test
    fun `CREATE 모드일 때 다이얼로그 제목이 새 태스크 생성이다`() = runComposeUiTest {
        val dialogState = DialogState()
        dialogState.mode = DialogMode.CREATE

        setContent {
            TaskCreateDialog(
                statuses = listOf(Status.TODO, Status.IN_PROGRESS, Status.REVIEW, Status.DONE),
                names = listOf("다이노", "테스터"),
                onTaskCreate = {},
                onEditTask = {},
                onDeleteTask = {},
                onDismissRequest = {},
                dialogState = dialogState,
            )
        }

        onNodeWithText("새 태스크 생성", useUnmergedTree = true).assertExists()
    }

    @Test
    fun `EDIT 모드일 때 다이얼로그 제목이 기존 태스크 수정이다`() = runComposeUiTest {
        val dialogState = DialogState()
        dialogState.mode = DialogMode.EDIT

        setContent {
            TaskCreateDialog(
                statuses = listOf(Status.TODO, Status.IN_PROGRESS, Status.REVIEW, Status.DONE),
                names = listOf("다이노", "테스터"),
                onTaskCreate = {},
                onEditTask = {},
                onDeleteTask = {},
                onDismissRequest = {},
                dialogState = dialogState,
            )
        }

        onNodeWithText("기존 태스크 수정", useUnmergedTree = true).assertExists()
    }

    @Test
    fun `To Do 상태일 때 없음 옵션이 담당자 선택지에 포함된다`() = runComposeUiTest {
        val dialogState = DialogState()
        dialogState.statusValue = Status.TODO

        setContent {
            TaskCreateDialog(
                statuses = listOf(Status.TODO, Status.IN_PROGRESS, Status.REVIEW, Status.DONE),
                names = listOf("다이노", "테스터"),
                onTaskCreate = {},
                onEditTask = {},
                onDeleteTask = {},
                onDismissRequest = {},
                dialogState = dialogState,
            )
        }

        onNodeWithText("없음", useUnmergedTree = true).assertExists()
    }

    @Test
    fun `In Progress 상태일 때 없음 옵션이 담당자 선택지에 포함되지 않는다`() = runComposeUiTest {
        val dialogState = DialogState()
        dialogState.statusValue = Status.IN_PROGRESS

        setContent {
            TaskCreateDialog(
                statuses = listOf(Status.TODO, Status.IN_PROGRESS, Status.REVIEW, Status.DONE),
                names = listOf("다이노", "테스터"),
                onTaskCreate = {},
                onEditTask = {},
                onDeleteTask = {},
                onDismissRequest = {},
                dialogState = dialogState,
            )
        }

    }
}
