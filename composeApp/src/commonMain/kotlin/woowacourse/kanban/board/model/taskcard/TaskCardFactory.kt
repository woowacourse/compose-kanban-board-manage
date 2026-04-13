package woowacourse.kanban.board.model.taskcard

import woowacourse.kanban.board.model.identifier.IdentifierGenerator

class TaskCardFactory(
    private val identifierGenerator: IdentifierGenerator,
) {
    fun create(
        title: Title,
        description: Description,
        tags: Tags,
        status: Status,
        profile: Profile,
    ): TaskCard {
        return TaskCard(
            id = identifierGenerator.next(),
            title = title,
            description = description,
            tags = tags,
            status = status,
            profile = profile,
        )
    }
}
