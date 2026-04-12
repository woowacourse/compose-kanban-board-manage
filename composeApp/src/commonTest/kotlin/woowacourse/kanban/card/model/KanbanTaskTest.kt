package woowacourse.kanban.card.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.Assert.assertThrows
import org.junit.Test
import woowacourse.kanban.domain.task.Assignee
import woowacourse.kanban.domain.task.KanbanTask
import woowacourse.kanban.domain.task.Tags
import woowacourse.kanban.domain.task.TaskData
import woowacourse.kanban.domain.task.TaskStatus
import woowacourse.kanban.domain.task.Title

class KanbanTaskTest {

    @Test
    fun `Task의 taskData의 내용을 변경할 수 있다`() {
        // given: 칸반 태스크가 주어진다
        val task = KanbanTask(
            data = TaskData(
                title = Title("제목"),
                content = "",
                tags = Tags(emptyList()),
                assignee = Assignee.DINO,
            ),
            status = TaskStatus.TO_DO,
        )

        // when: 새로운 데이터가 입력됐을 때
        val newTaskData = TaskData(
            title = Title("새로운 제목"),
            content = "",
            tags = Tags(emptyList()),
            assignee = Assignee.FAMES,
        )

        // then: 기존 태스크의 데이터가 변경된다
        assertThat(task.changeData(newTaskData)).isEqualTo(KanbanTask(data = newTaskData, status = TaskStatus.TO_DO))
    }

    @Test
    fun `To Do는 In Progress로 변경할 수 있다`() {
        // given: 칸반 태스크가 주어진다
        val task = KanbanTask(
            data = TaskData(
                title = Title("제목"),
                content = "",
                tags = Tags(emptyList()),
                assignee = Assignee.DINO,
            ),
            status = TaskStatus.TO_DO,
        )

        // when: 태스크의 상태를 IN_PROGRESS로 변경했을 때
        val targetStatus = TaskStatus.IN_PROGRESS

        // then: 태스크의 상태가 변경된다
        assertThat(task.changeStatus(targetStatus)).isEqualTo(KanbanTask(data = task.data, status = targetStatus))
    }

    @Test
    fun `To Do는 REVIEW로 변경할 수 없다`() {
        // given: 칸반 태스크가 주어진다
        val task = KanbanTask(
            data = TaskData(
                title = Title("제목"),
                content = "",
                tags = Tags(emptyList()),
                assignee = Assignee.DINO,
            ),
            status = TaskStatus.TO_DO,
        )

        // when: 태스크의 상태를 REVIEW로 변경했을 때
        val targetStatus = TaskStatus.REVIEW

        // then: 예외가 발생한다
        assertThrows(IllegalStateException::class.java) { task.changeStatus(targetStatus) }
    }

    @Test
    fun `To Do는 DONE으로 변경할 수 없다`() {
        // given: 칸반 태스크가 주어진다
        val task = KanbanTask(
            data = TaskData(
                title = Title("제목"),
                content = "",
                tags = Tags(emptyList()),
                assignee = Assignee.DINO,
            ),
            status = TaskStatus.TO_DO,
        )

        // when: 태스크의 상태를 REVIEW로 변경했을 때
        val targetStatus = TaskStatus.DONE

        // then: 예외가 발생한다
        assertThrows(IllegalStateException::class.java) { task.changeStatus(targetStatus) }
    }

    @Test
    fun `IN_PROGRESS는 TO_DO로 변경할 수 있다`() {
        // given: 칸반 태스크가 주어진다
        val task = KanbanTask(
            data = TaskData(
                title = Title("제목"),
                content = "",
                tags = Tags(emptyList()),
                assignee = Assignee.DINO,
            ),
            status = TaskStatus.IN_PROGRESS,
        )

        // when: 태스크의 상태를 TO_DO로 변경했을 때
        val targetStatus = TaskStatus.TO_DO

        // then: 태스크의 상태가 변경된다
        assertThat(task.changeStatus(targetStatus)).isEqualTo(KanbanTask(data = task.data, status = targetStatus))
    }

    @Test
    fun `IN_PROGRESS는 REVIEW로 변경할 수 있다`() {
        // given: 칸반 태스크가 주어진다
        val task = KanbanTask(
            data = TaskData(
                title = Title("제목"),
                content = "",
                tags = Tags(emptyList()),
                assignee = Assignee.DINO,
            ),
            status = TaskStatus.IN_PROGRESS,
        )

        // when: 태스크의 상태를 REVIEW로 변경했을 때
        val targetStatus = TaskStatus.REVIEW

        // then: 태스크의 상태가 변경된다
        assertThat(task.changeStatus(targetStatus)).isEqualTo(KanbanTask(data = task.data, status = targetStatus))
    }

    @Test
    fun `IN_PROGRESS는 DONE으로 변경할 수 없다`() {
        // given: 칸반 태스크가 주어진다
        val task = KanbanTask(
            data = TaskData(
                title = Title("제목"),
                content = "",
                tags = Tags(emptyList()),
                assignee = Assignee.DINO,
            ),
            status = TaskStatus.IN_PROGRESS,
        )

        // when: 태스크의 상태를 REVIEW로 변경했을 때
        val targetStatus = TaskStatus.DONE

        // then: 예외가 발생한다
        assertThrows(IllegalStateException::class.java) { task.changeStatus(targetStatus) }
    }

    @Test
    fun `REVIEW는 TO_DO로 변경할 수 없다`() {
        // given: 칸반 태스크가 주어진다
        val task = KanbanTask(
            data = TaskData(
                title = Title("제목"),
                content = "",
                tags = Tags(emptyList()),
                assignee = Assignee.DINO,
            ),
            status = TaskStatus.REVIEW,
        )

        // when: 태스크의 상태를 TO_DO로 변경했을 때
        val targetStatus = TaskStatus.TO_DO

        // then: 예외가 발생한다
        assertThrows(IllegalStateException::class.java) { task.changeStatus(targetStatus) }
    }

    @Test
    fun `REVIEW는 IN_PROGRESS로 변경할 수 있다`() {
        // given: 칸반 태스크가 주어진다
        val task = KanbanTask(
            data = TaskData(
                title = Title("제목"),
                content = "",
                tags = Tags(emptyList()),
                assignee = Assignee.DINO,
            ),
            status = TaskStatus.REVIEW,
        )

        // when: 태스크의 상태를 IN_PROGRESS로 변경했을 때
        val targetStatus = TaskStatus.IN_PROGRESS

        // then: 태스크의 상태가 변경된다
        assertThat(task.changeStatus(targetStatus)).isEqualTo(KanbanTask(data = task.data, status = targetStatus))
    }

    @Test
    fun `REVIEW는 DONE으로 변경할 수 있다`() {
        // given: 칸반 태스크가 주어진다
        val task = KanbanTask(
            data = TaskData(
                title = Title("제목"),
                content = "",
                tags = Tags(emptyList()),
                assignee = Assignee.DINO,
            ),
            status = TaskStatus.REVIEW,
        )

        // when: 태스크의 상태를 REVIEW로 변경했을 때
        val targetStatus = TaskStatus.DONE

        // then: 태스크의 상태가 변경된다
        assertThat(task.changeStatus(targetStatus)).isEqualTo(KanbanTask(data = task.data, status = targetStatus))
    }

    @Test
    fun `DONE은 TO_DO로 변경할 수 있다`() {
        // given: 칸반 태스크가 주어진다
        val task = KanbanTask(
            data = TaskData(
                title = Title("제목"),
                content = "",
                tags = Tags(emptyList()),
                assignee = Assignee.DINO,
            ),
            status = TaskStatus.DONE,
        )

        // when: 태스크의 상태를 TO_DO로 변경했을 때
        val targetStatus = TaskStatus.TO_DO

        // then: 태스크의 상태가 변경된다
        assertThat(task.changeStatus(targetStatus)).isEqualTo(KanbanTask(data = task.data, status = targetStatus))
    }

    @Test
    fun `DONE은 IN_PROGRESS로 변경할 수 없다`() {
        // given: 칸반 태스크가 주어진다
        val task = KanbanTask(
            data = TaskData(
                title = Title("제목"),
                content = "",
                tags = Tags(emptyList()),
                assignee = Assignee.DINO,
            ),
            status = TaskStatus.DONE,
        )

        // when: 태스크의 상태를 IN_PROGRESS로 변경했을 때
        val targetStatus = TaskStatus.IN_PROGRESS

        // then: 예외가 발생한다
        assertThrows(IllegalStateException::class.java) { task.changeStatus(targetStatus) }
    }

    @Test
    fun `DONE은 REVIEW으로 변경할 수 없다`() {
        // given: 칸반 태스크가 주어진다
        val task = KanbanTask(
            data = TaskData(
                title = Title("제목"),
                content = "",
                tags = Tags(emptyList()),
                assignee = Assignee.DINO,
            ),
            status = TaskStatus.DONE,
        )

        // when: 태스크의 상태를 REVIEW로 변경했을 때
        val targetStatus = TaskStatus.REVIEW

        // then: 예외가 발생한다
        assertThrows(IllegalStateException::class.java) { task.changeStatus(targetStatus) }
    }

    @Test
    fun `To Do는 담당자가 지정되지 않아도 Task를 생성할 수 있다`() {
        // given: To do 상태의 태스크를 생성할 데이터가 주어진다
        val data = TaskData(
            title = Title("제목"),
            content = "",
            tags = Tags(emptyList()),
            assignee = Assignee.NONE,
        )
        val status = TaskStatus.TO_DO

        // when: 태스크를 생성했을 때 담당자가 지정되어 있지 않아도
        val task = KanbanTask(data, status)

        // then: 태스크가 생성된다
        assertThat(task).isEqualTo(KanbanTask(data, status))
    }

    @Test
    fun `In Progress는 담당자가 지정되어야 Task를 생성할 수 있다`() {
        // given: To do 상태의 태스크를 생성할 데이터가 주어진다
        val data = TaskData(
            title = Title("제목"),
            content = "",
            tags = Tags(emptyList()),
            assignee = Assignee.DINO,
        )
        val status = TaskStatus.IN_PROGRESS

        // when: 태스크를 생성했을 때 담당자가 지정되어 있지 않아도
        val task = KanbanTask(data, status)

        // then: 태스크가 생성된다
        assertThat(task).isEqualTo(KanbanTask(data, status))
    }

    @Test
    fun `Review는 담당자가 지정되어야 Task를 생성할 수 있다`() {
        // given: To do 상태의 태스크를 생성할 데이터가 주어진다
        val data = TaskData(
            title = Title("제목"),
            content = "",
            tags = Tags(emptyList()),
            assignee = Assignee.DINO,
        )
        val status = TaskStatus.REVIEW

        // when: 태스크를 생성했을 때 담당자가 지정되어 있지 않아도
        val task = KanbanTask(data, status)

        // then: 태스크가 생성된다
        assertThat(task).isEqualTo(KanbanTask(data, status))
    }

    @Test
    fun `Done은 담당자가 지정되어야 Task를 생성할 수 있다`() {
        // given: To do 상태의 태스크를 생성할 데이터가 주어진다
        val data = TaskData(
            title = Title("제목"),
            content = "",
            tags = Tags(emptyList()),
            assignee = Assignee.DINO,
        )
        val status = TaskStatus.DONE

        // when: 태스크를 생성했을 때 담당자가 지정되어 있지 않아도
        val task = KanbanTask(data, status)

        // then: 태스크가 생성된다
        assertThat(task).isEqualTo(KanbanTask(data, status))
    }

    @Test
    fun `In Progress는 담당자가 지정되지 않으면 Task를 생성할 수 없다`() {
        // given: To do 상태의 태스크를 생성할 데이터가 주어진다
        val data = TaskData(
            title = Title("제목"),
            content = "",
            tags = Tags(emptyList()),
            assignee = Assignee.NONE,
        )
        val status = TaskStatus.IN_PROGRESS

        // when: 태스크를 생성했을 때 담당자가 지정되어 있지 않으면
        // then: 예외가 발생한다
        assertThrows(IllegalArgumentException::class.java) { KanbanTask(data, status) }
    }

    @Test
    fun `Review는 담당자가 지정되지 않으면 Task를 생성할 수 없다`() {
        // given: To do 상태의 태스크를 생성할 데이터가 주어진다
        val data = TaskData(
            title = Title("제목"),
            content = "",
            tags = Tags(emptyList()),
            assignee = Assignee.NONE,
        )
        val status = TaskStatus.REVIEW

        // when: 태스크를 생성했을 때 담당자가 지정되어 있지 않으면
        // then: 예외가 발생한다
        assertThrows(IllegalArgumentException::class.java) { KanbanTask(data, status) }
    }

    @Test
    fun `Done은 담당자가 지정되지 않으면 Task를 생성할 수 없다`() {
        // given: To do 상태의 태스크를 생성할 데이터가 주어진다
        val data = TaskData(
            title = Title("제목"),
            content = "",
            tags = Tags(emptyList()),
            assignee = Assignee.NONE,
        )
        val status = TaskStatus.DONE

        // when: 태스크를 생성했을 때 담당자가 지정되어 있지 않으면
        // then: 예외가 발생한다
        assertThrows(IllegalArgumentException::class.java) { KanbanTask(data, status) }
    }
}
