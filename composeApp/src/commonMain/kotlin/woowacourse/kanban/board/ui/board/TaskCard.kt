package woowacourse.kanban.board.ui.board

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import woowacourse.kanban.board.domain.model.Assignee
import woowacourse.kanban.board.domain.model.Status
import woowacourse.kanban.board.domain.model.Tag
import woowacourse.kanban.board.domain.model.Tags
import woowacourse.kanban.board.domain.model.Task
import woowacourse.kanban.board.ui.component.Chip
import woowacourse.kanban.board.ui.component.UserProfile
import woowacourse.kanban.board.ui.theme.Gray100
import woowacourse.kanban.board.ui.theme.Gray200
import woowacourse.kanban.board.ui.theme.Gray600
import woowacourse.kanban.board.ui.theme.Gray900

private const val TITLE_MAX_LINE = 1
private const val CONTENT_MAX_LINE = 2

@Composable
fun TaskCard(task: Task, modifier: Modifier = Modifier, onClick: (Task) -> Unit = {}) {
    Column(
        modifier = modifier
            .clip(shape = RoundedCornerShape(10.dp))
            .background(Color.White)
            .border(width = 1.dp, shape = RoundedCornerShape(10.dp), color = Gray200)
            .clickable { onClick(task) }
            .padding(17.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        TaskTitle(task.title)
        task.description?.let { content -> TaskDescription(content) }
        if (task.tags.items.isNotEmpty()) TaskTags(task.tags)
        task.assignee?.let {
            Box {
                HorizontalDivider(color = Gray100, thickness = 1.dp)
                UserProfile(it, Modifier.padding(10.dp))
            }
        }
    }
}

@Composable
private fun TaskTitle(title: String) {
    Text(
        text = title,
        fontSize = 16.sp,
        fontWeight = FontWeight.W500,
        color = Gray900,
        maxLines = TITLE_MAX_LINE,
        overflow = TextOverflow.Ellipsis,
    )
}

@Composable
private fun TaskDescription(description: String) {
    Text(
        text = description,
        fontSize = 14.sp,
        fontWeight = FontWeight.W400,
        color = Gray600,
        maxLines = CONTENT_MAX_LINE,
        overflow = TextOverflow.Ellipsis,
    )
}

@Composable
private fun TaskTags(tags: Tags) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        tags.items.forEach { tag ->
            Chip(tag.content)
        }
    }
}

class CardPreviewParameterProvider : PreviewParameterProvider<Task> {
    override val values = sequenceOf(
        Task(
            title = "LazyColumn 컴포넌트 구현",
            description = "세로 스크롤 가능한 리스트 컴포넌트를 만들고 성능 최적화를 적용합니다.",
            tags = Tags(listOf(Tag("컴포넌트"), Tag("성능"))),
            assignee = Assignee(name = "다이노"),
            status = Status.TODO,
        ),
        Task(
            title = "LazyColumn 컴포넌트 구현",
            tags = Tags(listOf(Tag("컴포넌트"), Tag("성능"))),
            assignee = Assignee(name = "다이노"),
            status = Status.TODO,
        ),
        Task(
            title = "LazyColumn 컴포넌트 구현",
            description = "세로 스크롤 가능한 리스트 컴포넌트를 만들고 성능 최적화를 적용합니다.",
            assignee = Assignee(name = "다이노"),
            tags = Tags(emptyList()),
            status = Status.TODO,
        ),
        Task(
            title = "LazyColumn 컴포넌트 구현",
            assignee = Assignee(name = "다이노"),
            tags = Tags(emptyList()),
            status = Status.TODO,
        ),
        Task(
            title = "LazyColumn 컴포넌트 구현",
            description = "너무너무너무 긴 설명은 두 줄까지만 노출하고 말줄임표로 처리합니다 두 줄까지만 노출하고 말줄임표로 처리합니다",
            tags = Tags(listOf(Tag("너무너무"), Tag("긴 태그"), Tag("최대로"), Tag("5자까지"), Tag("5개제한임"))),
            assignee = Assignee(name = "너무너무너무 긴 담당자도 한 줄 너무너무너무 긴 담당자도 한 줄"),
            status = Status.TODO,
        ),
    )
}

@Composable
@Preview
private fun TaskCardPreview(@PreviewParameter(CardPreviewParameterProvider::class) card: Task) {
    TaskCard(card)
}
