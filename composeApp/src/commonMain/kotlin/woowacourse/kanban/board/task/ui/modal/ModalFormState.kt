package woowacourse.kanban.board.task.ui.modal

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import woowacourse.kanban.board.task.domain.KanbanCard
import woowacourse.kanban.board.task.domain.KanbanCard.Companion.validateTags
import woowacourse.kanban.board.task.domain.KanbanCard.Companion.validateTitle
import woowacourse.kanban.board.task.domain.KanbanCardError
import woowacourse.kanban.board.task.domain.KanbanStatus

class ModalFormState {
    var title by mutableStateOf("")
    var content by mutableStateOf("")
    var tag by mutableStateOf("")
    var status by mutableIntStateOf(0)
    var assignee by mutableStateOf(AssigneeOption(AssigneeOptionType.NONE))

    var validTitle: KanbanCardError? by mutableStateOf(null)
    val isValidTitle by derivedStateOf { validTitle == null }

    var validTag: KanbanCardError? by mutableStateOf(null)
    val isValidTag by derivedStateOf { validTag == null }

    fun resetTitleError() {
        validTitle = null
    }

    fun resetTagError() {
        validTag = null
    }

    fun validate(): Boolean {
        validTitle = validateTitle(title)
        val tags = if (tag.isEmpty()) emptyList() else tag.split(",").map { it.trim() }
        validTag = validateTags(tags)
        return validTitle == null && validTag == null
    }

    fun toKanbanCard(): KanbanCard {
        val tags = if (tag.isEmpty()) emptyList() else tag.split(",").map { it.trim() }
        return KanbanCard(
            title = title,
            content = content,
            tags = tags,
            status = KanbanStatus.entries[status],
            assigneeName = when (assignee.type) {
                AssigneeOptionType.NONE -> null
                AssigneeOptionType.MEMBER -> assignee.name
            },
        )
    }

    fun toKanbanCardStatus(): KanbanStatus = KanbanStatus.entries[status]

    companion object {
        fun from(card: KanbanCard): ModalFormState {
            return ModalFormState().apply {
                title = card.title
                content = card.content
                tag = card.tags.joinToString(",")
                status = KanbanStatus.entries.indexOf(card.status)
                assignee = when (card.assigneeName) {
                    null -> AssigneeOption(AssigneeOptionType.NONE)
                    else -> AssigneeOption(AssigneeOptionType.MEMBER, card.assigneeName)
                }
            }
        }
    }
}
