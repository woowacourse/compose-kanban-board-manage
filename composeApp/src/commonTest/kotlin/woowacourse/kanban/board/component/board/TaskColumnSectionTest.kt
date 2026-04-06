package woowacourse.kanban.board.component.board

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.runComposeUiTest
import kotlin.test.Test
import kotlinx.collections.immutable.toImmutableList
import woowacourse.kanban.board.fixture.TaskCardDataFixture
import woowacourse.kanban.board.model.project.Project
import woowacourse.kanban.board.model.taskcard.Status

@OptIn(ExperimentalTestApi::class)
class TaskColumnSectionTest {
    @Test
    fun `todoTasks에 등록된 태스크가 3개면 3이 출력된다`() = runComposeUiTest {
        val data1 = TaskCardDataFixture.create(status = Status.TODO)
        val data2 = TaskCardDataFixture.create(status = Status.TODO)
        val data3 = TaskCardDataFixture.create(status = Status.TODO)

        val todoTasks = listOf(data1, data2, data3)
        val project = Project(
            title = "title",
            initialTasks = todoTasks.toImmutableList()
        )
        setContent {
            TaskColumnSection(
                project = project,
                onMoveSuccessSnackBar = {},
                onShowEditTaskModal = {},
                onMoveNoAssigneeSnackBar = {},
                onMoveFailedSnackBar = {},
            )
        }

        onNodeWithText("3").assertIsDisplayed()
    }

    @Test
    fun `progressTasks에 등록된 태스크가 5개면 5가 출력된다`() = runComposeUiTest {
        val data1 = TaskCardDataFixture.create(status = Status.PROGRESS)
        val data2 = TaskCardDataFixture.create(status = Status.PROGRESS)
        val data3 = TaskCardDataFixture.create(status = Status.PROGRESS)
        val data4 = TaskCardDataFixture.create(status = Status.PROGRESS)
        val data5 = TaskCardDataFixture.create(status = Status.PROGRESS)

        val progressTasks = listOf(data1, data2, data3, data4, data5)
        val project = Project(
            title = "title",
            initialTasks = progressTasks.toImmutableList()
        )
        setContent {
            TaskColumnSection(
                project = project,
                onMoveSuccessSnackBar = {},
                onShowEditTaskModal = {},
                onMoveNoAssigneeSnackBar = {},
                onMoveFailedSnackBar = {},
            )
        }

        onNodeWithText("5").assertIsDisplayed()
    }

    @Test
    fun `doneTasks에 등록된 태스크가 4개면 4가 출력된다`() = runComposeUiTest {
        val data1 = TaskCardDataFixture.create(status = Status.DONE)
        val data2 = TaskCardDataFixture.create(status = Status.DONE)
        val data3 = TaskCardDataFixture.create(status = Status.DONE)
        val data4 = TaskCardDataFixture.create(status = Status.DONE)

        val doneTasks = listOf(data1, data2, data3, data4)
        val project = Project(
            title = "title",
            initialTasks = doneTasks.toImmutableList()
        )
        setContent {
            TaskColumnSection(
                project = project,
                onMoveSuccessSnackBar = {},
                onShowEditTaskModal = {},
                onMoveNoAssigneeSnackBar = {},
                onMoveFailedSnackBar = {},
            )
        }

        onNodeWithText("4").assertIsDisplayed()
    }
}
