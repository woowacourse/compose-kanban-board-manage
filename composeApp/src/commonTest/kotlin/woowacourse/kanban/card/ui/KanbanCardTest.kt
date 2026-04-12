package woowacourse.kanban.card.ui

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.runComposeUiTest
import org.junit.Test
import woowacourse.kanban.card.KanbanCard
import woowacourse.kanban.domain.task.Assignee
import woowacourse.kanban.domain.task.Tags
import woowacourse.kanban.domain.task.TaskData
import woowacourse.kanban.domain.task.Title

@OptIn(ExperimentalTestApi::class)
class KanbanCardTest {
    @Test
    fun `assignee가 NONE이면 담당자가 표시되지 않는다`() = runComposeUiTest {
        // given: Assignee가 NONE인 태스크가 주어진다
        val task = TaskData(
            title = Title("제목"),
            content = "내용",
            tags = Tags(),
            assignee = Assignee.NONE,
        )

        // when: 태스크 카드를 생성할 때
        setContent {
            KanbanCard(task)
        }

        // 담당자가 표시되지 않는다
        onNodeWithTag("프로필", useUnmergedTree = true).assertDoesNotExist()
    }

    @Test
    fun `assignee가 NONE이 아니면 담당자가 표시된다`() = runComposeUiTest {
        // given: Assignee가 NONE이 아닌 태스크가 주어진다
        val task = TaskData(
            title = Title("제목"),
            content = "내용",
            tags = Tags(),
            assignee = Assignee.FAMES,
        )

        // when: 태스크 카드를 생성할 때
        setContent {
            KanbanCard(task)
        }

        // 담당자가 표시된다
        onNodeWithTag("프로필", useUnmergedTree = true).assertExists()
    }
}
