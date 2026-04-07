package woowacourse.kanban.board.model

import java.util.UUID
import woowacourse.kanban.board.model.MoveStatus.FAILED
import woowacourse.kanban.board.model.MoveStatus.IN_PROGRESS
import woowacourse.kanban.board.model.MoveStatus.SUCCESS

data class BoardData(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val description: String = "",
    val tags: List<Tag> = emptyList(),
    val status: Status,
    val nickname: Nickname = Nickname.NONE,
) {
    init {
        require(!isTitleError(title)) { "[ERROR] 제목이 비어있으면 안됩니다." }
        require(!isTagsError(tags)) { "[ERROR] 태그의 개수가 너무 많습니다." }
    }

    fun getMoveStatus(targetStatus: Status): MoveStatus {
        return when (status) {
            Status.TODO if (targetStatus == Status.IN_PROGRESS && nickname == Nickname.NONE) -> {
                IN_PROGRESS
            }
            Status.TODO if ((targetStatus == Status.REVIEW || targetStatus == Status.DONE)) -> {
                FAILED
            }
            Status.IN_PROGRESS if (targetStatus == Status.DONE) -> {
                FAILED
            }
            Status.REVIEW if (targetStatus == Status.TODO) -> {
                FAILED
            }
            Status.DONE if (targetStatus == Status.IN_PROGRESS || targetStatus == Status.REVIEW) -> {
                FAILED
            }
            else -> {
                SUCCESS
            }
        }
    }

    companion object {
        const val MAX_TAGS_SIZE = 5
        fun isTitleError(title: String): Boolean = title.isBlank()
        fun isTagsError(tags: List<Tag>): Boolean = tags.size > MAX_TAGS_SIZE
    }
}
