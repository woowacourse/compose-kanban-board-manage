package woowacourse.kanban.board.component.kanbanBoard

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import woowacourse.kanban.board.component.card.TaskCard
import woowacourse.kanban.board.domain.Task

@Composable
fun TaskCardView(
    task: Task,
    modifier: Modifier = Modifier,
    onDragStart: () -> Unit = {},
    onDragChange: (Offset) -> Unit = {},
    onDragEnd: () -> Unit = {},
    onDragCancel: () -> Unit = {},
    onClick: (Task) -> Unit,
) {
    var cardWindowPosition by remember { mutableStateOf(Offset.Zero) }

    TaskCard(
        modifier = modifier
            // 1) 카드가 화면 어디에 있는지 추적 (스크롤 대응을 위해 상태로 관리)
            .onGloballyPositioned { cardWindowPosition = it.positionInWindow() }
            // 2) 드래그 제스처 감지
            .pointerInput(task.id) {
                detectDragGestures(
                    onDragStart = { onDragStart() },
                    onDrag = { change, _ ->
                        change.consume()
                        onDragChange(cardWindowPosition + change.position)
                    },
                    onDragEnd = { onDragEnd() },
                    onDragCancel = { onDragCancel() },
                )
            },
        task = task,
        onClick = { onClick(task) },
    )
}
