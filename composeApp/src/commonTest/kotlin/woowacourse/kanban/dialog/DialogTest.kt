package woowacourse.kanban.dialog

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.assertIsNotSelected
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.runComposeUiTest
import kotlin.test.Test
import woowacourse.kanban.board.ui.dialog.ui.TaskManageDialog
import woowacourse.kanban.board.ui.dialog.ui.radioSelector.AssigneeButton
import woowacourse.kanban.board.ui.dialog.ui.radioSelector.RadioGridSelector
import woowacourse.kanban.board.ui.dialog.ui.radioSelector.StatusButton
import woowacourse.kanban.domain.Assignee
import woowacourse.kanban.domain.Nickname
import woowacourse.kanban.domain.TaskStatus

@OptIn(ExperimentalTestApi::class)
class DialogTest {
    var showDialog = mutableStateOf(false)
    private val TaskStatus.displayName: String
        get() = when (this) {
            TaskStatus.TO_DO -> "To Do"
            TaskStatus.IN_PROGRESS -> "In Progress"
            TaskStatus.DONE -> "Done"
            TaskStatus.REVIEW -> "REVIEW"
        }

    @Test
    fun `상태 버튼을 클릭 했을 때 다른 상태 버튼은 선택되지 않아야 한다`() = runComposeUiTest {
        var selectedStatusIndex = mutableIntStateOf(0)

        // given

        setContent {
            RadioGridSelector(
                header = "상태 *",
                TaskStatus.entries.size,
            ) { index ->
                StatusButton(
                    status = TaskStatus.entries[index],
                    isSelected = selectedStatusIndex.value == index,
                    onClick = { selectedStatusIndex.value = index },
                )
            }
        }

        // when
        onNodeWithText(TaskStatus.IN_PROGRESS.displayName).performClick()
        waitForIdle()
        // then
        onNodeWithText(TaskStatus.IN_PROGRESS.displayName).assertIsSelected()
        onNodeWithText(TaskStatus.TO_DO.displayName).assertIsNotSelected()
        onNodeWithText(TaskStatus.DONE.displayName).assertIsNotSelected()
    }

    @Test
    fun `담당자 버튼을 클릭 했을 때 다른 상태 버튼은 선택되지 않아야 한다`() = runComposeUiTest {
        var selectedCoachIndex = mutableIntStateOf(0)
        // given
        val assignees = listOf(
            Assignee(
                Nickname(
                    "다이노",
                ),
            ),
            Assignee(
                Nickname(
                    "페임스",
                ),
            ),
        )

        setContent {
            RadioGridSelector(
                header = "담당자",
                listSize = assignees.size,
            ) { index ->
                AssigneeButton(
                    assignee = assignees[index],
                    isSelected = selectedCoachIndex.value == index,
                    onClick = { selectedCoachIndex.value = index },
                )
            }
        }

        // when
        onNodeWithText("페임스").performClick()
        waitForIdle()
        // then
        onNodeWithText("페임스").assertIsSelected()
        onNodeWithText("다이노").assertIsNotSelected()
    }

    @Test
    fun `제목 검증 혹은 태그 검증에 실패시 생성 버튼 비활성화 되어야 한다`() = runComposeUiTest {
        // given
        setContent {

            TaskManageDialog(
                onDismiss = { showDialog.value = false },
                onCreateTask = {},
                modifier = Modifier,
            )
        }
        // when
        onNodeWithText("태스크 제목을 입력하세요").performTextInput("제목")
        waitForIdle()
        onNodeWithText("태그를 쉼표로 구분하여 입력하세요 (예: 버그, 긴급)").performTextInput("태그6글자이상, 태그")
        waitForIdle()
        onNodeWithText("생성").performClick()
        waitForIdle()
        // then
        onNodeWithText("생성").assertIsNotEnabled()
    }

    @Test
    fun `텍스트 필드에 입력한 내용이 입력한대로 출력되어야 한다`() = runComposeUiTest {
        // given
        setContent {

            TaskManageDialog(
                onDismiss = { showDialog.value = false },
                onCreateTask = {},
                modifier = Modifier,
            )
        }

        // when
        onNodeWithText("태그를 쉼표로 구분하여 입력하세요 (예: 버그, 긴급)").performTextInput("태그입력")
        onNodeWithText("태스크 제목을 입력하세요").performTextInput("제목입력")
        waitForIdle()
        // then
        onNodeWithText("태그입력").assertExists()
        onNodeWithText("제목입력").assertExists()
    }

    @Test
    fun `제목 검증 혹은 태그 검증에 실패시 생성 버튼을 누르면 제목과 태그에서 에러 표시가 출력되야 한다`() = runComposeUiTest {
        // given
        setContent {
            TaskManageDialog(
                onDismiss = { showDialog.value = false },
                onCreateTask = {},
                modifier = Modifier,
            )
        }

        // when
        onNodeWithText("태그를 쉼표로 구분하여 입력하세요 (예: 버그, 긴급)").performTextInput("태그6글자이상")
        waitForIdle()
        onNodeWithText("태스크 제목을 입력하세요").performTextInput("제목")
        waitForIdle()
        onNodeWithText("생성").performClick()
        waitForIdle()
        // then
        onNodeWithText("이건,,,,올바르지 않은 형식입니다,,,,,,,,,").assertExists()
    }
}
