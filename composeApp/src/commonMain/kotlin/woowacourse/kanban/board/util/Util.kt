package woowacourse.kanban.board.util

fun splitByComma(text: String) = text.split(",").map { tag -> tag.trim() }
