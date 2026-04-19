package woowacourse.kanban.board.ui.component.board

import androidx.compose.ui.graphics.Color
import woowacourse.kanban.board.domain.dialog.Status

fun Status.toMainColor(): Color = when (this) {
    Status.TO_DO -> Color(0xFF155DFC)
    Status.IN_PROGRESS -> Color(0xFFE17100)
    Status.REVIEW -> Color(0xFF8B5CF6)
    Status.DONE -> Color(0xFF00A63E)
}

fun Status.toBorderColor(): Color = when (this) {
    Status.TO_DO -> Color(0xFFBEDBFF)
    Status.IN_PROGRESS -> Color(0xFFFEE685)
    Status.REVIEW -> Color(0xFFD2C7EA)
    Status.DONE -> Color(0xFFB9F8CF)
}

fun Status.toBodyColor(): Color = when (this) {
    Status.TO_DO -> Color(0xFFEFF6FF)
    Status.IN_PROGRESS -> Color(0xFFFFFBEB)
    Status.REVIEW -> Color(0xFFEDE9FE)
    Status.DONE -> Color(0xFFF0FDF4)
}

fun Status.toTitle(): String = when (this) {
    Status.TO_DO -> "To Do"
    Status.IN_PROGRESS -> "In Progress"
    Status.REVIEW -> "Review"
    Status.DONE -> "Done"
}
