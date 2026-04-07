package woowacourse.kanban.domain

import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import org.junit.Test

class TagsTest {

    @Test
    fun `태그 5개 이하로 생성할 수 있다`() {
        // given : 5개의 태그가 주어진다
        val input = listOf("a", "b", "c", "d", "e")

        // when : Tags를 생성할 때
        val tags = Tags(input)

        // then : 정상적으로 생성되어야 한다
        assertEquals(5, tags.tags.size)
    }

    @Test
    fun `태그가 5개를 초과하면 예외가 발생한다`() {
        // given : 6개의 태그가 주어진다
        val input = listOf("a", "b", "c", "d", "e", "f")

        // when & then : Tags 생성 시 예외가 발생해야 한다
        assertFailsWith<IllegalArgumentException> { Tags(input) }
    }

    @Test
    fun `태그 내용이 5자 이하면 생성할 수 있다`() {
        // given : 5자 이하의 태그가 주어진다
        val input = listOf("12345")

        // when : Tags를 생성할 때
        val tags = Tags(input)

        // then : 정상적으로 생성되어야 한다
        assertEquals(1, tags.tags.size)
    }

    @Test
    fun `태그 내용이 5자를 초과하면 예외가 발생한다`() {
        // given : 6자 태그가 주어진다
        val input = listOf("123456")

        // when & then : Tags 생성 시 예외가 발생해야 한다
        assertFailsWith<IllegalArgumentException> { Tags(input) }
    }

    @Test
    fun `빈 리스트로 생성할 수 있다`() {
        // given : 빈 리스트가 주어진다
        val input = emptyList<String>()

        // when : Tags를 생성할 때
        val tags = Tags(input)

        // then : 정상적으로 생성되어야 한다
        assertEquals(0, tags.tags.size)
    }

    @Test
    fun `공백 태그가 있으면 isAllNotBlank는 false`() {
        // given : 공백 태그가 포함된 Tags가 주어진다
        val tags = Tags(listOf("tag", " "))

        // when : isAllNotBlank를 확인할 때
        // then : false여야 한다
        assertEquals(false, tags.isAllNotBlank)
    }

    @Test
    fun `모든 태그가 비어있지 않으면 isAllNotBlank는 true`() {
        // given : 유효한 태그로 구성된 Tags가 주어진다
        val tags = Tags(listOf("tag1", "tag2"))

        // when : isAllNotBlank를 확인할 때
        // then : true여야 한다
        assertEquals(true, tags.isAllNotBlank)
    }
}
