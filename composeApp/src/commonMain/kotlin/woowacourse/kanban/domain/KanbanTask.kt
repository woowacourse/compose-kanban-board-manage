package woowacourse.kanban.domain

import kotlin.collections.emptyList

class KanbanTask(val data: BoardData, val status: TaskStatus) {

    constructor(
        title: Title,
        content: String,
        tags: Tags,
        assignee: Assignee?,
        status: TaskStatus,
        id: Long? = System.currentTimeMillis(),
    ) : this(
        data = BoardData(
            title = title,
            content = content,
            tags = if (tags.tags.all { it.isNotBlank() }) tags else Tags(emptyList()),
            assignee = assignee,
            id = id
                ?: System.currentTimeMillis(),
        ),
        status = status,
    )

    fun copy(
        inputData: BoardData = data,
        inputStatus: TaskStatus = status,
    ): KanbanTask {
        return KanbanTask(inputData, inputStatus)
    }
}
