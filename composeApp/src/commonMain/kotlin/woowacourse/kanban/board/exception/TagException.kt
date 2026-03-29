package woowacourse.kanban.board.exception

class TagException(val error: TagError) : IllegalArgumentException()
