package woowacourse.kanban.board.ui.component.card

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kanbanboard.composeapp.generated.resources.Res
import kanbanboard.composeapp.generated.resources.assignee_null
import org.jetbrains.compose.resources.stringResource
import woowacourse.kanban.board.domain.Assignee

@Composable
fun KanbanCardProfile(
    assignee: Assignee,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.padding(end = 20.dp, bottom = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        if (assignee != null) {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "기본 이미지",
                tint = Color(0xFF838383),
                modifier = Modifier.size(24.dp),
            )
        }

        Text(
            text = assignee.name,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Preview(showBackground = true, name = "작성자 있음")
@Composable
private fun KanbanCardProfilePreview1() {
    KanbanCardProfile(assignee = Assignee("별터"))
}
