package woowacourse.kanban.board.ui.taskcard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import woowacourse.kanban.board.domain.Task

@Composable
fun TaskCards(
    tasks: List<Task>,
    onClick: (Task) -> Unit,
    modifier: Modifier = Modifier,
    onTaskDragStart: (Task) -> Unit,
    onTaskDragChange: (Offset) -> Unit,
    onTaskDragEnd: () -> Unit,
    onTaskDragCancel: () -> Unit,
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        items(items = tasks, key = { it }) { task ->
            TaskCard(
                task = task,
                onClick = onClick,
                modifier = Modifier,
                onDragStart = { onTaskDragStart(task) },
                onDragChange = onTaskDragChange,
                onDragEnd = onTaskDragEnd,
                onDragCancel = onTaskDragCancel,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TaskCardsPreview() {
    TaskCards(
        emptyList(),
        onClick = {},
        onTaskDragStart = {},
        onTaskDragChange = {},
        onTaskDragEnd = {},
        onTaskDragCancel = {},
    )
}
