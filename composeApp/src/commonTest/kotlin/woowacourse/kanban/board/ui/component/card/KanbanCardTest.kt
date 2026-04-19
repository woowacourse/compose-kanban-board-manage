package woowacourse.kanban.board.ui.component.card

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.runComposeUiTest
import kotlin.test.Test
import woowacourse.kanban.board.domain.KanbanTask
import woowacourse.kanban.board.domain.dialog.Status

@OptIn(ExperimentalTestApi::class)
class KanbanCardTest {

    @Test
    fun `모든 정보가 있는 경우 - 제목, 설명, 태그, 담당자가 모두 노출된다`() = runComposeUiTest {
        // Given
        val task = KanbanTask(
            title = "새로운 기능 구현",
            description = "이 기능은 매우 중요합니다.",
            tags = listOf("긴급", "백엔드"),
            status = Status.TO_DO,
            assignee = "아키",
        )

        // When
        setContent {
            KanbanCard(
                title = task.title,
                description = task.description,
                tags = task.tags,
                crewName = task.assignee,
                onClick = {},
            )
        }

        // Then
        onNodeWithText("새로운 기능 구현").assertIsDisplayed()
        onNodeWithText("이 기능은 매우 중요합니다.").assertIsDisplayed()
        onNodeWithText("긴급").assertIsDisplayed()
        onNodeWithText("백엔드").assertIsDisplayed()
        onNodeWithText("아키").assertIsDisplayed()
    }

    @Test
    fun `설명이 없는 경우 - 설명 영역이 노출되지 않는다`() = runComposeUiTest {
        // Given
        val task = KanbanTask(
            title = "새로운 기능 구현",
            description = null,
            tags = listOf("긴급"),
            status = Status.TO_DO,
            assignee = "아키",
        )

        // When
        setContent {
            KanbanCard(
                title = task.title,
                description = task.description,
                tags = task.tags,
                crewName = task.assignee,
                onClick = {},
            )
        }

        // Then
        onNodeWithText("새로운 기능 구현").assertIsDisplayed()
        onNodeWithText("아키").assertIsDisplayed()
        onNodeWithText("이 기능은 매우 중요합니다.").assertDoesNotExist()
    }

    @Test
    fun `태그가 없는 경우 - 태그 영역이 노출되지 않는다`() = runComposeUiTest {
        // Given
        val task = KanbanTask(
            title = "새로운 기능 구현",
            description = "설명입니다.",
            tags = emptyList(),
            status = Status.TO_DO,
            assignee = "아키",
        )

        // When
        setContent {
            KanbanCard(
                title = task.title,
                description = task.description,
                tags = task.tags,
                crewName = task.assignee,
                onClick = {},
            )
        }

        // Then
        onNodeWithText("새로운 기능 구현").assertIsDisplayed()
        onNodeWithText("설명입니다.").assertIsDisplayed()
        onNodeWithText("긴급").assertDoesNotExist()
    }
}
