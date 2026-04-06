package woowacourse.kanban.board.domain

import java.util.UUID

data class Task(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val description: String = "",
    val tags: List<Tag> = emptyList(),
    val status: Status,
    val nickname: String,
) {
    init {
        require(!isTitleError(title)) { TITLE_ERROR_MESSAGE }
        require(!isTagsError(tags)) { TAGS_ERROR_MESSAGE }
        require(isAssigneeValidFor(status, nickname)) { ASSIGNEE_ERROR_MESSAGE }
    }

    companion object {
        const val MAX_TAGS_SIZE = 5
        const val UNASSIGNED_NICKNAME = "없음"
        const val TITLE_ERROR_MESSAGE = "[ERROR] 제목이 비어있으면 안됩니다."
        const val TAGS_ERROR_MESSAGE = "[ERROR] 태그의 개수가 너무 많습니다."
        const val ASSIGNEE_ERROR_MESSAGE = "[ERROR] To Do 상태만 담당자 미지정이 가능합니다."

        fun isTitleError(title: String): Boolean = title.isBlank()
        fun isTagsError(tags: List<Tag>): Boolean = tags.size > MAX_TAGS_SIZE
        fun isAssigneeValidFor(status: Status, nickname: String): Boolean =
            status == Status.TODO || nickname != UNASSIGNED_NICKNAME
    }

    fun canMoveTo(targetStatus: Status): Boolean {
        if (status == targetStatus) return false

        return when (status) {
            Status.TODO -> targetStatus == Status.IN_PROGRESS
            Status.IN_PROGRESS -> targetStatus == Status.TODO || targetStatus == Status.REVIEW
            Status.REVIEW -> targetStatus == Status.IN_PROGRESS || targetStatus == Status.DONE
            Status.DONE -> targetStatus == Status.TODO
        }
    }

    fun requiresAssigneeFor(targetStatus: Status): Boolean =
        status == Status.TODO && targetStatus == Status.IN_PROGRESS

    fun hasAssignee(): Boolean = nickname != UNASSIGNED_NICKNAME

    fun canMoveToWithAssigneeRule(targetStatus: Status): Boolean {
        if (!canMoveTo(targetStatus)) return false
        if (requiresAssigneeFor(targetStatus) && !hasAssignee()) return false
        return true
    }

    fun canBeDeleted(): Boolean = status == Status.TODO || status == Status.IN_PROGRESS
}
