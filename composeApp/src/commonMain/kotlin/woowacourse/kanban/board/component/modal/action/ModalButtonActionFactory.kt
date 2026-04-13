package woowacourse.kanban.board.component.modal.action

import woowacourse.kanban.board.component.modal.state.ModalState
import woowacourse.kanban.board.component.util.ComponentText
import woowacourse.kanban.board.model.taskcard.Status
import woowacourse.kanban.board.model.taskcard.TaskCard
import woowacourse.kanban.board.model.taskcard.TaskCardPolicy

fun interface ModalButtonAction {
    fun execute()
}

class ModalButtonActionFactory(
    private val modalState: ModalState,
    private val initialTask: TaskCard?,
    private val onModalStateChange: (ModalState) -> Unit,
    private val buildTaskCard: (String?) -> TaskCard,
    private val onShowSnackbar: (String) -> Unit,
    private val onCreateTask: (TaskCard) -> Unit,
    private val onUpdateTask: (String, TaskCard) -> Unit,
    private val onDeleteTask: (String) -> Unit,
) {
    fun createStatusChangeAction(nextStatus: Status): ModalButtonAction = ModalButtonAction {
        when {
            modalState.status == nextStatus -> Unit
            !TaskCardPolicy.canModifyStatus(modalState.status, nextStatus) -> {
                onShowSnackbar(ComponentText.BOARD_TASK_INVALID_STATUS_SNACKBAR)
            }
            TaskCardPolicy.requireProfile(nextStatus) && !modalState.profile.isAssigned -> {
                onShowSnackbar(ComponentText.BOARD_TASK_REQUIRE_PROFILE_SNACKBAR)
            }
            else -> {
                onModalStateChange(modalState.copy(status = nextStatus))
            }
        }
    }

    fun createTaskCreateAction(): ModalButtonAction = ModalButtonAction {
        if (modalState.isSubmittable.not()) return@ModalButtonAction

        onCreateTask(buildTaskCard(null))
    }

    fun createTaskModifyAction(): ModalButtonAction = ModalButtonAction {
        if (modalState.isSubmittable.not()) return@ModalButtonAction

        initialTask?.let { task ->
            onUpdateTask(task.id, buildTaskCard(task.id))
        }
    }

    fun createTaskDeleteAction(): ModalButtonAction = ModalButtonAction {
        initialTask?.let { task ->
            if (TaskCardPolicy.canDelete(modalState.status)) {
                onDeleteTask(task.id)
            } else {
                onShowSnackbar(ComponentText.BOARD_TASK_DELETE_DENIED_SNACKBAR)
            }
        }
    }
}
