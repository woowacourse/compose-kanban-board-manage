package woowacourse.kanban.board.component.taskcard

import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.runComposeUiTest
import kotlin.test.Test
import kotlinx.collections.immutable.toImmutableList
import woowacourse.kanban.board.model.taskcard.TaskTag
import woowacourse.kanban.board.model.taskcard.TaskTags

@OptIn(ExperimentalTestApi::class)
class TaskTagsTest {

    @Test
    fun `Tags에 빈 태그 리스트가 들어오면 Tags 컴포넌트가 출력되지 않는다`() = runComposeUiTest {
        setContent {
            TagsSection(
                taskTags = TaskTags(listOf<TaskTag>().toImmutableList()),
                modifier = Modifier.testTag("tags"),
            )
        }
        onNodeWithTag("tags").assertDoesNotExist()
    }

    @Test
    fun `Tags에 요소가 1개 이상인 태그 리스트가 들어오면 Tags 컴포넌트가 출력된다`() = runComposeUiTest {
        setContent {
            TagsSection(
                taskTags = TaskTags(value = listOf(TaskTag("컴포넌트"), TaskTag("컴포넌트1")).toImmutableList()),
                modifier = Modifier.testTag("tags"),
            )
        }
        onNodeWithTag("tags").assertExists()
    }
}
