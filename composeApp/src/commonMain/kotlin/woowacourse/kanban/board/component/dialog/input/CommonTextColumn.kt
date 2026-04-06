package woowacourse.kanban.board.component.dialog.input

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import woowacourse.kanban.board.theme.TEXT_FIELD_ERROR

@Composable
fun CommonTextColumn(
    title: String,
    content: String,
    onValueChange: (String) -> Unit,
    placeholderText: String,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    isSupportingText: Boolean = false,
) {
    Column {
        Text(title)
        OutlinedTextField(
            modifier = modifier,
            value = content,
            onValueChange = { onValueChange(it) },
            placeholder = {
                if (isError) Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        "이건,,,,올바르지 않은 형식입니다,,,,,,,,,",
                        fontWeight = FontWeight.W400,
                        fontSize = 12.sp,
                        color = Color(TEXT_FIELD_ERROR),
                    )
                    Icon(
                        imageVector = Icons.Default.Error,
                        contentDescription = "오류 아이콘",
                        tint = Color(TEXT_FIELD_ERROR),
                        modifier = Modifier.size(20.dp),
                    )
                } else {
                    Text(placeholderText)
                }
            },
            isError = isError,
            supportingText = if (isSupportingText) {
                { if (isError) Text("태그 형식이 올바르지 않습니다.") else Text("5자 이내의 태그를 최대 5개까지 등록할 수 있습니다.") }
            } else {
                null
            },
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TitleCommonTextColumnPreview() {
    CommonTextColumn(
        modifier = Modifier.fillMaxWidth(),
        title = "제목 *",
        content = "",
        onValueChange = {},
        isError = false,
        placeholderText = "태스크 제목을 입력하세요",
    )
}

@Preview(showBackground = true)
@Composable
private fun ContentCommonTextColumnPreview() {
    CommonTextColumn(
        modifier = Modifier.fillMaxWidth().height(116.dp),
        title = "설명",
        content = "",
        onValueChange = {},
        placeholderText = "태스크에 대한 자세한 설명을 입력하세요",
    )
}

@Preview(showBackground = true)
@Composable
private fun TagsCommonTextColumnPreview() {
    CommonTextColumn(
        modifier = Modifier.fillMaxWidth(),
        title = "태그 *",
        content = "",
        onValueChange = {},
        isError = false,
        placeholderText = "태그를 쉼표로 구분하여 입력하세요 (예: 버그, 긴급)",
        isSupportingText = true,
    )
}

@Preview(showBackground = true)
@Composable
private fun TagsErrorCommonTextColumnPreview() {
    CommonTextColumn(
        modifier = Modifier.fillMaxWidth(),
        title = "태그 *",
        content = "",
        onValueChange = {},
        isError = true,
        placeholderText = "태그를 쉼표로 구분하여 입력하세요 (예: 버그, 긴급)",
        isSupportingText = true,
    )
}
