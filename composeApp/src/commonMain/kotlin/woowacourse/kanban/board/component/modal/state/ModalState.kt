package woowacourse.kanban.board.component.modal.state

import kotlinx.collections.immutable.ImmutableList
import woowacourse.kanban.board.model.taskcard.Profile
import woowacourse.kanban.board.model.taskcard.Status
import woowacourse.kanban.board.model.taskcard.Tags
import woowacourse.kanban.board.model.taskcard.TaskCard
import woowacourse.kanban.board.model.taskcard.Title

data class ModalState(
    val title: String,
    val description: String,
    val tags: String,
    val status: Status,
    val profile: Profile,
) {
    constructor(
        profiles: ImmutableList<Profile>,
        initialTask: TaskCard?,
    ) : this(
        title = initialTask?.title?.value ?: "",
        description = initialTask?.description?.value ?: "",
        tags = initialTask?.tags?.value?.joinToString(",") { it.value } ?: "",
        status = initialTask?.status ?: Status.TODO,
        profile = initialTask?.profile ?: profiles.first(),
    )

    val isTitleValid: Boolean
        get() = Title.isTitleValid(title)

    val isTagsValid: Boolean
        get() = Tags.isValidInput(tags)

    val isSubmittable: Boolean
        get() = isTitleValid && isTagsValid
}
