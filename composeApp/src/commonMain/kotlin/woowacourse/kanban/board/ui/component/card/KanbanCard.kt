package woowacourse.kanban.board.ui.component.card

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import woowacourse.kanban.board.data.AssigneePool
import woowacourse.kanban.board.domain.Assignee
import woowacourse.kanban.board.domain.KanbanTask
import woowacourse.kanban.board.domain.Status

@Composable
fun KanbanCard(
    title: String,
    assignee: Assignee?,
    modifier: Modifier = Modifier,
    tags: List<String> = emptyList(),
    description: String? = null,
    onClick: () -> Unit = { },
    onDragStart: () -> Unit = { },
    onDragChange: (Offset) -> Unit = { },
    onDragEnd: () -> Unit = { },
    onDragCancel: () -> Unit = { },
) {
    var cardWindowPosition by remember { mutableStateOf(Offset.Zero) }

    Column(
        modifier = modifier
            .width(286.dp)
            .background(Color.White, RoundedCornerShape(10.dp))
            .border(Dp.Hairline, Color.Gray, RoundedCornerShape(10.dp))
            .padding(17.dp)
            .onGloballyPositioned { cardWindowPosition = it.positionInWindow() }
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = { onClick() },
                )
                detectDragGestures(
                    onDragStart = { onDragStart() },
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
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text(
            text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )

        if (!description.isNullOrBlank()) {
            Text(
                text = description,
                fontSize = 14.sp,
                color = Color.DarkGray,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
        }

        if (tags.isNotEmpty()) {
            KanbanCardTags(tags = tags)
        }

        if (assignee != null) {
            HorizontalDivider(thickness = Dp.Hairline, color = Color.LightGray)
            KanbanCardProfile(assignee = assignee)
        }
    }
}

@Composable
private fun KanbanCardTags(tags: List<String>) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        tags.forEach { tag -> TagChip(name = tag) }
    }
}

private class KanbanCardPreviewParameterProvider : PreviewParameterProvider<KanbanTask> {
    val assignee = AssigneePool.getAll()[0]
    override val values = sequenceOf(
        KanbanTask(
            title = "너무너무 긴 제목은 한 줄까지만 노출합니다",
            description = "세로 스크롤 가능한 리스트 컴포넌트를 만들고 성능 최적화를 적용합니다.",
            tags = listOf("컴포넌트", "성능", "긴 태그", "최대로", "5자까지"),
            status = Status.TO_DO,
            assignee = assignee,
        ),
        KanbanTask(
            title = "LazyColumn 컴포넌트 구현",
            description = "세로 스크롤 가능한 리스트 컴포넌트를 만들고 성능 최적화를 적용합니다.",
            tags = listOf("컴포넌트", "성능"),
            status = Status.TO_DO,
            assignee = assignee,
        ),
        KanbanTask(
            title = "LazyColumn 컴포넌트 구현",
            description = "세로 스크롤 가능한 리스트 컴포넌트를 만들고 성능 최적화를 적용합니다.",
            status = Status.TO_DO,
            assignee = assignee,
        ),
        KanbanTask(
            title = "LazyColumn 컴포넌트 구현",
            tags = listOf("컴포넌트", "성능"),
            status = Status.TO_DO,
            assignee = assignee,
        ),
        KanbanTask(
            title = "LazyColumn 컴포넌트 구현",
            status = Status.TO_DO,
            assignee = null,
        ),
    )
}

@Preview
@Composable
private fun KanbanCardPreview(@PreviewParameter(KanbanCardPreviewParameterProvider::class) card: KanbanTask) {
    Box(modifier = Modifier.padding(12.dp)) {
        KanbanCard(
            title = card.title,
            assignee = card.assignee,
            tags = card.tags,
            description = card.description,
        )
    }
}
