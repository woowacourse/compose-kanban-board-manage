package woowacourse.kanban.board.fixture

import woowacourse.kanban.board.domain.KanbanTask
import woowacourse.kanban.board.domain.dialog.Status

fun createKanbanTask(
    id: Long = 0L,
    title: String = "제목",
    status: Status = Status.TO_DO,
    assignee: String = "별터",
    description: String? = null,
    tags: List<String> = emptyList(),
): KanbanTask {
    return KanbanTask(
        id = id,
        title = title,
        status = status,
        assignee = assignee,
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
