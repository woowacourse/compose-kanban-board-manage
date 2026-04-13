package woowacourse.kanban.board.model

import kotlin.test.Test
import kotlinx.collections.immutable.persistentListOf
import org.assertj.core.api.Assertions.assertThat
import woowacourse.kanban.board.model.taskcard.Description
import woowacourse.kanban.board.model.taskcard.Profile
import woowacourse.kanban.board.model.taskcard.Status
import woowacourse.kanban.board.model.taskcard.Tag
import woowacourse.kanban.board.model.taskcard.Tags
import woowacourse.kanban.board.model.taskcard.TaskCard
import woowacourse.kanban.board.model.taskcard.Title

class TaskCardTest {

    @Test
    fun `changeStatus를 호출하면 전달한 상태를 가진 새 TaskCard를 반환한다`() {
        val taskCard = createTask(status = Status.TODO)

        val changedTaskCard = taskCard.changeStatus(Status.PROGRESS)

        assertThat(changedTaskCard.status).isEqualTo(Status.PROGRESS)
    }

    @Test
    fun `changeStatus를 호출해도 상태를 제외한 기존 TaskCard의 값은 유지된다`() {
        val taskCard = createTask(status = Status.REVIEW)

        val changedTaskCard = taskCard.changeStatus(Status.DONE)

        assertThat(changedTaskCard.id).isEqualTo(taskCard.id)
        assertThat(changedTaskCard.title).isEqualTo(taskCard.title)
        assertThat(changedTaskCard.description).isEqualTo(taskCard.description)
        assertThat(changedTaskCard.tags).isEqualTo(taskCard.tags)
        assertThat(changedTaskCard.profile).isEqualTo(taskCard.profile)
    }

    private fun createTask(
        status: Status,
    ): TaskCard {
        return TaskCard(
            id = "task-1",
            title = Title("제목"),
            description = Description("설명"),
            tags = Tags(persistentListOf(Tag("태그1"))),
            status = status,
            profile = Profile("다이노"),
        )
    }
}
