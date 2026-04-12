package woowacourse.kanban.dialog.create

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import woowacourse.kanban.domain.task.Assignee
import woowacourse.kanban.domain.task.KanbanTask
import woowacourse.kanban.domain.task.Tags
import woowacourse.kanban.domain.task.TaskData
import woowacourse.kanban.domain.task.TaskStatus
import woowacourse.kanban.domain.task.Title

class TaskCreateState {
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
    var selectedAssigneeIndex by mutableIntStateOf(0)
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

    fun onCreateValidate(): Boolean {
        isTitleError = titleValidation()
        isTagError = tagValidation()

        if (isTitleError) titleInputValue = ""
        if (isTagError) tagInputValue = ""

        return isTitleError || isTagError
    }

    fun titleValidation(): Boolean {
        return Title.isValidTitle(titleInputValue)
    }

    fun tagValidation(): Boolean {
        return Tags.isValidTags(tagInputValue)
    }

    fun taskCreate(assignee: Assignee): KanbanTask {
        return KanbanTask(
            data = TaskData(
                title = Title(titleInputValue),
                content = contentInputValue,
                tags = Tags(
                    if (tagInputValue.isNotBlank()) tagInputValue.split(",")
                        .map { it.trim() } else emptyList(),
                ),
                assignee = assignee,
            ),
            status = TaskStatus.entries[selectedStatusIndex],
        )
    }
}
