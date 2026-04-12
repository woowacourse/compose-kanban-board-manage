package woowacourse.kanban.dialog.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import woowacourse.kanban.core.design.Colors

@Composable
fun CreateFooterRow(
    onCancel: () -> Unit,
    onCreate: () -> Unit,
    isCreateError: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
    ) {
        FooterButton(
            modifier = Modifier,
            text = "취소",
            backgroundColor = Color.White,
            textColor = Colors.PrimaryText,
            onClick = onCancel,
        )
        Spacer(modifier = Modifier.width(12.dp))
        FooterButton(
            modifier = Modifier,
            text = "생성",
            textColor = Color.White,
            backgroundColor = if (isCreateError) Colors.ActionPrimaryDisabled else Colors.ActionPrimary,
            onClick = onCreate,
            enabled = !isCreateError,
        )
    }
}
