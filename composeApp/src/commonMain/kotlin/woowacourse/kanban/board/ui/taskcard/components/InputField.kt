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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
    LabelText("제목 *")
    TextInputField(
        value = title,
        onValueChange = onValueChange,
        placeholder = "태스크 제목을 입력하세요",
        singleLine = true,
        modifier = Modifier.fillMaxWidth(),
        borderColor = if (isError) OnError else Outline,
        isError = isError,
        infoContent = if (titleError == TitleError.BLANK) "제목을 입력해주세요." else "",
        infoTextColor = OnError,
    )
}

@Composable
fun ContentInputField(content: String, onValueChange: (String) -> Unit) {
    LabelText("설명")
    TextInputField(
        value = content,
        onValueChange = onValueChange,
        borderColor = Outline,
        placeholder = "태스크에 대한 자세한 설명을 입력하세요",
        singleLine = false,
        modifier = Modifier.heightIn(min = 100.dp),
    )
}

@Composable
fun TagsInputField(tags: String, tagError: TagError, onValueChange: (String) -> Unit) {
    val isError = tagError != TagError.NONE
    LabelText("태그")
    TextInputField(
        value = tags,
        onValueChange = onValueChange,
        placeholder = "태그를 쉼표로 구분하여 입력하세요 (예: 버그, 긴급)",
        singleLine = true,
        borderColor = if (isError) OnError else Outline,
        modifier = Modifier.fillMaxWidth(),
        isError = isError,
        infoContent = when (tagError) {
            TagError.INVALID_FORMAT -> "태그 형식이 올바르지 않습니다."
            TagError.TOO_LONG, TagError.TOO_MANY -> "태그는 5자 이내로 5개까지만 등록할 수 있습니다."
            TagError.NONE -> "5자 이내의 태그를 최대 5개까지 등록할 수 있습니다."
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
