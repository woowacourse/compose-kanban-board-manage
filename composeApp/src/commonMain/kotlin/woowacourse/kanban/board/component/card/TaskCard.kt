package woowacourse.kanban.board.component.card

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import woowacourse.kanban.board.theme.BORDER_COLOR
import woowacourse.kanban.board.domain.Task
import woowacourse.kanban.board.domain.Status
import woowacourse.kanban.board.domain.Tag

@Composable
fun TaskCard(task: Task, modifier: Modifier = Modifier,
             onClick: () -> Unit = {}) {
    Box(
        modifier = modifier
            .clip(shape = RoundedCornerShape(15.dp))
            .clickable(onClick = onClick)
            .background(color = Color.White)
            .border(
                width = 1.dp,
                color = Color(BORDER_COLOR),
                shape = RoundedCornerShape(15.dp),
            )
            .width(270.dp)
            .padding(12.dp),
    ) {
        Column {
            // 제목
            TitleComponent(title = task.title, modifier = Modifier.padding(vertical = 8.dp).testTag("제목"))

            // 중간 내용
            if (task.description.isNotBlank()) {
                DescriptionComponent(description = task.description, modifier = Modifier.padding(vertical = 4.dp).testTag("중간내용"))
            }

            // 태그
            if (task.tags.isNotEmpty()) {
                TagsComponent(tags = task.tags, modifier = Modifier.padding(vertical = 8.dp).testTag("테그목록"))
            }

            // 구분선
            HorizontalDivider(thickness = 2.dp)

            // 작성자
            ProfileComponent(nickname = task.nickname, modifier = Modifier.padding(vertical = 8.dp).testTag("프로필"))
        }
    }
}

private const val DEFAULT_TITLE = "LazyColumn 컴포넌트 구현"
private const val MAX_TITLE = "너무너무 긴 제목은 한 줄까지만 노출합니다."
private const val DEFAULT_CONTENT = "세로 스크롤 가능한 리스트 컴포넌트를 만들고 성능 최적화를 적용합니다."
private const val MAX_CONTENT = "너무너무너무 긴 설명은 두 줄까지만 노출하고 말줄임표로 처리합니다 두 줄까지만 노출합니다."
private const val DEFAULT_NAME = "다이노"
private const val MAX_NAME = "너무너무너무 긴 담당자도 한 줄까지만 노출합니다."

private class BoardPreviewParameterProvider : PreviewParameterProvider<Task> {
    override val values = sequenceOf(
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
}

@Preview(showBackground = true)
@Composable
private fun BoardScreenView(@PreviewParameter(BoardPreviewParameterProvider::class) task: Task) {
    TaskCard(task)
}
