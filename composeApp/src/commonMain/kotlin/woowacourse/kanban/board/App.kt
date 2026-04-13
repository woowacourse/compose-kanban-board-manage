package woowacourse.kanban.board

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import kotlinx.collections.immutable.toImmutableList
import woowacourse.kanban.board.component.WorkSpace
import woowacourse.kanban.board.model.identifier.UuidIdentifierGenerator
import woowacourse.kanban.board.model.project.ProjectFactory
import woowacourse.kanban.board.model.taskcard.Profile
import woowacourse.kanban.board.model.taskcard.TaskCard
import woowacourse.kanban.board.model.workspace.WorkSpace as WorkSpaceModel

@Composable
fun App() {
    val projectFactory = remember { ProjectFactory(UuidIdentifierGenerator()) }
    val workSpace = remember {
        WorkSpaceModel(
            listOf(
                projectFactory.create("Compose1", listOf<TaskCard>().toImmutableList()),
                projectFactory.create("Compose2", listOf<TaskCard>().toImmutableList()),
                projectFactory.create("Compose3너무너무긴문장은말줄임표로표시합니다", listOf<TaskCard>().toImmutableList()),
            ).toImmutableList()
        )
    }

    val profiles = remember {
        listOf(
            Profile("다이노"),
            Profile("페임스")
        ).toImmutableList()
    }

    WorkSpace(
        workSpace = workSpace,
        profiles = profiles,
    )
}
