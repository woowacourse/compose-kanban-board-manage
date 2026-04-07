package woowacourse.kanban.board.task.ui.card

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.runComposeUiTest
import org.junit.Test

@OptIn(ExperimentalTestApi::class)
class KanbanCardTagsTest {
    @Test
    fun `빈 태그 리스트 테스트`() = runComposeUiTest {
        val title = "LazyColumn 컴포넌트 구현"
        val assigneeName = "다이노"
        val content = "세로 스크롤 가능한 리스트 컴포넌트를 만들고 성능 최적화를 적용합니다."

        setContent {
            KanbanCardItem(
                title = title,
                content = content,
                assigneeName = assigneeName,
                tags = emptyList(),
            )
        }

        onNodeWithContentDescription("칸반 카드 태그 목록").assertDoesNotExist()
    }
}
