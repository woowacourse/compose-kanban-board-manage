package woowacourse.kanban.board.component.board

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import woowacourse.kanban.board.Gray10
import woowacourse.kanban.board.Gray30
import woowacourse.kanban.board.component.ComponentText
import woowacourse.kanban.board.component.sample.ProjectPreviewData
import kotlin.math.roundToInt

@Composable
fun TaskBoardHeader(
    title: String,
    doneRate: Float,
    doneTasks: Int,
    totalTasks: Int,
    onClickCreateTask: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .background(Color.White),
    ) {
        Column(
            modifier = Modifier
                .padding(vertical = 16.dp, horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            BoardHeaderTitle(
                title = title,
                doneTasks = doneTasks,
                doneRate = doneRate,
                totalTasks = totalTasks,
                onClickCreateTask = onClickCreateTask,
                modifier = Modifier.fillMaxWidth(),
            )
            ProgressBar(progress = doneRate)
        }
    }
}

@Composable
private fun ProgressBar(
    progress: Float,
    modifier: Modifier = Modifier,
) {
    LinearProgressIndicator(
        gapSize = 0.dp,
        strokeCap = StrokeCap.Square,
        progress = { progress },
        modifier = modifier
            .fillMaxWidth()
            .height(8.dp)
            .clip(RoundedCornerShape(15.dp)),
        color = Color.Blue,
        trackColor = Color.LightGray,
        drawStopIndicator = {}
    )
}

@Composable
private fun BoardHeaderTitle(
    title: String,
    doneTasks: Int,
    doneRate: Float,
    totalTasks: Int,
    onClickCreateTask: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val doneRateFormat = "${(doneRate * 100).roundToInt()}%"

    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column {
            Text(
                text = title,
                fontSize = 24.sp,
                fontWeight = FontWeight.Normal,
                color = Gray10,
            )
            Text(
                text = "${ComponentText.BOARD_HEADER_PROGRESS} $doneRateFormat ($doneTasks/$totalTasks)",
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                color = Gray30,
            )
        }
        TaskCreateButton(
            onClickCreateTask = onClickCreateTask,
            modifier = Modifier,
        )
    }
}

@Composable
private fun TaskCreateButton(
    onClickCreateTask: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = { onClickCreateTask() },
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Blue,
        ),
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(4.dp),
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "새 태스크 생성",
                modifier = Modifier.size(20.dp),
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = ComponentText.BOARD_TASK_CREATE_BUTTON,
                fontSize = 16.sp,
                color = Color.White,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TaskBoardHeaderPreview() {
    val project = ProjectPreviewData().values.toMutableList()[0]
    MaterialTheme {
        TaskBoardHeader(
            title = project.title,
            doneRate = project.calculateDoneRate(),
            doneTasks = project.doneTasks.size,
            totalTasks = project.allTasksCount,
            onClickCreateTask = {},
        )
    }
}
