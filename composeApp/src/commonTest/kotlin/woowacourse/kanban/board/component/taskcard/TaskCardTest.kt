package woowacourse.kanban.board.component.taskcard

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.runComposeUiTest
import kotlin.test.Test
import kotlinx.collections.immutable.toImmutableList
import woowacourse.kanban.board.model.taskcard.Description
import woowacourse.kanban.board.model.taskcard.Profile
import woowacourse.kanban.board.model.taskcard.Status
import woowacourse.kanban.board.model.taskcard.Tag
import woowacourse.kanban.board.model.taskcard.Tags
import woowacourse.kanban.board.model.taskcard.TaskCard
import woowacourse.kanban.board.model.taskcard.Title

@OptIn(ExperimentalTestApi::class)
class TaskCardTest {

    @Test
    fun `모든 필드가 있는 카드 - 제목, 설명, 태그, 닉네임 출력`() = runComposeUiTest {
        val tags = listOf("컴포넌트", "성능")
        val TaskCard = TaskCard(
            id = "task-with-profile",
            title = Title("LazyColumn 컴포넌트 구현"),
            description = Description("세로 스크롤 가능한 리스트 컴포넌트를 만들고 성능 최적화를 적용합니다."),
            tags = Tags(listOf(Tag("컴포넌트"), Tag("성능")).toImmutableList()),
            status = Status.PROGRESS,
            profile = Profile("다이노"),
        )
        setContent {
            TaskCard(data = TaskCard)
        }

        onNodeWithText("LazyColumn 컴포넌트 구현").assertExists()
        onNodeWithText("세로 스크롤 가능한 리스트 컴포넌트를 만들고 성능 최적화를 적용합니다.").assertExists()
        tags.forEach { tag ->
            onNodeWithText(tag).assertExists()
        }
        onNodeWithText("다이노").assertExists()
    }

    @Test
    fun `담당자가 없는 카드에서는 담당자 정보가 표시되지 않는다`() = runComposeUiTest {
        val taskCard = TaskCard(
            id = "task-without-profile",
            title = Title("담당자 없는 카드"),
            description = Description("설명"),
            tags = Tags(listOf(Tag("태그")).toImmutableList()),
            status = Status.TODO,
            profile = Profile.NONE,
        )

        setContent {
            TaskCard(data = taskCard)
        }

        onNodeWithText(Profile.NONE.nickname).assertDoesNotExist()
    }
}
