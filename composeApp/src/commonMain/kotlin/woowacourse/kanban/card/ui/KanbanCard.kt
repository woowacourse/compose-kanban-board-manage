package woowacourse.kanban.card.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import woowacourse.kanban.Colors
import woowacourse.kanban.card.constant.DEFAULT_CONTENT
import woowacourse.kanban.card.constant.DEFAULT_NAME
import woowacourse.kanban.card.constant.DEFAULT_TITLE
import woowacourse.kanban.card.constant.MAX_CONTENT
import woowacourse.kanban.card.constant.MAX_NAME
import woowacourse.kanban.card.constant.MAX_TITLE
import woowacourse.kanban.domain.BoardData
import woowacourse.kanban.domain.Nickname
import woowacourse.kanban.domain.Tags
import woowacourse.kanban.domain.Title

@Composable
fun KanbanCard(
    board: BoardData,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    Box(
        modifier = modifier
            .width(270.dp)
            .clip(shape = RoundedCornerShape(15.dp))
            .background(Color.White)
            .clickable(onClick = onClick)
            .border(
                width = 1.dp,
                color = Colors.PrimaryBorder,
                shape = RoundedCornerShape(15.dp),
            )
            .padding(12.dp),
    ) {
        Column {
            // 제목
            CardTitle(title = board.title, modifier = Modifier.padding(vertical = 8.dp).testTag("제목"))

            // 중간 내용
            if (board.content.isNotBlank()) {
                Content(content = board.content, modifier = Modifier.padding(vertical = 4.dp).testTag("중간내용"))
            }

            // 태그
            if (board.tags.tags.isNotEmpty()) {
                TagsComponent(tags = board.tags, modifier = Modifier.padding(vertical = 8.dp).testTag("테그목록"))
            }

            // 구분선
            HorizontalDivider(thickness = 2.dp)

            // 작성자
            Profile(nickname = board.nickname, modifier = Modifier.padding(vertical = 8.dp).testTag("프로필"))
        }
    }
}

class BoardPreviewParameterProvider : PreviewParameterProvider<BoardData> {
    override val values = sequenceOf(
        BoardData(
            title = Title(DEFAULT_TITLE),
            content = DEFAULT_CONTENT,
            tags = Tags(listOf("컴포넌트", "성능")),
            nickname = Nickname(DEFAULT_NAME),
        ),
        BoardData(
            title = Title(DEFAULT_TITLE),
            tags = Tags(listOf("컴포넌트", "성능")),
            nickname = Nickname(DEFAULT_NAME),
        ),
        BoardData(
            title = Title(DEFAULT_TITLE),
            content = DEFAULT_CONTENT,
            tags = Tags(),
            nickname = Nickname(DEFAULT_NAME),
        ),
        BoardData(
            title = Title(DEFAULT_TITLE),
            tags = Tags(),
            nickname = Nickname(DEFAULT_NAME),
        ),
        BoardData(
            title = Title(MAX_TITLE),
            content = MAX_CONTENT,
            tags = Tags(listOf("너무너무", "긴 태그", "최대로", "5자까지", "5개제한임")),
            nickname = Nickname(MAX_NAME),
        ),
    )
}

@Preview()
@Composable
private fun BoardScreenView(
    @PreviewParameter(BoardPreviewParameterProvider::class)
    board: BoardData,
) {
    KanbanCard(
        board,
    )
}
