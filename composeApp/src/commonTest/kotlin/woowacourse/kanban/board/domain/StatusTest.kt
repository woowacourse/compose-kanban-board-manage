@file:Suppress("NonAsciiCharacters")

package woowacourse.kanban.board.domain

import org.assertj.core.api.Assertions.assertThat
import kotlin.test.Test

class StatusTest {
    @Test
    fun `isValidAssignee()에 작성자를 넣어 호출했을 때 태스크 작성 가능 여부를 반환한다`() {
        // Given & When & Then: To Do 이면서 Assignee가 있을 때 true 를 반환한다.
        val result1 = Status.TO_DO.isValidAssignee(Assignee("별터"))
        assertThat(result1).isEqualTo(true)

        // Given & When & Then: In Progress 이면서 Assignee가 없을 때 false 를 반환한다.
        val result2 = Status.IN_PROGRESS.isValidAssignee(null)
        assertThat(result2).isEqualTo(false)
    }

    @Test
    fun `isValidTransition()에 이동할 상태를 넣어 호출했을 때 이동 가능 여부를 반환한다`() {
        // Given & When & Then: To Do 에서 In Progress 로 이동 가능하다.
        val result1 = Status.TO_DO.isValidTransition(Status.IN_PROGRESS)
        assertThat(result1).isEqualTo(true)

        // Given & When & Then: To Do 에서 Review 로 이동 불가능하다.
        val result2 = Status.TO_DO.isValidTransition(Status.REVIEW)
        assertThat(result2).isEqualTo(false)
    }
}
