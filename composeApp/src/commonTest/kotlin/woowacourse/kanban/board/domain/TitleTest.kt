package woowacourse.kanban.board.domain

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.Test
import woowacourse.kanban.board.exception.TitleError
import woowacourse.kanban.board.exception.TitleException

class TitleTest {

    @Test
    fun `제목이 비어 있지 않은 경우 제목을 생성한다`() {
        // given
        val name = "title"

        // when
        val title = Title(name)

        // then
        assertThat(title.value).isEqualTo("title")
    }

    @Test
    fun `제목이 비어 있는 경우 예외가 발생한다`() {
        assertThatThrownBy { Title("") }
            .isInstanceOf(TitleException::class.java)
            .extracting("error")
            .isEqualTo(TitleError.BLANK)
    }

    @Test
    fun `제목이 공백인 경우 예외가 발생한다`() {
        assertThatThrownBy { Title("\t \n") }
            .isInstanceOf(TitleException::class.java)
            .extracting("error")
            .isEqualTo(TitleError.BLANK)
    }
}
