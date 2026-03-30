package woowacourse.kanban.board.domain

import woowacourse.kanban.domain.KanbanTask

class KanbanProject(inputTasks: List<KanbanTask>, val title: String = "") {
    private val tasks: MutableList<KanbanTask> = inputTasks.toMutableList()

    fun getTasks(): List<KanbanTask> {
        return tasks.toList()
    }
}
