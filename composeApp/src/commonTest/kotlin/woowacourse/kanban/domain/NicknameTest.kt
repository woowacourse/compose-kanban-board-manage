package woowacourse.kanban.domain

import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import org.junit.Test

class NicknameTest {

    @Test
    fun `유효한 닉네임으로 생성할 수 있다`() {
        // given : 유효한 문자열이 주어진다
        val input = "아오"

        // when : Nickname을 생성할 때
        val nickname = Nickname(input)

        // then : 정상적으로 생성되어야 한다
        assertEquals("아오", nickname.nickname)
    }

    @Test
    fun `빈 문자열로 생성하면 예외가 발생한다`() {
        // given : 빈 문자열이 주어진다
        // when & then : Nickname 생성 시 예외가 발생해야 한다
        assertFailsWith<IllegalArgumentException> { Nickname("") }
    }

    @Test
    fun `공백 문자열로 생성하면 예외가 발생한다`() {
        // given : 공백 문자열이 주어진다
        // when & then : Nickname 생성 시 예외가 발생해야 한다
        assertFailsWith<IllegalArgumentException> { Nickname("   ") }
    }
}
