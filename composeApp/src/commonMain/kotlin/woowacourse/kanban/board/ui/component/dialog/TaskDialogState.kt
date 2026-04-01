package woowacourse.kanban.board.ui.component.dialog

import androidx.compose.runtime.getValue
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
        private set
    var isTitleDirty by mutableStateOf(false)
        private set
    var descriptionValue by mutableStateOf(initialDescription)
    var tagValue by mutableStateOf(initialTag)
    var selectedStatus by mutableStateOf(initialStatus)
        private set
    val assignees: List<Assignee?>
        get() = if (selectedStatus.isRequiredAssignee) {
            allAssignees
        } else {
            listOf(null) + allAssignees
        }
    var assignee by mutableStateOf(initialAssignee)
        private set

    fun changeTitle(newTitle: String) {
        titleValue = newTitle
        isTitleDirty = true
    }

    fun changeStatus(newSelectedStatus: Status) {
        selectedStatus = newSelectedStatus

        if ((assignee in assignees).not()) {
            assignee = assignees.firstOrNull()
        }
    }

    fun changeAssignee(newAssignee: Assignee?) {
        assignee = newAssignee
    }
}
