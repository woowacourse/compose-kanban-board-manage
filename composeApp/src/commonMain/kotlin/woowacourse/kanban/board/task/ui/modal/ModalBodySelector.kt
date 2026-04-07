package woowacourse.kanban.board.task.ui.modal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kanbanboard.composeapp.generated.resources.Res
import kanbanboard.composeapp.generated.resources.label_assignee
import kanbanboard.composeapp.generated.resources.label_status
import org.jetbrains.compose.resources.stringResource
import woowacourse.kanban.board.task.domain.KanbanStatus
import woowacourse.kanban.board.task.domain.TaskMockData
import woowacourse.kanban.board.theme.AssigneeButtonBackground
import woowacourse.kanban.board.theme.BorderAssigneeButton
import woowacourse.kanban.board.theme.BorderStatusButton
import woowacourse.kanban.board.theme.StatusButtonBackground

@Composable
fun ModalSelector(title: String, modifier: Modifier = Modifier, content: @Composable RowScope.() -> Unit) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        ModalInputTitle(title)

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            content()
        }
    }
}

@Preview
@Composable
private fun ModalStatusSelectorPreview() {
    var selectedId by remember { mutableIntStateOf(0) }

    ModalSelector(
        title = stringResource(Res.string.label_status),
    ) {
        KanbanStatus.entries.forEachIndexed { id, status ->
            ModalOptionButton(
                modifier = Modifier.height(52.dp),
                onClick = { selectedId = id },
                isSelected = selectedId == id,
                selectedContainerColor = StatusButtonBackground,
                selectedBorderColor = BorderStatusButton,
            ) {
                ModalOptionStatus(
                    modifier = Modifier,
                    kanbanStatus = status,
                )
            }
        }
    }
}

@Preview
@Composable
private fun ModalAssigneeSelectorPreview() {
    var selectedId by remember { mutableIntStateOf(0) }

    ModalSelector(
        title = stringResource(Res.string.label_assignee),
    ) {
        TaskMockData.assignees.forEachIndexed { id, name ->
            ModalOptionButton(
                modifier = Modifier,
                onClick = {
                    selectedId = id
                },
                isSelected = selectedId == id,
                selectedContainerColor = AssigneeButtonBackground,
                selectedBorderColor = BorderAssigneeButton,
            ) {
                ModalOptionAssignee(
                    modifier = Modifier,
                    name = name,
                )
            }
        }
    }
}
