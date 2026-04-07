package woowacourse.kanban.board.fixture

import woowacourse.kanban.board.domain.Assigned
import woowacourse.kanban.board.domain.Assignee
import woowacourse.kanban.board.domain.AssigneeState
import woowacourse.kanban.board.domain.KanbanTask
import woowacourse.kanban.board.domain.Status

fun createKanbanTask(
    id: Long = 0L,
    title: String = "제목",
    status: Status = Status.TO_DO,
    assigneeState: AssigneeState = Assigned(Assignee("별터")),
    description: String? = null,
    tags: List<String> = emptyList(),
): KanbanTask {
    return KanbanTask(
        id = id,
        title = title,
        status = status,
        assigneeState = assigneeState,
        description = description,
        tags = tags,
    )
}

fun createKanbanTasks(requiredTaskCount: Int, startId: Long): List<KanbanTask> {
    var id = startId
    val tasks = mutableListOf<KanbanTask>()
    repeat(requiredTaskCount) {
        tasks.add(createKanbanTask(id = id++))
    }
    return tasks.toList()
}
