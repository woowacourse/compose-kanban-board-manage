package woowacourse.kanban.board.component.extension

import woowacourse.kanban.board.component.ComponentText
import woowacourse.kanban.board.model.taskmodal.TextInputValue

fun TextInputValue.toLabel(): String = when (this) {
    TextInputValue.TITLE -> ComponentText.TITLE_LABEL
    TextInputValue.DESCRIPTION -> ComponentText.DESCRIPTION_LABEL
    TextInputValue.TAGS -> ComponentText.TAG_LABEL
}

fun TextInputValue.toPlaceholder(): String = when (this) {
    TextInputValue.TITLE -> ComponentText.TITLE_PLACEHOLDER
    TextInputValue.DESCRIPTION -> ComponentText.DESCRIPTION_PLACEHOLDER
    TextInputValue.TAGS -> ComponentText.TAG_PLACEHOLDER
}

fun TextInputValue.toErrorText(): String? = when (this) {
    TextInputValue.TITLE -> ComponentText.TITLE_ERROR
    TextInputValue.DESCRIPTION -> null
    TextInputValue.TAGS -> ComponentText.TAG_ERROR
}

fun TextInputValue.toSupportingText(): String? = when (this) {
    TextInputValue.TITLE -> null
    TextInputValue.DESCRIPTION -> null
    TextInputValue.TAGS -> ComponentText.TAG_SUPPORTING
}
