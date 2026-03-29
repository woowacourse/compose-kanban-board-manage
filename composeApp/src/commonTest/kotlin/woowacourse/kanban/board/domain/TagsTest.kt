package woowacourse.kanban.board.domain

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.Test
import woowacourse.kanban.board.exception.TagError
import woowacourse.kanban.board.exception.TagException

class TagsTest {

    @Test
    fun `태그가 5개를 초과하지 않는 경우 Tags를 생성한다`() {
        // given
        val tagsInput = listOf("tag1", "tag2", "tag3", "tag4", "tag5")

        // when
        val tags = Tags(tagsInput)

        // then
        assertThat(tags.tags).isEqualTo(listOf("tag1", "tag2", "tag3", "tag4", "tag5"))
    }

    @Test
    fun `태그가 5개 초과인 경우 예외가 발생한다`() {
        assertThatThrownBy { Tags(listOf("tag1", "tag2", "tag3", "tag4", "tag5", "tag6")) }
            .isInstanceOf(TagException::class.java)
            .extracting("error")
            .isEqualTo(TagError.TOO_MANY)
    }
}
