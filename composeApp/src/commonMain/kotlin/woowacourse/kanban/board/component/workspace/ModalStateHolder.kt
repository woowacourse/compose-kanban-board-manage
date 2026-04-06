package woowacourse.kanban.board.component.workspace

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import woowacourse.kanban.board.model.taskcard.Assignee
import woowacourse.kanban.board.model.taskcard.Status
import woowacourse.kanban.board.model.taskcard.TaskCardData
import woowacourse.kanban.board.model.taskcard.TaskDescription
import woowacourse.kanban.board.model.taskcard.TaskTag
import woowacourse.kanban.board.model.taskcard.TaskTags
import woowacourse.kanban.board.model.taskcard.TaskTitle

@Stable
class ModalState(
    assignees: ImmutableList<Assignee>,
) {
    var title by mutableStateOf("")
    var description by mutableStateOf("")
    var tags by mutableStateOf("")
    var status by mutableStateOf(Status.TODO)
    var assignees by mutableStateOf(assignees)
    var assignee by mutableStateOf<Assignee?>(assignees.first())

    val isTaskTitleValid by derivedStateOf { TaskTitle.isTitleValid(title) }
    val isTaskTagsValid by derivedStateOf {
        TaskTag.isTagValid(tags) &&
                TaskTags.isTagsValid(
                    TaskTag.extractedTags(
                        tags,
                    ),
                )
    }

    val isTaskAssigneeValid by derivedStateOf {
        if (status != Status.TODO && assignee == null) false
        else true
    }

    val isFormValid by derivedStateOf {
        isTaskTitleValid && isTaskAssigneeValid && isTaskTagsValid
    }

    fun loadData(taskCardData: TaskCardData) {
        title = taskCardData.taskTitle.value
        description = taskCardData.taskDescription.value
        tags = taskCardData.taskTags.value.joinToString(",") { it.value }
        status = taskCardData.status
        assignee = taskCardData.assignee
    }

    fun clear() {
        title = ""
        description = ""
        tags = ""
        status = Status.TODO
        assignee = assignees.first()
    }

    fun toTaskCardData(): TaskCardData {
        val data = TaskCardData(
            taskTitle = TaskTitle(value = title),
            taskDescription = TaskDescription(value = description),
            taskTags = TaskTags(TaskTag.extractedTags(tags).toImmutableList()),
            status = status,
            assignee = assignee,
        )
        return data
    }
}

@Composable
fun rememberModalState(assignees: ImmutableList<Assignee>): ModalState = remember { ModalState(assignees) }
