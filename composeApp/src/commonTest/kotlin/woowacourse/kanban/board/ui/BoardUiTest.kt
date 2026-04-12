package woowacourse.kanban.board.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.runComposeUiTest
import java.util.UUID
import kotlin.test.Test
import woowacourse.kanban.board.BoardState
import woowacourse.kanban.board.components.KanbanBoard
import woowacourse.kanban.board.constant.MockData
import woowacourse.kanban.board.constant.SnackBarText
import woowacourse.kanban.domain.project.KanbanProject
import woowacourse.kanban.domain.task.Assignee
import woowacourse.kanban.domain.task.KanbanTask
import woowacourse.kanban.domain.task.Tags
import woowacourse.kanban.domain.task.TaskData
import woowacourse.kanban.domain.task.TaskStatus
import woowacourse.kanban.domain.task.Title

@OptIn(ExperimentalTestApi::class)
class BoardUiTest {

    val todoTaskProject = KanbanProject(
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
        ),
    )

    val inProgressTaskProject = KanbanProject(
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
                status = TaskStatus.IN_PROGRESS,
            ),
        ),
    )

    val unDeletableTaskProject = KanbanProject(
        title = "Compose1",
        tasks = mutableListOf(
            KanbanTask(
                data = TaskData(
                    title = Title("REVIEW"),
                    content = "내용",
                    tags = Tags(),
                    assignee = Assignee.DINO,
                    id = UUID.randomUUID(),
                ),
                status = TaskStatus.REVIEW,
            ),
            KanbanTask(
                data = TaskData(
                    title = Title("DONE"),
                    content = "내용",
                    tags = Tags(),
                    assignee = Assignee.DINO,
                    id = UUID.randomUUID(),
                ),
                status = TaskStatus.DONE,
            ),
        ),
    )

    @Test
    fun `새 태스크 생성 버튼을 누르면 생성 다이얼로그가 열려야 한다`() = runComposeUiTest {
        // given : 새 태스크 버튼이 주어진다
        setContent {
            KanbanBoard(
                project = KanbanProject(listOf()),
            )
        }

        // when : 새 태스크 버튼을 눌렀을 때
        onNodeWithText("새 태스크 생성").performClick()
        waitForIdle()
        // then : 생성 다이얼로그가 열려야 한다
        onNodeWithText("태스크 제목을 입력하세요").assertExists()
    }

    @Test
    fun `생성 다이얼로그에서 정상적인 값들을 입력 후 생성 버튼을 누르면 칸반 보드 리스트에 표시되어야 한다`() = runComposeUiTest {
        // given : 태스크 카드 정상 입력값이 주어진다
        val project = MockData.MOCK_PROJECTS.first()

        lateinit var state: BoardState
        lateinit var snackbarHostState: SnackbarHostState

        setContent {
            snackbarHostState = remember { SnackbarHostState() }
            state = remember { BoardState(project) }

            Scaffold(snackbarHost = { SnackbarHost(hostState = snackbarHostState) }) { paddingValues ->
                KanbanBoard(
                    project = project,
                    snackbarHostState = snackbarHostState,
                    modifier = Modifier.padding(paddingValues),
                )
            }
        }

        // when: 새로운 태스크가 생성됐을 때
        onNodeWithText("새 태스크 생성").performClick()
        waitForIdle()
        onNodeWithText("태스크 제목을 입력하세요").performTextInput("태스크제목")
        waitForIdle()
        onNodeWithText("생성").performClick()

        // then : 칸반 보드에서 입력된 카드가 보여야 한다
        onNodeWithText("태스크제목", useUnmergedTree = true).assertExists()
    }

    @Test
    fun `태스크 카드가 생성되고 스낵바가 출력되어야 한다`() = runComposeUiTest {
        // given : 태스크 카드 정상 입력값이 주어진다
        val project = MockData.MOCK_PROJECTS.first()

        lateinit var state: BoardState
        lateinit var snackbarHostState: SnackbarHostState

        setContent {
            snackbarHostState = remember { SnackbarHostState() }
            state = remember { BoardState(project) }

            Scaffold(snackbarHost = { SnackbarHost(hostState = snackbarHostState) }) { paddingValues ->
                KanbanBoard(
                    project = project,
                    snackbarHostState = snackbarHostState,
                    modifier = Modifier.padding(paddingValues),
                )
            }
        }

        // when : 생성 다이얼로그에서 정상적인 값을 입력 후 생성 버튼을 누를 때
        onNodeWithText("새 태스크 생성").performClick()
        waitForIdle()
        onNodeWithText("태스크 제목을 입력하세요").performTextInput("태스크제목")
        waitForIdle()
        onNodeWithText("생성").performClick()
        waitForIdle()

        // then : 칸반 보드 하단에 스낵바가 출력되어야 한다
        onNodeWithText(SnackBarText.CREATE_TASK, useUnmergedTree = true).assertExists()
    }

    @Test
    fun `태스크 카드를 클릭하면 TaskEditDialog가 출력되어야 한다`() = runComposeUiTest {
        // given : KanbanBoard가 생성된다
        lateinit var state: BoardState
        lateinit var snackbarHostState: SnackbarHostState

        setContent {
            snackbarHostState = remember { SnackbarHostState() }
            state = remember { BoardState(todoTaskProject) }

            Scaffold(snackbarHost = { SnackbarHost(hostState = snackbarHostState) }) { paddingValues ->
                KanbanBoard(
                    project = todoTaskProject,
                    snackbarHostState = snackbarHostState,
                    modifier = Modifier.padding(paddingValues),
                )
            }
        }

        // when : 태스크 카드를 클릭하면
        onNodeWithText("제목", useUnmergedTree = true).performClick()
        waitForIdle()

        // then : EditTaskDialog가 출력된다.
        onNodeWithText("기존 태스크 수정", useUnmergedTree = true).assertExists()
    }

    @Test
    fun `TaskEditDialog에는 '삭제', '수정' 버튼이 출력되어야 한다`() = runComposeUiTest {
        // given : KanbanBoard가 생성된다
        lateinit var state: BoardState
        lateinit var snackbarHostState: SnackbarHostState

        setContent {
            snackbarHostState = remember { SnackbarHostState() }
            state = remember { BoardState(todoTaskProject) }

            Scaffold(snackbarHost = { SnackbarHost(hostState = snackbarHostState) }) { paddingValues ->
                KanbanBoard(
                    project = todoTaskProject,
                    snackbarHostState = snackbarHostState,
                    modifier = Modifier.padding(paddingValues),
                )
            }
        }

        // when : 태스크 카드를 클릭하면
        onNodeWithText("제목", useUnmergedTree = true).performClick()
        waitForIdle()

        // then : EditTaskDialog가 출력된다.
        onNodeWithText("삭제", useUnmergedTree = true).assertExists()
        onNodeWithText("수정", useUnmergedTree = true).assertExists()
    }

    @Test
    fun `TODO 상태의 태스크를 클릭하면 다이얼로그에 담당자 없음이 표시된다`() = runComposeUiTest {
        // given : KanbanBoard가 생성된다
        lateinit var state: BoardState
        lateinit var snackbarHostState: SnackbarHostState

        setContent {
            snackbarHostState = remember { SnackbarHostState() }
            state = remember { BoardState(todoTaskProject) }

            Scaffold(snackbarHost = { SnackbarHost(hostState = snackbarHostState) }) { paddingValues ->
                KanbanBoard(
                    project = todoTaskProject,
                    snackbarHostState = snackbarHostState,
                    modifier = Modifier.padding(paddingValues),
                )
            }
        }

        // when : 태스크 카드를 클릭하면
        onNodeWithText("제목", useUnmergedTree = true).performClick()
        waitForIdle()

        // then : 담당자 없음 버튼이 표시된다
        onNodeWithText("없음", useUnmergedTree = true).assertExists()
    }

    @Test
    fun `TODO 상태가 아닌 태스크를 클릭하면 다이얼로그에 담당자 없음이 표시되지 않는다`() = runComposeUiTest {
        // given : KanbanBoard가 생성된다
        lateinit var state: BoardState
        lateinit var snackbarHostState: SnackbarHostState

        setContent {
            snackbarHostState = remember { SnackbarHostState() }
            state = remember { BoardState(inProgressTaskProject) }

            Scaffold(snackbarHost = { SnackbarHost(hostState = snackbarHostState) }) { paddingValues ->
                KanbanBoard(
                    project = inProgressTaskProject,
                    snackbarHostState = snackbarHostState,
                    modifier = Modifier.padding(paddingValues),
                )
            }
        }

        // when : 태스크 카드를 클릭하면
        onNodeWithText("제목", useUnmergedTree = true).performClick()
        waitForIdle()

        // then : 담당자 없음 버튼이 표시되지 않는다
        onNodeWithText("없음", useUnmergedTree = true).assertDoesNotExist()
    }

    @Test
    fun `TODO 상태인 태스크를 삭제하면 태스크가 삭제되며 스낵바가 출력된다`() = runComposeUiTest {
        // given : KanbanBoard가 생성된다
        lateinit var state: BoardState
        lateinit var snackbarHostState: SnackbarHostState

        setContent {
            snackbarHostState = remember { SnackbarHostState() }
            state = remember { BoardState(todoTaskProject) }

            Scaffold(snackbarHost = { SnackbarHost(hostState = snackbarHostState) }) { paddingValues ->
                KanbanBoard(
                    project = todoTaskProject,
                    snackbarHostState = snackbarHostState,
                    modifier = Modifier.padding(paddingValues),
                )
            }
        }

        // when : 태스크 카드를 클릭한 후 삭제 버튼을 누르면
        onNodeWithText("제목", useUnmergedTree = true).assertExists().performClick()
        waitForIdle()
        onNodeWithText("삭제", useUnmergedTree = true).assertExists().performClick()
        waitForIdle()

        // then : 스낵바가 출력되고 태스크가 제거된다
        onNodeWithText(SnackBarText.DELETE_TASK).assertExists()
        onNodeWithText("제목", useUnmergedTree = true).assertDoesNotExist()
    }

    @Test
    fun `IN_PROGRESS 상태인 태스크를 삭제하면 태스크가 삭제되며 스낵바가 출력된다`() = runComposeUiTest {
        // given : KanbanBoard가 생성된다
        lateinit var state: BoardState
        lateinit var snackbarHostState: SnackbarHostState

        setContent {
            snackbarHostState = remember { SnackbarHostState() }
            state = remember { BoardState(inProgressTaskProject) }

            Scaffold(snackbarHost = { SnackbarHost(hostState = snackbarHostState) }) { paddingValues ->
                KanbanBoard(
                    project = inProgressTaskProject,
                    snackbarHostState = snackbarHostState,
                    modifier = Modifier.padding(paddingValues),
                )
            }
        }

        // when : 태스크 카드를 클릭한 후 삭제 버튼을 누르면
        onNodeWithText("제목", useUnmergedTree = true).assertExists().performClick()
        waitForIdle()
        onNodeWithText("삭제", useUnmergedTree = true).assertExists().performClick()
        waitForIdle()

        // then : 스낵바가 출력되고 태스크가 제거된다
        onNodeWithText(SnackBarText.DELETE_TASK).assertExists()
        onNodeWithText("제목", useUnmergedTree = true).assertDoesNotExist()
    }

    @Test
    fun `REVIEW 상태인 태스크를 삭제하면 삭제 불가능 스낵바가 표시된다`() = runComposeUiTest {
        // given : KanbanBoard가 생성된다
        lateinit var state: BoardState
        lateinit var snackbarHostState: SnackbarHostState

        setContent {
            snackbarHostState = remember { SnackbarHostState() }
            state = remember { BoardState(unDeletableTaskProject) }

            Scaffold(snackbarHost = { SnackbarHost(hostState = snackbarHostState) }) { paddingValues ->
                KanbanBoard(
                    project = unDeletableTaskProject,
                    snackbarHostState = snackbarHostState,
                    modifier = Modifier.padding(paddingValues),
                )
            }
        }

        // when : 태스크 카드를 클릭한 후 삭제 버튼을 누르면
        onNodeWithText("REVIEW", useUnmergedTree = true).assertExists().performClick()
        waitForIdle()
        onNodeWithText("삭제", useUnmergedTree = true).assertExists().performClick()
        waitForIdle()

        // then : 스낵바가 출력되고 태스크가 제거된다
        onNodeWithText(SnackBarText.INVALID_DELETE_TASK).assertExists()
        onNodeWithText("REVIEW", useUnmergedTree = true).assertExists()
    }

    @Test
    fun `DONE 상태인 태스크를 삭제하면 삭제 불가능 스낵바가 표시된다`() = runComposeUiTest {
        // given : KanbanBoard가 생성된다
        lateinit var state: BoardState
        lateinit var snackbarHostState: SnackbarHostState

        setContent {
            snackbarHostState = remember { SnackbarHostState() }
            state = remember { BoardState(unDeletableTaskProject) }

            Scaffold(snackbarHost = { SnackbarHost(hostState = snackbarHostState) }) { paddingValues ->
                KanbanBoard(
                    project = unDeletableTaskProject,
                    snackbarHostState = snackbarHostState,
                    modifier = Modifier.padding(paddingValues),
                )
            }
        }

        // when : 태스크 카드를 클릭한 후 삭제 버튼을 누르면
        onNodeWithText("DONE", useUnmergedTree = true).assertExists().performClick()
        waitForIdle()
        onNodeWithText("삭제", useUnmergedTree = true).assertExists().performClick()
        waitForIdle()

        // then : 스낵바가 출력되고 태스크가 제거된다
        onNodeWithText(SnackBarText.INVALID_DELETE_TASK).assertExists()
        onNodeWithText("DONE", useUnmergedTree = true).assertExists()
    }

    @Test
    fun `담당자 없음 상태로 TODO 태스크를 생성할 수 있다`() = runComposeUiTest {
        // given: KanbanBoard에서 새 태스크 생성 버튼을 클릭한다
        val project = KanbanProject(
            title = "Compose1",
            tasks = emptyList<KanbanTask>(),
        )

        lateinit var state: BoardState
        lateinit var snackbarHostState: SnackbarHostState

        setContent {
            snackbarHostState = remember { SnackbarHostState() }
            state = remember { BoardState(project) }

            Scaffold(snackbarHost = { SnackbarHost(hostState = snackbarHostState) }) { paddingValues ->
                KanbanBoard(
                    project = project,
                    snackbarHostState = snackbarHostState,
                    modifier = Modifier.padding(paddingValues),
                )
            }
        }

        // when: 담당자 없음을 선택하고 Todo상태를 선택한 후 생성 버튼을 클릭하면
        onNodeWithText("새 태스크 생성").performClick()
        waitForIdle()
        onNodeWithText("태스크 제목을 입력하세요").performTextInput("태스크제목")
        waitForIdle()
        onNodeWithText("없음").performClick()
        waitForIdle()
        onNodeWithText("생성").performClick()
        waitForIdle()

        // then: 태스크가 생성되고 생성 스낵바가 출력된다
        onNodeWithText("태스크제목").assertExists()
        onNodeWithText(SnackBarText.CREATE_TASK).assertExists()
    }

    @Test
    fun `담당자 없음 상태로 IN_PROGRESS 태스크를 생성할 수 없다`() = runComposeUiTest {
        // given: KanbanBoard에서 새 태스크 생성 버튼을 클릭한다
        val project = MockData.MOCK_PROJECTS.first()

        lateinit var state: BoardState
        lateinit var snackbarHostState: SnackbarHostState

        setContent {
            snackbarHostState = remember { SnackbarHostState() }
            state = remember { BoardState(project) }

            Scaffold(snackbarHost = { SnackbarHost(hostState = snackbarHostState) }) { paddingValues ->
                KanbanBoard(
                    project = project,
                    snackbarHostState = snackbarHostState,
                    modifier = Modifier.padding(paddingValues),
                )
            }
        }

        // when: 담당자 없음을 선택하고 IN_PROGRESS상태를 선택한 후 생성 버튼을 클릭하면
        onNodeWithText("새 태스크 생성").performClick()
        waitForIdle()
        onNodeWithText("태스크 제목을 입력하세요").performTextInput("태스크제목")
        waitForIdle()
        onNode(hasText("In Progress") and hasClickAction()).performClick()
        waitForIdle()
        onNodeWithText("없음").performClick()
        waitForIdle()
        onNodeWithText("생성").performClick()
        waitForIdle()

        // then: 태스크가 생성되고 생성 스낵바가 출력된다
        onNodeWithText("태스크제목").assertDoesNotExist()
        onNodeWithText(SnackBarText.NONE_ASSIGNEE).assertExists()
    }

    @Test
    fun `담당자 없음 상태로 REVIEW 태스크를 생성할 수 없다`() = runComposeUiTest {
        // given: KanbanBoard에서 새 태스크 생성 버튼을 클릭한다
        val project = MockData.MOCK_PROJECTS.first()

        lateinit var state: BoardState
        lateinit var snackbarHostState: SnackbarHostState

        setContent {
            snackbarHostState = remember { SnackbarHostState() }
            state = remember { BoardState(project) }

            Scaffold(snackbarHost = { SnackbarHost(hostState = snackbarHostState) }) { paddingValues ->
                KanbanBoard(
                    project = project,
                    snackbarHostState = snackbarHostState,
                    modifier = Modifier.padding(paddingValues),
                )
            }
        }

        // when: 담당자 없음을 선택하고 REVIEW상태를 선택한 후 생성 버튼을 클릭하면
        onNodeWithText("새 태스크 생성").performClick()
        waitForIdle()
        onNodeWithText("태스크 제목을 입력하세요").performTextInput("태스크제목")
        waitForIdle()
        onNode(hasText("Review") and hasClickAction()).performClick()
        waitForIdle()
        onNodeWithText("없음").performClick()
        waitForIdle()
        onNodeWithText("생성").performClick()
        waitForIdle()

        // then: 태스크가 생성되고 생성 스낵바가 출력된다
        onNodeWithText("태스크제목").assertDoesNotExist()
        onNodeWithText(SnackBarText.NONE_ASSIGNEE).assertExists()
    }

    @Test
    fun `담당자 없음 상태로 DONE 태스크를 생성할 수 없다`() = runComposeUiTest {
        // given: KanbanBoard에서 새 태스크 생성 버튼을 클릭한다
        val project = MockData.MOCK_PROJECTS.first()
        lateinit var state: BoardState
        lateinit var snackbarHostState: SnackbarHostState

        setContent {
            snackbarHostState = remember { SnackbarHostState() }

            Scaffold(snackbarHost = { SnackbarHost(hostState = snackbarHostState) }) { paddingValues ->
                KanbanBoard(
                    project = project,
                    snackbarHostState = snackbarHostState,
                    modifier = Modifier.padding(paddingValues),
                )
            }
        }

        // when: 담당자 없음을 선택하고 DONE상태를 선택한 후 생성 버튼을 클릭하면
        onNodeWithText("새 태스크 생성").performClick()
        waitForIdle()
        onNodeWithText("태스크 제목을 입력하세요").performTextInput("태스크제목")
        waitForIdle()
        onNode(hasText("Done") and hasClickAction()).performClick()
        waitForIdle()
        onNodeWithText("없음").performClick()
        waitForIdle()
        onNodeWithText("생성").performClick()
        waitForIdle()

        // then: 태스크가 생성되고 생성 스낵바가 출력된다
        onNodeWithText("태스크제목").assertDoesNotExist()
        onNodeWithText(SnackBarText.NONE_ASSIGNEE).assertExists()
    }

    @Test
    fun `담당자가 지정되지 않은 상태로 Todo 태스크의 상태를 변경할 수 없다`() = runComposeUiTest {
        val project = KanbanProject(
            title = "Compose1",
            tasks = listOf(
                KanbanTask(
                    data = TaskData(
                        title = Title("제목"),
                        content = "내용",
                        tags = Tags(),
                        assignee = Assignee.NONE,
                        id = UUID.randomUUID(),
                    ),
                    status = TaskStatus.TO_DO,
                ),
            ),
        )

        lateinit var state: BoardState
        lateinit var snackbarHostState: SnackbarHostState

        setContent {
            snackbarHostState = remember { SnackbarHostState() }
            state = remember { BoardState(project) }

            Scaffold(snackbarHost = { SnackbarHost(hostState = snackbarHostState) }) { paddingValues ->
                KanbanBoard(
                    project = project,
                    snackbarHostState = snackbarHostState,
                    modifier = Modifier.padding(paddingValues),
                )
            }
        }

        onNodeWithText("제목", useUnmergedTree = true).assertExists().performClick()
        waitForIdle()
        onNode(hasText("In Progress") and hasClickAction()).performClick()
        waitForIdle()
        onNode(hasText("수정") and hasClickAction()).performClick()
        waitForIdle()

        onNodeWithText(SnackBarText.NONE_ASSIGNEE_MOVE, useUnmergedTree = true).assertExists()
    }
}
