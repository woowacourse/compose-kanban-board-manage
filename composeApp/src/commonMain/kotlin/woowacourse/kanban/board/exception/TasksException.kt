package woowacourse.kanban.board.exception

class TasksException(val error: TasksError) : IllegalStateException()
