package woowacourse.kanban.board.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import woowacourse.kanban.board.domain.KanbanProject
import woowacourse.kanban.board.domain.TaskReturnType
import woowacourse.kanban.board.ui.constant.SnackBarText
import woowacourse.kanban.board.ui.stateholder.BoardState
import woowacourse.kanban.domain.Assignee

@Composable
fun KanbanPage(
    projects: List<KanbanProject>,
    assignees: List<Assignee>,
    modifier: Modifier = Modifier,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    var selectedProjectIndex by remember { mutableIntStateOf(0) }

    val boardStates = remember(projects) {
        projects.map { BoardState(it) }
    }

    val scope = rememberCoroutineScope()
    val showSnackBar: (TaskReturnType) -> Unit = { type ->
        val display =
            when (type) {
                TaskReturnType.CREATE_SUCCESS -> SnackBarText.CREATE_TASK
                TaskReturnType.TASK_STATUS_SUCCESS -> SnackBarText.STATUS_EDIT
                TaskReturnType.UPDATE_SUCCESS -> SnackBarText.UPDATE_TASK
                TaskReturnType.DELETE_SUCCESS -> SnackBarText.DELETE_TASK
                TaskReturnType.NOT_UPDATABLE -> SnackBarText.ILLEGAL_STATUS_EDIT
                TaskReturnType.NOT_DELETABLE -> SnackBarText.ILLEGAL_DELETE
                TaskReturnType.NOT_ASSIGNED -> SnackBarText.ILLEGAL_STATUS_EDIT_ASSIGNEE
            }
        scope.launch {
            snackbarHostState.currentSnackbarData?.dismiss()
            snackbarHostState.showSnackbar(display)
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbarHostState, modifier = Modifier.offset(y = (-50).dp)) { data ->
                KanbanSnackBar(data)
            }
        },
        modifier = modifier,
    ) { innerPadding ->
        Row(modifier = Modifier.padding(innerPadding)) {
            KanbanSidebar(
                projects,
                selectedProjectIndex = selectedProjectIndex,
                onClick = { index ->
                    selectedProjectIndex = index
                },
            )
            KanbanBoard(
                boardState = boardStates[selectedProjectIndex],
                projectTitle = projects[selectedProjectIndex].title,
                onTaskCreated = { task ->
                    boardStates[selectedProjectIndex].addTask(task)
                    showSnackBar(TaskReturnType.CREATE_SUCCESS)
                },
                onTaskUpdated = { task ->
                    boardStates[selectedProjectIndex].updateTask(task)
                    showSnackBar(TaskReturnType.UPDATE_SUCCESS)
                },
                onTaskDeleted = { id ->
                    val taskReturnType = boardStates[selectedProjectIndex].deleteTask(taskId = id)
                    showSnackBar(taskReturnType)
                },
                onStatusChanged = { status, id ->
                    val taskReturnType = boardStates[selectedProjectIndex].changeStatus(status = status, taskId = id)
                    showSnackBar(taskReturnType)
                },
                assignees = assignees,
            )
        }
    }
}

@Preview(widthDp = 1600, heightDp = 900)
@Composable
fun KanbanPagePreview() {
    KanbanPage(
        projects = emptyList(),
        assignees = emptyList(),
    )
}
