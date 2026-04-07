package woowacourse.kanban.board.component.kanbanBoard

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.runComposeUiTest
import org.junit.Test
import woowacourse.kanban.board.constant.DEFAULT_CONTENT
import woowacourse.kanban.board.constant.DEFAULT_TITLE
import woowacourse.kanban.board.constant.MAX_CONTENT
import woowacourse.kanban.board.constant.MAX_TITLE
import woowacourse.kanban.board.model.BoardData
import woowacourse.kanban.board.model.Nickname
import woowacourse.kanban.board.model.Status
import woowacourse.kanban.board.model.StatusColor
import woowacourse.kanban.board.model.Tag

@OptIn(ExperimentalTestApi::class)
class StatusCardManageBoxTest {

    private val boardList = listOf(
        BoardData(
            title = DEFAULT_TITLE,
            description = DEFAULT_CONTENT,
            tags = listOf(Tag("컴포넌트"), Tag("성능")),
            status = Status.TODO,
            nickname = Nickname.DINO,
        ),
        BoardData(
            title = DEFAULT_TITLE,
            tags = listOf(Tag("컴포넌트"), Tag("성능")),
            status = Status.TODO,
            nickname = Nickname.DINO,
        ),
        BoardData(
            title = DEFAULT_TITLE,
            description = DEFAULT_CONTENT,
            status = Status.IN_PROGRESS,
            nickname = Nickname.DINO,
        ),
        BoardData(
            title = DEFAULT_TITLE,
            status = Status.TODO,
            nickname = Nickname.DINO,
        ),
        BoardData(
            title = MAX_TITLE,
            description = MAX_CONTENT,
            tags = listOf(Tag("너무너무"), Tag("긴태그"), Tag("최대로"), Tag("5자까지"), Tag("5개제한임")),
            status = Status.DONE,
            nickname = Nickname.DINO,
        ),
    )

    @Test
    fun `boardList 중에 TODO에 해당하는 카드 3개를 박스에 그린다`() = runComposeUiTest {

        val status = Status.TODO

        setContent {
            StatusCardManageBox(
                boardList = boardList,
                status = status,
                statusColor = StatusColor.getStatusColor(status),
            )
        }

        onNodeWithText("3").assertExists()
    }
}
