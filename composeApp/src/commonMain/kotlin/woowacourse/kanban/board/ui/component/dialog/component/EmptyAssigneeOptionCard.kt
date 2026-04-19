package woowacourse.kanban.board.ui.component.dialog.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun EmptyAssigneeOptionCard(
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TaskOptionCard(
        isSelected = isSelected,
        onClick = onClick,
        modifier = modifier,
    ) {
        Text(
            text = "없음",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .padding(23.dp),
        )
    }
}

private class EmptyAssigneeOptionCardParameterProvider : PreviewParameterProvider<Boolean> {
    override val values = sequenceOf(
        true,
        false,
    )
}

@Preview(showBackground = true)
@Composable
private fun EmptyAssigneeOptionCardPreview(@PreviewParameter(EmptyAssigneeOptionCardParameterProvider::class) isSelected: Boolean) {
    EmptyAssigneeOptionCard(
        isSelected = isSelected,
        onClick = { },
    )
}
