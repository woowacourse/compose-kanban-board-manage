package woowacourse.kanban.board.component

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.runComposeUiTest
import kanbanboard.composeapp.generated.resources.Res
import kanbanboard.composeapp.generated.resources.profile
import kotlin.test.Test
import kotlinx.collections.immutable.toImmutableList
import woowacourse.kanban.board.component.sample.ProjectPreviewData
import woowacourse.kanban.board.component.workspace.WorkSpace
import woowacourse.kanban.board.model.taskcard.Assignee

@OptIn(ExperimentalTestApi::class)
class SideBarTest {

    @Test
    fun `사이트바에 등록된 프로젝트의 타이틀이 모두 출력된다`() = runComposeUiTest {
        val projects = ProjectPreviewData().values.toImmutableList()
        val assignees = listOf(
            Assignee("다이노", Res.drawable.profile),
            Assignee("페임스", Res.drawable.profile)
        ).toImmutableList()
        setContent {
            WorkSpace(
                projects = projects,
                assignees = assignees
            )
        }
        onAllNodesWithText("Compose1").assertCountEquals(2)
        onNodeWithText("Compose2").assertIsDisplayed()
        onNodeWithText("Compose3너무너무길경우에는 말줄임표로 표시됩니다.").assertIsDisplayed()
    }
}
