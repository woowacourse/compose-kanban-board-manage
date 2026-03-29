package woowacourse.kanban.board.ui.board.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import woowacourse.kanban.board.domain.Task
import woowacourse.kanban.board.domain.TaskState
import woowacourse.kanban.board.domain.Tasks
import woowacourse.kanban.board.ui.theme.CompletedRate
import woowacourse.kanban.board.ui.theme.OnSurface
import woowacourse.kanban.board.ui.theme.OutlineVariant
import woowacourse.kanban.board.ui.theme.TextPrimary

@Composable
fun BoardHeader(projectName: String, tasks: Tasks, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.padding(16.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column {
                BoardTitle(projectName)
                Spacer(modifier = Modifier.height(6.dp))
                TaskCompletedRate(
                    tasks.completedRate(),
                    tasks.countByState(TaskState.DONE),
                    tasks.totalCount,
                )
            }
            CreateTaskButton(onClick = onClick)
        }
        Spacer(modifier = Modifier.height(16.dp))
        ProjectProgress(
            doneCount = tasks.countByState(TaskState.DONE),
            totalCount = tasks.totalCount,
        )
    }
}

@Composable
private fun BoardTitle(projectName: String, modifier: Modifier = Modifier) {
    Text(
        text = projectName,
        modifier = modifier,
        fontWeight = FontWeight.W500,
        fontSize = 24.sp,
        color = TextPrimary,
    )
}

@Composable
private fun TaskCompletedRate(completedRate: Int, doneCount: Int, totalCount: Int, modifier: Modifier = Modifier) {
    Text(
        modifier = modifier,
        text = "완료율: $completedRate% ($doneCount/$totalCount)",
        fontWeight = FontWeight.W400,
        fontSize = 14.sp,
        color = CompletedRate,
    )
}

@Composable
private fun CreateTaskButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        onClick = onClick,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = OnSurface,
            contentColor = Color.White,
        ),
        shape = RoundedCornerShape(8.dp),
    ) {
        Icon(
            Icons.Default.Add,
            contentDescription = "새 태스크 생성",
            modifier = Modifier.size(20.dp),
        )
        Text(
            text = "새 태스크 생성",
            fontWeight = FontWeight.W400,
            fontSize = 16.sp,
        )
    }
}

@Composable
private fun ProjectProgress(doneCount: Int, totalCount: Int, modifier: Modifier = Modifier) {
    val progress = if (totalCount == 0) 0f else doneCount.toFloat() / totalCount.toFloat()

    LinearProgressIndicator(
        gapSize = 0.dp,
        progress = { progress },
        modifier = modifier
            .fillMaxWidth()
            .height(8.dp),
        color = OnSurface,
        trackColor = OutlineVariant,
    )
}

@Preview
@Composable
private fun BoardHeaderPreview() {
    BoardHeader(
        projectName = "Compose Desktop 칸반 보드",
        tasks = Tasks(
            listOf(
                Task(title = "title1", taskState = TaskState.DONE),
                Task(title = "title2", taskState = TaskState.DONE),
                Task(title = "title3", taskState = TaskState.TO_DO),
                Task(title = "title4", taskState = TaskState.IN_PROGRESS),
            ),
        ),
        onClick = {},
    )
}
