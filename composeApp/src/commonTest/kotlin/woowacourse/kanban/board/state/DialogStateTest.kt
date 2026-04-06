package woowacourse.kanban.board.state

import kotlin.test.Test
import org.assertj.core.api.Assertions.assertThat
import woowacourse.kanban.board.domain.Status
import woowacourse.kanban.board.domain.Tag
import woowacourse.kanban.board.domain.Task

private const val TEST_TITLE = "테스트 제목"
private const val TEST_DESCRIPTION = "테스트 설명"
private const val TEST_NAME = "테스터"

class DialogStateTest {

    @Test
    fun `CREATE 모드에서 onTaskCreate 호출 시 태스크가 생성된다`() {
        val dialogState = DialogState()
        dialogState.titleInputValue = TEST_TITLE
        dialogState.descriptionInputValue = TEST_DESCRIPTION
        dialogState.statusValue = Status.TODO
        dialogState.nameValue = TEST_NAME

        dialogState.onTaskCreate()

        assertThat(dialogState.createdTask).isNotNull()
        assertThat(dialogState.createdTask?.title).isEqualTo(TEST_TITLE)
        assertThat(dialogState.createdTask?.description).isEqualTo(TEST_DESCRIPTION)
        assertThat(dialogState.createdTask?.status).isEqualTo(Status.TODO)
        assertThat(dialogState.createdTask?.nickname).isEqualTo(TEST_NAME)
    }


    @Test
    fun `기존 태스크 데이터를 다이얼로그에 로드한다`() {
        val dialogState = DialogState()
        val originalTask = Task(
            title = "원본 제목",
            description = "원본 설명",
            tags = listOf(Tag("태그1"), Tag("태그2")),
            status = Status.IN_PROGRESS,
            nickname = "원본담당자",
        )

        dialogState.loadTaskData(originalTask)

        assertThat(dialogState.titleInputValue).isEqualTo("원본 제목")
        assertThat(dialogState.descriptionInputValue).isEqualTo("원본 설명")
        assertThat(dialogState.tagsInputValue).contains("태그1")
        assertThat(dialogState.statusValue).isEqualTo(Status.IN_PROGRESS)
        assertThat(dialogState.nameValue).isEqualTo("원본담당자")
        assertThat(dialogState.editTask).isEqualTo(originalTask)
    }

    @Test
    fun `EDIT 모드에서 태스크 수정이 완료된다`() {
        val dialogState = DialogState()
        val originalTask = Task(
            title = "원본 제목",
            description = "원본 설명",
            status = Status.TODO,
            nickname = "원본담당자",
        )

        dialogState.loadTaskData(originalTask)
        dialogState.titleInputValue = "수정된 제목"
        dialogState.descriptionInputValue = "수정된 설명"
        dialogState.nameValue = "수정된담당자"

        dialogState.onTaskEdit()

        assertThat(dialogState.editTask?.title).isEqualTo("수정된 제목")
        assertThat(dialogState.editTask?.description).isEqualTo("수정된 설명")
        assertThat(dialogState.editTask?.nickname).isEqualTo("수정된담당자")
        assertThat(dialogState.createdTask).isEqualTo(dialogState.editTask)
    }

    @Test
    fun `onTaskDelete 호출 시 editTask가 deleteTask로 설정된다`() {
        val dialogState = DialogState()
        val task = Task(
            title = "삭제할 태스크",
            status = Status.TODO,
            nickname = "담당자",
        )

        dialogState.editTask = task
        dialogState.onTaskDelete()

        assertThat(dialogState.deleteTask).isEqualTo(task)
    }

    @Test
    fun `selectMode CREATE 선택 시 onTaskCreate가 호출된다`() {
        val dialogState = DialogState()
        dialogState.titleInputValue = TEST_TITLE
        dialogState.nameValue = TEST_NAME

        dialogState.selectMode()

        assertThat(dialogState.createdTask).isNotNull()
        assertThat(dialogState.createdTask?.title).isEqualTo(TEST_TITLE)
    }

    @Test
    fun `selectMode EDIT 선택 시 onTaskEdit가 호출된다`() {
        val dialogState = DialogState()
        val originalTask = Task(
            title = "원본",
            status = Status.TODO,
            nickname = "담당자",
        )

        dialogState.editTask = originalTask
        dialogState.titleInputValue = "수정됨"

        dialogState.selectMode()

        assertThat(dialogState.editTask?.title).isEqualTo("수정됨")
    }

    @Test
    fun `resetDialog 호출 시 모든 입력값이 초기화된다`() {
        val dialogState = DialogState()
        dialogState.titleInputValue = "제목"
        dialogState.descriptionInputValue = "설명"
        dialogState.tagsInputValue = "태그"
        dialogState.statusValue = Status.DONE
        dialogState.nameValue = "담당자"
        dialogState.isTitleError = true
        dialogState.isTagsError = true

        dialogState.resetDialog()

        assertThat(dialogState.titleInputValue).isEmpty()
        assertThat(dialogState.descriptionInputValue).isEmpty()
        assertThat(dialogState.tagsInputValue).isEmpty()
        assertThat(dialogState.statusValue).isEqualTo(Status.TODO)
        assertThat(dialogState.nameValue).isEqualTo("다이노")
        assertThat(dialogState.isTitleError).isFalse()
        assertThat(dialogState.isTagsError).isFalse()
        assertThat(dialogState.createdTask).isNull()
        assertThat(dialogState.editTask).isNull()
        assertThat(dialogState.deleteTask).isNull()
    }
}
