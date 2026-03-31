@file:Suppress("NonAsciiCharacters")

package woowacourse.kanban.board.ui.component.board

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.runComposeUiTest
import kotlin.test.Test
import woowacourse.kanban.board.domain.KanbanTask
import woowacourse.kanban.board.domain.Status

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
                cards = listOf(
                    KanbanTask(
                        title = "LazyColumn 컴포넌트 구현",
                        description = "세로 스크롤 가능한 리스트 컴포넌트를 만들고 성능 최적화를 적용합니다.",
                        tags = listOf("컴포넌트", "성능"),
                        status = Status.TO_DO,
                        assignee = "다이노",
                    ),
                    KanbanTask(
                        title = "LazyColumn 컴포넌트 구현",
                        description = "세로 스크롤 가능한 리스트 컴포넌트를 만들고 성능 최적화를 적용합니다.",
                        tags = listOf("컴포넌트", "성능"),
                        status = Status.TO_DO,
                        assignee = "다이노",
                    ),
                ),
            )
        }

        onNodeWithText("2").assertIsDisplayed()
    }
}
