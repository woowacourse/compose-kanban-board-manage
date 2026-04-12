package woowacourse.kanban.card

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import woowacourse.kanban.card.components.CardTitle
import woowacourse.kanban.card.components.Content
import woowacourse.kanban.card.components.Profile
import woowacourse.kanban.card.components.TagsComponent
import woowacourse.kanban.card.constant.DEFAULT_CONTENT
import woowacourse.kanban.card.constant.DEFAULT_TITLE
import woowacourse.kanban.card.constant.MAX_CONTENT
import woowacourse.kanban.card.constant.MAX_TITLE
import woowacourse.kanban.core.design.Colors
import woowacourse.kanban.domain.task.Assignee
import woowacourse.kanban.domain.task.Tags
import woowacourse.kanban.domain.task.TaskData
import woowacourse.kanban.domain.task.Title

private val Assignee.toNickName: String
    get() = when (this) {
        Assignee.NONE -> ""
        Assignee.DINO -> "다이노"
        Assignee.FAMES -> "페임스"
    }

@Composable
fun KanbanCard(
    board: TaskData,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    onDragStart: () -> Unit = {},
    onDragChange: (Offset) -> Unit = {},
    onDragEnd: () -> Unit = {},
    onDragCancel: () -> Unit = {},
) {
    var cardWindowPosition by remember { mutableStateOf(Offset.Zero) }

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
            .padding(12.dp)
            // 1) 카드가 화면 어디에 있는지 추적 (스크롤 대응을 위해 상태로 관리)
            .onGloballyPositioned { cardWindowPosition = it.positionInWindow() }
            // 2) 드래그 제스처 감지
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = {
                        onDragStart()
                    },
                    onDrag = { change, _ ->
                        change.consume()
                        onDragChange(cardWindowPosition + change.position)
                    },
                    onDragEnd = {
                        onDragEnd()
                    },
                    onDragCancel = {
                        onDragCancel()
                    },
                )
            },

    ) {
        Column {
            // 제목
            CardTitle(
                title = board.title,
                modifier = Modifier.padding(vertical = 8.dp).testTag("제목"),
            )

            // 중간 내용
            if (board.content.isNotBlank()) {
                Content(
                    content = board.content,
                    modifier = Modifier.padding(vertical = 4.dp).testTag("중간내용"),
                )
            }

            // 태그
            if (board.tags.tags.isNotEmpty()) {
                TagsComponent(
                    tags = board.tags,
                    modifier = Modifier.padding(vertical = 8.dp).testTag("테그목록"),
                )
            }

            if (board.assignee != Assignee.NONE) {
                // 구분선
                HorizontalDivider(thickness = 2.dp)

                // 작성자
                Profile(
                    nickname = board.assignee.toNickName,
                    modifier = Modifier.padding(vertical = 8.dp).testTag("프로필"),
                )
            }
        }
    }
}

class BoardPreviewParameterProvider : PreviewParameterProvider<TaskData> {
    override val values = sequenceOf(
        TaskData(
            title = Title(DEFAULT_TITLE),
            content = DEFAULT_CONTENT,
            tags = Tags(listOf("컴포넌트", "성능")),
            assignee = Assignee.NONE,
        ),
        TaskData(
            title = Title(DEFAULT_TITLE),
            tags = Tags(listOf("컴포넌트", "성능")),
            assignee = Assignee.DINO,
        ),
        TaskData(
            title = Title(DEFAULT_TITLE),
            content = DEFAULT_CONTENT,
            tags = Tags(),
            assignee = Assignee.DINO,
        ),
        TaskData(
            title = Title(DEFAULT_TITLE),
            tags = Tags(),
            assignee = Assignee.DINO,
        ),
        TaskData(
            title = Title(MAX_TITLE),
            content = MAX_CONTENT,
            tags = Tags(listOf("너무너무", "긴 태그", "최대로", "5자까지", "5개제한임")),
            assignee = Assignee.DINO,
        ),
    )
}

@Preview()
@Composable
private fun BoardScreenView(
    @PreviewParameter(BoardPreviewParameterProvider::class)
    board: TaskData,
) {
    KanbanCard(
        board,
    )
}
