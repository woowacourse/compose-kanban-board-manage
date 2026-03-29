package woowacourse.kanban.board.ui.component.board

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun KanbanBoardTopAppBar(
    title: String,
    progress: Float,
    progressPercent: Int,
    completeCount: Int,
    totalCount: Int,
    onNewTaskClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .border(1.dp, Color(0xFFE5E7EB))
            .padding(horizontal = 24.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                TopAppBarTitle(text = title)

                TaskProgressText(
                    progressPercent = progressPercent,
                    completeCount = completeCount,
                    totalCount = totalCount,
                )
            }

            NewTaskButton(
                text = "새 태스크 생성",
                onClick = onNewTaskClick,
            )
        }

        TaskProgressIndicator(progress = progress)
    }
}

@Preview(showBackground = true, widthDp = 800)
@Composable
private fun KanbanBoardTopAppBar() {
    KanbanBoardTopAppBar(
        title = "Compose Desktop 칸반 보드",
        progress = 0.5f,
        progressPercent = 50,
        completeCount = 3,
        totalCount = 6,
        onNewTaskClick = { },
    )
}
