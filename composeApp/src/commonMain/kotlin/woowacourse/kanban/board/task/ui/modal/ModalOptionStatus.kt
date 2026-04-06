package woowacourse.kanban.board.task.ui.modal

import androidx.compose.foundation.background
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import kanbanboard.composeapp.generated.resources.Res
import kanbanboard.composeapp.generated.resources.status_Done
import kanbanboard.composeapp.generated.resources.status_In_Progress
import kanbanboard.composeapp.generated.resources.status_To_Do
import kanbanboard.composeapp.generated.resources.status_review
import org.jetbrains.compose.resources.stringResource
import woowacourse.kanban.board.task.domain.KanbanStatus

@Composable
fun ModalOptionStatus(modifier: Modifier = Modifier, kanbanStatus: KanbanStatus) {
    val status = when (kanbanStatus) {
        KanbanStatus.TO_DO -> stringResource(Res.string.status_To_Do)
        KanbanStatus.IN_PROGRESS -> stringResource(Res.string.status_In_Progress)
        KanbanStatus.REVIEW -> stringResource(Res.string.status_review)
        KanbanStatus.DONE -> stringResource(Res.string.status_Done)
    }
    Text(
        modifier = modifier,
        text = status,
    )
}

@Preview
@Composable
private fun ModalOptionStatusPreview() {
    ModalOptionStatus(
        modifier = Modifier.background(Color.White),
        kanbanStatus = KanbanStatus.TO_DO,
    )
}
