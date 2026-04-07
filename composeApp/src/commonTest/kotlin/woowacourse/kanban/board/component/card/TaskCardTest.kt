package woowacourse.kanban.board.component.card

import androidx.compose.runtime.Composable
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.runComposeUiTest
import kotlin.test.Test
import woowacourse.kanban.board.constant.DEFAULT_CONTENT
import woowacourse.kanban.board.constant.DEFAULT_TITLE
import woowacourse.kanban.board.model.BoardData
import woowacourse.kanban.board.model.Nickname
import woowacourse.kanban.board.model.Status
import woowacourse.kanban.board.model.Tag

@OptIn(ExperimentalTestApi::class)
class TaskCardTest {

    private fun createBoard(
        title: String = DEFAULT_TITLE,
        content: String = DEFAULT_CONTENT,
        tags: List<Tag> = listOf(Tag("컴포넌트"), Tag("성능")),
        status: Status = Status.TODO,
        nickname: Nickname = Nickname.DINO,
    ) = BoardData(
        title = title,
        description = content,
        tags = tags,
        status = status,
        nickname = nickname,
    )

    @Composable
    private fun CreateUi(board: BoardData) {
        TaskCard(board)
    }

    @Test
    fun `모든 필드가 있는 카드`() = runComposeUiTest {
        // given
        val board = createBoard()

        setContent {
            CreateUi(board)
        }

        // when
        // then
        onNodeWithTag("제목").assertExists()
        onNodeWithTag("중간내용").assertExists()
        onNodeWithTag("테그목록").assertExists()
        onNodeWithTag("프로필").assertExists()
    }

    @Test
    fun `중간 내용 필드만 없는 카드`() = runComposeUiTest {
        // given
        val board = createBoard(content = "")

        setContent {
            CreateUi(board)
        }

        // when
        // then
        onNodeWithTag("중간내용").assertDoesNotExist()
    }

    @Test
    fun `태그 필드만 없는 카드`() = runComposeUiTest {
        // given
        val board = createBoard(tags = listOf())

        setContent {
            CreateUi(board)
        }

        // when
        // then
        onNodeWithTag("테그목록").assertDoesNotExist()
    }

    @Test
    fun `중간 내용과 태그 필드가 없는 카드`() = runComposeUiTest {
        // given
        val board = createBoard(content = "", tags = listOf())

        setContent {
            CreateUi(board)
        }

        // when
        // then
        onNodeWithTag("중간내용").assertDoesNotExist()
        onNodeWithTag("테그목록").assertDoesNotExist()
    }
}
