package woowacourse.kanban.board.domain

import kotlin.test.Test
import org.assertj.core.api.Assertions.assertThat

class TaskStatusTest {

    @Test
    fun `To Do는 In Progress로만 전이 가능하다`() {
        assertThat(TaskStatus.TODO.isTransitionableTo(TaskStatus.IN_PROGRESS)).isTrue()
        assertThat(TaskStatus.TODO.isTransitionableTo(TaskStatus.REVIEW)).isFalse()
        assertThat(TaskStatus.TODO.isTransitionableTo(TaskStatus.DONE)).isFalse()
        assertThat(TaskStatus.TODO.isTransitionableTo(TaskStatus.TODO)).isFalse()
    }

    @Test
    fun `In Progress는 To Do와 Review로만 전이 가능하다`() {
        assertThat(TaskStatus.IN_PROGRESS.isTransitionableTo(TaskStatus.TODO)).isTrue()
        assertThat(TaskStatus.IN_PROGRESS.isTransitionableTo(TaskStatus.REVIEW)).isTrue()
        assertThat(TaskStatus.IN_PROGRESS.isTransitionableTo(TaskStatus.DONE)).isFalse()
        assertThat(TaskStatus.IN_PROGRESS.isTransitionableTo(TaskStatus.IN_PROGRESS)).isFalse()
    }

    @Test
    fun `Review는 In Progress와 Done으로만 전이 가능하다`() {
        assertThat(TaskStatus.REVIEW.isTransitionableTo(TaskStatus.IN_PROGRESS)).isTrue()
        assertThat(TaskStatus.REVIEW.isTransitionableTo(TaskStatus.DONE)).isTrue()
        assertThat(TaskStatus.REVIEW.isTransitionableTo(TaskStatus.TODO)).isFalse()
        assertThat(TaskStatus.REVIEW.isTransitionableTo(TaskStatus.REVIEW)).isFalse()
    }

    @Test
    fun `Done은 To Do로만 전이 가능하다`() {
        assertThat(TaskStatus.DONE.isTransitionableTo(TaskStatus.TODO)).isTrue()
        assertThat(TaskStatus.DONE.isTransitionableTo(TaskStatus.IN_PROGRESS)).isFalse()
        assertThat(TaskStatus.DONE.isTransitionableTo(TaskStatus.REVIEW)).isFalse()
        assertThat(TaskStatus.DONE.isTransitionableTo(TaskStatus.DONE)).isFalse()
    }

    @Test
    fun `To Do와 In Progress 태스크는 삭제 가능하다`() {
        assertThat(TaskStatus.TODO.isDeletable).isTrue()
        assertThat(TaskStatus.IN_PROGRESS.isDeletable).isTrue()
    }

    @Test
    fun `Review와 Done 태스크는 삭제 불가능하다`() {
        assertThat(TaskStatus.REVIEW.isDeletable).isFalse()
        assertThat(TaskStatus.DONE.isDeletable).isFalse()
    }

    @Test
    fun `To Do 태스크는 담당자 지정이 필수가 아니다`() {
        assertThat(TaskStatus.TODO.isAssigneeRequired).isFalse()
    }

    @Test
    fun `In Progress, Review, Done 태스크는 담당자 지정이 필수다`() {
        assertThat(TaskStatus.IN_PROGRESS.isAssigneeRequired).isTrue()
        assertThat(TaskStatus.REVIEW.isAssigneeRequired).isTrue()
        assertThat(TaskStatus.DONE.isAssigneeRequired).isTrue()
    }
}
