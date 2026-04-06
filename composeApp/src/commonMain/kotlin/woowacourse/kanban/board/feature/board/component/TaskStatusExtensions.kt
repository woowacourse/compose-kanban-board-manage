package woowacourse.kanban.board.feature.board.component

import woowacourse.kanban.board.domain.TaskStatus

val TaskStatus.displayName: String
    get() = when (this) {
        TaskStatus.TODO -> "To Do"
        TaskStatus.IN_PROGRESS -> "In Progress"
        TaskStatus.REVIEW -> "Review"
        TaskStatus.DONE -> "Done"
    }
