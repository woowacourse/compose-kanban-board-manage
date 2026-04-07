package woowacourse.kanban.board.task.ui.board

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kanbanboard.composeapp.generated.resources.Res
import kanbanboard.composeapp.generated.resources.button_task_create
import kanbanboard.composeapp.generated.resources.progress_text
import org.jetbrains.compose.resources.stringResource
import woowacourse.kanban.board.theme.CreateButtonBackground
import woowacourse.kanban.board.theme.ProgressText

@Composable
fun KanbanBoardHeader(
    title: String,
    doneCount: Int,
    totalCount: Int,
    progress: Int,
    onCreateClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        BoardHeader(
            title = title,
            modifier = Modifier.fillMaxWidth(),
            doneCount = doneCount,
            totalCount = totalCount,
            progress = progress,
            onCreateClick = onCreateClick,
        )

        LinearProgressIndicator(
            progress = { progress * 0.01f },
            modifier = Modifier.fillMaxWidth().height(8.dp).clip(CircleShape),
            color = CreateButtonBackground,
            trackColor = Color.LightGray,
            strokeCap = StrokeCap.Butt,
            gapSize = 0.dp,
            drawStopIndicator = {},
        )
    }
}

@Composable
private fun BoardHeader(
    title: String,
    doneCount: Int,
    totalCount: Int,
    progress: Int,
    onCreateClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        BoardHeaderTitleProgress(
            title = title,
            doneCount = doneCount,
            totalCount = totalCount,
            progress = progress,
        )

        BoardCreateButton(onCreateClick = onCreateClick)
    }
}

@Composable
private fun BoardCreateButton(onCreateClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        modifier = modifier.height(40.dp),
        onClick = onCreateClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = CreateButtonBackground,
            contentColor = Color.White,
        ),
        shape = RoundedCornerShape(10.dp),
        contentPadding = PaddingValues(8.dp),
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            modifier = Modifier.size(20.dp),
            contentDescription = "추가",
        )
        Text(
            text = stringResource(Res.string.button_task_create),
            fontSize = 16.sp,
        )
    }
}

@Composable
private fun BoardHeaderTitleProgress(title: String, doneCount: Int, totalCount: Int, progress: Int, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Text(
            text = title,
            fontWeight = FontWeight.Medium,
            fontSize = 24.sp,
        )

        Text(
            text = stringResource(
                Res.string.progress_text,
                progress,
                doneCount,
                totalCount,
            ),
            color = ProgressText,
            fontSize = 14.sp,
        )
    }
}

@Preview(widthDp = 1000)
@Composable
private fun KanbanBoardHeaderPreview() {
    KanbanBoardHeader(
        title = "보드 제목",
        doneCount = 1,
        totalCount = 3,
        progress = 33,
        onCreateClick = {},
    )
}
