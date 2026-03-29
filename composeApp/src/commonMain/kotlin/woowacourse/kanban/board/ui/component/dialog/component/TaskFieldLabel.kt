package woowacourse.kanban.board.ui.component.dialog.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.sp

@Composable
fun TaskFieldLabel(
    label: String,
    modifier: Modifier = Modifier,
    isRequired: Boolean = false,
) {
    val text = if (isRequired) "$label *" else label

    Text(
        text = text,
        fontSize = 14.sp,
        modifier = modifier,
    )
}

private class TaskFieldLabelParameterProvider : PreviewParameterProvider<Pair<String, Boolean>> {
    override val values = sequenceOf(
        "제목" to true,
        "설명" to false,
    )
}

@Preview(showBackground = true)
@Composable
private fun TaskFieldLabelPreview(@PreviewParameter(TaskFieldLabelParameterProvider::class) params: Pair<String, Boolean>) {
    TaskFieldLabel(
        label = params.first,
        isRequired = params.second,
    )
}
