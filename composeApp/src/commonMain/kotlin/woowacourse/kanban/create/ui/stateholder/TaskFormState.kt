package woowacourse.kanban.create.ui.stateholder

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import woowacourse.kanban.create.domain.TaskCreateAction

class TaskFormState(private val action: TaskCreateAction = TaskCreateAction()) {
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
        val result = action.validate(titleInputValue, tagInputValue)
        isTitleError = result.isTitleError
        isTagError = result.isTagError

        if (isTitleError) titleInputValue = ""
        if (isTagError) tagInputValue = ""

        return isTitleError || isTagError
    }
}
