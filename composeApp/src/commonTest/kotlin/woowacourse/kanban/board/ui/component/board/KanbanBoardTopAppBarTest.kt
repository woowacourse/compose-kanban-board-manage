@file:Suppress("NonAsciiCharacters")

package woowacourse.kanban.board.ui.component.board

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.runComposeUiTest
import kotlin.test.Test

@OptIn(ExperimentalTestApi::class)
class KanbanBoardTopAppBarTest {
    @Test
    fun `제목이 화면에 표시된다`() = runComposeUiTest {
        val completeCount = 3
        val totalCount = 6
        val title = "안녕하세요 제목입니다"

        setContent {
            KanbanBoardTopAppBar(
                title = title,
                progress = 0.5f,
                progressPercent = 50,
                completeCount = completeCount,
                totalCount = totalCount,
                onNewTaskClick = { },
            )
        }

        onNodeWithText(title).assertIsDisplayed()
    }
}
