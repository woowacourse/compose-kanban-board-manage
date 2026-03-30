package woowacourse.kanban.card

import androidx.compose.runtime.Composable
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.runComposeUiTest
import kotlin.test.Test
import woowacourse.kanban.card.constant.DEFAULT_CONTENT
import woowacourse.kanban.card.constant.DEFAULT_NAME
import woowacourse.kanban.card.constant.DEFAULT_TITLE
import woowacourse.kanban.card.ui.KanbanCard
import woowacourse.kanban.domain.BoardData
import woowacourse.kanban.domain.Nickname
import woowacourse.kanban.domain.Tags
import woowacourse.kanban.domain.Title

@OptIn(ExperimentalTestApi::class)
class BoardTest {

    private fun createBoard(
        title: String = DEFAULT_TITLE,
        content: String = DEFAULT_CONTENT,
        tags: List<String> = listOf("컴포넌트", "성능"),
        nickname: String = DEFAULT_NAME,
    ) = BoardData(
        title = Title(title),
        content = content,
        tags = Tags(tags),
        nickname = Nickname(nickname),
    )

    @Composable
    private fun CreateUi(board: BoardData) {
        KanbanCard(board)
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
