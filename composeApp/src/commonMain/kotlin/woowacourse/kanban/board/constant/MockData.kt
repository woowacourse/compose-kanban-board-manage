package woowacourse.kanban.board.constant

import java.util.UUID
import woowacourse.kanban.domain.project.KanbanProject
import woowacourse.kanban.domain.task.Assignee
import woowacourse.kanban.domain.task.KanbanTask
import woowacourse.kanban.domain.task.Tags
import woowacourse.kanban.domain.task.TaskData
import woowacourse.kanban.domain.task.TaskStatus
import woowacourse.kanban.domain.task.Title

object MockData {

    val MOCK_PROJECTS = listOf(
        KanbanProject(
            title = "Compose1",
            tasks = mutableListOf(
                KanbanTask(
                    data = TaskData(
                        title = Title("제목"),
                        content = "내용",
                        tags = Tags(),
                        assignee = Assignee.DINO,
                        id = UUID.randomUUID(),
                    ),
                    status = TaskStatus.TO_DO,
                ),
                KanbanTask(
                    data = TaskData(
                        title = Title("제목"),
                        content = "내용",
                        tags = Tags(),
                        assignee = Assignee.DINO,
                        id = UUID.randomUUID(),
                    ),
                    status = TaskStatus.TO_DO,
                ),
                KanbanTask(
                    data = TaskData(
                        title = Title("제목"),
                        content = "내용",
                        tags = Tags(),
                        assignee = Assignee.DINO,
                        id = UUID.randomUUID(),
                    ),
                    status = TaskStatus.TO_DO,
                ),
            ),
        ),
        KanbanProject(
            title = "Compose2",
            tasks = mutableListOf(
                KanbanTask(
                    data = TaskData(
                        title = Title("제목"),
                        content = "내용",
                        tags = Tags(),
                        assignee = Assignee.DINO,
                        id = UUID.randomUUID(),
                    ),
                    status = TaskStatus.IN_PROGRESS,
                ),
                KanbanTask(
                    data = TaskData(
                        title = Title("제목"),
                        content = "내용",
                        tags = Tags(),
                        assignee = Assignee.DINO,
                        id = UUID.randomUUID(),
                    ),
                    status = TaskStatus.IN_PROGRESS,
                ),
                KanbanTask(
                    data = TaskData(
                        title = Title("제목"),
                        content = "내용",
                        tags = Tags(),
                        assignee = Assignee.DINO,
                        id = UUID.randomUUID(),
                    ),
                    status = TaskStatus.IN_PROGRESS,
                ),
            ),
        ),
        KanbanProject(
            title = "compose3 너무너무 길어진 프로젝트 이름",
            tasks = mutableListOf(
                KanbanTask(
                    data = TaskData(
                        title = Title("제목"),
                        content = "내용",
                        tags = Tags(),
                        assignee = Assignee.DINO,
                        id = UUID.randomUUID(),
                    ),
                    status = TaskStatus.DONE,
                ),
                KanbanTask(
                    data = TaskData(
                        title = Title("제목"),
                        content = "내용",
                        tags = Tags(),
                        assignee = Assignee.DINO,
                        id = UUID.randomUUID(),
                    ),
                    status = TaskStatus.DONE,
                ),
                KanbanTask(
                    data = TaskData(
                        title = Title("제목"),
                        content = "내용",
                        tags = Tags(),
                        assignee = Assignee.DINO,
                        id = UUID.randomUUID(),
                    ),
                    status = TaskStatus.DONE,
                ),
            ),
        ),
    )
}
