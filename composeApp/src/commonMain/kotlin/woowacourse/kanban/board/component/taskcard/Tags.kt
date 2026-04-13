package woowacourse.kanban.board.component.taskcard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.collections.immutable.toImmutableList
import woowacourse.kanban.board.component.util.Gray20
import woowacourse.kanban.board.component.util.Gray80
import woowacourse.kanban.board.model.taskcard.Tag
import woowacourse.kanban.board.model.taskcard.Tags

@Composable
fun Tags(
    tags: Tags,
    modifier: Modifier = Modifier,
) {
    if (tags.value.isNotEmpty()) {
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = modifier
                .fillMaxWidth(),
        ) {
            tags.value.forEach { tag ->
                TagBox(tag)
            }
        }
    }
}

@Composable
private fun TagBox(tag: Tag) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(14.dp))
            .background(
                color = Gray80,
            )
            .padding(vertical = 4.dp, horizontal = 6.dp),
    ) {
        Text(
            text = tag.value,
            fontSize = 12.sp,
            color = Gray20,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TagsPreview() {
    val tags = Tags(value = listOf(Tag(value = "컴포넌트")).toImmutableList())
    Tags(tags = tags)
}
