package woowacourse.kanban.board.component.taskmodal

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import woowacourse.kanban.board.Blue50
import woowacourse.kanban.board.Blue80
import woowacourse.kanban.board.Gray20
import woowacourse.kanban.board.Gray70
import woowacourse.kanban.board.Red50
import woowacourse.kanban.board.component.ComponentText
import woowacourse.kanban.board.component.sample.ProfilePreviewData
import woowacourse.kanban.board.component.workspace.ModalState
import woowacourse.kanban.board.component.workspace.rememberModalState
import woowacourse.kanban.board.model.taskcard.Assignee
import woowacourse.kanban.board.model.taskcard.Status

@Composable
fun TaskModalButtonSection(
    modalState: ModalState,
    assignees: ImmutableList<Assignee>,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            text = ComponentText.STATE_BUTTON_LABEL,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = Gray20,
        )
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Status.entries.forEach { it ->
                TaskStatusButton(
                    modifier = modifier.weight(1f),
                    currentStatus = modalState.status,
                    myStatus = it,
                    onClick = { modalState.status = it },
                )
            }
        }
        Text(
            text = ComponentText.ASSIGNEE_LABEL_BUTTON_LABEL,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = Gray20,
        )
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            if (modalState.status.isAvailableEmptyAssignee()) {
                NoAssigneeLabelButton(
                    currentAssignee = modalState.assignee,
                    onClick = { modalState.assignee = null },
                )
            }
            assignees.forEach { profile ->
                TaskAssigneeLabelButton(
                    currentState = modalState.assignee,
                    myState = profile,
                    onClick = { modalState.assignee = profile },
                )
            }
        }
        if (modalState.isTaskAssigneeValid.not()) {
            Text(
                text = ComponentText.ASSIGNEE_ERROR,
                color = Red50,
            )
        }
    }
}

@Composable
private fun NoAssigneeLabelButton(
    currentAssignee: Assignee?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val backgroundColor = if (currentAssignee == null) Blue80 else Color.Transparent
    val borderColor = if (currentAssignee == null) Blue50 else Gray70

    Box(
        modifier = modifier
            .width(100.dp)
            .clip(RoundedCornerShape(10.dp))
            .border(1.dp, color = borderColor, shape = RoundedCornerShape(10.dp))
            .background(color = backgroundColor)
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 20.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                text = ComponentText.NO_ASSIGNEE_BUTTON_LABEL,
                modifier = Modifier.fillMaxWidth(),
                fontSize = 16.sp,
                color = Gray20,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Normal,
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun TaskModalButtonSectionTodoStatusPreview() {
    val profiles = ProfilePreviewData().values.toImmutableList()
    TaskModalButtonSection(
        assignees = profiles,
        modalState = rememberModalState(profiles),
    )
}
