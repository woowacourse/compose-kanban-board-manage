package woowacourse.kanban.board.ui.dialog.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import woowacourse.kanban.Colors

@Composable
fun FooterRow(
    onCancel: () -> Unit,
    isCreateError: Boolean,
    modifier: Modifier = Modifier,
    onCreate: (() -> Unit)? = null,
    onUpdate: (() -> Unit)? = null,
    onDelete: (() -> Unit)? = null,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.End),
    ) {
        FooterButton(
            modifier = Modifier,
            text = "취소",
            backgroundColor = Color.White,
            textColor = Colors.PrimaryText,
            onClick = onCancel,
        )
        if (onDelete != null)
            FooterButton(
                modifier = Modifier,
                text = "삭제",
                textColor = Color.White,
                backgroundColor = Colors.DialogDeleteButtonBg,
                onClick = onDelete,
            )
        if (onUpdate != null)
            FooterButton(
                modifier = Modifier,
                text = "수정",
                textColor = Color.White,
                backgroundColor = if (isCreateError) Colors.ActionPrimaryDisabled else Colors.ActionPrimary,
                onClick = onUpdate,
                enabled = !isCreateError,
            )
        if (onCreate != null)
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

@Composable
fun FooterButton(
    text: String,
    backgroundColor: Color,
    textColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(
                width = 68.dp,
                height = 44.dp,
            )
            .clip(shape = RoundedCornerShape(10.dp))
            .background(backgroundColor)
            .clickable(
                onClick = onClick,
                enabled = enabled,
            ),

    ) {
        Text(
            text,
            color = textColor,
        )
    }
}
