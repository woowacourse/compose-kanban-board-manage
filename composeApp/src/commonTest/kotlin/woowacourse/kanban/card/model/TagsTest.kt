package woowacourse.kanban.card.model

import androidx.compose.ui.test.ExperimentalTestApi
import kotlin.test.Test
import kotlin.test.assertEquals
import org.junit.Assert
import woowacourse.kanban.domain.Tags

@OptIn(ExperimentalTestApi::class)
class TagsTest {

    @Test
    fun `태그의 갯수가 5개 이하이며 태그의 내용도 5자 이하인 경우 오류가 발생하지 않아야 한다`() {
        // given
        val tags =
            listOf(
                "일",
                "이",
                "삼",
                "사",
                "오",
            )

        // when
        val data =
            Tags(tags)

        // then
        assertEquals(
            listOf<String>(
                "일",
                "이",
                "삼",
                "사",
                "오",
            ),
            data.tags,
        )
    }

    @Test
    fun `태그의 개수가 5개 초과하면 오류가 발생한다`() {
        // given
        val tags =
            listOf(
                "일",
                "이",
                "삼",
                "사",
                "오",
                "육",
            )
        // when
        // then
        Assert.assertThrows(IllegalArgumentException::class.java) {
            Tags(tags)
        }
    }

    @Test
    fun `태그의 내용이 5자 초과하면 오류가 발생한다`() {
        // given
        val tags =
            listOf(
                "일이삼사오육",
                "일이삼사오",
                "일이",
            )
        // when
        // then
        Assert.assertThrows(IllegalArgumentException::class.java) {
            Tags(tags)
        }
    }
}
