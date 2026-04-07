package woowacourse.kanban.board.component.dialog

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.runComposeUiTest
import kotlin.test.Test
import org.assertj.core.api.Assertions.assertThat
import woowacourse.kanban.board.model.Nickname
import woowacourse.kanban.board.model.Status
import woowacourse.kanban.board.state.BoardDataState

@OptIn(ExperimentalTestApi::class)
class CommonButtonColumnTest {

    @Test
    fun `In Progress가 적힌 버튼을 클릭했을 때 boardDataState는 IN_PROGRESS 객체 상태를 가진다`() = runComposeUiTest {

        val status = Status.entries

        val boardDataState = BoardDataState()

        setContent {
            CommonButtonColumn(
                header = "상태 *",
                items = status,
                isSelected = { boardDataState.isSelectedStatus(it) },
                onValueChange = { boardDataState.statusOnValueChange(it) },
            ) { status, isSelected, onClick ->
                StatusButton(status = status, isSelected = isSelected, onClick = onClick)
            }
        }

        onNodeWithText("In Progress").performClick()
        waitForIdle()
        assertThat(boardDataState.statusValue).isEqualTo(Status.IN_PROGRESS)
    }

    @Test
    fun `페임스가 적힌 버튼을 클릭했을 때 boardDataState는 "페임스" 상태를 가진다`() = runComposeUiTest {
        val names = listOf(Nickname.DINO, Nickname.PAMES)

        val boardDataState = BoardDataState()

        setContent {
            CommonButtonColumn(
                header = "상태 *",
                items = names,
                isSelected = { boardDataState.isSelectedName(it) },
                onValueChange = { boardDataState.nameOnValueChange(it) },
            ) { name, isSelected, onClick ->
                CoachButton(name = boardDataState.getNickname(name), isSelected = isSelected, onClick = onClick)
            }
        }

        onNodeWithText("페임스").performClick()
        waitForIdle()
        assertThat(boardDataState.nameValue).isEqualTo(Nickname.PAMES)
    }
}
