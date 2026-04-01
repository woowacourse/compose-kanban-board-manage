package woowacourse.kanban.board.ui.component.dialog

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import woowacourse.kanban.board.data.AssigneePool
import woowacourse.kanban.board.domain.Status

class TaskDialogState {
    var titleValue by mutableStateOf("")
    var isTitleDirty by mutableStateOf(false)
    var descriptionValue by mutableStateOf("")
    var tagValue by mutableStateOf("")
    var selectedStatus by mutableStateOf(Status.TO_DO)
    val assignees = AssigneePool.getAll()
    var selectedAssigneeIndex by mutableIntStateOf(0)
}
