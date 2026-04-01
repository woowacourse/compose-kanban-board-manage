package woowacourse.kanban.board.ui.component.board

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import woowacourse.kanban.board.data.AssigneePool
import woowacourse.kanban.board.domain.KanbanTask
import woowacourse.kanban.board.domain.Status
import woowacourse.kanban.board.ui.toBodyColor
import woowacourse.kanban.board.ui.toBorderColor
import woowacourse.kanban.board.ui.toMainColor
import woowacourse.kanban.board.ui.toTitle

@Composable
fun CardGroup(
    cards: List<KanbanTask>,
    modifier: Modifier = Modifier,
    getIsDropTarget: (Status) -> Boolean = { false },
    onBoundsChanged: (Rect, Status) -> Unit = { _, _ -> },
    onTaskDragStart: (KanbanTask) -> Unit = { },
    onTaskDragChange: (Offset) -> Unit = { },
    onTaskDragEnd: () -> Unit = { },
    onTaskDragCancel: () -> Unit = { },
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Status.entries.forEach { status ->
            CardHolder(
                title = status.toTitle(),
                bodyColor = status.toBodyColor(),
                borderColor = status.toBorderColor(),
                mainColor = status.toMainColor(),
                cards = cards.filter { card -> card.status == status },
                modifier = Modifier.width(320.dp),
                getIsDropTarget = { getIsDropTarget(status) },
                onBoundsChanged = { onBoundsChanged(it, status) },
                onTaskDragStart = onTaskDragStart,
                onTaskDragChange = onTaskDragChange,
                onTaskDragEnd = onTaskDragEnd,
                onTaskDragCancel = onTaskDragCancel,
            )
        }
    }
}

@Preview(widthDp = 1300)
@Composable
private fun CardGroupPreview() {
    val assignee = AssigneePool.getAll()[0]
    CardGroup(
        cards = listOf(
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
                tags = listOf("컴포넌트", "성능"),
                status = Status.TO_DO,
                assignee = assignee,
            ),
            KanbanTask(
                title = "LazyColumn 컴포넌트 구현",
                description = "세로 스크롤 가능한 리스트 컴포넌트를 만들고 성능 최적화를 적용합니다.",
                tags = listOf("컴포넌트", "성능"),
                status = Status.IN_PROGRESS,
                assignee = assignee,
            ),
            KanbanTask(
                title = "LazyColumn 컴포넌트 구현",
                description = "세로 스크롤 가능한 리스트 컴포넌트를 만들고 성능 최적화를 적용합니다.",
                tags = listOf("컴포넌트", "성능"),
                status = Status.DONE,
                assignee = assignee,
            ),
            KanbanTask(
                title = "LazyColumn 컴포넌트 구현",
                description = "세로 스크롤 가능한 리스트 컴포넌트를 만들고 성능 최적화를 적용합니다.",
                tags = listOf("컴포넌트", "성능"),
                status = Status.DONE,
                assignee = assignee,
            ),
            KanbanTask(
                title = "LazyColumn 컴포넌트 구현",
                description = "세로 스크롤 가능한 리스트 컴포넌트를 만들고 성능 최적화를 적용합니다.",
                tags = listOf("컴포넌트", "성능"),
                status = Status.DONE,
                assignee = assignee,
            ),
        ),
    )
}
