package woowacourse.kanban.board.model.project

import kotlinx.collections.immutable.ImmutableList
import woowacourse.kanban.board.model.identifier.IdentifierGenerator
import woowacourse.kanban.board.model.taskcard.TaskCard

class ProjectFactory(
    private val identifierGenerator: IdentifierGenerator,
) {
    fun create(
        title: String,
        tasks: ImmutableList<TaskCard>,
    ): Project {
        return Project(
            id = identifierGenerator.next(),
            title = title,
            tasks = tasks,
        )
    }
}
