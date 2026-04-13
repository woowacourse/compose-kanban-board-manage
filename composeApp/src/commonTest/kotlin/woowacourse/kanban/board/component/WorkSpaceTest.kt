package woowacourse.kanban.board.component

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.runComposeUiTest
import kotlin.test.Test
import kotlinx.collections.immutable.toImmutableList
import woowacourse.kanban.board.component.sample.ProjectPreviewData
import woowacourse.kanban.board.model.taskcard.Profile
import woowacourse.kanban.board.model.workspace.WorkSpace as WorkSpaceModel

@OptIn(ExperimentalTestApi::class)
class WorkSpaceTest {

    @Test
    fun `사이트탭에 등록된 프로젝트의 타이틀이 모두 출력된다`() = runComposeUiTest {
        val projects = ProjectPreviewData().values.toImmutableList()
        val workSpace = WorkSpaceModel(projects)
        val profiles = listOf(
            Profile("다이노"),
            Profile("페임스")
        ).toImmutableList()
        setContent {
            WorkSpace(
                workSpace = workSpace,
                profiles = profiles
            )
        }
        onAllNodesWithText("Compose1").assertCountEquals(2)
        onNodeWithText("Compose2").assertIsDisplayed()
        onNodeWithText("Compose3너무너무길경우에는 말줄임표로 표시됩니다.").assertIsDisplayed()
    }
}
