package woowacourse.kanban.board.model.taskcard

class TaskCard(
    val id: String,
    val title: Title,
    val description: Description,
    val tags: Tags,
    val status: Status,
    val profile: Profile,
) {
    fun changeStatus(afterStatus: Status): TaskCard {
        return TaskCard(
            id = id,
            title = title,
            description = description,
            tags = tags,
            status = afterStatus,
            profile = profile
        )
    }

    fun update(updatedTask: TaskCard): TaskCard {
        return TaskCard(
            id = id,
            title = updatedTask.title,
            description = updatedTask.description,
            tags = updatedTask.tags,
            status = updatedTask.status,
            profile = updatedTask.profile
        )
    }
}
