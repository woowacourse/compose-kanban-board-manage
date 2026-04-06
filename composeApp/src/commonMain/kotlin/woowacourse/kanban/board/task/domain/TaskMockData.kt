package woowacourse.kanban.board.task.domain

object TaskMockData {
    val assignees = listOf("커비", "바드", "호이")
    val boards = listOf(
        KanbanBoard(
            boardId = 0,
            title = "Compose1",
        ),
        KanbanBoard(
            boardId = 1,
            title = "Compose2",
        ),
        KanbanBoard(
            boardId = 2,
            title = "Compose3너무너무길다 너무너무길다",
        ),
    )
}
