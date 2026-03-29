package woowacourse.kanban.board.ui.board

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.runComposeUiTest
import java.util.UUID
import org.junit.Test
import woowacourse.kanban.board.domain.Project
import woowacourse.kanban.board.domain.Task
import woowacourse.kanban.board.domain.Tasks

@OptIn(ExperimentalTestApi::class)
class ProjectScreenTest {
    @Test
    fun `프로젝트 제목 리스트가 노출된다`() = runComposeUiTest {
        setContent {
            ProjectScreen(
                state = projectScreenState,
                authors = authors,
            )
        }

        onNode(hasText("Compose1") and hasClickAction()).assertExists()
        onNode(hasText("Compose2") and hasClickAction()).assertExists()
    }

    @Test
    fun `프로젝트 버튼을 클릭하면 해당 프로젝트의 태스크가 노출된다`() = runComposeUiTest {
        setContent {
            ProjectScreen(
                state = projectScreenState,
                authors = authors,
            )
        }

        onNodeWithText("Compose2", useUnmergedTree = true).performClick()
        onNodeWithText("test_title", useUnmergedTree = true).assertExists()
    }

    companion object {
        val authors = listOf("페임스", "다이노")
        val projectScreenState = ProjectScreenState(
            listOf(
                Project(
                    name = "Compose1",
                    tasks = Tasks(emptyList()),
                ),
                Project(
                    name = "Compose2",
                    tasks = Tasks(
                        listOf(
                            Task(
                                id = UUID.randomUUID(),
                                title = "test_title",
                                author = "elle",
                            ),
                        ),
                    ),
                ),
            ),
        )
    }
}
