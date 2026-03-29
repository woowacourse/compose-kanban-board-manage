package woowacourse.kanban.board.ui.taskcard.state

import woowacourse.kanban.board.domain.TaskState
import woowacourse.kanban.board.exception.TagError
import woowacourse.kanban.board.exception.TitleError

data class TaskInputState(
    val title: String = "",
    val titleError: TitleError = TitleError.NONE,
    val content: String = "",
    val tags: String = "",
    val tagError: TagError = TagError.NONE,
    val selectedState: TaskState = TaskState.TO_DO,
    val selectedAuthor: String = "",
) {
    val init: Boolean
        get() = title.isEmpty() && content.isEmpty() && tags.isEmpty()
    val isNewTaskEnabled: Boolean
        get() = titleError == TitleError.NONE && tagError == TagError.NONE
}
