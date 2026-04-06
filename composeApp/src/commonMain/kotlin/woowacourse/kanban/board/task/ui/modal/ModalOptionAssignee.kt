package woowacourse.kanban.board.task.ui.modal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ModalOptionAssignee(modifier: Modifier = Modifier, name: String, isExist: Boolean) {
    Row(
        modifier = modifier.fillMaxWidth()
            .padding(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (isExist) {
            Icon(
                modifier = Modifier.size(24.dp),
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "담당자 아이콘",
                tint = Color.Gray,
            )
        }

        Text(
            text = name,
            fontSize = 14.sp,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

private data class ModalOptionAssigneePreviewCase(val name: String, val isExist: Boolean)

private class ModalOptionAssigneePreviewProvider : PreviewParameterProvider<ModalOptionAssigneePreviewCase> {
    override val values = sequenceOf(
        ModalOptionAssigneePreviewCase(
            name = "없음",
            isExist = false,
        ),
        ModalOptionAssigneePreviewCase(
            name = "다이노,",
            isExist = true,
        ),
    )
}

@Preview
@Composable
private fun ModalOptionAssigneePreview(
    @PreviewParameter(ModalOptionAssigneePreviewProvider::class) previewCase: ModalOptionAssigneePreviewCase,
) {
    ModalOptionAssignee(
        modifier = Modifier.background(Color.White),
        name = previewCase.name,
        isExist = previewCase.isExist,
    )
}
