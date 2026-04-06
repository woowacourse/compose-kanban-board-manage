package woowacourse.kanban.board.component.taskcard

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kanbanboard.composeapp.generated.resources.Res
import kanbanboard.composeapp.generated.resources.profile
import org.jetbrains.compose.resources.painterResource
import woowacourse.kanban.board.Gray20
import woowacourse.kanban.board.component.ComponentText
import woowacourse.kanban.board.model.taskcard.Assignee

@Composable
fun AssigneeLabel(
    assignee: Assignee?,
    modifier: Modifier = Modifier,
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        if (assignee != null) {
            Image(
                painter = painterResource(assignee.icon),
                contentDescription = "프로필 이미지",
                modifier = modifier.size(24.dp),
            )
            Spacer(modifier = modifier.width(8.dp))
            Text(
                text = assignee.nickname,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Gray20,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        } else {
            Text(
                text = ComponentText.NO_ASSIGNEE_LABEL,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Gray20,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AssigneeLabelPreview() {
    val assignee = Assignee("다이노", Res.drawable.profile)
    AssigneeLabel(assignee = assignee)
}
