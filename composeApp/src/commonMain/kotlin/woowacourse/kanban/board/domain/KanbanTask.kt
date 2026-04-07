package woowacourse.kanban.board.domain

import woowacourse.kanban.domain.Assignee
import woowacourse.kanban.domain.BoardData
import woowacourse.kanban.domain.ChangeableResult
import woowacourse.kanban.domain.Tags
import woowacourse.kanban.domain.TaskStatus
import woowacourse.kanban.domain.Title

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
            tags = if (tags.isAllNotBlank) tags else Tags(emptyList()),
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

    fun changeStatus(targetStatus: TaskStatus): TaskChangeResult {
        return when (status.isChangeable(targetStatus, data.assignee)) {
            ChangeableResult.Changeable -> TaskChangeResult.Success(copy(data, targetStatus))
            ChangeableResult.NotAssigned -> TaskChangeResult.NotAssigned
            ChangeableResult.NotChangeable -> TaskChangeResult.NotChangeable
        }
    }
}
