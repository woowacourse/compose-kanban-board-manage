package woowacourse.kanban.board.domain

import woowacourse.kanban.board.domain.dialog.Status
import woowacourse.kanban.board.domain.result.TaskResult
import woowacourse.kanban.board.domain.validator.TaskEditValidator

data class KanbanTask(
    val id: Long = idIndex++,
    val title: String,
    val status: Status,
    val assignee: String? = null,
    val description: String? = null,
    val tags: List<String> = emptyList(),
) {
    init {
        require(isTitleValid(title)) { "제목은 비어 있거나 공백만 있을 수 없습니다." }
        require(isTagCountValid(tags)) { "태그는 5개까지만 등록할 수 있습니다." }
        require(isTagFormatValid(tags)) { "태그의 길이는 1에서 5자로 설정해야됩니다." }
        require(!(status != Status.TO_DO && assignee == null)) { "담당자가 필요한 상태입니다." }
    }

    fun changeStatus(newStatus: Status): TaskResult<EditError> {
        val error = TaskEditValidator.validateEditStatus(status, newStatus, assignee != null)

        if (error != null) {
            return TaskResult.Failed(error)
        }

        return TaskResult.Success(task = copy(status = newStatus))
    }

    fun editTask(newTask: KanbanTask): TaskResult<EditError> {
        val error = TaskEditValidator.validateEditStatus(status, newTask.status, assignee != null)

        if (error != null) {
            return TaskResult.Failed(error)
        }

        return TaskResult.Success(task = newTask)
    }

    companion object {
        private var idIndex = 0L

        fun isTitleValid(title: String): Boolean = title.isNotBlank()

        fun isTagCountValid(tags: List<String>): Boolean = tags.size <= 5

        fun isTagFormatValid(tags: List<String>): Boolean {
            if (tags.isEmpty()) return true
            return tags.all { it.length in 1..5 }
        }
    }
}
