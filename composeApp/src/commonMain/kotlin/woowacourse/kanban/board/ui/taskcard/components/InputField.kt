package woowacourse.kanban.board.ui.taskcard.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kanbanboard.composeapp.generated.resources.Res
import kanbanboard.composeapp.generated.resources.content_label
import kanbanboard.composeapp.generated.resources.content_place_holder
import kanbanboard.composeapp.generated.resources.invalid_format_tag
import kanbanboard.composeapp.generated.resources.tag_info_message
import kanbanboard.composeapp.generated.resources.tag_label
import kanbanboard.composeapp.generated.resources.tag_place_holder
import kanbanboard.composeapp.generated.resources.title_blank_error
import kanbanboard.composeapp.generated.resources.title_label
import kanbanboard.composeapp.generated.resources.title_place_holder
import kanbanboard.composeapp.generated.resources.too_long_or_too_many_tag
import org.jetbrains.compose.resources.stringResource
import woowacourse.kanban.board.exception.TagError
import woowacourse.kanban.board.exception.TitleError
import woowacourse.kanban.board.ui.theme.OnError
import woowacourse.kanban.board.ui.theme.Outline
import woowacourse.kanban.board.ui.theme.PrimaryContainer
import woowacourse.kanban.board.ui.theme.TextFieldPlaceholder
import woowacourse.kanban.board.ui.theme.TextTertiary

@Composable
fun TitleInputField(title: String, titleError: TitleError, onValueChange: (String) -> Unit) {
    val isError = titleError != TitleError.NONE
    LabelText(stringResource(Res.string.title_label))
    TextInputField(
        value = title,
        onValueChange = onValueChange,
        placeholder = stringResource(Res.string.title_place_holder),
        singleLine = true,
        modifier = Modifier.fillMaxWidth(),
        borderColor = if (isError) OnError else Outline,
        isError = isError,
        infoContent = if (titleError == TitleError.BLANK) stringResource(Res.string.title_blank_error) else "",
        infoTextColor = OnError,
    )
}

@Composable
fun ContentInputField(content: String, onValueChange: (String) -> Unit) {
    LabelText(stringResource(Res.string.content_label))
    TextInputField(
        value = content,
        onValueChange = onValueChange,
        borderColor = Outline,
        placeholder = stringResource(Res.string.content_place_holder),
        singleLine = false,
        modifier = Modifier.heightIn(min = 100.dp),
    )
}

@Composable
fun TagsInputField(tags: String, tagError: TagError, onValueChange: (String) -> Unit) {
    val isError = tagError != TagError.NONE
    LabelText(stringResource(Res.string.tag_label))
    TextInputField(
        value = tags,
        onValueChange = onValueChange,
        placeholder = stringResource(Res.string.tag_place_holder),
        singleLine = true,
        borderColor = if (isError) OnError else Outline,
        modifier = Modifier.fillMaxWidth(),
        isError = isError,
        infoContent = when (tagError) {
            TagError.INVALID_FORMAT -> stringResource(Res.string.invalid_format_tag)
            TagError.TOO_LONG, TagError.TOO_MANY -> stringResource(Res.string.too_long_or_too_many_tag)
            TagError.NONE -> stringResource(Res.string.tag_info_message)
        },
        infoTextColor = if (isError) OnError else TextTertiary,
    )
}

@Composable
private fun TextInputField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    singleLine: Boolean,
    borderColor: Color,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    infoContent: String = "",
    infoTextColor: Color = TextTertiary,
) {
    Column {
        TextField(
            modifier = modifier
                .fillMaxWidth()
                .border(1.dp, borderColor, RoundedCornerShape(8.dp)),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = PrimaryContainer,
                unfocusedContainerColor = PrimaryContainer,
                disabledContainerColor = PrimaryContainer,
                focusedIndicatorColor = PrimaryContainer,
                unfocusedIndicatorColor = PrimaryContainer,
            ),
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    text = placeholder,
                    color = TextFieldPlaceholder,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W400,
                )
            },
            singleLine = singleLine,
            trailingIcon = {
                if (isError) Icon(
                    Icons.Default.Error,
                    tint = OnError,
                    contentDescription = "경고",
                )
            },
        )
        if (isError) {
            Text(
                modifier = Modifier.padding(start = 16.dp, top = 4.dp),
                text = infoContent,
                fontWeight = FontWeight.W400,
                fontSize = 12.sp,
                color = infoTextColor,
            )
        }
    }
}

@Preview
@Composable
private fun TitleInputFieldPreview() {
    TitleInputField(title = "제목", titleError = TitleError.NONE, onValueChange = {})
}

@Preview
@Composable
private fun ContentInputFieldPreview() {
    ContentInputField(content = "내용", onValueChange = {})
}

@Preview
@Composable
private fun TagsInputFieldPreview() {
    TagsInputField(tags = "태그1, 태그2", tagError = TagError.NONE, onValueChange = {})
}
