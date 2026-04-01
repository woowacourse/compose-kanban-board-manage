@file:Suppress("NonAsciiCharacters")

package woowacourse.kanban.board.ui.component.board

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.runComposeUiTest
import woowacourse.kanban.board.domain.Assignee
import kotlin.test.Test
import woowacourse.kanban.board.domain.KanbanTask
import woowacourse.kanban.board.domain.Status
import woowacourse.kanban.board.fixture.createKanbanTasks

@OptIn(ExperimentalTestApi::class)
class CardHolderTest {
    @Test
    fun `리스트의 개수가 화면에 잘 표시된다`() = runComposeUiTest {
        setContent {
            CardHolder(
                title = "To Do",
                mainColor = Color(0xFF155DFC),
                bodyColor = Color(0xFFEFF6FF),
                borderColor = Color(0xFFBEDBFF),
                tasks = createKanbanTasks(2, 0L),
            )
        }

        onNodeWithText("2").assertIsDisplayed()
    }
}
