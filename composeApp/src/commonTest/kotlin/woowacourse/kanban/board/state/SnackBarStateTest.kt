package woowacourse.kanban.board.state

import kotlin.test.Test
import org.assertj.core.api.Assertions.assertThat

class SnackBarStateTest {

    @Test
    fun `태스크 생성 성공 스낵바 메시지`() {
        val snackBarState = SnackBarState(
            isVisible = true,
            text = "새로운 태스크가 추가되었습니다."
        )

        assertThat(snackBarState.isVisible).isTrue()
        assertThat(snackBarState.text).isEqualTo("새로운 태스크가 추가되었습니다.")
    }

    @Test
    fun `태스크 수정 성공 스낵바 메시지`() {
        val snackBarState = SnackBarState(
            isVisible = true,
            text = "태스크가 수정되었습니다."
        )

        assertThat(snackBarState.isVisible).isTrue()
        assertThat(snackBarState.text).isEqualTo("태스크가 수정되었습니다.")
    }

    @Test
    fun `태스크 삭제 성공 스낵바 메시지`() {
        val snackBarState = SnackBarState(
            isVisible = true,
            text = "태스크가 삭제되었습니다."
        )

        assertThat(snackBarState.isVisible).isTrue()
        assertThat(snackBarState.text).isEqualTo("태스크가 삭제되었습니다.")
    }

    @Test
    fun `태스크 삭제 불가능 상태 스낵바 메시지`() {
        val snackBarState = SnackBarState(
            isVisible = true,
            text = "Review나 Done 상태에서는 태스크를 삭제할 수 없습니다."
        )

        assertThat(snackBarState.isVisible).isTrue()
        assertThat(snackBarState.text).contains("삭제할 수 없습니다")
    }

    @Test
    fun `상태 이동 성공 스낵바 메시지`() {
        val snackBarState = SnackBarState(
            isVisible = true,
            text = "태스크가 이동되었습니다."
        )

        assertThat(snackBarState.isVisible).isTrue()
        assertThat(snackBarState.text).isEqualTo("태스크가 이동되었습니다.")
    }

    @Test
    fun `상태 이동 불가능 스낵바 메시지`() {
        val snackBarState = SnackBarState(
            isVisible = true,
            text = "해당 상태로 옮길 수 없습니다."
        )

        assertThat(snackBarState.isVisible).isTrue()
        assertThat(snackBarState.text).isEqualTo("해당 상태로 옮길 수 없습니다.")
    }

    @Test
    fun `담당자 미지정 경고 스낵바 메시지`() {
        val snackBarState = SnackBarState(
            isVisible = true,
            text = "담당자를 지정해야 상태를 옮길 수 있습니다."
        )

        assertThat(snackBarState.isVisible).isTrue()
        assertThat(snackBarState.text).contains("담당자를")
    }
}
