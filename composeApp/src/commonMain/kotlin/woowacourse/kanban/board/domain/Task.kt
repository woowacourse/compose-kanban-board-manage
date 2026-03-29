package woowacourse.kanban.board.domain

import java.util.UUID

data class Task(
    val id: UUID = UUID.randomUUID(),
    val title: String,
    val content: String = "",
    val tags: List<String> = emptyList(),
    val taskState: TaskState = TaskState.TO_DO,
    val author: String = "다이노",
)
