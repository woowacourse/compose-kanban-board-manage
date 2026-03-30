package woowacourse.kanban.board.ui.component.board

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import kanbanboard.composeapp.generated.resources.Res
import kanbanboard.composeapp.generated.resources.status_done
import kanbanboard.composeapp.generated.resources.status_in_progress
import kanbanboard.composeapp.generated.resources.status_to_do
import org.jetbrains.compose.resources.stringResource
import woowacourse.kanban.board.domain.dialog.Status

fun Status.toMainColor(): Color = when (this) {
    Status.TO_DO -> Color(0xFF155DFC)
    Status.IN_PROGRESS -> Color(0xFFE17100)
    Status.DONE -> Color(0xFF00A63E)
}

fun Status.toBorderColor(): Color = when (this) {
    Status.TO_DO -> Color(0xFFBEDBFF)
    Status.IN_PROGRESS -> Color(0xFFFEE685)
    Status.DONE -> Color(0xFFB9F8CF)
}

fun Status.toBodyColor(): Color = when (this) {
    Status.TO_DO -> Color(0xFFEFF6FF)
    Status.IN_PROGRESS -> Color(0xFFFFFBEB)
    Status.DONE -> Color(0xFFF0FDF4)
}

@Composable
fun Status.toTitle(): String = when (this) {
    Status.TO_DO -> stringResource(Res.string.status_to_do)
    Status.IN_PROGRESS -> stringResource(Res.string.status_in_progress)
    Status.DONE -> stringResource(Res.string.status_done)
}
