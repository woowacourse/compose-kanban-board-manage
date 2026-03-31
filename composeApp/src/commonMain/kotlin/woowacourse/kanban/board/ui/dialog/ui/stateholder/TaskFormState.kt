package woowacourse.kanban.board.ui.dialog.ui.stateholder

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import woowacourse.kanban.domain.Assignee
import woowacourse.kanban.domain.KanbanTask
import woowacourse.kanban.domain.Tags
import woowacourse.kanban.domain.Title

class TaskFormState {
    var titleInputValue by mutableStateOf("")
        private set
    var contentInputValue by mutableStateOf("")
        private set
    var tagInputValue by mutableStateOf("")
        private set

    var isTitleError by mutableStateOf(false)
        private set
    var isTagError by mutableStateOf(false)
        private set
    val isCreateError by derivedStateOf { isTitleError || isTagError }

    var selectedStatusIndex by mutableIntStateOf(0)
        private set
    var selectedAssigneeIndex: Int? by mutableStateOf(null)
        private set

    fun onTitleChange(input: String) {
        titleInputValue = input
        if (isTitleError) isTitleError = false
    }

    fun onContentChange(input: String) {
        contentInputValue = input
    }

    fun onTagChange(input: String) {
        tagInputValue = input
        if (isTagError) isTagError = false
    }

    fun onStatusSelect(index: Int) {
        selectedStatusIndex = index
    }

    fun onCoachSelect(index: Int) {
        selectedAssigneeIndex = index
    }

    fun onNoneAssigneeSelect() {
        selectedAssigneeIndex = null
    }

    fun onCreateValidate(): Boolean {
        isTitleError = !Title.isValid(titleInputValue)
        isTagError = !Tags.isLengthValid(tagInputValue.split(",")) ||
            !Tags.isContentValid(tagInputValue.split(","))

        if (isTitleError) titleInputValue = ""
        if (isTagError) tagInputValue = ""

        return isTitleError || isTagError
    }

    fun setTask(
        task: KanbanTask,
        assignees: List<Assignee>,
    ) {
        titleInputValue = task.data.title.content
        contentInputValue = task.data.content
        tagInputValue = task.data.tags.tags.joinToString()
        selectedStatusIndex = task.status.ordinal
        val foundIndex = assignees.indexOfFirst { it.nickname == task.data.assignee?.nickname }
        selectedAssigneeIndex = if (foundIndex == -1) null else foundIndex
    }
}
