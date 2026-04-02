package woowacourse.kanban.board.ui.taskcard.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun RoundedBottomButtons(
    onClick: () -> Unit,
    enabled: Boolean,
    text: String,
    shapes: RoundedCornerShape = RoundedCornerShape(10.dp),
    colors: ButtonColors,
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = { onClick() },
        enabled = enabled,
        shape = shapes,
        colors = colors,
        modifier = modifier,
    ) {
        Text(text = text, textAlign = TextAlign.Center)
    }
}
