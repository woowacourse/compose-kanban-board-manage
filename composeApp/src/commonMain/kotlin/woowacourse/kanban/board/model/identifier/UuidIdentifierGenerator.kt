package woowacourse.kanban.board.model.identifier

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class UuidIdentifierGenerator : IdentifierGenerator {
    override fun next(): String = Uuid.random().toString()
}
