package woowacourse.kanban.board.ui.component.dialog

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import woowacourse.kanban.board.data.AssigneePool
import woowacourse.kanban.board.domain.Assignee
import woowacourse.kanban.board.domain.Status

class TaskDialogState(
    initialTitle: String = "",
    initialDescription: String = "",
    initialTag: String = "",
    initialStatus: Status = Status.TO_DO,
    initialAssignee: Assignee? = null,
) {
    private val allAssignees: List<Assignee> = AssigneePool.getAll()

    var titleValue by mutableStateOf(initialTitle)
    var isTitleDirty by mutableStateOf(false)
    var descriptionValue by mutableStateOf(initialDescription)
    var tagValue by mutableStateOf(initialTag)
    var selectedStatus by mutableStateOf(initialStatus)
    val assignees: List<Assignee?>
        get() = if (selectedStatus.isRequiredAssignee) {
            allAssignees
        } else {
            listOf(null) + allAssignees
        }
    var assignee by mutableStateOf<Assignee?>(initialAssignee)
    var selectedAssigneeIndex by mutableIntStateOf(
        assignees.indexOf(initialAssignee).takeIf { it >= 0 } ?: 0,
    )
}
