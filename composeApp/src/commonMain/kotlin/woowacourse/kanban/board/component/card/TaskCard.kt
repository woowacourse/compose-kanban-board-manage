package woowacourse.kanban.board.component.card

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import woowacourse.kanban.board.constant.BORDER_COLOR
import woowacourse.kanban.board.constant.DEFAULT_CONTENT
import woowacourse.kanban.board.constant.DEFAULT_TITLE
import woowacourse.kanban.board.constant.MAX_CONTENT
import woowacourse.kanban.board.constant.MAX_TITLE
import woowacourse.kanban.board.model.BoardData
import woowacourse.kanban.board.model.Nickname
import woowacourse.kanban.board.model.Status
import woowacourse.kanban.board.model.Tag

@Composable
fun TaskCard(board: BoardData, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .background(color = Color.White)
            .border(
                width = 1.dp,
                color = Color(BORDER_COLOR),
                shape = RoundedCornerShape(15.dp),
            )
            .width(270.dp)
            .padding(12.dp),
    ) {
        Column {
            // 제목
            TitleComponent(title = board.title, modifier = Modifier.padding(vertical = 8.dp).testTag("제목"))

            // 중간 내용
            if (board.description.isNotBlank()) {
                DescriptionComponent(description = board.description, modifier = Modifier.padding(vertical = 4.dp).testTag("중간내용"))
            }

            // 태그
            if (board.tags.isNotEmpty()) {
                TagsComponent(tags = board.tags, modifier = Modifier.padding(vertical = 8.dp).testTag("테그목록"))
            }

            if (board.nickname != Nickname.NONE) {
                // 구분선
                HorizontalDivider(thickness = 2.dp)

                // 작성자
                ProfileComponent(
                    nickname = when (board.nickname) {
                        Nickname.DINO -> "다이노"
                        Nickname.PAMES -> "페임스"
                    },
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .testTag("프로필"),
                )
            }
        }
    }
}

private class BoardPreviewParameterProvider : PreviewParameterProvider<BoardData> {
    override val values = sequenceOf(
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
    )
}

@Preview(showBackground = true)
@Composable
private fun BoardScreenView(@PreviewParameter(BoardPreviewParameterProvider::class) board: BoardData) {
    TaskCard(board)
}
