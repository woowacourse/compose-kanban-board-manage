package woowacourse.kanban.board.exception

class TransStateException(val error: TransStateError) : IllegalArgumentException()
