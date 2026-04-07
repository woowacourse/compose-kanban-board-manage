package woowacourse.kanban.board.task.ui.modal

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.runComposeUiTest
import androidx.compose.ui.unit.Density
import kotlin.test.Test
import woowacourse.kanban.board.task.domain.TaskMockData

@OptIn(ExperimentalTestApi::class)
class ModalCreateFormTest {

    @Test
    fun `제목을 입력하지 않고 생성 버튼을 누르면 에러 메시지가 표시된다`() = runComposeUiTest {
        // given
        setContent {
            CompositionLocalProvider(LocalDensity provides Density(density = 0.1f)) {
                ModalCreateForm(
                    state = ModalCreateFormState(TaskMockData.assignees),
                    onDismissRequest = {},
                    onCreate = { _ -> },
                )
            }
        }

        // when
        onNodeWithText("생성").performClick()

        // then
        onNodeWithText("제목을 입력해 주세요.").assertExists()
    }

    @Test
    fun `제목을 입력하면 에러 메시지가 표시되지 않는다`() = runComposeUiTest {
        // given
        setContent {
            CompositionLocalProvider(LocalDensity provides Density(density = 0.1f)) {
                ModalCreateForm(
                    state = ModalCreateFormState(TaskMockData.assignees),
                    onDismissRequest = {},
                    onCreate = { _ -> },
                )
            }
        }

        // when
        onNodeWithText("태스크 제목을 입력하세요").performTextInput("새로운 제목")
        onNodeWithText("생성").performClick()

        // then
        onNodeWithText("제목을 입력해주세요.").assertDoesNotExist()
    }

    @Test
    fun `올바르지 않은 태그 형식을 입력하면 에러 메시지가 표시된다`() = runComposeUiTest {
        // given
        setContent {
            CompositionLocalProvider(LocalDensity provides Density(density = 0.1f)) {
                ModalCreateForm(
                    state = ModalCreateFormState(TaskMockData.assignees),
                    onDismissRequest = {},
                    onCreate = { _ -> },
                )
            }
        }

        // when
        onNodeWithText("태그를 쉼표로 구분하여 입력하세요 (예: 버그, 긴급)").performTextInput("태그1,,태그2")
        onNodeWithText("생성").performClick()

        // then
        onNodeWithText("태그 형식이 올바르지 않습니다.").assertExists()
    }

    @Test
    fun `5자를 초과하는 태그를 입력하면 에러 메시지가 표시된다`() = runComposeUiTest {
        // given
        setContent {
            CompositionLocalProvider(LocalDensity provides Density(density = 0.1f)) {
                ModalCreateForm(
                    state = ModalCreateFormState(TaskMockData.assignees),
                    onDismissRequest = {},
                    onCreate = { _ -> },
                )
            }
        }

        // when
        onNodeWithText("태그를 쉼표로 구분하여 입력하세요 (예: 버그, 긴급)").performTextInput("여섯글자태그")
        onNodeWithText("생성").performClick()

        // then
        onNodeWithText("태그는 5자 이내로 5개까지만 등록할 수 있습니다.").assertExists()
    }
}
