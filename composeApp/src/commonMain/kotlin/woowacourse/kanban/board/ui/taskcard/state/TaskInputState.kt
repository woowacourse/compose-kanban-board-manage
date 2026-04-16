package woowacourse.kanban.board.ui.taskcard.state

import woowacourse.kanban.board.domain.Task.Companion.checkEditable
import woowacourse.kanban.board.domain.Task.Companion.checkNeedProfile
import woowacourse.kanban.board.domain.TaskState
import woowacourse.kanban.board.exception.TagError
import woowacourse.kanban.board.exception.TitleError

data class TaskInputState(
    val title: String = "",
    val titleError: TitleError = TitleError.NONE,
    val content: String = "",
    val tags: String = "",
    val tagError: TagError = TagError.NONE,
    val selectedState: TaskState = TaskState.ToDo,
    val selectedAuthor: String = "",
) {
    val init: Boolean
        get() = title.isEmpty() && content.isEmpty() && tags.isEmpty()
    val isNewTaskEnabled: Boolean
        get() = titleError == TitleError.NONE && tagError == TagError.NONE && checkEditable(selectedState, selectedAuthor)
    val isUpdateTaskEnabled: Boolean
        get() = isNewTaskEnabled && checkEditable(selectedState, selectedAuthor)
    val isDeleteEnabled: Boolean
        get() = selectedState.isDeletable
    val needProfile: Boolean
        get() = checkNeedProfile(selectedState)
}
