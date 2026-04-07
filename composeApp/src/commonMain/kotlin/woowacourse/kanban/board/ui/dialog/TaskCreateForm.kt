package woowacourse.kanban.board.ui.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import woowacourse.kanban.board.domain.model.Assignee
import woowacourse.kanban.board.domain.model.Status
import woowacourse.kanban.board.domain.model.Task
import woowacourse.kanban.board.ui.dialog.section.AssigneeSection
import woowacourse.kanban.board.ui.dialog.section.DescriptionSection
import woowacourse.kanban.board.ui.dialog.section.EditFooter
import woowacourse.kanban.board.ui.dialog.section.Footer
import woowacourse.kanban.board.ui.dialog.section.Header
import woowacourse.kanban.board.ui.dialog.section.StatusSection
import woowacourse.kanban.board.ui.dialog.section.TagSection
import woowacourse.kanban.board.ui.dialog.section.TitleSection

@Composable
fun TaskCreateForm(
    onDismiss: () -> Unit,
    assignees: List<Assignee>,
    onClickCreate: (title: String, content: String, tags: List<String>, status: Status, assignee: Assignee?) -> Unit,
    onClickDelete: () -> Unit,
    onClickEdit: (title: String, content: String, tags: List<String>, status: Status, assignee: Assignee?) -> Unit,
    modifier: Modifier = Modifier,
    originTask: Task? = null,
) {
    val formState = remember { TaskCreateFormState(assignees, originTask) }

    Column(
        modifier = modifier,
    ) {
        Header(
            onDismiss = onDismiss,
            isEditMode = formState.isEditingMode,
        )
        HorizontalDivider()
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),

        ) {
            TitleSection(
                value = formState.title,
                onTitleChange = {
                    formState.updateTitle(it)
                },
                validation = formState.titleValidation,
            )

            DescriptionSection(
                value = formState.description,
                onContentChange = {
                    formState.updateContent(it)
                },
            )

            TagSection(
                value = formState.tag,
                onTagChange = {
                    formState.updateTag(it)
                },
                validation = formState.tagValidation,
            )

            StatusSection(
                selectedStatus = formState.selectedStatus,
                onStatusChange = {
                    formState.updateStatus(it)
                },
            )

            AssigneeSection(
                assignees = formState.assignees,
                selectedAssignee = formState.selectedAssignee,
                requiredAssignee = formState.requiredAssignee,
                onUserChange = {
                    formState.updateAssignee(it)
                },
            )
        }
        HorizontalDivider()
        if (formState.isEditingMode) {
            EditFooter(
                onClickCancel = onDismiss,
                onClickDelete = {
                    onClickDelete()
                },
                onClickEdit = {
                    onClickEdit(
                        formState.title,
                        formState.description,
                        formState.tag.split(",").filter { it.isNotEmpty() }.map { it.trim() },
                        formState.selectedStatus,
                        formState.selectedAssignee,
                    )
                },
                enabled = formState.canCreate,
            )
        } else {
            Footer(
                onClickCancel = onDismiss,
                onClickCreate = {
                    onClickCreate(
                        formState.title,
                        formState.description,
                        formState.tag.split(",").filter { it.isNotEmpty() }.map { it.trim() },
                        formState.selectedStatus,
                        formState.selectedAssignee,
                    )
                },
                enabled = formState.canCreate,
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun TaskCreateFormPreview() {
    TaskCreateForm(
        onDismiss = {},
        assignees = listOf(Assignee("다이노"), Assignee("다이노소어"), Assignee("우우우")),
        onClickCreate = { _, _, _, _, _ -> },
        onClickDelete = {},
        onClickEdit = { _, _, _, _, _ -> },
    )
}
