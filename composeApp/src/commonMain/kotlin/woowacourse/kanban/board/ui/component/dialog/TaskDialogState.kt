package woowacourse.kanban.board.ui.component.dialog

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import woowacourse.kanban.board.data.AssigneePool
import woowacourse.kanban.board.domain.Assigned
import woowacourse.kanban.board.domain.AssigneeState
import woowacourse.kanban.board.domain.Status
import woowacourse.kanban.board.domain.Unassigned

class TaskDialogState(
    initialTitle: String = "",
    initialDescription: String = "",
    initialTag: String = "",
    initialStatus: Status = Status.TO_DO,
    initialAssigneeState: AssigneeState = Unassigned,
) {
    private val allAssigneeStates = AssigneePool.getAll().map { Assigned(it) }

    var titleValue by mutableStateOf(initialTitle)
        private set
    var isTitleDirty by mutableStateOf(false)
        private set
    var descriptionValue by mutableStateOf(initialDescription)
    var tagValue by mutableStateOf(initialTag)
    var selectedStatus by mutableStateOf(initialStatus)
        private set
    val assigneeStates: List<AssigneeState>
        get() = if (selectedStatus.isRequiredAssignee) {
            allAssigneeStates
        } else {
            listOf(Unassigned) + allAssigneeStates
        }
    var assigneeState by mutableStateOf(initialAssigneeState)
        private set

    fun changeTitle(newTitle: String) {
        titleValue = newTitle
        isTitleDirty = true
    }

    fun changeStatus(newSelectedStatus: Status) {
        selectedStatus = newSelectedStatus

        if ((assigneeState in assigneeStates).not()) {
            assigneeState = assigneeStates.first()
        }
    }

    fun changeAssignee(newAssigneeState: AssigneeState) {
        assigneeState = newAssigneeState
    }
}
