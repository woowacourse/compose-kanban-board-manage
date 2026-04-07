package woowacourse.kanban.board.domain.model

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
data class Task(
    val id: String = Uuid.random().toString(),
    val title: String,
    val description: String? = null,
    val tags: Tags,
    val assignee: Assignee? = null,
    val status: Status,
) {
    init {
        require(title.isNotBlank()) { "제목이 비어있습니다." }
        if(status.requiredAssignee) require(assignee!=null){"${status}는 담당자가 필수입니다."}
    }
}
