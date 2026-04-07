package woowacourse.kanban.board.ui.dialog.section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kanbanboard.composeapp.generated.resources.Res
import kanbanboard.composeapp.generated.resources.create_dialog_close
import kanbanboard.composeapp.generated.resources.create_dialog_title
import kanbanboard.composeapp.generated.resources.edit_dialog_title
import org.jetbrains.compose.resources.stringResource
import woowacourse.kanban.board.ui.theme.Gray900

@Composable
fun Header(modifier: Modifier = Modifier, isEditMode: Boolean, onDismiss: () -> Unit) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(24.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = stringResource(if (isEditMode) Res.string.edit_dialog_title else Res.string.create_dialog_title),
            fontSize = 28.sp,
            fontWeight = FontWeight.SemiBold,
            color = Gray900,
        )
        IconButton(
            onClick = onDismiss,
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = stringResource(Res.string.create_dialog_close),
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun HeaderPreview() {
    Header(
        onDismiss = {},
        isEditMode = true,
    )
}
