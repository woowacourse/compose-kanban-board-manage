package woowacourse.kanban.card.model

import androidx.compose.ui.test.ExperimentalTestApi
import kotlin.test.assertEquals
import org.junit.Assert
import org.junit.Test
import woowacourse.kanban.domain.Nickname

@OptIn(ExperimentalTestApi::class)
class NicknameTest {

    @Test
    fun `닉네임이 비어있거나 공백이 아닌 경우 오류가 발생하지 않아야 한다`() {
        // given
        val nickname =
            "아오"

        // when
        val data =
            Nickname(nickname)

        // then
        assertEquals(
            "아오",
            data.nickname,
        )
    }

    @Test
    fun `닉네임이 비어져있는 경우 오류가 발생해야 한다`() {
        // given
        val nickname =
            ""
        // when
        // then
        Assert.assertThrows(IllegalArgumentException::class.java) {
            Nickname(nickname)
        }
    }

    @Test
    fun `닉네임이 공백으로만 이루어진 경우 오류가 발생해야 한다`() {
        // given
        val nickname =
            " "
        // when
        // then
        Assert.assertThrows(IllegalArgumentException::class.java) {
            Nickname(nickname)
        }
    }
}
