package woowacourse.kanban.board.model

import kotlin.test.Test
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy

class BoardDataTest {
    @Test
    fun `태스크는 제목, 설명, 태그, 상태, 담당자를 가진다`() {
        val taskCreateData = BoardData(
            title = "제목",
            description = "내용 어쩌구 저쩌구",
            tags = listOf(Tag("버그"), Tag("다시하기"), Tag("1시간")),
            status = Status.TODO,
            nickname = Nickname.DINO,
        )

        assertThat(taskCreateData.title).isEqualTo("제목")
        assertThat(taskCreateData.description).isEqualTo("내용 어쩌구 저쩌구")
        assertThat(taskCreateData.tags.map { it.text }).isEqualTo(listOf("버그", "다시하기", "1시간"))
        assertThat(taskCreateData.status.state).isEqualTo("To Do")
        assertThat(taskCreateData.nickname).isEqualTo(Nickname.DINO)
    }

    @Test
    fun `제목이 빈 문자열이면 태스크 생성이 불가능하다`() {
        assertThatThrownBy {
            BoardData(
                title = "",
                description = "내용 어쩌구 저쩌구",
                tags = listOf(Tag("버그"), Tag("다시하기"), Tag("1시간")),
                status = Status.TODO,
                nickname = Nickname.DINO,
            )
        }.isInstanceOf(IllegalArgumentException::class.java)

        assertThatThrownBy {
            BoardData(
                title = "",
                status = Status.TODO,
                nickname = Nickname.DINO,
            )
        }.isInstanceOf(IllegalArgumentException::class.java)
    }
}
