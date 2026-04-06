package woowacourse.kanban.board.task.domain

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid


data class KanbanCard @OptIn(ExperimentalUuidApi::class) constructor(
    val id: String = Uuid.random().toString(),
    val title: String,
    val content: String = "",
    val status: KanbanStatus,
    val assigneeName: String?,
    val tags: List<String> = emptyList(),
) {
    init {
        // 제목 검증
        val titleError = validateTitle(title)
        require(titleError == null) { "칸반 카드의 제목이 올바르지 않습니다. - 제목: $title" }

        // 태그 검증
        val tagError = validateTags(tags)
        require(tagError == null) { "칸반 카드의 태그 형식이 올바르지 않습니다. - 에러 타입: $tagError, tags: $tags" }

        // 카드 검증
        require(assigneeName?.isNotBlank() ?: true) { "칸반 카드의 담당자는 공백이거나 비어있을수 없습니다." }
    }

    fun updateStatus(next: KanbanStatus): KanbanCard {
        require(status.canTransitionTo(next)) {
            "해당 상태로 옮길 수 없습니다."
        }
        require(
            !(
                    status == KanbanStatus.TO_DO &&
                            next == KanbanStatus.IN_PROGRESS &&
                            assigneeName == null
                    ),
        ) {
            "담당자를 지정해야 상태를 옮길 수 있습니다."
        }

        return copy(status = next)
    }

    fun update(card: KanbanCard): KanbanCard {
        return copy(
            title = card.title,
            content = card.content,
            tags = card.tags,
            status = card.status,
            assigneeName = card.assigneeName,
        )
    }

    fun validateDeletable() {
        require(status != KanbanStatus.REVIEW && status != KanbanStatus.DONE) {
            "해당 상태에서는 태스크 삭제가 불가합니다."
        }
    }

    companion object {
        const val MAX_TAG_COUNT = 5
        const val MAX_TAG_LENGTH = 5

        fun create(card: KanbanCard): KanbanCard {
            return KanbanCard(
                title = card.title,
                content = card.content,
                status = card.status,
                assigneeName = card.assigneeName,
                tags = card.tags,
            )
        }

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
