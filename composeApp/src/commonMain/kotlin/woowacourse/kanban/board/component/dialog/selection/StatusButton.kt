package woowacourse.kanban.board.component.dialog.selection

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import woowacourse.kanban.board.theme.PRIMARY_BORDER
import woowacourse.kanban.board.theme.PRIMARY_SUB_TEXT
import woowacourse.kanban.board.theme.STATUS_BG_SELECTED
import woowacourse.kanban.board.theme.STATUS_BORDER_SELECTED
import woowacourse.kanban.board.theme.STATUS_TEXT_SELECTED
import woowacourse.kanban.board.domain.Status

@Composable
fun StatusButton(status: Status, onClick: () -> Unit, modifier: Modifier = Modifier, isSelected: Boolean = false) {
    val unSelectedModifier = modifier.border(width = 2.dp, color = Color(PRIMARY_BORDER), shape = RoundedCornerShape(10.dp))
    val selectedModifier = modifier.border(width = 2.dp, color = Color(STATUS_BORDER_SELECTED), shape = RoundedCornerShape(10.dp))
        .background(Color(STATUS_BG_SELECTED), shape = RoundedCornerShape(10.dp))
    Box(
        modifier = if (!isSelected) {
            unSelectedModifier
        } else {
            selectedModifier
        }.clickable(
            onClick = onClick,
        ),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            status.state,
            fontWeight = FontWeight.W500,
            fontSize = 16.sp,
            modifier = Modifier.padding(vertical = 14.dp),
            color = if (!isSelected) {
                Color(PRIMARY_SUB_TEXT)
            } else {
                Color(STATUS_TEXT_SELECTED)
            },
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SelectedStatusButtonPreview() {
    StatusButton(status = Status.TODO, isSelected = true, onClick = {})
}
