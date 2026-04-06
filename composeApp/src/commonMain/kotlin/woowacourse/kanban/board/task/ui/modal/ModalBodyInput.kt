package woowacourse.kanban.board.task.ui.modal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kanbanboard.composeapp.generated.resources.Res
import kanbanboard.composeapp.generated.resources.error_empty_title
import kanbanboard.composeapp.generated.resources.error_invalid_tag_format
import kanbanboard.composeapp.generated.resources.error_max_tags_format
import kanbanboard.composeapp.generated.resources.label_title
import kanbanboard.composeapp.generated.resources.place_holder_input_title
import org.jetbrains.compose.resources.stringResource
import woowacourse.kanban.board.task.domain.KanbanCardError

@Composable
fun ModalBodyInput(
    title: String,
    placeholder: String,
    maxLines: Int,
    state: String,
    isValid: Boolean,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    errorType: KanbanCardError? = null,
    supportingMessage: String? = null,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
    ) {
        ModalInputTitle(title)
        ModalInputField(
            value = state,
            onValueChange = onValueChange,
            isValid = isValid,
            placeHolder = placeholder,
            maxLines = maxLines,
            supportingMessage = supportingMessage,
            errorMessage = when (errorType) {
                KanbanCardError.TITLE_FORMAT -> stringResource(Res.string.error_empty_title)
                KanbanCardError.TAG_FORMAT -> stringResource(Res.string.error_invalid_tag_format)
                KanbanCardError.TAG_SIZE -> stringResource(Res.string.error_max_tags_format)
                null -> null
            },
        )
    }
}

@Preview
@Composable
private fun ModalBodyInputPreview() {
    Box(
        modifier = Modifier
            .background(Color.White)
            .padding(10.dp),
    ) {
        var state by remember { mutableStateOf("") }
        ModalBodyInput(
            title = stringResource(Res.string.label_title),
            placeholder = stringResource(Res.string.place_holder_input_title),
            maxLines = 1,
            state = state,
            onValueChange = {
                state = it
            },
            isValid = false,
            errorType = KanbanCardError.TITLE_FORMAT,
        )
    }
}
