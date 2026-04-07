package woowacourse.kanban.board.model

import kotlin.test.Test
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy

class TagTest {
    @Test
    fun `태그는 최대 5개까지 생성 가능하다`() {
        assertThatThrownBy {
            BoardData(
                title = "제목",
                tags = listOf("일", "이", "삼", "사", "오", "육").map { Tag(it) },
                status = Status.TODO,
                nickname = Nickname.DINO,
            )
        }.isInstanceOf(IllegalArgumentException::class.java)
    }

    @Test
    fun `태그는 5글자까지만 가능하다`() {
        val tag = Tag("일이삼사오")

        assertThat(tag.text).isEqualTo("일이삼사오")
    }

    @Test
    fun `태그는 5글자를 초과하면 오류가 발생한다`() {

        assertThatThrownBy {
            Tag("일이삼사오육")
        }.isInstanceOf(IllegalArgumentException::class.java)
    }

    @Test
    fun `태그에 공백이 들어가있으면 오류가 발생한다`() {

        assertThatThrownBy {
            Tag("일이 삼사")
        }.isInstanceOf(IllegalArgumentException::class.java)
    }
}
