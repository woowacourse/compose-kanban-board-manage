package woowacourse.kanban.board.ui.screen.board

import woowacourse.kanban.board.domain.DeleteError

sealed class DeleteUiEvent {
    data object Success : DeleteUiEvent()
    data class Error(val error: DeleteError) : DeleteUiEvent()
}
