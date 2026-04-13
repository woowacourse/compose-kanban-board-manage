package woowacourse.kanban.board.model

import kotlin.test.Test
import kotlin.test.assertTrue
import kotlinx.collections.immutable.persistentListOf
import org.assertj.core.api.Assertions.assertThat
import woowacourse.kanban.board.model.project.Project
import woowacourse.kanban.board.model.taskcard.Description
import woowacourse.kanban.board.model.taskcard.Profile
import woowacourse.kanban.board.model.taskcard.Status
import woowacourse.kanban.board.model.taskcard.Tag
import woowacourse.kanban.board.model.taskcard.Tags
import woowacourse.kanban.board.model.taskcard.TaskCard
import woowacourse.kanban.board.model.taskcard.Title

class ProjectTest {
    @Test
    fun `입력한 id를 가진 태스크 카드가 변경값으로 입력한 status로 변경된다`() {
        val project = Project(
            id = "project-1",
            title = "테스트 프로젝트",
            tasks = persistentListOf(
                TaskCard(
                    id = "테스트",
                    title = Title("제목"),
                    description = Description("설명"),
                    tags = Tags(persistentListOf(Tag("태그1"), Tag("태그1"))),
                    status = Status.TODO,
                    profile = Profile("다이노"),
                )
            )
        )
        val updatedProject = project.updateTaskStatus("테스트", Status.PROGRESS)
        assertThat(updatedProject.filterTasksbyStatus(Status.TODO).size).isEqualTo(0)
        assertThat(updatedProject.filterTasksbyStatus(Status.PROGRESS).size).isEqualTo(1)
    }

    @Test
    fun `찾고자 하는 태스크 카드의 id값을 넣었을 때 해당 id 값을 가진 TaskCard를 찾을 수 있다`() {
        val task = TaskCard(
            id = "테스트",
            title = Title("제목"),
            description = Description("설명"),
            tags = Tags(persistentListOf(Tag("태그1"), Tag("태그1"))),
            status = Status.TODO,
            profile = Profile("다이노"),
        )
        val project = Project(
            id = "project-2",
            title = "테스트 프로젝트",
            tasks = persistentListOf(
                task
            )
        )
        assertTrue { project.findTaskById("테스트") == task }
    }
}
