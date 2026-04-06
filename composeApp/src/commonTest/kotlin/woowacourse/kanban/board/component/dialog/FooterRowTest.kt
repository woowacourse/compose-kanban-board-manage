package woowacourse.kanban.board.component.dialog

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.runComposeUiTest
import kotlin.test.Test
import org.assertj.core.api.Assertions.assertThat
import woowacourse.kanban.board.component.dialog.action.FooterRow
import woowacourse.kanban.board.state.DialogMode

@OptIn(ExperimentalTestApi::class)
class FooterRowTest {

    @Test
    fun `제목 검증 혹은 태그 검증에 실패시 생성 버튼 비활성화 되어야 한다`() = runComposeUiTest {
        val isCreateError = true

        setContent {
            FooterRow(
                onCancel = {},
                onCreate = {},
                onDelete = {},
                isCreateError = isCreateError,
                mode = DialogMode.CREATE,

                )
        }

        onNodeWithText("생성").assertIsNotEnabled()
    }

    @Test
    fun `제목 검증과 태그 검증에 성공하면 생성 버튼이 활성화 되어야 한다`() = runComposeUiTest {
        val isCreateError = false
        var count = 0

        setContent {
            FooterRow(
                onCancel = {},
                onCreate = { count += 1 },
                onDelete = {},
                isCreateError = isCreateError,
                mode = DialogMode.CREATE,

                )
        }

        onNodeWithText("생성").performClick()
        assertThat(count).isEqualTo(1)
        onNodeWithText("생성").assertIsEnabled()
    }
}
