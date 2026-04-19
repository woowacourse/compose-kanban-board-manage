package woowacourse.kanban.board.domain

import kotlin.test.Test
import kotlin.test.assertFailsWith
import org.assertj.core.api.Assertions.assertThat
import woowacourse.kanban.board.domain.dialog.Status
import woowacourse.kanban.board.domain.result.TaskResult

class KanbanTaskTest {
    @Test
    fun `KanbanTask 정상 생성 - 모든 정보가 올바른 경우`() {
        // Given
        val title = "새로운 기능 구현"
        val description = "이 기능은 매우 중요합니다."
        val tags = listOf("긴급", "백엔드")
        val crewName = "아키"

        // When
        val task = KanbanTask(
            title = title,
            description = description,
            tags = tags,
            status = Status.TO_DO,
            assignee = crewName,
        )

        // Then
        assertThat(task.title).isEqualTo(title)
        assertThat(task.description).isEqualTo(description)
        assertThat(task.tags).containsExactlyElementsOf(tags)
        assertThat(task.assignee).isEqualTo(crewName)
    }

    @Test
    fun `KanbanTask 생성 실패 - 제목이 비어 있는 경우`() {
        // Given
        val emptyTitle = ""

        // When & Then
        val exception = assertFailsWith<IllegalArgumentException> {
            KanbanTask(
                title = emptyTitle,
                status = Status.TO_DO,
                assignee = "아키",
            )
        }
        assertThat(exception.message).isEqualTo("제목은 비어 있거나 공백만 있을 수 없습니다.")
    }

    @Test
    fun `KanbanTask 생성 실패 - 제목이 공백만 있는 경우`() {
        // Given
        val blankTitle = "   \t\n\r\n\t\n"

        // When & Then
        val exception = assertFailsWith<IllegalArgumentException> {
            KanbanTask(
                title = blankTitle,
                status = Status.TO_DO,
                assignee = "아키",
            )
        }
        assertThat(exception.message).isEqualTo("제목은 비어 있거나 공백만 있을 수 없습니다.")
    }

    @Test
    fun `KanbanTask 생성 실패 - 태그가 5개 이상인 경우`() {
        // Given
        val tags = listOf("안녕1", "안녕2", "안녕3", "안녕4", "안녕5", "안녕6")

        // When & Then
        val exception = assertFailsWith<IllegalArgumentException> {
            KanbanTask(
                title = "제목",
                status = Status.TO_DO,
                assignee = "아키",
                tags = tags,
            )
        }
        assertThat(exception.message).isEqualTo("태그는 5개까지만 등록할 수 있습니다.")
    }

    @Test
    fun `KanbanTask 생성 실패 - 태그 중 5글자가 넘는 태그가 있는 경우`() {
        // Given
        val tags = listOf("안녕하세요긴태그", "안녕")

        // When & Then
        val exception = assertFailsWith<IllegalArgumentException> {
            KanbanTask(
                title = "제목",
                status = Status.TO_DO,
                assignee = "아키",
                tags = tags,
            )
        }
        assertThat(exception.message).isEqualTo("태그의 길이는 1에서 5자로 설정해야됩니다.")
    }

    @Test
    fun `isTitleValid 검증 - 정상적인 제목이면 true를 반환한다`() {
        assertThat(KanbanTask.isTitleValid("제목")).isTrue()
    }

    @Test
    fun `isTitleValid 검증 - 공백이거나 비어있으면 false를 반환한다`() {
        assertThat(KanbanTask.isTitleValid("")).isFalse()
        assertThat(KanbanTask.isTitleValid("   ")).isFalse()
    }

    @Test
    fun `isTagCountValid 검증 - 태그가 5개 이하이면 true를 반환한다`() {
        assertThat(KanbanTask.isTagCountValid(emptyList())).isTrue()
        assertThat(KanbanTask.isTagCountValid(listOf("1", "2", "3", "4", "5"))).isTrue()
    }

    @Test
    fun `isTagCountValid 검증 - 태그가 6개 이상이면 false를 반환한다`() {
        assertThat(KanbanTask.isTagCountValid(listOf("1", "2", "3", "4", "5", "6"))).isFalse()
    }

    @Test
    fun `isTagFormatValid 검증 - 빈 리스트면 true를 반환한다`() {
        assertThat(KanbanTask.isTagFormatValid(emptyList())).isTrue()
    }

    @Test
    fun `isTagFormatValid 검증 - 모든 태그가 1~5자이면 true를 반환한다`() {
        assertThat(KanbanTask.isTagFormatValid(listOf("1", "12345", "태그임다"))).isTrue()
    }

    @Test
    fun `isTagFormatValid 검증 - 태그 중 하나라도 5자를 초과하면 false를 반환한다`() {
        assertThat(KanbanTask.isTagFormatValid(listOf("정상태그", "여섯글자태그"))).isFalse()
    }

    @Test
    fun `isTagFormatValid 검증 - 태그 중 하나라도 비어있으면 false를 반환한다`() {
        assertThat(KanbanTask.isTagFormatValid(listOf("정상", ""))).isFalse()
    }

    @Test
    fun `태스크 상태를 변경하면 새로운 상태가 적용된다`() {
        // Given
        val task = KanbanTask(
            title = "안녕하세요~",
            status = Status.REVIEW,
            assignee = "볼트",
        )

        // When
        val result = task.changeStatus(Status.DONE) as TaskResult.Success
        val statusUpdateTask = result.task

        // Then
        assertThat(statusUpdateTask.status).isEqualTo(Status.DONE)
    }

    @Test
    fun `테스크 수정에 성공한다`() {
        val task = KanbanTask(
            title = "안녕하세요~",
            status = Status.REVIEW,
            assignee = "볼트",
        )

        val updateTask = task.copy(
            title = "수정입니다",
            status = Status.DONE,
            assignee = "크롱",
        )

        val result = task.editTask(newTask = updateTask) as TaskResult.Success

        assertThat(result.task).isEqualTo(updateTask)
    }
}
