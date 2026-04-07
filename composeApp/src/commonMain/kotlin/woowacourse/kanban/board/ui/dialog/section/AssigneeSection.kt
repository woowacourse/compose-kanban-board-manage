package woowacourse.kanban.board.ui.dialog.section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kanbanboard.composeapp.generated.resources.Res
import kanbanboard.composeapp.generated.resources.label_assignee
import org.jetbrains.compose.resources.stringResource
import woowacourse.kanban.board.domain.model.Assignee
import woowacourse.kanban.board.ui.component.Label
import woowacourse.kanban.board.ui.component.UserProfile

@Composable
fun AssigneeSection(
    modifier: Modifier = Modifier,
    assignees: List<Assignee>,
    selectedAssignee: Assignee?,
    requiredAssignee: Boolean,
    onUserChange: (Assignee?) -> Unit,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Label(stringResource(Res.string.label_assignee), true)
        assignees.chunked(3).forEach { users ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                if (!requiredAssignee) {
                    UnassignedChip(selected = selectedAssignee == null, onUserChange = { onUserChange(null) }, modifier = Modifier)
                }
                users.forEach { assignee ->
                    AssigneeChip(
                        assignee = assignee,
                        selected = assignee == selectedAssignee,
                        onUserChange = { onUserChange(assignee) },
                        modifier = Modifier.weight(1f),
                    )
                }
            }
        }
    }
}

@Composable
fun UnassignedChip(selected: Boolean, onUserChange: () -> Unit, modifier: Modifier = Modifier) {
    FilterChip(
        selected = selected,
        onClick = onUserChange,
        label = {
            Text("없음", modifier = Modifier.padding(vertical = 16.dp))
        },
        colors = FilterChipDefaults.filterChipColors(
            containerColor = Color.White,
            selectedContainerColor = Color(0xffEFF6FF),
            selectedLabelColor = Color(0xff1447E6),
        ),
        modifier = modifier.width(70.dp),
        border = FilterChipDefaults.filterChipBorder(
            enabled = true,
            selected = selected,
            borderColor = Color.Gray,
            selectedBorderColor = Color.Blue,
            borderWidth = 1.dp,
            selectedBorderWidth = 1.dp,
        ),
    )
}

@Composable
fun AssigneeChip(assignee: Assignee?, selected: Boolean, onUserChange: () -> Unit, modifier: Modifier = Modifier) {
    FilterChip(
        selected = selected,
        onClick = onUserChange,
        label = {
            if (assignee == null) {
                Text("없음", modifier = Modifier.padding(vertical = 16.dp))
            } else {
                UserProfile(assignee = assignee, Modifier.padding(vertical = 16.dp))
            }
        },
        colors = FilterChipDefaults.filterChipColors(
            containerColor = Color.White,
            selectedContainerColor = Color(0xffEFF6FF),
            selectedLabelColor = Color(0xff1447E6),
        ),
        modifier = modifier.width(200.dp),
        border = FilterChipDefaults.filterChipBorder(
            enabled = true,
            selected = selected,
            borderColor = Color.Gray,
            selectedBorderColor = Color.Blue,
            borderWidth = 1.dp,
            selectedBorderWidth = 1.dp,
        ),
    )
}

@Composable
@Preview(showBackground = true)
private fun AssigneePreview() {
    var selectedAssignee by remember { mutableStateOf(Assignee("디노")) }

    val managers = listOf(
        Assignee("디노"),
        Assignee("제임스"),
        Assignee("로미"),
        Assignee("로미"),
        Assignee("로미"),
    )

    AssigneeSection(
        assignees = managers,
        selectedAssignee = selectedAssignee,
        requiredAssignee = false,
        onUserChange = { },
    )
}

@Composable
@Preview(showBackground = true)
private fun AssigneeChipPreview() {
    AssigneeChip(
        assignee = Assignee("김철수"),
        selected = true,
        onUserChange = {},
    )
}
