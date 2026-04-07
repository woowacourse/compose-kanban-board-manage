package woowacourse.kanban.board.state

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import woowacourse.kanban.board.constant.DialogMessage
import woowacourse.kanban.board.model.BoardData
import woowacourse.kanban.board.model.Nickname
import woowacourse.kanban.board.model.Status
import woowacourse.kanban.board.model.Tag

class BoardDataState(
    val id: String = "",
    title: String = "",
    description: String = "",
    tags: List<Tag> = emptyList(),
    status: Status = Status.TODO,
    name: Nickname = Nickname.DINO,
) {

    val statuses = Status.entries

    fun names(): List<Nickname> {
        return if (statusValue == Status.TODO) {
            Nickname.entries
        } else {
            listOf(Nickname.DINO, Nickname.PAMES)
        }
    }

    var titleInputValue by mutableStateOf(title)
    var descriptionInputValue by mutableStateOf(description)
    var tagsInputValue by mutableStateOf(tags.joinToString(",") { it.text })
    var statusValue by mutableStateOf(status)
    var nameValue by mutableStateOf(name)

    var isTitleError by mutableStateOf(false)
    var isTagsError by mutableStateOf(false)

    var isNicknameError by mutableStateOf(false)

    fun titleOnValueChange(value: String) {
        titleInputValue = value
        isTitleError = BoardData.isTitleError(titleInputValue)
    }

    fun descriptionOnValueChange(value: String) {
        descriptionInputValue = value
    }

    fun tagsOnValueChange(value: String) {
        tagsInputValue = value
        val tags = if (tagsInputValue.isNotBlank()) tagsInputValue.split(",") else emptyList()
        isTagsError = tags.any { Tag.isTagError(it) } || BoardData.isTagsError(tags.map { Tag(it) })
    }

    fun statusOnValueChange(status: Status) {
        statusValue = status
        isNicknameError = (nameValue == Nickname.NONE && statusValue != Status.TODO)
    }

    fun isSelectedStatus(status: Status): Boolean {
        return statusValue == status
    }

    fun nameOnValueChange(name: Nickname) {
        nameValue = name
        isNicknameError = (nameValue == Nickname.NONE && statusValue != Status.TODO)
    }

    fun isSelectedName(name: Nickname): Boolean {
        return nameValue == name
    }

    fun changeTagsValue(): List<Tag> = if (tagsInputValue.isNotBlank()) tagsInputValue.split(",").map { Tag(it) } else emptyList()

    fun getDialogMessage(): String {
        var message = ""
        if (isTitleError) message = DialogMessage.TITLE_ERROR_MESSAGE
        if (isTagsError) message = DialogMessage.TAGS_ERROR_MESSAGE
        if (isNicknameError) message = DialogMessage.NICKNAME_ERROR_MESSAGE
        return message
    }

    fun getNickname(name: Nickname): String {
        return when (name) {
            Nickname.DINO -> "다이노"
            Nickname.PAMES -> "페임스"
            Nickname.NONE -> ""
        }
    }
}
