package woowacourse.kanban.board.component.dialog

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.runComposeUiTest
import kotlin.test.Test
import org.assertj.core.api.Assertions.assertThat
import woowacourse.kanban.board.component.dialog.selection.CoachButton
import woowacourse.kanban.board.component.dialog.selection.CommonButtonColumn
import woowacourse.kanban.board.component.dialog.selection.StatusButton
import woowacourse.kanban.board.domain.Status
import woowacourse.kanban.board.state.DialogState

@OptIn(ExperimentalTestApi::class)
class CommonButtonColumnTest {

    @Test
    fun `In Progress가 적힌 버튼을 클릭했을 때 boardDataState는 IN_PROGRESS 객체 상태를 가진다`() = runComposeUiTest {

        val status = Status.entries

        val dialogState = DialogState()

        setContent {
            CommonButtonColumn(
                header = "상태 *",
                items = status,
                isSelected = { dialogState.isSelectedStatus(it) },
                onValueChange = { dialogState.statusOnValueChange(it) },
            ) { status, isSelected, onClick ->
                StatusButton(status = status, isSelected = isSelected, onClick = onClick)
            }
        }

        onNodeWithText("In Progress").performClick()
        waitForIdle()
        assertThat(dialogState.statusValue).isEqualTo(Status.IN_PROGRESS)
    }

    @Test
    fun `페임스가 적힌 버튼을 클릭했을 때 boardDataState는 페임스 상태를 가진다`() = runComposeUiTest {
        val names = listOf("다이노", "페임스")

        val dialogState = DialogState()

        setContent {
            CommonButtonColumn(
                header = "상태 *",
                items = names,
                isSelected = { dialogState.isSelectedName(it) },
                onValueChange = { dialogState.nameOnValueChange(it) },
            ) { name, isSelected, onClick ->
                CoachButton(name = name, isSelected = isSelected, onClick = onClick)
            }
        }

        onNodeWithText("페임스").performClick()
        waitForIdle()
        assertThat(dialogState.nameValue).isEqualTo("페임스")
    }
}
