package woowacourse.kanban.board.component.board

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import woowacourse.kanban.board.Gray80
import woowacourse.kanban.board.component.sample.ProjectPreviewData
import woowacourse.kanban.board.model.project.Project
import woowacourse.kanban.board.model.taskcard.TaskCardData

@Composable
fun TaskBoard(
    project: Project,
    onShowMoveSuccessSnackBar: () -> Unit,
    onShowMoveFailedSnackbar: () -> Unit,
    onShowNoAssigneeSnackbar: () -> Unit,
    onShowCreateTaskModal: () -> Unit,
    onShowEditTaskModal: (TaskCardData) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .background(Gray80),
    ) {
        TaskBoardHeader(
            title = project.title,
            doneRate = project.calculateDoneRate(),
            doneTasks = project.doneTasks.size,
            totalTasks = project.allTasksCount,
            onClickCreateTask = onShowCreateTaskModal,
        )
        TaskColumnSection(
            project = project,
            onMoveSuccessSnackBar = onShowMoveSuccessSnackBar,
            onShowEditTaskModal = onShowEditTaskModal,
            onMoveFailedSnackBar = onShowMoveFailedSnackbar,
            onMoveNoAssigneeSnackBar = onShowNoAssigneeSnackbar,
        )
    }
}

@Preview(showBackground = true, widthDp = 1000)
@Composable
private fun TaskBoardPreview() {
    val project = ProjectPreviewData().values.toMutableList()[0]
    MaterialTheme {
        TaskBoard(
            project = project,
            onShowMoveSuccessSnackBar = { },
            onShowCreateTaskModal = { },
            onShowEditTaskModal = { },
            onShowMoveFailedSnackbar = {},
            onShowNoAssigneeSnackbar = {},
        )
    }
}
