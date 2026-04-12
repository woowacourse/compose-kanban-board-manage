package woowacourse.kanban.dialog.edit

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

class TaskEditState(task: KanbanTask, assignees: List<Assignee>) {
    var titleInputValue by mutableStateOf(task.data.title.content)
        private set
    var contentInputValue by mutableStateOf(task.data.content)
        private set
    var tagInputValue by mutableStateOf(task.data.tags.tags.joinToString(", "))
        private set

    var isTitleError by mutableStateOf(false)
        private set
    var isTagError by mutableStateOf(false)
        private set
    val isCreateError by derivedStateOf { isTitleError || isTagError }

    var selectedStatusIndex by mutableIntStateOf(TaskStatus.entries.indexOfFirst { it == task.status })
        private set
    var selectedAssigneeIndex by mutableIntStateOf(assignees.indexOfFirst { it == task.data.assignee })
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
