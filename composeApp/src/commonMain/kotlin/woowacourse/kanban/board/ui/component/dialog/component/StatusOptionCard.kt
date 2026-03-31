package woowacourse.kanban.board.ui.component.dialog.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import woowacourse.kanban.board.domain.Status
import woowacourse.kanban.board.ui.toTitle

@Composable
fun StatusOptionCard(
    status: Status,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val text = status.toTitle()

    TaskOptionCard(
        isSelected = isSelected,
        onClick = onClick,
        modifier = modifier,
    ) {
        Text(
            text = text,
            color = if (isSelected) Color.Blue else Black,
            fontSize = 16.sp,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(vertical = 14.dp, horizontal = 16.dp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun StatusOptionCardPreview() {
    var isSelected by remember { mutableStateOf(false) }

    StatusOptionCard(
        status = Status.TO_DO,
        isSelected = isSelected,
        onClick = { isSelected = !isSelected },
    )
}
