package woowacourse.kanban.board.component.sample

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import kotlinx.collections.immutable.toImmutableList
import woowacourse.kanban.board.model.project.Project

class ProjectPreviewData : PreviewParameterProvider<Project> {
    val taskCards = TaskCardPreviewData().values.toImmutableList()
    override val values: Sequence<Project> = sequenceOf(
        Project(
            title = "Compose1",
            initialTasks = taskCards
        ),
        Project(
            title = "Compose2",
            initialTasks = taskCards
        ),
        Project(
            title = "Compose3너무너무길경우에는 말줄임표로 표시됩니다.",
            initialTasks = taskCards
        ),
    )
}
