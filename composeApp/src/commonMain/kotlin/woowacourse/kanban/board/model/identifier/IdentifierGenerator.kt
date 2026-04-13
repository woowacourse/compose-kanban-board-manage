package woowacourse.kanban.board.model.identifier

interface IdentifierGenerator {
    fun next(): String
}
