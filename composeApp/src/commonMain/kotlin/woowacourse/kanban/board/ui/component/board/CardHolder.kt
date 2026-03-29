package woowacourse.kanban.board.ui.component.board

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import woowacourse.kanban.board.domain.KanbanTask
import woowacourse.kanban.board.domain.dialog.Status
import woowacourse.kanban.board.ui.component.card.KanbanCard

@Composable
fun CardHolder(
    title: String,
    mainColor: Color,
    bodyColor: Color,
    borderColor: Color,
    cards: List<KanbanTask>,
    modifier: Modifier = Modifier,
    getIsDropTarget: () -> Boolean = { false },
    onBoundsChanged: (Rect) -> Unit = { },
    onTaskDragStart: (KanbanTask) -> Unit = { },
    onTaskDragChange: (Offset) -> Unit = { },
    onTaskDragEnd: () -> Unit = { },
    onTaskDragCancel: () -> Unit = { },
) {
    val isDropTarget by remember {
        derivedStateOf {
            getIsDropTarget()
        }
    }

    val lastBoundsHolder = remember { mutableStateOf<Rect?>(null) }

    Column(
        modifier = modifier
            .onGloballyPositioned {
                val newBounds = it.boundsInWindow()
                if (newBounds != lastBoundsHolder.value) {
                    lastBoundsHolder.value = newBounds
                    onBoundsChanged(newBounds)
                }
            }
            .then(
                if (isDropTarget)
                    Modifier.border(width = 2.dp, color = mainColor, RoundedCornerShape(12.dp))
                else
                    Modifier,
            ),
    ) {
        CardHolderTitle(
            text = title,
            color = mainColor,
            cardCount = cards.size,
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 700.dp)
                .clip(RoundedCornerShape(bottomStart = 10.dp, bottomEnd = 10.dp))
                .background(bodyColor)
                .border(1.dp, borderColor, RoundedCornerShape(bottomStart = 10.dp, bottomEnd = 10.dp))
                .padding(vertical = 16.dp, horizontal = 17.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            items(
                items = cards,
                key = { it.id },
            ) { card ->
                KanbanCard(
                    title = card.title,
                    crewName = card.assignee,
                    tags = card.tags,
                    description = card.description,
                    onDragStart = { onTaskDragStart(card) },
                    onDragChange = onTaskDragChange,
                    onDragEnd = onTaskDragEnd,
                    onDragCancel = onTaskDragCancel,
                )
            }
        }
    }
}

@Composable
private fun CardHolderTitle(
    text: String,
    color: Color,
    cardCount: Int,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp))
            .background(color)
            .padding(vertical = 12.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.White,
        )

        Box(
            modifier = Modifier
                .clip(CircleShape)
                .background(Color.White)
                .padding(vertical = 2.dp, horizontal = 10.dp),
        ) {
            Text(
                text = cardCount.toString(),
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 320)
@Composable
private fun CardHolderPreview() {
    CardHolder(
        title = "To Do",
        mainColor = Color(0xFF155DFC),
        bodyColor = Color(0xFFEFF6FF),
        borderColor = Color(0xFFBEDBFF),
        cards = listOf(
            KanbanTask(
                title = "LazyColumn 컴포넌트 구현",
                description = "세로 스크롤 가능한 리스트 컴포넌트를 만들고 성능 최적화를 적용합니다.",
                tags = listOf("컴포넌트", "성능"),
                status = Status.TO_DO,
                assignee = "다이노",
            ),
            KanbanTask(
                title = "LazyColumn 컴포넌트 구현",
                description = "세로 스크롤 가능한 리스트 컴포넌트를 만들고 성능 최적화를 적용합니다.",
                tags = listOf("컴포넌트", "성능"),
                status = Status.TO_DO,
                assignee = "다이노",
            ),
        ),
    )
}

@Preview(showBackground = true, widthDp = 320)
@Composable
private fun CardHolderTitlePreview() {
    CardHolderTitle(
        text = "To Do",
        color = Color(0xFF155DFC),
        cardCount = 3,
    )
}
