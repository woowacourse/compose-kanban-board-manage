package woowacourse.kanban.board.ui.taskcard

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.runComposeUiTest
import org.junit.Test
import woowacourse.kanban.board.domain.TaskState
import woowacourse.kanban.board.ui.taskcard.state.TaskInputState

@OptIn(ExperimentalTestApi::class)
class UpdateTaskCardModalTest {
    @Test
    fun `제목과 태그가 규칙에 맞게 입력되면 수정 버튼이 활성화 된다`() = runComposeUiTest {
        setContent {
            var taskInputState by remember {
                mutableStateOf(
                    TaskInputState(
                        title = "제목",
                        tags = "태그1, 태그2, 태그3",
                        selectedAuthor = authors.first(),
                    ),
                )
            }
            UpdateTaskCardModal(
                taskInputState = taskInputState,
                onStateChange = { taskInputState = it },
                onDismissRequest = {},
                onDeleteRequest = {},
                onUpdateRequest = {},
                authors = authors,
            )
        }

        onNodeWithText("수정").assertIsEnabled()
    }

    @Test
    fun `제목을 지우면 에러 문구가 노출된다`() = runComposeUiTest {
        setContent {
            var taskInputState by remember {
                mutableStateOf(
                    TaskInputState(
                        title = "제목",
                        tags = "태그1, 태그2, 태그3",
                        selectedAuthor = authors.first(),
                    ),
                )
            }
            UpdateTaskCardModal(
                taskInputState = taskInputState,
                onStateChange = { taskInputState = it },
                onDismissRequest = {},
                onDeleteRequest = {},
                onUpdateRequest = {},
                authors = authors,
            )
        }

        onNodeWithText("제목").performTextClearance()
        onNodeWithText("제목을 입력해주세요.").assertExists()
    }

    @Test
    fun `제목을 입력하지 않으면 수정 버튼이 활성화 되지 않는다`() = runComposeUiTest {
        setContent {
            var taskInputState by remember {
                mutableStateOf(
                    TaskInputState(
                        title = "제목",
                        tags = "태그1, 태그2, 태그3",
                        selectedAuthor = authors.first(),
                    ),
                )
            }
            UpdateTaskCardModal(
                taskInputState = taskInputState,
                onStateChange = { taskInputState = it },
                onDismissRequest = {},
                onDeleteRequest = {},
                onUpdateRequest = {},
                authors = authors,
            )
        }

        onNodeWithText("제목").performTextClearance()
        onNodeWithText("수정").assertIsNotEnabled()
    }

    @Test
    fun `태그가 5개를 초과하면 태그 규칙 위반 에러가 노출되고 수정 버튼이 활성화되지 않는다`() = runComposeUiTest {
        setContent {
            var taskInputState by remember { mutableStateOf(TaskInputState(title = "제목", selectedAuthor = authors.first())) }
            UpdateTaskCardModal(
                taskInputState = taskInputState,
                onStateChange = { taskInputState = it },
                onDismissRequest = {},
                onDeleteRequest = {},
                onUpdateRequest = {},
                authors = authors,
            )
        }
        onNodeWithText("수정").assertIsEnabled()

        onNodeWithText("태그는 5자 이내로 5개까지만 등록할 수 있습니다.").assertDoesNotExist()
        onNodeWithText("태그를 쉼표로 구분하여 입력하세요 (예: 버그, 긴급)").performTextInput("1,2,3,4,5,6")
        onNodeWithText("태그는 5자 이내로 5개까지만 등록할 수 있습니다.").assertExists()
        onNodeWithText("수정").assertIsNotEnabled()
    }

    @Test
    fun `태그가 5자를 초과하면 태그 규칙 위반 에러가 노출되고 수정 버튼이 활성화되지 않는다`() = runComposeUiTest {
        setContent {
            var taskInputState by remember { mutableStateOf(TaskInputState(title = "제목", selectedAuthor = authors.first())) }
            UpdateTaskCardModal(
                taskInputState = taskInputState,
                onStateChange = { taskInputState = it },
                onDismissRequest = {},
                onDeleteRequest = {},
                onUpdateRequest = {},
                authors = authors,
            )
        }
        onNodeWithText("수정").assertIsEnabled()

        onNodeWithText("태그는 5자 이내로 5개까지만 등록할 수 있습니다.").assertDoesNotExist()
        onNodeWithText("태그를 쉼표로 구분하여 입력하세요 (예: 버그, 긴급)").performTextInput("123456")
        onNodeWithText("태그는 5자 이내로 5개까지만 등록할 수 있습니다.").assertExists()
        onNodeWithText("수정").assertIsNotEnabled()
    }

    @Test
    fun `모달에 Task의 정보가 입력되어 있다`() = runComposeUiTest {
        setContent {
            var taskInputState by remember {
                mutableStateOf(
                    TaskInputState(
                        title = "제목",
                        content = "본문",
                        selectedAuthor = authors.first(),
                    ),
                )
            }
            UpdateTaskCardModal(
                taskInputState = taskInputState,
                onStateChange = { taskInputState = it },
                onDismissRequest = {},
                onDeleteRequest = {},
                onUpdateRequest = {},
                authors = authors,
            )
        }

        onNodeWithText("제목").isDisplayed()
        onNodeWithText("본문").isDisplayed()
    }

    @Test
    fun `삭제 불가능한 상태일 경우 삭제 버튼이 비활성화된다`() = runComposeUiTest {
        setContent {
            var taskInputState by remember {
                mutableStateOf(
                    TaskInputState(
                        title = "제목",
                        tags = "태그1, 태그2, 태그3",
                        selectedState = TaskState.REVIEW,
                        selectedAuthor = authors.first(),
                    ),
                )
            }
            UpdateTaskCardModal(
                taskInputState = taskInputState,
                onStateChange = { taskInputState = it },
                onDismissRequest = {},
                onDeleteRequest = {},
                onUpdateRequest = {},
                authors = authors,
            )
        }

        onNodeWithText("삭제").assertIsNotEnabled()
    }

    @Test
    fun `상태가 Todo일 때 담당자를 없음을 선택할수 있다`() = runComposeUiTest {
        setContent {
            var taskInputState by remember {
                mutableStateOf(
                    TaskInputState(
                        title = "제목",
                        tags = "태그1, 태그2, 태그3",
                        selectedState = TaskState.TO_DO,
                        selectedAuthor = authors.first(),
                    ),
                )
            }
            UpdateTaskCardModal(
                taskInputState = taskInputState,
                onStateChange = { taskInputState = it },
                onDismissRequest = {},
                onDeleteRequest = {},
                onUpdateRequest = {},
                authors = authors,
            )
        }

        onNodeWithText("없음").assertIsEnabled()
    }

    @Test
    fun `담당자가 필수인 상태일 경우 담당자를 없음 옵션이 없어진다`() = runComposeUiTest {
        setContent {
            var taskInputState by remember {
                mutableStateOf(
                    TaskInputState(
                        title = "제목",
                        tags = "태그1, 태그2, 태그3",
                        selectedState = TaskState.IN_PROGRESS,
                        selectedAuthor = authors.first(),
                    ),
                )
            }
            UpdateTaskCardModal(
                taskInputState = taskInputState,
                onStateChange = { taskInputState = it },
                onDismissRequest = {},
                onDeleteRequest = {},
                onUpdateRequest = {},
                authors = authors,
            )
        }

        onNodeWithText("없음").assertDoesNotExist()
    }

    companion object {
        val authors = listOf("다이노", "페임스")
    }
}
