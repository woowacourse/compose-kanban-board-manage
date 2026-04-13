package woowacourse.kanban.board.model

import kotlin.test.Test
import org.assertj.core.api.Assertions.assertThat
import woowacourse.kanban.board.model.taskcard.Status
import woowacourse.kanban.board.model.taskcard.TaskCardPolicy

class TaskCardPolicyTest {

    @Test
    fun `todo와 in progress 상태에서는 삭제가 가능하다`() {
        assertThat(TaskCardPolicy.canDelete(Status.TODO)).isTrue()
        assertThat(TaskCardPolicy.canDelete(Status.PROGRESS)).isTrue()
    }

    @Test
    fun `review와 done 상태에서는 삭제가 불가능하다`() {
        assertThat(TaskCardPolicy.canDelete(Status.REVIEW)).isFalse()
        assertThat(TaskCardPolicy.canDelete(Status.DONE)).isFalse()
    }

    @Test
    fun `todo 상태만 담당자 미지정이 가능하다`() {
        assertThat(TaskCardPolicy.requireProfile(Status.TODO)).isFalse()
        assertThat(TaskCardPolicy.requireProfile(Status.PROGRESS)).isTrue()
        assertThat(TaskCardPolicy.requireProfile(Status.REVIEW)).isTrue()
        assertThat(TaskCardPolicy.requireProfile(Status.DONE)).isTrue()
    }

    @Test
    fun `상태 전이 정책에 맞는 경우만 true를 반환한다`() {
        assertThat(TaskCardPolicy.canModifyStatus(Status.TODO, Status.PROGRESS)).isTrue()
        assertThat(TaskCardPolicy.canModifyStatus(Status.PROGRESS, Status.TODO)).isTrue()
        assertThat(TaskCardPolicy.canModifyStatus(Status.PROGRESS, Status.REVIEW)).isTrue()
        assertThat(TaskCardPolicy.canModifyStatus(Status.REVIEW, Status.PROGRESS)).isTrue()
        assertThat(TaskCardPolicy.canModifyStatus(Status.REVIEW, Status.DONE)).isTrue()
        assertThat(TaskCardPolicy.canModifyStatus(Status.DONE, Status.TODO)).isTrue()

        assertThat(TaskCardPolicy.canModifyStatus(Status.TODO, Status.DONE)).isFalse()
        assertThat(TaskCardPolicy.canModifyStatus(Status.TODO, Status.REVIEW)).isFalse()
        assertThat(TaskCardPolicy.canModifyStatus(Status.PROGRESS, Status.DONE)).isFalse()
        assertThat(TaskCardPolicy.canModifyStatus(Status.REVIEW, Status.TODO)).isFalse()
        assertThat(TaskCardPolicy.canModifyStatus(Status.DONE, Status.REVIEW)).isFalse()
        assertThat(TaskCardPolicy.canModifyStatus(Status.DONE, Status.PROGRESS)).isFalse()
    }
}
