package woowacourse.kanban.board.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.unit.dp
import woowacourse.kanban.board.component.card.TaskCard
import woowacourse.kanban.board.model.BoardData

@Composable
fun TaskCardView(
    boardData: BoardData,
    modifier: Modifier = Modifier,
    onDragStart: () -> Unit = {},
    onDragChange: (Offset) -> Unit = {},
    onDragEnd: () -> Unit = {},
    onDragCancel: () -> Unit = {},
    onClick: (BoardData) -> Unit = {},
) {
    var cardWindowPosition by remember { mutableStateOf(Offset.Zero) }

    TaskCard(
        modifier = modifier
            .clip(shape = RoundedCornerShape(15.dp))
            // 1) 카드가 화면 어디에 있는지 추적 (스크롤 대응을 위해 상태로 관리)
            .onGloballyPositioned { cardWindowPosition = it.positionInWindow() }
            // 2) 드래그 제스처 감지
            .pointerInput(boardData) {
                detectDragGestures(
                    onDragStart = { onDragStart() },
                    onDrag = { change, _ ->
                        change.consume()
                        onDragChange(cardWindowPosition + change.position)
                    },
                    onDragEnd = { onDragEnd() },
                    onDragCancel = { onDragCancel() },
                )
            }
            .clickable(onClick = { onClick(boardData) }),
        board = boardData,
    )
}
