package woowacourse.kanban.board.component.sample

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import kanbanboard.composeapp.generated.resources.Res
import kanbanboard.composeapp.generated.resources.profile
import woowacourse.kanban.board.model.taskcard.Assignee

class ProfilePreviewData : PreviewParameterProvider<Assignee> {
    override val values: Sequence<Assignee> = sequenceOf(
        Assignee("다이노", Res.drawable.profile),
        Assignee("페임스", Res.drawable.profile)
    )
}
