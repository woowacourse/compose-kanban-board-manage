package woowacourse.kanban.board.task.ui.card

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.runComposeUiTest
import org.junit.Test
import woowacourse.kanban.board.task.domain.KanbanCard
import woowacourse.kanban.board.task.domain.KanbanStatus

@OptIn(ExperimentalTestApi::class)
class KanbanCardTagsTest {
    @Test
    fun `빈 태그 리스트 테스트`() = runComposeUiTest {
        val kanbanCard = KanbanCard(
            title = "LazyColumn 컴포넌트 구현",
            assigneeName = "바드",
            content = "세로 스크롤 가능한 리스트 컴포넌트를 만들고 성능 최적화를 적용합니다.",
            status = KanbanStatus.TO_DO,
        )

        setContent {
            KanbanCardItem(
                kanbanCard = kanbanCard,
                onCardClick = {},
            )
        }

        onNodeWithContentDescription("칸반 카드 태그 목록").assertDoesNotExist()
    }
}
