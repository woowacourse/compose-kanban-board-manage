package woowacourse.kanban.board.ui.constant

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import woowacourse.kanban.board.domain.KanbanProject
import woowacourse.kanban.domain.Assignee
import woowacourse.kanban.domain.BoardData
import woowacourse.kanban.domain.KanbanTask
import woowacourse.kanban.domain.Nickname
import woowacourse.kanban.domain.Tags
import woowacourse.kanban.domain.TaskStatus
import woowacourse.kanban.domain.Title

object MockData {
    val ASSIGNEES = listOf(
        Assignee(
            Nickname(
                "다이노",
            ),
            icon = Icons.Default.AccountCircle,
        ),
        Assignee(
            Nickname(
                "페임스",
            ),
            icon = Icons.Default.AccountCircle,
        ),
    )

    private val DAINO = Assignee(Nickname("다이노"), icon = Icons.Default.AccountCircle)
    private val FAMES = Assignee(Nickname("페임스"), icon = Icons.Default.AccountCircle)

    var MOCK_PROJECTS = mutableListOf(
        KanbanProject(
            title = "Compose1",
            inputTasks = mutableListOf(
                KanbanTask(
                    data = BoardData(
                        title = Title("제목"),
                        content = "내용",
                        tags = Tags(),
                        assignee = DAINO,
                        id = 0,
                    ),
                    status = TaskStatus.TO_DO,
                ),
                KanbanTask(
                    data = BoardData(
                        title = Title("제목"),
                        content = "내용",
                        tags = Tags(),
                        assignee = FAMES,
                        id = 1,
                    ),
                    status = TaskStatus.TO_DO,
                ),
                KanbanTask(
                    data = BoardData(
                        title = Title("제목"),
                        content = "내용",
                        tags = Tags(),
                        assignee = DAINO,
                        id = 2,
                    ),
                    status = TaskStatus.TO_DO,
                ),
            ),
        ),
        KanbanProject(
            title = "Compose2",
            inputTasks = mutableListOf(
                KanbanTask(
                    data = BoardData(
                        title = Title("제목"),
                        content = "내용",
                        tags = Tags(),
                        assignee = FAMES,
                        id = 3,
                    ),
                    status = TaskStatus.IN_PROGRESS,
                ),
                KanbanTask(
                    data = BoardData(
                        title = Title("제목"),
                        content = "내용",
                        tags = Tags(),
                        assignee = DAINO,
                        id = 4,
                    ),
                    status = TaskStatus.IN_PROGRESS,
                ),
                KanbanTask(
                    data = BoardData(
                        title = Title("제목"),
                        content = "내용",
                        tags = Tags(),
                        assignee = FAMES,
                        id = 5,
                    ),
                    status = TaskStatus.IN_PROGRESS,
                ),
            ),
        ),
        KanbanProject(
            title = "compose3 너무너무 길어진 프로젝트 이름",
            inputTasks = mutableListOf(
                KanbanTask(
                    data = BoardData(
                        title = Title("제목"),
                        content = "내용",
                        tags = Tags(),
                        assignee = DAINO,
                        id = 6,
                    ),
                    status = TaskStatus.DONE,
                ),
                KanbanTask(
                    data = BoardData(
                        title = Title("제목"),
                        content = "내용",
                        tags = Tags(),
                        assignee = FAMES,
                        id = 7,
                    ),
                    status = TaskStatus.DONE,
                ),
                KanbanTask(
                    data = BoardData(
                        title = Title("제목"),
                        content = "내용",
                        tags = Tags(),
                        assignee = DAINO,
                        id = 8,
                    ),
                    status = TaskStatus.DONE,
                ),
            ),
        ),
    )
}
