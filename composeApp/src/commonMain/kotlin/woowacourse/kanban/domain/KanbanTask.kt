package woowacourse.kanban.domain

class KanbanTask(val data: BoardData, val status: TaskStatus) {

    constructor(
        title: Title,
        content: String,
        tags: Tags,
        assignee: Assignee,
        status: TaskStatus,
    ) : this(
        data = BoardData(
            title = title,
            content = content,
            tags = tags,
            assignee = assignee,
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
