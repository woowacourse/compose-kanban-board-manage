package woowacourse.kanban.board.component.dialog.action

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun FooterRow(actions: List<FooterAction>, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
    ) {
        actions.forEachIndexed { index, action ->
            FooterButton(
                text = action.text,
                backgroundColor = action.backgroundColor,
                textColor = action.textColor,
                onClick = action.onClick,
                enabled = action.enabled,
            )

            if (index != actions.lastIndex) {
                Spacer(modifier = Modifier.width(12.dp))
            }
        }
    }
}

@Composable
private fun FooterButton(
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
            .background(backgroundColor).clickable(onClick = onClick, enabled = enabled),
    ) {
        Text(text, color = textColor)
    }
}

@Preview(showBackground = true)
@Composable
private fun CreateEnableFooterRowPreview() {
    val isCreateError = false
    FooterRow(
        actions = listOf(
            cancelFooterAction(onClick = {}),
            createFooterAction(onClick = {}, isEnabled = !isCreateError),
        ),
    )
}
