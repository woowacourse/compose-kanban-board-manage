package woowacourse.kanban.board.ui.board

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import woowacourse.kanban.board.domain.Task
import woowacourse.kanban.board.domain.TaskState
import woowacourse.kanban.board.domain.Tasks
import woowacourse.kanban.board.ui.board.components.BoardHeader
import woowacourse.kanban.board.ui.board.components.CreateTaskModalDialog
import woowacourse.kanban.board.ui.board.components.KanbanBoardContent
import woowacourse.kanban.board.ui.theme.OutlineVariant
import woowacourse.kanban.board.ui.theme.Primary

@Composable
fun Board(
    projectName: String,
    tasks: Tasks,
    onTaskCreated: (Task) -> Unit,
    authors: List<String>,
    modifier: Modifier = Modifier,
    onTaskStateChange: (Int, TaskState) -> Unit,
) {
    var openDialog by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
    ) { innerPadding ->
        Box(
            modifier = modifier.padding(innerPadding),
        ) {
            Column {
                BoardHeader(
                    projectName = projectName,
                    tasks = tasks,
                    onClick = { openDialog = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White),
                )

                HorizontalDivider(color = OutlineVariant)

                KanbanBoardContent(
                    tasks,
                    onTaskStateChange = { idx, targetStatus ->
                        onTaskStateChange(idx, targetStatus)
                        scope.launch {
                            snackbarHostState.currentSnackbarData?.dismiss()
                            snackbarHostState.showSnackbar(
                                message = "태스크가 이동되었습니다.",
                                withDismissAction = true,
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Primary),
                )
            }

            if (openDialog) {
                CreateTaskModalDialog(
                    authors = authors,
                    onDismissRequest = {
                        openDialog = false
                    },
                    onConfirmation = {
                        onTaskCreated(it)
                        openDialog = false
                        scope.launch {
                            snackbarHostState.currentSnackbarData?.dismiss()
                            snackbarHostState.showSnackbar(
                                message = "새로운 태스크가 추가되었습니다.",
                                withDismissAction = true,
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center),
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun BoardPreview() {
    Board(
        projectName = "Compose Desktop 칸반보드",
        tasks = Tasks(emptyList()),
        onTaskCreated = {},
        authors = listOf("다이노", "페임스"),
        modifier = Modifier.size(width = 1295.dp, height = 909.dp),
        onTaskStateChange = { _, _ -> },
    )
}
