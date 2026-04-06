package woowacourse.kanban.board.component.taskmodal

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kanbanboard.composeapp.generated.resources.Res
import kanbanboard.composeapp.generated.resources.icon
import org.jetbrains.compose.resources.painterResource
import woowacourse.kanban.board.Gray20
import woowacourse.kanban.board.Gray50
import woowacourse.kanban.board.Gray70
import woowacourse.kanban.board.Red50
import woowacourse.kanban.board.component.extension.toErrorText
import woowacourse.kanban.board.component.extension.toLabel
import woowacourse.kanban.board.component.extension.toPlaceholder
import woowacourse.kanban.board.component.extension.toSupportingText
import woowacourse.kanban.board.model.taskmodal.TextInputValue
import woowacourse.kanban.board.model.taskcard.TaskTag
import woowacourse.kanban.board.model.taskcard.TaskTags
import woowacourse.kanban.board.model.taskcard.TaskTitle

@Composable
fun TaskModalTextInput(
    textInputValue: TextInputValue,
    value: String,
    onTextChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    singleLine: Boolean = true,
    isError: Boolean = false,
) {
    val borderColor = if (isError) Red50 else Gray70
    val textColor = if (isError) Red50 else Gray20

    val labelText = textInputValue.toLabel()
    val placeholderText = textInputValue.toPlaceholder()
    val errorText = textInputValue.toErrorText()
    val supportingText = textInputValue.toSupportingText()

    Column(
        modifier = modifier,
    ) {
        Text(
            text = labelText,
            fontSize = 14.sp,
            color = Gray20,
            fontWeight = FontWeight.Bold,
        )
        OutlinedTextField(
            value = value,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            singleLine = singleLine,
            placeholder = {
                Text(
                    text = placeholderText,
                    color = Gray50,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                )
            },
            trailingIcon = {
                if (isError) {
                    Image(
                        painter = painterResource(Res.drawable.icon),
                        contentDescription = "에러 아이콘",
                        modifier = Modifier.size(20.dp),
                    )
                }
            },
            supportingText = {
                if (isError && errorText != null) {
                    Text(
                        text = errorText,
                        color = textColor,
                    )
                } else if (supportingText != null) {
                    Text(text = supportingText)
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = borderColor,
                focusedBorderColor = borderColor,
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                unfocusedTextColor = textColor,
                focusedTextColor = textColor,
            ),
            onValueChange = onTextChange,
        )
    }
}

@Preview(showBackground = true, heightDp = 100)
@Composable
private fun TitleInputEmptyPreviewTaskModal() {
    var title by remember { mutableStateOf("") }

    val isTaskTitleValid by remember {
        derivedStateOf {
            TaskTitle.isTitleValid(title)
        }
    }

    Column {
        TaskModalTextInput(
            textInputValue = TextInputValue.TITLE,
            value = title,
            onTextChange = { title = it },
            isError = isTaskTitleValid.not(),
        )
    }
}

@Preview(showBackground = true, heightDp = 100)
@Composable
private fun TitleInputPreviewTaskModal() {
    var title by remember { mutableStateOf("제목") }

    val isTaskTitleValid by remember {
        derivedStateOf {
            TaskTitle.isTitleValid(title)
        }
    }

    Column {
        TaskModalTextInput(
            textInputValue = TextInputValue.TITLE,
            value = title,
            onTextChange = { title = it },
            isError = isTaskTitleValid.not(),
        )
    }
}

@Preview(showBackground = true, heightDp = 100)
@Composable
private fun DescriptionInputEmptyPreviewTaskModal() {
    var description by remember { mutableStateOf("") }

    Column {
        TaskModalTextInput(
            textInputValue = TextInputValue.DESCRIPTION,
            value = description,
            onTextChange = { description = it },
            isError = false,
        )
    }
}

@Preview(showBackground = true, heightDp = 100)
@Composable
private fun TagsInputEmptyPreviewTaskModal() {
    var tags by remember { mutableStateOf("") }

    Column {
        TaskModalTextInput(
            textInputValue = TextInputValue.TAGS,
            value = tags,
            onTextChange = { tags = it },
            isError = false,
        )
    }
}

@Preview(showBackground = true, heightDp = 100)
@Composable
private fun InvalidTagsInputPreviewTaskModal() {
    var tags by remember { mutableStateOf("태그") }

    val isTagValid by remember {
        derivedStateOf {
            val extractedTaskTags = TaskTag.extractedTags(tags)
            TaskTags.isTagsValid(extractedTaskTags)
        }
    }

    Column {
        TaskModalTextInput(
            textInputValue = TextInputValue.TAGS,
            value = tags,
            onTextChange = { tags = it },
            isError = isTagValid.not(),
        )
    }
}

@Preview(showBackground = true, heightDp = 100)
@Composable
private fun ValidTagsInputPreviewTaskModal() {

    var tags by remember { mutableStateOf("태그1") }

    val isTagValid by remember {
        derivedStateOf {
            val extractedTaskTags = TaskTag.extractedTags(tags)
            TaskTags.isTagsValid(extractedTaskTags)
        }
    }

    Column {
        TaskModalTextInput(
            textInputValue = TextInputValue.TAGS,
            value = tags,
            onTextChange = { tags = it },
            isError = isTagValid.not(),
        )
    }
}
