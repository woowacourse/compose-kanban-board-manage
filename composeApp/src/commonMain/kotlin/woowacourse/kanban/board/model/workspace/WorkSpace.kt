package woowacourse.kanban.board.model.workspace

import kotlinx.collections.immutable.ImmutableList
import woowacourse.kanban.board.model.project.Project

data class WorkSpace(
    val projects: ImmutableList<Project>
)
