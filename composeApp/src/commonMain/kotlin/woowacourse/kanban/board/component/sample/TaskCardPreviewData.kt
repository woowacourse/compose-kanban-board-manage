package woowacourse.kanban.board.component.sample

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import kanbanboard.composeapp.generated.resources.Res
import kanbanboard.composeapp.generated.resources.profile
import kotlinx.collections.immutable.toImmutableList
import woowacourse.kanban.board.model.taskcard.Assignee
import woowacourse.kanban.board.model.taskcard.Status
import woowacourse.kanban.board.model.taskcard.TaskCardData
import woowacourse.kanban.board.model.taskcard.TaskDescription
import woowacourse.kanban.board.model.taskcard.TaskTag
import woowacourse.kanban.board.model.taskcard.TaskTags
import woowacourse.kanban.board.model.taskcard.TaskTitle

class TaskCardPreviewData : PreviewParameterProvider<TaskCardData> {
    override val values: Sequence<TaskCardData> = sequenceOf(
        TaskCardData(
            taskTitle = TaskTitle("LazyColumn 컴포넌트 구현"),
            taskDescription = TaskDescription("세로 스크롤 가능한 리스트 컴포넌트를 만들고 성능 최적화를 적용합니다."),
            taskTags = TaskTags(listOf(TaskTag("컴포넌트"), TaskTag("성능")).toImmutableList()),
            assignee = Assignee("다이노", Res.drawable.profile),
            status = Status.TODO,
        ),
        TaskCardData(
            taskTitle = TaskTitle("Side-effect API 학습"),
            taskDescription = TaskDescription("LaunchedEffect, DisposableEffect 등의 API를 학습하고 적절한 사용 예제를 작성합니다."),
            taskTags = TaskTags(listOf(TaskTag("학습"), TaskTag("API")).toImmutableList()),
            assignee = Assignee("페임스", Res.drawable.profile),
            status = Status.TODO,
        ),
        TaskCardData(
            taskTitle = TaskTitle("상태 관리 리팩토링"),
            taskDescription = TaskDescription("복잡한 상태를 효율적으로 관리하기 위한 구조를 설계합니다."),
            taskTags = TaskTags(listOf(TaskTag("리팩토링"), TaskTag("상태관리")).toImmutableList()),
            assignee = Assignee("다이노", Res.drawable.profile),
            status = Status.PROGRESS,
        ),
        TaskCardData(
            taskTitle = TaskTitle("리컴포지션 최적화"),
            taskDescription = TaskDescription("derivedStateOf와 key를 활용하여 불필요한 리컴포지션을 방지합니다."),
            taskTags = TaskTags(listOf(TaskTag("최적화"), TaskTag("성능")).toImmutableList()),
            assignee = Assignee("다이노", Res.drawable.profile),
            status = Status.DONE,
        ),
        TaskCardData(
            taskTitle = TaskTitle("Mock API 설정"),
            taskDescription = TaskDescription("JSON 파일 또는 Mock API를 통해 초기 데이터를 로드하는 로직을 구현합니다."),
            taskTags = TaskTags(listOf(TaskTag("API"), TaskTag("비동기")).toImmutableList()),
            assignee = Assignee("페임스", Res.drawable.profile),
            status = Status.DONE,
        ),
        TaskCardData(
            taskTitle = TaskTitle("Drag & Drop 기능 구현"),
            taskDescription = TaskDescription("카드를 드래그하여 다른 컬럼으로 이동할 수 있는 기능을 구현합니다."),
            taskTags = TaskTags(listOf(TaskTag("기능"), TaskTag("UX")).toImmutableList()),
            assignee = Assignee("다이노", Res.drawable.profile),
            status = Status.DONE,
        ),
    )
}
