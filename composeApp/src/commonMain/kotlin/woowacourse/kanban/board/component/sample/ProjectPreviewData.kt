package woowacourse.kanban.board.component.sample

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import kotlinx.collections.immutable.toImmutableList
import woowacourse.kanban.board.model.project.Project
import woowacourse.kanban.board.model.taskcard.Description
import woowacourse.kanban.board.model.taskcard.Profile
import woowacourse.kanban.board.model.taskcard.Status
import woowacourse.kanban.board.model.taskcard.Tag
import woowacourse.kanban.board.model.taskcard.Tags
import woowacourse.kanban.board.model.taskcard.TaskCard
import woowacourse.kanban.board.model.taskcard.Title

class ProjectPreviewData : PreviewParameterProvider<Project> {
    val taskCards = TaskCardPreviewData().values.toImmutableList()
    override val values: Sequence<Project> = sequenceOf(
        Project(
            id = "project-1",
            title = "Compose1",
            tasks = taskCards
        ),
        Project(
            id = "project-2",
            title = "Compose2",
            tasks = taskCards
        ),
        Project(
            id = "project-3",
            title = "Compose3너무너무길경우에는 말줄임표로 표시됩니다.",
            tasks = taskCards
        ),
    )
}

class TaskCardPreviewData : PreviewParameterProvider<TaskCard> {
    override val values: Sequence<TaskCard> = sequenceOf(
        TaskCard(
            id = "task-1",
            title = Title("LazyColumn 컴포넌트 구현"),
            description = Description("세로 스크롤 가능한 리스트 컴포넌트를 만들고 성능 최적화를 적용합니다."),
            tags = Tags(listOf(Tag("컴포넌트"), Tag("성능")).toImmutableList()),
            profile = Profile.NONE,
            status = Status.TODO,
        ),
        TaskCard(
            id = "task-2",
            title = Title("Side-effect API 학습"),
            description = Description("LaunchedEffect, DisposableEffect 등의 API를 학습하고 적절한 사용 예제를 작성합니다."),
            tags = Tags(listOf(Tag("학습"), Tag("API")).toImmutableList()),
            profile = Profile("페임스"),
            status = Status.TODO,
        ),
        TaskCard(
            id = "task-3",
            title = Title("상태 관리 리팩토링"),
            description = Description("복잡한 상태를 효율적으로 관리하기 위한 구조를 설계합니다."),
            tags = Tags(listOf(Tag("리팩토링"), Tag("상태관리")).toImmutableList()),
            profile = Profile("다이노"),
            status = Status.PROGRESS,
        ),
        TaskCard(
            id = "task-4",
            title = Title("리컴포지션 최적화"),
            description = Description("derivedStateOf와 key를 활용하여 불필요한 리컴포지션을 방지합니다."),
            tags = Tags(listOf(Tag("최적화"), Tag("성능")).toImmutableList()),
            profile = Profile("다이노"),
            status = Status.DONE,
        ),
        TaskCard(
            id = "task-5",
            title = Title("Mock API 설정"),
            description = Description("JSON 파일 또는 Mock API를 통해 초기 데이터를 로드하는 로직을 구현합니다."),
            tags = Tags(listOf(Tag("API"), Tag("비동기")).toImmutableList()),
            profile = Profile("페임스"),
            status = Status.DONE,
        ),
        TaskCard(
            id = "task-6",
            title = Title("Drag & Drop 기능 구현"),
            description = Description("카드를 드래그하여 다른 컬럼으로 이동할 수 있는 기능을 구현합니다."),
            tags = Tags(listOf(Tag("기능"), Tag("UX")).toImmutableList()),
            profile = Profile("다이노"),
            status = Status.DONE,
        ),
        TaskCard(
            id = "task-7",
            title = Title("리컴포지션 최적화"),
            description = Description("derivedStateOf와 key를 활용하여 불필요한 리컴포지션을 방지합니다."),
            tags = Tags(listOf(Tag("최적화"), Tag("성능")).toImmutableList()),
            profile = Profile("페임스"),
            status = Status.REVIEW
        )
    )
}

class ProfilePreviewData : PreviewParameterProvider<Profile> {
    override val values: Sequence<Profile> = sequenceOf(
        Profile("다이노"),
        Profile("페임스")
    )
}
