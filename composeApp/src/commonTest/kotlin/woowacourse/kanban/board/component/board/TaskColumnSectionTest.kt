package woowacourse.kanban.board.component.board

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.runComposeUiTest
import kotlin.test.Test
import kotlinx.collections.immutable.toImmutableList
import woowacourse.kanban.board.model.project.Project
import woowacourse.kanban.board.model.taskcard.Description
import woowacourse.kanban.board.model.taskcard.Profile
import woowacourse.kanban.board.model.taskcard.Status
import woowacourse.kanban.board.model.taskcard.Tag
import woowacourse.kanban.board.model.taskcard.Tags
import woowacourse.kanban.board.model.taskcard.TaskCard
import woowacourse.kanban.board.model.taskcard.Title

@OptIn(ExperimentalTestApi::class)
class TaskColumnSectionTest {
    private var taskSequence = 0

    @Test
    fun `todoTasks에 등록된 태스크가 3개면 3이 출력된다`() = runComposeUiTest {
        val data1 = createData(Status.TODO)
        val data2 = createData(Status.TODO)
        val data3 = createData(Status.TODO)
        val todoTasks = listOf(data1, data2, data3)
        val project = Project(
            id = "project-todo",
            title = "title",
            tasks = todoTasks.toImmutableList()
        )
        setContent {
            TaskColumnSection(
                project = project,
                onMoveSnackBar = {},
                onInvalidStatusMove = {},
                onRequireProfileMove = {},
                onUpdateTaskStatus = { _, _ -> },
                onTaskClick = {},
            )
        }

        onNodeWithText("3").assertIsDisplayed()
    }

    @Test
    fun `progressTasks에 등록된 태스크가 5개면 5가 출력된다`() = runComposeUiTest {
        val data1 = createData(Status.PROGRESS)
        val data2 = createData(Status.PROGRESS)
        val data3 = createData(Status.PROGRESS)
        val data4 = createData(Status.PROGRESS)
        val data5 = createData(Status.PROGRESS)

        val progressTasks = listOf(data1, data2, data3, data4, data5)
        val project = Project(
            id = "project-progress",
            title = "title",
            tasks = progressTasks.toImmutableList()
        )
        setContent {
            TaskColumnSection(
                project = project,
                onMoveSnackBar = {},
                onInvalidStatusMove = {},
                onRequireProfileMove = {},
                onUpdateTaskStatus = { _, _ -> },
                onTaskClick = {},
            )
        }

        onNodeWithText("5").assertIsDisplayed()
    }

    @Test
    fun `doneTasks에 등록된 태스크가 4개면 4가 출력된다`() = runComposeUiTest {
        val data1 = createData(Status.DONE)
        val data2 = createData(Status.DONE)
        val data3 = createData(Status.DONE)
        val data4 = createData(Status.DONE)

        val doneTasks = listOf(data1, data2, data3, data4)
        val project = Project(
            id = "project-done",
            title = "title",
            tasks = doneTasks.toImmutableList()
        )
        setContent {
            TaskColumnSection(
                project = project,
                onMoveSnackBar = {},
                onInvalidStatusMove = {},
                onRequireProfileMove = {},
                onUpdateTaskStatus = { _, _ -> },
                onTaskClick = {},
            )
        }

        onNodeWithText("4").assertIsDisplayed()
    }

    @Test
    fun `reviewTasks에 등록된 태스크가 2개면 2가 출력된다`() = runComposeUiTest {
        val data1 = createData(Status.REVIEW)
        val data2 = createData(Status.REVIEW)

        val reviewTasks = listOf(data1, data2)
        val project = Project(
            id = "project-review",
            title = "title",
            tasks = reviewTasks.toImmutableList()
        )
        setContent {
            TaskColumnSection(
                project = project,
                onMoveSnackBar = {},
                onInvalidStatusMove = {},
                onRequireProfileMove = {},
                onUpdateTaskStatus = { _, _ -> },
                onTaskClick = {},
            )
        }

        onNodeWithText("2").assertIsDisplayed()
    }

    private fun createData(status: Status): TaskCard {
        taskSequence += 1
        return TaskCard(
            id = "task-$status-$taskSequence",
            title = Title(value = "업무1"),
            description = Description(""),
            tags = Tags(value = listOf(Tag("컴포넌트")).toImmutableList()),
            status = status,
            profile = Profile("다이노")
        )
    }
}
