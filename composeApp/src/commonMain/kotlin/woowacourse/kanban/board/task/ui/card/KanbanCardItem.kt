package woowacourse.kanban.board.task.ui.card

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * @param tags 최대 5개까지만 표시되는 태그 리스트입니다. 5개를 초과하면 상위 5개만 렌더링됩니다.
 */
@Composable
fun KanbanCardItem(title: String, content: String, tags: List<String>, assigneeName: String?, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.padding(17.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        KanbanCardTitle(title)

        if (content.isNotBlank()) {
            KanbanCardContent(content)
        }

        if (tags.isNotEmpty()) {
            KanbanCardTags(tags)
        }

        if (assigneeName != null) {
            HorizontalDivider(
                thickness = Dp.Hairline,
                color = Color.LightGray,
            )
            KanbanCardProfile(assigneeName)
        }
    }
}

data class KanbanCardInfo(val title: String, val crewName: String, val tags: List<String> = emptyList(), val content: String = "")

private class KanbanCardPreviewParameterProvider : PreviewParameterProvider<KanbanCardInfo> {
    val tags = listOf(
        "컴포넌트",
        "성능",
    )
    override val values = sequenceOf(
        KanbanCardInfo(
            title = "LazyColumn 컴포넌트 구현",
            crewName = "바드",
            tags = tags,
            content = "세로 스크롤 가능한 리스트 컴포넌트를 만들고 성능 최적화를 적용합니다.",
        ),
        KanbanCardInfo(
            title = "LazyColumn 컴포넌트 구현",
            crewName = "바드",
            tags = tags,
            content = "세로 스크롤 가능한 리스트 컴포넌트를 만들고 성능 최적화를 적용합니다.",
        ),
        KanbanCardInfo(
            title = "LazyColumn 컴포넌트 구현",
            crewName = "바드",
            content = "세로 스크롤 가능한 리스트 컴포넌트를 만들고 성능 최적화를 적용합니다.",
        ),
        KanbanCardInfo(
            title = "LazyColumn 컴포넌트 구현",
            crewName = "바드",
        ),
    )
}

@Preview
@Composable
private fun KanbanCardPreview(@PreviewParameter(KanbanCardPreviewParameterProvider::class) kanbanCardInfo: KanbanCardInfo) {
    Box(modifier = Modifier.padding(12.dp)) {
        KanbanCardItem(
            title = kanbanCardInfo.title,
            assigneeName = kanbanCardInfo.crewName,
            tags = kanbanCardInfo.tags,
            content = kanbanCardInfo.content,
        )
    }
}

@Preview
@Composable
private fun KanbanCardMaxPreview() {
    Box(modifier = Modifier.padding(12.dp)) {
        KanbanCardItem(
            title = "너무너무 긴 제목은 한 줄까지만 노출합니다. 그렇습니다. 감사합니다.",
            assigneeName = "바드바드바드바드바드바드바드바드바드바드바드바드바드바드",
            tags = listOf(
                "컴포넌트",
                "성능",
                "긴 태그",
                "최대로",
            ),
            content = "세로 스크롤 가능한 리스트 컴포넌트를 만들고 성능 최적화를 적용합니다.".repeat(3),
        )
    }
}
