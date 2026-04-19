package woowacourse.kanban.board.ui.screen.board

import woowacourse.kanban.board.domain.EditError

sealed class EditUiEvent {
    data object Success : EditUiEvent()
    data class Error(val error: EditError) : EditUiEvent()
}
