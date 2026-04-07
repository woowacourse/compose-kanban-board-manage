package woowacourse.kanban.domain

import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import org.junit.Test

class TitleTest {

    @Test
    fun `유효한 제목으로 생성할 수 있다`() {
        // given : 유효한 문자열이 주어진다
        val input = "제목"

        // when : Title을 생성할 때
        val title = Title(input)

        // then : 정상적으로 생성되어야 한다
        assertEquals("제목", title.content)
    }

    @Test
    fun `빈 문자열로 생성하면 예외가 발생한다`() {
        // given : 빈 문자열이 주어진다
        // when & then : Title 생성 시 예외가 발생해야 한다
        assertFailsWith<IllegalArgumentException> { Title("") }
    }

    @Test
    fun `공백 문자열로 생성하면 예외가 발생한다`() {
        // given : 공백 문자열이 주어진다
        // when & then : Title 생성 시 예외가 발생해야 한다
        assertFailsWith<IllegalArgumentException> { Title("   ") }
    }
}
