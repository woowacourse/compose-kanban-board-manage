package woowacourse.kanban.board.state

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import woowacourse.kanban.board.domain.Task
import woowacourse.kanban.board.domain.Status
import woowacourse.kanban.board.domain.Tag

class DialogState {
    var titleInputValue by mutableStateOf("")
    var descriptionInputValue by mutableStateOf("")
    var tagsInputValue by mutableStateOf("")
    var statusValue by mutableStateOf(Status.TODO)
    var nameValue by mutableStateOf("다이노")

    var isTitleError by mutableStateOf(false)
    var isTagsError by mutableStateOf(false)
    var createdTask by mutableStateOf<Task?>(null)
    var mode by mutableStateOf(DialogMode.CREATE)
    var snackbarMessage by mutableStateOf<String?>(null)

    var editTask by mutableStateOf<Task?>(null)
    var deleteTask by mutableStateOf<Task?>(null)

    fun titleOnValueChange(value: String) {
        titleInputValue = value
        isTitleError = Task.isTitleError(titleInputValue)
    }

    fun descriptionOnValueChange(value: String) {
        descriptionInputValue = value
    }

    fun tagsOnValueChange(value: String) {
        tagsInputValue = value
        val tags = parseTagTexts()
        isTagsError = tags.any { Tag.isTagError(it) } || Task.isTagsError(tags.map { Tag(it) })
    }

    fun statusOnValueChange(status: Status) {
        statusValue = status
    }

    fun isSelectedStatus(status: Status): Boolean {
        return statusValue == status
    }

    fun nameOnValueChange(name: String) {
        nameValue = name
    }

    fun isSelectedName(name: String): Boolean {
        return nameValue == name
    }

    fun selectMode() {
        when (mode) {
            DialogMode.CREATE -> onTaskCreate()
            DialogMode.EDIT -> onTaskEdit()
        }
    }

    fun onTaskCreate() {
        runCatching {
            Task(
                title = titleInputValue,
                description = descriptionInputValue,
                tags = parseTagTexts().map { Tag(it) },
                status = statusValue,
                nickname = nameValue,
            )
        }.onSuccess {
            createdTask = it
        }.onFailure { error ->
            snackbarMessage = validationMessage(error)
        }
    }

    fun onTaskEdit(){
        val currentTask = editTask ?: return
        runCatching {
            currentTask.copy(
                title = titleInputValue,
                description = descriptionInputValue,
                tags = parseTagTexts().map { Tag(it) },
                status = statusValue,
                nickname = nameValue,
            )
        }.onSuccess {
            editTask = it
            createdTask = it
        }.onFailure { error ->
            snackbarMessage = validationMessage(error)
        }
    }

    private fun validationMessage(error: Throwable): String? {
        return when (error.message) {
            Task.TITLE_ERROR_MESSAGE -> null
            Task.TAGS_ERROR_MESSAGE -> null
            Task.ASSIGNEE_ERROR_MESSAGE -> "이 상태에서는 담당자를 지정해야 합니다."
            else -> null
        }
    }

    private fun parseTagTexts(): List<String> =
        if (tagsInputValue.isNotBlank()) tagsInputValue.split(",") else emptyList()

    fun onTaskDelete(){
        val task = editTask ?: return

        deleteTask = task
    }

    fun loadTaskData(task: Task){
        titleInputValue = task.title
        descriptionInputValue = task.description
        tagsInputValue = task.tags.joinToString(", ") { it.text }
        statusValue = task.status
        nameValue = task.nickname
        editTask = task
    }

    fun resetDialog() {
        titleInputValue = ""
        descriptionInputValue = ""
        tagsInputValue = ""
        statusValue = Status.TODO
        nameValue = "다이노"
        isTitleError = false
        isTagsError = false
        createdTask = null
        editTask = null
        deleteTask = null
        mode = DialogMode.CREATE
        snackbarMessage = null
    }

    fun resetSnackbarMessage() {
        snackbarMessage = null
    }
}

enum class DialogMode {
    CREATE, EDIT
}
