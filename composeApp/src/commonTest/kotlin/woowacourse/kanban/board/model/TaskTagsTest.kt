package woowacourse.kanban.board.model

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import woowacourse.kanban.board.model.taskcard.TaskTag
import woowacourse.kanban.board.model.taskcard.TaskTags

class TaskTagsTest {

    @Test
    fun `isValidTags에 최대 태그수 이하의 태그가 입력되면 true를 반환한다`() {
        val taskTags = listOf(TaskTag("태그1"), TaskTag("태그2"), TaskTag("태그3"))
        assertTrue { TaskTags.isTagsValid(taskTags) }
    }

    @Test
    fun `isValidTags에 최대 태그수가 1개 이상일 때, 1개 태그만 입력되면 true를 반환한다`() {
        assertTrue { TaskTags.isTagsValid(listOf(TaskTag(value = "태그1"))) }
    }

    @Test
    fun `isValidTags에 최대 태그 개수를 초과하는 태그가 입력되면 false를 반환한다`() {
        assertFalse {
            TaskTags.isTagsValid(listOf(TaskTag("태그1"), TaskTag("태그1"), TaskTag("태그1"), TaskTag("태그1"), TaskTag("태그1"), TaskTag("태그1")))
        }
    }
}
