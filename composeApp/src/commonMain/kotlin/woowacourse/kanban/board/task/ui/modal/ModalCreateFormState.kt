package woowacourse.kanban.board.task.ui.modal

import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import woowacourse.kanban.board.task.domain.KanbanCard
import woowacourse.kanban.board.task.domain.KanbanCard.Companion.validateTags
import woowacourse.kanban.board.task.domain.KanbanCard.Companion.validateTitle
import woowacourse.kanban.board.task.domain.KanbanCardError
import woowacourse.kanban.board.task.domain.KanbanStatus

@Composable
fun RememberModalCreateFormState(assignees: List<String>, initialCard: KanbanCard? = null): ModalCreateFormState {
    return remember(initialCard) { ModalCreateFormState(assignees = assignees, initialCard = initialCard) }
}

class ModalCreateFormState(val assignees: List<String>, val initialCard: KanbanCard? = null) {
    var title by mutableStateOf(initialCard?.title ?: "")
    var content by mutableStateOf(initialCard?.content ?: "")
    var tag by mutableStateOf(initialCard?.tags?.joinToString(",") ?: "")
    var status by mutableIntStateOf(initialCard?.status?.let { KanbanStatus.entries.indexOf(it) } ?: 0)
    var assignee by mutableStateOf(
        initialCard?.assigneeName?.let { assignees.indexOf(it) }
            ?: if (initialCard?.status == KanbanStatus.TO_DO || initialCard == null) null else 0,
    )

    var validTitle: KanbanCardError? by mutableStateOf(null)

    val isValidTitle by derivedStateOf {
        validTitle == null
    }

    var validTag: KanbanCardError? by mutableStateOf(null)

    val isValidTag by derivedStateOf {
        validTag == null
    }

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

    fun toCard(): KanbanCard {
        val tags = if (tag.isEmpty()) emptyList() else tag.split(",").map { it.trim() }
        return KanbanCard(
            title = title,
            content = content,
            tags = tags,
            assigneeName = assignee?.let { assignees[it] },
            status = KanbanStatus.entries[status],
        )
    }
}
