package woowacourse.kanban.board.component.modal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import woowacourse.kanban.board.component.modal.action.ModalButtonActionFactory
import woowacourse.kanban.board.component.modal.input.TextInputState
import woowacourse.kanban.board.component.modal.section.ButtonSection
import woowacourse.kanban.board.component.modal.section.Footer
import woowacourse.kanban.board.component.modal.section.Header
import woowacourse.kanban.board.component.modal.section.TextInputSection
import woowacourse.kanban.board.component.modal.state.ModalState
import woowacourse.kanban.board.component.sample.ProfilePreviewData
import woowacourse.kanban.board.component.sample.TaskCardPreviewData
import woowacourse.kanban.board.model.identifier.UuidIdentifierGenerator
import woowacourse.kanban.board.model.taskcard.Description
import woowacourse.kanban.board.model.taskcard.Profile
import woowacourse.kanban.board.model.taskcard.Tag
import woowacourse.kanban.board.model.taskcard.Tags
import woowacourse.kanban.board.model.taskcard.TaskCard
import woowacourse.kanban.board.model.taskcard.TaskCardFactory
import woowacourse.kanban.board.model.taskcard.Title

@Composable
fun Modal(
    profiles: ImmutableList<Profile>,
    initialTask: TaskCard?,
    onClickClose: () -> Unit,
    onShowSnackbar: (String) -> Unit,
    onCreateTask: (TaskCard) -> Unit,
    onUpdateTask: (String, TaskCard) -> Unit,
    onDeleteTask: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    var modalState by remember(profiles, initialTask) {
        mutableStateOf(ModalState(profiles, initialTask))
    }
    val taskCardFactory = remember { TaskCardFactory(UuidIdentifierGenerator()) }
    val titleInputState = TextInputState(
        value = modalState.title,
        onChange = { modalState = modalState.copy(title = it) },
        isError = modalState.isTitleValid.not(),
    )
    val descriptionInputState = TextInputState(
        value = modalState.description,
        onChange = { modalState = modalState.copy(description = it) },
    )
    val tagsInputState = TextInputState(
        value = modalState.tags,
        onChange = { modalState = modalState.copy(tags = it) },
        isError = modalState.isTagsValid.not(),
    )
    val buildTaskCard: (String?) -> TaskCard = { id ->
        val title = Title(value = modalState.title)
        val description = Description(value = modalState.description)
        val tags = Tags(Tag.parseAll(modalState.tags).toImmutableList())
        val status = modalState.status
        val profile = modalState.profile

        if (id == null) {
            taskCardFactory.create(
                title = title,
                description = description,
                tags = tags,
                status = status,
                profile = profile,
            )
        } else {
            TaskCard(
                id = id,
                title = title,
                description = description,
                tags = tags,
                status = status,
                profile = profile,
            )
        }
    }
    val buttonActionFactory = ModalButtonActionFactory(
        modalState = modalState,
        initialTask = initialTask,
        onModalStateChange = { modalState = it },
        buildTaskCard = buildTaskCard,
        onShowSnackbar = onShowSnackbar,
        onCreateTask = onCreateTask,
        onUpdateTask = onUpdateTask,
        onDeleteTask = onDeleteTask,
    )

    Card(
        modifier = modifier
            .width(800.dp)
            .padding(50.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            Header(
                onClickClose = onClickClose,
            )
            HorizontalDivider()
            TextInputSection(
                titleInputState = titleInputState,
                descriptionInputState = descriptionInputState,
                tagsInputState = tagsInputState,
            )
            ButtonSection(
                state = modalState.status,
                currentProfile = modalState.profile,
                profiles = profiles,
                onStateClick = { nextStatus ->
                    buttonActionFactory.createStatusChangeAction(nextStatus).execute()
                },
                onProfileClick = { modalState = modalState.copy(profile = it) },
            )
            Footer(
                onClickClose = onClickClose,
                onClickTaskCreate = {
                    buttonActionFactory.createTaskCreateAction().execute()
                },
                onClickTaskDelete = {
                    buttonActionFactory.createTaskDeleteAction().execute()
                },
                onClickTaskModify = {
                    buttonActionFactory.createTaskModifyAction().execute()
                },
                isButtonEnabled = modalState.isSubmittable,
                isCreateMode = initialTask == null,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ModalPreview() {
    val profiles = ProfilePreviewData().values.toImmutableList()
    val task = TaskCardPreviewData().values.toImmutableList()[0]
    Modal(
        profiles = profiles,
        initialTask = task,
        onClickClose = {},
        onCreateTask = {},
        onUpdateTask = { _, _ -> },
        onDeleteTask = {},
        onShowSnackbar = {},
    )
}
