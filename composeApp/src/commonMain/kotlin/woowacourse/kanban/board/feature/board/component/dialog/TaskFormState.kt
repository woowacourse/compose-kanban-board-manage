package woowacourse.kanban.board.feature.board.component.dialog

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import woowacourse.kanban.board.domain.KanbanTask
import woowacourse.kanban.board.domain.Tag
import woowacourse.kanban.board.domain.TaskStatus

class TaskFormState(task: KanbanTask? = null) {
    var title by mutableStateOf(task?.title ?: "")
    var isTitleDirty by mutableStateOf(task != null)
    var description by mutableStateOf(task?.description ?: "")
    var tagValue by mutableStateOf(task?.tags?.joinToString(", ") { it.value } ?: "")
    var selectedStatus by mutableStateOf(task?.status ?: TaskStatus.TODO)
    var selectedAssignee by mutableStateOf<String?>(task?.crewName)

    val isTitleError: Boolean
        get() = isTitleDirty && !KanbanTask.isTitleValid(title)

    val tags: List<Tag>
        get() {
            if (tagValue.isBlank()) return emptyList()
            return tagValue.split(",")
                .map { it.trim() }
                .filter { Tag.isValid(it) }
                .map { Tag(it) }
        }

    val rawTags: List<String>
        get() {
            if (tagValue.isBlank()) return emptyList()
            return tagValue.split(",").map { it.trim() }
        }

    val isTagCountError: Boolean
        get() = rawTags.size > 5

    val isTagFormatError: Boolean
        get() = tagValue.isNotBlank() && !rawTags.all { Tag.isValid(it) }

    val isAssigneeRequired: Boolean
        get() = selectedStatus.isAssigneeRequired

    val isAssigneeError: Boolean
        get() = isAssigneeRequired && selectedAssignee == null

    val isConfirmButtonEnabled: Boolean
        get() = KanbanTask.isTitleValid(title) && !isTagCountError && !isTagFormatError && (!isAssigneeRequired || selectedAssignee != null)
}

@Composable
fun rememberTaskFormState(task: KanbanTask? = null): TaskFormState = remember(task) { TaskFormState(task) }
