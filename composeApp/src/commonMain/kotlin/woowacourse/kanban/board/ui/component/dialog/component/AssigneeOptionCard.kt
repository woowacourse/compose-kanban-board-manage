package woowacourse.kanban.board.ui.component.dialog.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kanbanboard.composeapp.generated.resources.Res
import kanbanboard.composeapp.generated.resources.assignee_null
import org.jetbrains.compose.resources.stringResource
import woowacourse.kanban.board.domain.Assigned
import woowacourse.kanban.board.domain.Assignee
import woowacourse.kanban.board.domain.AssigneeState
import woowacourse.kanban.board.domain.Unassigned
import woowacourse.kanban.board.ui.KanbanTypography

@Composable
fun AssigneeOptionCard(
    assigneeState: AssigneeState,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    if (assigneeState is Assigned) {
        TaskOptionCard(
            isSelected = isSelected,
            onClick = onClick,
            modifier = modifier,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .padding(vertical = 20.dp, horizontal = 16.dp),
            ) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "기본 이미지",
                    tint = Color(0xFF838383),
                    modifier = Modifier.size(24.dp),
                )

                Text(
                    text = assigneeState.assignee.name,
                    style = KanbanTypography.label14Medium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    } else {
        TaskOptionCard(
            isSelected = isSelected,
            onClick = onClick,
            modifier = modifier,
            width = 72,
        ) {
            Text(
                text = stringResource(Res.string.assignee_null),
                modifier = Modifier.padding(23.dp),
                style = KanbanTypography.label14Medium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

@Preview(showBackground = true, name = "작성자 있음")
@Composable
private fun AssigneeOptionCardPreview1() {
    var isSelected by remember { mutableStateOf(false) }

    AssigneeOptionCard(
        assigneeState = Assigned(Assignee("다이노")),
        isSelected = isSelected,
        onClick = { isSelected = !isSelected },
    )
}

@Preview(showBackground = true, name = "작성자 없음")
@Composable
private fun AssigneeOptionCardPreview2() {
    var isSelected by remember { mutableStateOf(false) }

    AssigneeOptionCard(
        assigneeState = Unassigned,
        isSelected = isSelected,
        onClick = { isSelected = !isSelected },
    )
}
