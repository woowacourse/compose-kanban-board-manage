package woowacourse.kanban.board.ui.component.dialog

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import woowacourse.kanban.board.domain.KanbanTask
import woowacourse.kanban.board.domain.dialog.Status

class TaskDialogState(task: KanbanTask? = null) {
    var titleValue by mutableStateOf("")
        private set
    var isTitleDirty by mutableStateOf(false)
        private set
    val isTitleError by derivedStateOf {
        isTitleDirty && !KanbanTask.isTitleValid(titleValue)
    }
    var descriptionValue by mutableStateOf("")
        private set
    var tagValue by mutableStateOf("")
        private set
    val tags by derivedStateOf {
        tagValue.split(",").map { it.trim() }
    }
    val isTagCountError by derivedStateOf {
        tagValue.isNotBlank() && !KanbanTask.isTagCountValid(tags)
    }
    val isTagFormatError by derivedStateOf {
        tagValue.isNotBlank() && !KanbanTask.isTagFormatValid(tags)
    }
    var selectedStatus by mutableStateOf(Status.TO_DO)
        private set
    val assignees = listOf("다이노", "페임스")
    var selectedAssignee by mutableStateOf(assignees[0])
        private set
    var isSelectedEmptyAssignee by mutableStateOf(false)
        private set
    val enabled by derivedStateOf {
        KanbanTask.isTitleValid(titleValue) && !isTagCountError && !isTagFormatError
    }

    init {
        if (task != null) {
            setTaskData(task)
        }
    }

    fun updateTitleValue(newTitle: String) {
        titleValue = newTitle
    }

    fun updateTitleDirty() {
        isTitleDirty = true
    }

    fun updateDescriptionValue(newDescription: String) {
        descriptionValue = newDescription
    }

    fun updateTagValue(newTag: String) {
        tagValue = newTag
    }

    fun updateSelectedStatus(newStatus: Status) {
        selectedStatus = newStatus
    }

    fun updateSelectedAssignee(newAssignee: String) {
        selectedAssignee = newAssignee
    }

    fun updateIsSelectedEmptyAssignee(isSelected: Boolean) {
        isSelectedEmptyAssignee = isSelected
    }

    fun setTaskData(task: KanbanTask) {
        titleValue = task.title
        selectedStatus = task.status
        descriptionValue = task.description ?: ""
        tagValue = task.tags.joinToString(",")
        if (task.assignee == null) {
            isSelectedEmptyAssignee = true
        } else {
            selectedAssignee = task.assignee
        }
    }

    fun updateTask(task: KanbanTask): KanbanTask {
        return task.copy(
            title = titleValue,
            description = descriptionValue.takeIf { it.isNotBlank() },
            tags = if (tagValue.isEmpty()) emptyList() else tags,
            status = selectedStatus,
            assignee = if (isSelectedEmptyAssignee) null else selectedAssignee,
        )
    }

    fun createTask(): KanbanTask {
        return KanbanTask(
            title = titleValue,
            description = descriptionValue.takeIf { it.isNotBlank() },
            tags = if (tagValue.isEmpty()) emptyList() else tags,
            status = selectedStatus,
            assignee = if (isSelectedEmptyAssignee) null else selectedAssignee,
        )
    }
}
