package woowacourse.kanban.board.task.domain

import java.util.UUID

data class KanbanCard(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val assigneeName: String?,
    val status: KanbanStatus,
    val content: String = "",
    val tags: List<String> = emptyList(),
) {
    init {
        val titleError = validateTitle(title)
        require(titleError == null) { "칸반 카드의 제목이 올바르지 않습니다. - 제목: $title" }
        val tagError = validateTags(tags)
        require(tagError == null) { "칸반 카드의 태그 형식이 올바르지 않습니다. - 에러 타입: $tagError, tags: $tags" }
    }

    fun updateStatus(toStatus: KanbanStatus): KanbanCardResult {
        if (!status.isTransitionStatus(toStatus)) {
            return KanbanCardResult.Failure.InvalidTransition(status, toStatus)
        }
        if (!isTranslationStatusWithAssignee(toStatus)) {
            return KanbanCardResult.Failure.AssigneeRequired(status)
        }
        return KanbanCardResult.Success(copy(status = toStatus))
    }

    fun updateCard(
        title: String,
        assigneeName: String?,
        status: KanbanStatus,
        content: String = "",
        tags: List<String> = emptyList(),
    ): KanbanCardResult {
        if (!this.status.isTransitionStatus(status)) {
            return KanbanCardResult.Failure.InvalidTransition(this.status, status)
        }
        if (status.isAssigneeRequired && assigneeName == null) {
            return KanbanCardResult.Failure.AssigneeRequired(status)
        }

        return KanbanCardResult.Success(
            copy(
                title = title,
                assigneeName = assigneeName,
                status = status,
                content = content,
                tags = tags,
            ),
        )
    }

    private fun isTranslationStatusWithAssignee(toStatus: KanbanStatus) = !(toStatus.isAssigneeRequired && assigneeName == null)

    companion object {
        private const val MAX_TAG_COUNT = 5
        private const val MAX_TAG_LENGTH = 5

        fun validateTitle(title: String): KanbanCardError? {
            if (title.isBlank()) return KanbanCardError.TITLE_FORMAT
            return null
        }

        fun validateTags(tags: List<String>): KanbanCardError? {
            if (tags.any { it.isBlank() }) return KanbanCardError.TAG_FORMAT
            if (tags.size > MAX_TAG_COUNT || tags.any { it.length > MAX_TAG_LENGTH }) return KanbanCardError.TAG_SIZE
            return null
        }
    }
}

enum class KanbanCardError {
    TAG_FORMAT,
    TAG_SIZE,
    TITLE_FORMAT,
}

enum class KanbanError {
    INVALID_TRANSITION,
    ASSIGNEE_REQUIRED,
    DELETION_NOT_ALLOWED,
    KANBAN_NOT_FOUND,
}
