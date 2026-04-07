package woowacourse.kanban.board.ui.dialog.section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kanbanboard.composeapp.generated.resources.Res
import kanbanboard.composeapp.generated.resources.button_edit
import org.jetbrains.compose.resources.stringResource
import woowacourse.kanban.board.ui.component.CancelButton
import woowacourse.kanban.board.ui.component.ConfirmButton
import woowacourse.kanban.board.ui.component.DeleteButton

@Composable
fun EditFooter(
    modifier: Modifier = Modifier,
    onClickCancel: () -> Unit = {},
    onClickDelete: () -> Unit = {},
    onClickEdit: () -> Unit = {},
    enabled: Boolean = true,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(24.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.End),
    ) {
        CancelButton(onClickCancel = onClickCancel)
        DeleteButton(onClickDelete = onClickDelete)
        ConfirmButton(
            onClickConfirm = onClickEdit,
            content = {
                Text(
                    text = stringResource(Res.string.button_edit),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W500,
                )
            },
            enabled = enabled,
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun EditFooterPreview() {
    EditFooter(
        onClickCancel = {},
        onClickDelete = {},
        onClickEdit = {},
    )
}
