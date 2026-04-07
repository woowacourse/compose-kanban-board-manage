package woowacourse.kanban.board.ui.dialog

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import woowacourse.kanban.board.domain.model.Assignee
import woowacourse.kanban.board.domain.model.Status
import woowacourse.kanban.board.domain.model.Task
import woowacourse.kanban.board.domain.validator.TaskValidator
import woowacourse.kanban.board.domain.validator.ValidationResult

class TaskCreateFormState(val assignees: List<Assignee>, val originTask: Task? = null) {
    val isEditingMode: Boolean get() = (originTask != null)

    var title by mutableStateOf(originTask?.title ?: "")
        private set
    val titleValidation: ValidationResult by derivedStateOf { TaskValidator.validateTitle(title) }

    var description by mutableStateOf(originTask?.description ?: "")
        private set

    var tag by mutableStateOf(originTask?.tags?.items?.joinToString(separator = ", ", transform = { it.content }) ?: "")
        private set

    val tagValidation: ValidationResult by derivedStateOf { TaskValidator.validateTags(tag) }

    var selectedStatus by mutableStateOf(originTask?.status ?: Status.TODO)
        private set

    var selectedAssignee: Assignee? by mutableStateOf(originTask?.assignee)
        private set

    // 담당자 필수 여부
    val requiredAssignee get() = selectedStatus.requiredAssignee

    val assigneeValidation: ValidationResult by derivedStateOf { TaskValidator.validateAssignee(requiredAssignee, selectedAssignee) }

    val canCreate by derivedStateOf {
        titleValidation is ValidationResult.Valid &&
            tagValidation !is ValidationResult.Invalid &&
            assigneeValidation is ValidationResult.Valid
    }
    fun updateTitle(input: String) {
        title = input
    }

    fun updateContent(input: String) {
        description = input
    }

    fun updateTag(input: String) {
        tag = input
    }

    fun updateAssignee(assignee: Assignee?) {
        selectedAssignee = assignee
    }

    fun updateStatus(status: Status) {
        this.selectedStatus = status
    }
}
