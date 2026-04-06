package woowacourse.kanban.board.component.kanbanBoard

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.runComposeUiTest
import kotlin.test.Test
import woowacourse.kanban.board.domain.Task
import woowacourse.kanban.board.domain.Status
import woowacourse.kanban.board.domain.Tag
import woowacourse.kanban.board.theme.StatusColor

private const val DEFAULT_TITLE = "테스트 태스크"
private const val DEFAULT_NAME = "테스터"

@OptIn(ExperimentalTestApi::class)
class ReviewStatusBoxTest {

    private fun createTaskWithStatus(status: Status): Task {
        return Task(
            title = DEFAULT_TITLE,
            status = status,
            nickname = DEFAULT_NAME,
        )
    }

    @Test
    fun `모든 상태 박스가 칸반 보드에 표시된다`() = runComposeUiTest {
        val taskList = listOf(
            createTaskWithStatus(Status.TODO),
            createTaskWithStatus(Status.IN_PROGRESS),
            createTaskWithStatus(Status.REVIEW),
            createTaskWithStatus(Status.DONE),
        )

        setContent {
            Status.values().forEach { status ->
                StatusCardManageBox(
                    boardList = taskList,
                    status = status,
                    statusColor = StatusColor.getStatusColor(status),
                )
            }
        }

        onAllNodesWithText("1", useUnmergedTree = true)
            .assertCountEquals(4)
    }

    @Test
    fun `Review 상태에 태스크가 없으면 0이 표시된다`() = runComposeUiTest {
        val taskList = listOf(
            createTaskWithStatus(Status.TODO),
            createTaskWithStatus(Status.IN_PROGRESS),
            createTaskWithStatus(Status.DONE),
        )

        setContent {
            StatusCardManageBox(
                boardList = taskList,
                status = Status.REVIEW,
                statusColor = StatusColor.getStatusColor(Status.REVIEW),
            )
        }

        onNodeWithText("0", useUnmergedTree = true).assertExists()
    }

        @Test
    fun `Review 상태 박스가 렌더링된다`() = runComposeUiTest {
        val taskList = listOf(
            createTaskWithStatus(Status.TODO),
            createTaskWithStatus(Status.IN_PROGRESS),
            createTaskWithStatus(Status.REVIEW),
            createTaskWithStatus(Status.DONE),
        )

        setContent {
            StatusCardManageBox(
                boardList = taskList,
                status = Status.REVIEW,
                statusColor = StatusColor.getStatusColor(Status.REVIEW),
            )
        }

        onNodeWithText("1", useUnmergedTree = true).assertExists()
    }
}
