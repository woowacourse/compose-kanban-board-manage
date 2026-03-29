package woowacourse.kanban.board.domain

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.Test
import woowacourse.kanban.board.exception.TagError
import woowacourse.kanban.board.exception.TagException

class TagTest {

    @Test
    fun `태그 내용이 비어 있지 않고 5자 이하인 경우 태그가 생성된다`() {
        // given
        val name = "tag"

        // when
        val tag = Tag(name)

        // then
        assertThat(tag.value).isEqualTo("tag")
    }

    @Test
    fun `태그 내용이 비어 있는 경우 예외가 반환된다`() {
        assertThatThrownBy { Tag("") }
            .isInstanceOf(TagException::class.java)
            .extracting("error")
            .isEqualTo(TagError.INVALID_FORMAT)
    }

    @Test
    fun `태그 내용이 공백인 경우 예외가 반환된다`() {
        assertThatThrownBy { Tag("\t \n") }
            .isInstanceOf(TagException::class.java)
            .extracting("error")
            .isEqualTo(TagError.INVALID_FORMAT)
    }

    @Test
    fun `태그의 길이가 5자를 초과할 경우 예외가 반환된다`() {
        assertThatThrownBy { Tag("123456") }
            .isInstanceOf(TagException::class.java)
            .extracting("error")
            .isEqualTo(TagError.TOO_LONG)
    }
}
