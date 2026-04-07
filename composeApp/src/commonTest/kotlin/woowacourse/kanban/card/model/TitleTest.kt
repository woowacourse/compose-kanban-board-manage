package woowacourse.kanban.card.model

import androidx.compose.ui.test.ExperimentalTestApi
import kotlin.test.assertEquals
import org.junit.Assert
import org.junit.Test
import woowacourse.kanban.domain.Title

@OptIn(ExperimentalTestApi::class)
class TitleTest {

    @Test
    fun `제목이 비어있지 않고 공백이지도 않은 경우 오류가 발생하지 않아야 한다`() {
        // given
        val title =
            "제목"
        // when
        val data =
            Title(title)
        // then
        assertEquals(
            "제목",
            data.content,
        )
    }

    @Test
    fun `제목이 비어 있는 경우 오류가 발생해야 한다`() {
        // given
        val title =
            ""
        // when
        // then
        Assert.assertThrows(IllegalArgumentException::class.java) {
            Title(title)
        }
    }

    @Test
    fun `제목이 공백으로만 이루어져 있는 경우 오류가 발생해야 한다`() {
        // given
        val title =
            " "
        // when
        // then
        Assert.assertThrows(IllegalArgumentException::class.java) {
            Title(title)
        }
    }
}
