package woowacourse.kanban.board.component.taskcard

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kanbanboard.composeapp.generated.resources.Res
import kanbanboard.composeapp.generated.resources.profile
import kotlinx.collections.immutable.toImmutableList
import woowacourse.kanban.board.Gray70
import woowacourse.kanban.board.Gray80
import woowacourse.kanban.board.component.sample.TaskCardPreviewData
import woowacourse.kanban.board.model.taskcard.Assignee
import woowacourse.kanban.board.model.taskcard.Status
import woowacourse.kanban.board.model.taskcard.TaskCardData
import woowacourse.kanban.board.model.taskcard.TaskDescription
import woowacourse.kanban.board.model.taskcard.TaskTag
import woowacourse.kanban.board.model.taskcard.TaskTags
import woowacourse.kanban.board.model.taskcard.TaskTitle

@Composable
fun TaskCard(
    data: TaskCardData,
    modifier: Modifier = Modifier,
    onShowEditTaskModal: (TaskCardData) -> Unit = {},
    onDragStart: () -> Unit = {},
    onDragChange: (Offset) -> Unit = {},
    onDragEnd: () -> Unit = {},
    onDragCancel: () -> Unit = {},
) {
    var cardWindowPosition by remember { mutableStateOf(Offset.Zero) }
    Card(
        modifier = modifier
            .onGloballyPositioned { cardWindowPosition = it.positionInWindow() }
            .pointerInput(Unit) {
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
            .width(286.dp)
            .clip(RoundedCornerShape(15.dp))
            .border(1.dp, Gray70, RoundedCornerShape(15.dp))
            .clickable { onShowEditTaskModal(data) },
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        )
    ) {
        Column(
            modifier = modifier
                .padding(17.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            TaskTitleText(title = data.taskTitle.value)
            TaskDescriptionText(description = data.taskDescription.value)
            TagsSection(taskTags = data.taskTags)
            HorizontalDivider(
                thickness = 1.dp,
                color = Gray80,
            )
            AssigneeLabel(assignee = data.assignee)
        }
    }
}

@Preview
@Composable
private fun TaskCardPreview() {
    TaskCard(
        data = TaskCardPreviewData().values.toMutableList()[0]
    )
}

@Preview
@Composable
private fun TaskCardEmptyDescriptionPreview() {
    TaskCard(
        data = TaskCardData(
            taskTitle = TaskTitle(value = "LazyColumn 컴포넌트 구현"),
            taskDescription = TaskDescription(value = ""),
            taskTags = TaskTags(value = listOf(TaskTag(value = "컴포넌트")).toImmutableList()),
            status = Status.PROGRESS,
            assignee = Assignee("다이노", Res.drawable.profile),
        ),
    )
}

@Preview
@Composable
private fun TaskCardEmptyTagPreview() {
    TaskCard(
        data = TaskCardData(
            taskTitle = TaskTitle(value = "LazyColumn 컴포넌트 구현"),
            taskDescription = TaskDescription(value = "세로 스크롤"),
            taskTags = TaskTags(value = listOf<TaskTag>().toImmutableList()),
            status = Status.PROGRESS,
            assignee = Assignee("다이노", Res.drawable.profile),
        ),
    )
}

@Preview
@Composable
private fun TaskCardEmptyTagAndDescriptionPreview() {
    TaskCard(
        data = TaskCardData(
            taskTitle = TaskTitle(value = "LazyColumn 컴포넌트 구현"),
            taskDescription = TaskDescription(value = ""),
            taskTags = TaskTags(value = listOf<TaskTag>().toImmutableList()),
            status = Status.PROGRESS,
            assignee = Assignee("다이노", Res.drawable.profile),
        ),
    )
}

@Preview
@Composable
private fun TaskCardLongTitlePreview() {
    TaskCard(
        data = TaskCardData(
            taskTitle = TaskTitle(value = "LazyColumn 컴포넌트 구현LazyColumn 컴포넌트 구현"),
            taskDescription = TaskDescription(value = ""),
            taskTags = TaskTags(value = listOf<TaskTag>().toImmutableList()),
            status = Status.PROGRESS,
            assignee = Assignee("다이노", Res.drawable.profile),
        ),
    )
}

@Preview
@Composable
private fun TaskCardLongDescriptionPreview() {
    TaskCard(
        data = TaskCardData(
            taskTitle = TaskTitle(value = "LazyColumn 컴포넌트 구현"),
            taskDescription = TaskDescription(
                value = "세로 스크롤  세로 스크롤" +
                    "세로 스크롤 세로 스크롤세로 스크롤 " +
                    " 세로 스크롤세로 스크롤 세로 스크롤" +
                    "세로 스크롤  세로 스크롤세로 스크롤 " +
                    "세로 스크롤세로 스크롤  세로 스크롤" +
                    "세로 스크롤 세로 스크롤세로 스크롤" +
                    "  세로 스크롤세로 스크롤 세로 스크롤"
            ),
            taskTags = TaskTags(value = listOf<TaskTag>().toImmutableList()),
            status = Status.PROGRESS,
            assignee = Assignee("다이노", Res.drawable.profile),
        ),
    )
}
