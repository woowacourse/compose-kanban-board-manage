package woowacourse.kanban.board.component.taskmodal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import woowacourse.kanban.board.component.sample.ProfilePreviewData
import woowacourse.kanban.board.component.workspace.ModalState
import woowacourse.kanban.board.component.workspace.rememberModalState
import woowacourse.kanban.board.model.taskmodal.TextInputState
import woowacourse.kanban.board.model.taskcard.Assignee
import woowacourse.kanban.board.model.taskcard.TaskCardData

@Composable
fun TaskModal(
    assignees: ImmutableList<Assignee>,
    onClickClose: () -> Unit,
    title: @Composable () -> Unit,
    footerButtonSection: @Composable () -> Unit,
    modalState: ModalState,
    modifier: Modifier = Modifier,
    data: TaskCardData? = null,
) {
    LaunchedEffect(data) {
        if (data != null) modalState.loadData(data)
        else modalState.clear()
    }
    val titleInputState = TextInputState(
        value = modalState.title,
        onChange = { modalState.title = it },
        isError = modalState.isTaskTitleValid.not(),
    )
    val descriptionInputState = TextInputState(
        value = modalState.description,
        onChange = { modalState.description = it },
    )
    val tagsInputState = TextInputState(
        value = modalState.tags,
        onChange = { modalState.tags = it },
        isError = modalState.isTaskTagsValid.not(),
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
            TaskModalHeader(
                title = title,
                onClickClose = onClickClose,
            )
            HorizontalDivider()
            TaskModalTextInputSection(
                titleInputState = titleInputState,
                descriptionInputState = descriptionInputState,
                tagsInputState = tagsInputState,
            )
            TaskModalButtonSection(
                assignees = assignees,
                modalState = modalState
            )
            TaskModalFooter(
                onClickClose = onClickClose,
                footerButtonSection = footerButtonSection,
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 1000, heightDp = 1000)
@Composable
private fun CreateTaskModalPreview() {
    val profiles = ProfilePreviewData().values.toImmutableList()
    TaskModal(
        assignees = profiles,
        onClickClose = {},
        title = {},
        footerButtonSection = {},
        modalState = rememberModalState(profiles)
    )
}

@Preview(showBackground = true, widthDp = 1000, heightDp = 1000)
@Composable
private fun EditTaskModalPreview() {
    val profiles = ProfilePreviewData().values.toImmutableList()
    TaskModal(
        assignees = profiles,
        title = {},
        footerButtonSection = {},
        onClickClose = {},
        modalState = rememberModalState(profiles)
    )
}
