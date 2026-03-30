package woowacourse.kanban.domain

class KanbanTask(val data: BoardData, val status: TaskStatus) {

    constructor(
        title: Title,
        content: String,
        tags: Tags,
        nickname: Nickname,
        status: TaskStatus,
    ) : this(
        data = BoardData(
            title = title,
            content = content,
            tags = tags,
            nickname = nickname,
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
