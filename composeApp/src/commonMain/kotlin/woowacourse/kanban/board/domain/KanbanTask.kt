package woowacourse.kanban.board.domain

import kotlin.concurrent.atomics.AtomicLong
import kotlin.concurrent.atomics.ExperimentalAtomicApi
import kotlin.concurrent.atomics.fetchAndIncrement

@OptIn(ExperimentalAtomicApi::class)
data class KanbanTask(
    val id: Long = generateId(),
    val title: String,
    val status: Status,
    val assignee: Assignee?,
    val description: String? = null,
    val tags: List<String> = emptyList(),
) {
    init {
        require(isTitleValid(title)) { "제목은 비어 있거나 공백만 있을 수 없습니다." }
        require(isTagCountValid(tags)) { "태그는 5개까지만 등록할 수 있습니다." }
        require(isTagFormatValid(tags)) { "태그의 길이는 1에서 5자로 설정해야됩니다." }
    }

    fun canMoveTo(newStatus: Status, assignee: Assignee?) = newStatus.validateAssignee(assignee)

    fun changeStatus(newStatus: Status) = copy(status = newStatus)

    companion object {
        private val idIndex = AtomicLong(0L)

        fun isTitleValid(title: String): Boolean = title.isNotBlank()

        fun isTagCountValid(tags: List<String>): Boolean = tags.size <= 5

        fun isTagFormatValid(tags: List<String>): Boolean {
            if (tags.isEmpty()) return true
            return tags.all { it.length in 1..5 }
        }

        private fun generateId(): Long {
            return idIndex.fetchAndIncrement()
        }
    }
}
