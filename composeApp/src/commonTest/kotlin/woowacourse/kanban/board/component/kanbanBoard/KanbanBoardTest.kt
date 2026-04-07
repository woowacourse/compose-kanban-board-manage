package woowacourse.kanban.board.component.kanbanBoard

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.getUnclippedBoundsInRoot
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.runComposeUiTest
import kotlin.test.Test
import woowacourse.kanban.board.constant.DEFAULT_CONTENT
import woowacourse.kanban.board.constant.DEFAULT_TITLE
import woowacourse.kanban.board.constant.MAX_CONTENT
import woowacourse.kanban.board.constant.MAX_TITLE
import woowacourse.kanban.board.model.BoardData
import woowacourse.kanban.board.model.KanbanBoardData
import woowacourse.kanban.board.model.Nickname
import woowacourse.kanban.board.model.Status
import woowacourse.kanban.board.model.Tag

@OptIn(ExperimentalTestApi::class)
class KanbanBoardTest {

    private val kanbanBoardData = KanbanBoardData(
        title = "Compose1",
        boardList = listOf(
            BoardData(
                title = "옮겨질 태스크",
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
                status = Status.TODO,
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
                status = Status.TODO,
                nickname = Nickname.DINO,
            ),
        ),
    )

    @Test
    fun `To Do 태스크 카드를 Done으로 드래그 앤 드롭을 했을 때 태스크 카드의 상태가 DONE으로 변경된다`() = runComposeUiTest {

        setContent {
            KanbanBoard(kanbanBoardData = kanbanBoardData)
        }

        val todoTask = onNodeWithText("옮겨질 태스크")
        val doneColumn = onNodeWithText("Done")

        val density = this.density
        val bounds = todoTask.getUnclippedBoundsInRoot()

        val start = with(density) {
            Offset(
                ((bounds.left + bounds.right) / 2).toPx(),
                ((bounds.top + bounds.bottom) / 2).toPx(),
            )
        }

        val doneBounds = doneColumn.getUnclippedBoundsInRoot()

        val end = with(density) {
            Offset(
                ((doneBounds.left + doneBounds.right) / 2).toPx(),
                ((doneBounds.top + doneBounds.bottom) / 2).toPx(),
            )
        }

        todoTask.performTouchInput {
            down(start)
            moveTo(end)
            up()
        }

        doneColumn.assertExists()
    }
}
