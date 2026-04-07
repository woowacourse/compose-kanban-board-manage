package woowacourse.kanban.board.kanban

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.runComposeUiTest
import kotlin.test.Test
import woowacourse.kanban.board.domain.model.Assignee
import woowacourse.kanban.board.ui.component.UserProfile

@OptIn(ExperimentalTestApi::class)
class AssigneeProfileUiTest {

    @Test
    fun `전달된 유저 이름 표시`() = runComposeUiTest {
        val given = "다이노"
        setContent {
            UserProfile(
                Assignee(given),
            )
        }

        onNodeWithText(given).assertIsDisplayed()
    }
}
