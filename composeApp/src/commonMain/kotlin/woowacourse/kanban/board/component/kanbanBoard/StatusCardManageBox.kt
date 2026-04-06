package woowacourse.kanban.board.component.kanbanBoard

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
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
import woowacourse.kanban.board.theme.TODO_CARD_BOX_BORDER_COLOR
import woowacourse.kanban.board.theme.TODO_CARD_BOX_CONTENT_COLOR
import woowacourse.kanban.board.theme.TODO_CARD_BOX_TITLE_COLOR
import woowacourse.kanban.board.domain.Task
import woowacourse.kanban.board.domain.Status
import woowacourse.kanban.board.domain.Tag
import woowacourse.kanban.board.theme.StatusColor

@Composable
fun StatusCardManageBox(
    boardList: List<Task>,
    status: Status,
    statusColor: StatusColor,
    modifier: Modifier = Modifier,
    getIsDropTarget: () -> Boolean = { false },
    onBoundsChanged: (Rect) -> Unit = {},
    onTaskDragStart: (Task) -> Unit = {},
    onTaskDragChange: (Offset) -> Unit = {},
    onTaskDragEnd: () -> Unit = {},
    onTaskDragCancel: () -> Unit = {},
    onTaskClick: (Task) -> Unit = {},
) {

    val isDropTarget by remember { derivedStateOf { getIsDropTarget() } }
    val lastBoundsHolder = remember { mutableStateOf<Rect?>(null) }

    Column(
        modifier = modifier.size(width = 320.dp, height = 700.dp).onGloballyPositioned {
            val newBounds = it.boundsInWindow()
            if (newBounds != lastBoundsHolder.value) {
                lastBoundsHolder.value = newBounds
                onBoundsChanged(newBounds)
            }
        }
            .then(
                if (isDropTarget) modifier.border(2.dp, Color.Red, RoundedCornerShape(12.dp)) else modifier,
            ),
    ) {
        Box(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp))
                .background(
                    color = Color(statusColor.titleBgColor),
                )
                .padding(vertical = 12.dp, horizontal = 16.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                ) {
                    Text(status.state, fontSize = 16.sp, fontWeight = FontWeight.W600, color = Color.White)
                }
                Box(
                    modifier = Modifier.size(width = 24.dp, height = 29.dp).background(color = Color.White, shape = CircleShape),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = "${boardList.count { it.status == status }}",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W500,
                        color = Color.Black,
                    )
                }
            }
        }
        Box(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(bottomStart = 10.dp, bottomEnd = 10.dp))
                .fillMaxSize()
                .background(
                    color = Color(statusColor.boardBgColor),
                )
                .border(
                    width = 1.dp,
                    color = Color(statusColor.boardBorderColor),
                    shape = RoundedCornerShape(bottomStart = 10.dp, bottomEnd = 10.dp),
                ),
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 17.dp, vertical = 16.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                boardList.forEachIndexed { index, boardData ->
                    key(boardData.id) {
                        TaskCardView(
                            boardData,
                            onDragStart = { onTaskDragStart(boardData) },
                            onDragChange = onTaskDragChange,
                            onDragEnd = onTaskDragEnd,
                            onDragCancel = onTaskDragCancel,
                            onClick = onTaskClick,
                        )
                    }
                    if (index != boardList.lastIndex) Box(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}

private const val DEFAULT_TITLE = "LazyColumn 컴포넌트 구현"
private const val MAX_TITLE = "너무너무 긴 제목은 한 줄까지만 노출합니다."
private const val DEFAULT_CONTENT = "세로 스크롤 가능한 리스트 컴포넌트를 만들고 성능 최적화를 적용합니다."
private const val MAX_CONTENT = "너무너무너무 긴 설명은 두 줄까지만 노출하고 말줄임표로 처리합니다 두 줄까지만 노출합니다."
private const val DEFAULT_NAME = "다이노"
private const val MAX_NAME = "너무너무너무 긴 담당자도 한 줄까지만 노출합니다."

@Preview(showBackground = true)
@Composable
private fun StatusCardManageBoxPreview() {
    val boardList = listOf(
        Task(
            title = DEFAULT_TITLE,
            description = DEFAULT_CONTENT,
            tags = listOf(Tag("컴포넌트"), Tag("성능")),
            status = Status.TODO,
            nickname = DEFAULT_NAME,
        ),
        Task(
            title = DEFAULT_TITLE,
            tags = listOf(Tag("컴포넌트"), Tag("성능")),
            status = Status.TODO,
            nickname = DEFAULT_NAME,
        ),
        Task(
            title = DEFAULT_TITLE,
            description = DEFAULT_CONTENT,
            status = Status.TODO,
            nickname = DEFAULT_NAME,
        ),
        Task(
            title = DEFAULT_TITLE,
            status = Status.TODO,
            nickname = DEFAULT_NAME,
        ),
        Task(
            title = MAX_TITLE,
            description = MAX_CONTENT,
            tags = listOf(Tag("너무너무"), Tag("긴태그"), Tag("최대로"), Tag("5자까지"), Tag("5개제한임")),
            status = Status.TODO,
            nickname = MAX_NAME,
        ),
    )
    StatusCardManageBox(
        boardList = boardList,
        status = Status.TODO,
        statusColor = StatusColor(TODO_CARD_BOX_TITLE_COLOR, TODO_CARD_BOX_CONTENT_COLOR, TODO_CARD_BOX_BORDER_COLOR),
    )
}
