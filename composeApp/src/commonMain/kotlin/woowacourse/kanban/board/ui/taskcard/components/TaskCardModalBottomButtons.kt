package woowacourse.kanban.board.ui.taskcard.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import woowacourse.kanban.board.ui.theme.DisabledContainer
import woowacourse.kanban.board.ui.theme.OnSurface
import woowacourse.kanban.board.ui.theme.OnSurfaceVariant

@Composable
fun TaskCardModalBottomButtons(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        content()
    }
}

@Preview
@Composable
private fun TaskCardModalBottomButtonsPreview() {
    TaskCardModalBottomButtons(
        modifier = Modifier,
    ) {
        RoundedBottomButtons(
            onClick = { },
            enabled = true,
            text = "생성",
            colors = ButtonDefaults.buttonColors(
                containerColor = OnSurface,
                contentColor = OnSurfaceVariant,
                disabledContainerColor = DisabledContainer,
                disabledContentColor = OnSurfaceVariant,
            ),
            modifier = Modifier,
        )
    }
}
