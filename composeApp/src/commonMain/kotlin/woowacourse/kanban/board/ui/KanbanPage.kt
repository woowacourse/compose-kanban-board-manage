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
                    scope.launch {
                        snackbarHostState.currentSnackbarData?.dismiss()
                        snackbarHostState.showSnackbar(SnackBarText.CREATE_TASK)
                    }
                },
                onTaskUpdated = { task ->
                    boardStates[selectedProjectIndex].updateTask(task)
                    scope.launch {
                        snackbarHostState.currentSnackbarData?.dismiss()
                        snackbarHostState.showSnackbar(SnackBarText.CREATE_TASK)
                    }
                },
                onTaskDeleted = { id ->
                    boardStates[selectedProjectIndex].deleteTask(taskId = id)
                    scope.launch {
                        snackbarHostState.currentSnackbarData?.dismiss()
                        snackbarHostState.showSnackbar(SnackBarText.CREATE_TASK)
                    }
                },
                onStatusChanged = { status, id ->
                    boardStates[selectedProjectIndex].changeStatus(status = status, taskId = id)
                    scope.launch {
                        snackbarHostState.currentSnackbarData?.dismiss()
                        snackbarHostState.showSnackbar(SnackBarText.EDIT_TASK)
                    }
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
