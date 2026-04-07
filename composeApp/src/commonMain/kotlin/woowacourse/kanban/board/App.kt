package woowacourse.kanban.board

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import woowacourse.kanban.board.component.projectManage.ProjectBoard
import woowacourse.kanban.board.model.ProjectData
import woowacourse.kanban.board.state.ProjectBoardState

@Composable
fun App() {
    val projectDataState = remember { ProjectBoardState(ProjectData.defaultKanbanBoardDatas) }

    ProjectBoard(projectDataState)
}
