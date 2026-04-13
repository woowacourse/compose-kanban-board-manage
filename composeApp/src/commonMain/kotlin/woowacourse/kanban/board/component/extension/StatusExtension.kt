package woowacourse.kanban.board.component.extension

import androidx.compose.ui.graphics.Color
import kotlinx.collections.immutable.ImmutableList
import woowacourse.kanban.board.component.util.Blue60
import woowacourse.kanban.board.component.util.Blue70
import woowacourse.kanban.board.component.util.Blue90
import woowacourse.kanban.board.component.util.ComponentText
import woowacourse.kanban.board.component.util.Green60
import woowacourse.kanban.board.component.util.Green70
import woowacourse.kanban.board.component.util.Green90
import woowacourse.kanban.board.component.util.Purple20
import woowacourse.kanban.board.component.util.Purple40
import woowacourse.kanban.board.component.util.Purple60
import woowacourse.kanban.board.component.util.Yellow60
import woowacourse.kanban.board.component.util.Yellow70
import woowacourse.kanban.board.component.util.Yellow90
import woowacourse.kanban.board.model.project.Project
import woowacourse.kanban.board.model.taskcard.Status
import woowacourse.kanban.board.model.taskcard.TaskCard

fun Status.toText(): String = when (this) {
    Status.TODO -> ComponentText.STATE_BUTTON_TODO
    Status.PROGRESS -> ComponentText.STATE_BUTTON_PROGRESS
    Status.REVIEW -> ComponentText.STATE_BUTTON_REVIEW
    Status.DONE -> ComponentText.STATE_BUTTON_DONE
}

fun Status.toBackgroundColor(): Color = when (this) {
    Status.TODO -> Blue90
    Status.PROGRESS -> Yellow90
    Status.REVIEW -> Purple20
    Status.DONE -> Green90
}

fun Status.toBorderColor(): Color = when (this) {
    Status.TODO -> Blue70
    Status.PROGRESS -> Yellow70
    Status.REVIEW -> Purple40
    Status.DONE -> Green70
}

fun Status.toHeaderColor(): Color = when (this) {
    Status.TODO -> Blue60
    Status.PROGRESS -> Yellow60
    Status.REVIEW -> Purple60
    Status.DONE -> Green60
}

fun Status.toFilterTask(project: Project): ImmutableList<TaskCard> =
    project.filterTasksbyStatus(this)
