package woowacourse.kanban.board.component.card

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.runComposeUiTest
import kotlin.test.Test
import org.assertj.core.api.Assertions.assertThat
import woowacourse.kanban.board.domain.Task
import woowacourse.kanban.board.domain.Status
import woowacourse.kanban.board.domain.Tag

private const val DEFAULT_TITLE = "LazyColumn 컴포넌트 구현"
private const val DEFAULT_CONTENT = "세로 스크롤 가능한 리스트 컴포넌트를 만들고 성능 최적화를 적용합니다."
private const val DEFAULT_NAME = "다이노"

@OptIn(ExperimentalTestApi::class)
class TaskCardTest {
    private fun createBoard(
        id: String = "1",
        title: String = DEFAULT_TITLE,
        content: String = DEFAULT_CONTENT,
        tags: List<Tag> = listOf(Tag("컴포넌트"), Tag("성능")),
        status: Status = Status.TODO,
        nickname: String = DEFAULT_NAME,
    ) = Task(
        id = id,
        title = title,
        description = content,
        tags = tags,
        status = status,
        nickname = nickname,
    )

    @Test
    fun `모든 필드가 있는 카드`() = runComposeUiTest {
        // given
        val board = createBoard()

        setContent { TaskCard(board) }

        // when
        // then
        onNodeWithTag("제목", useUnmergedTree = true).assertExists()
        onNodeWithTag("중간내용", useUnmergedTree = true).assertExists()
        onNodeWithTag("테그목록", useUnmergedTree = true).assertExists()
        onNodeWithTag("프로필", useUnmergedTree = true).assertExists()
    }

    @Test
    fun `중간 내용 필드만 없는 카드`() = runComposeUiTest {
        // given
        val board = createBoard(content = "")

        setContent { TaskCard(board) }

        // when
        // then
        onNodeWithTag("중간내용", useUnmergedTree = true).assertDoesNotExist()
    }

    @Test
    fun `태그 필드만 없는 카드`() = runComposeUiTest {
        // given
        val board = createBoard(tags = listOf())

        setContent { TaskCard(board) }

        // when
        // then
        onNodeWithTag("테그목록", useUnmergedTree = true).assertDoesNotExist()
    }

    @Test
    fun `중간 내용과 태그 필드가 없는 카드`() = runComposeUiTest {
        // given
        val board = createBoard(content = "", tags = listOf())

        setContent { TaskCard(board) }

        // when
        // then
        onNodeWithTag("중간내용", useUnmergedTree = true).assertDoesNotExist()
        onNodeWithTag("테그목록", useUnmergedTree = true).assertDoesNotExist()
    }

    @Test
    fun `TaskCard 클릭 시 onClick 콜백이 호출된다`() = runComposeUiTest {
        // given
        val board = createBoard()
        var onClickCalled = false

        setContent {
            TaskCard(board, onClick = { onClickCalled = true })
        }

        // when
        onNodeWithTag("제목", useUnmergedTree = true).performClick()

        // then
        assertThat(onClickCalled).isTrue()
    }

    @Test
    fun `TaskCard 클릭 시 다이얼로그가 열린다`() = runComposeUiTest {
        // given
        val board = createBoard()
        var clickCount = 0

        setContent {
            TaskCard(
                board,
                onClick = { clickCount++ }
            )
        }

        // when
        onNodeWithTag("제목", useUnmergedTree = true).performClick()

        // then
        assertThat(clickCount).isEqualTo(1)
    }
}
