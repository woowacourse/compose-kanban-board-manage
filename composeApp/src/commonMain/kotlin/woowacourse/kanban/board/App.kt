package woowacourse.kanban.board

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.tooling.preview.Preview
import woowacourse.kanban.board.task.domain.KanbanProject
import woowacourse.kanban.board.task.domain.TaskMockData
import woowacourse.kanban.board.task.ui.project.KanbanProjectScreen
import woowacourse.kanban.board.task.ui.project.RememberKanbanProjectState

@Composable
@Preview(widthDp = 1300, heightDp = 900)
fun App() {
    MaterialTheme {
        val coroutineScope = rememberCoroutineScope()
        val kanbanProject = KanbanProject(
            projectTitle = "4주차 미션 보드",
            boards = TaskMockData.boards,
        )
        val kanbanProjectState = RememberKanbanProjectState(
            coroutineScope = coroutineScope,
            kanbanProject = kanbanProject,
        )
        KanbanProjectScreen(
            kanbanProjectState = kanbanProjectState,
        )
    }
}
