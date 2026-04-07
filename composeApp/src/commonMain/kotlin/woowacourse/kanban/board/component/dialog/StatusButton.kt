package woowacourse.kanban.board.component.dialog

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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import woowacourse.kanban.board.constant.PRIMARY_BORDER
import woowacourse.kanban.board.constant.PRIMARY_SUB_TEXT
import woowacourse.kanban.board.constant.STATUS_BG_SELECTED
import woowacourse.kanban.board.constant.STATUS_BORDER_SELECTED
import woowacourse.kanban.board.constant.STATUS_TEXT_SELECTED
import woowacourse.kanban.board.model.Status

@Composable
fun StatusButton(status: Status, onClick: () -> Unit, modifier: Modifier = Modifier, isSelected: Boolean = false) {
    Box(
        modifier = modifier.clip(shape = RoundedCornerShape(10.dp))
            .border(
                width = 2.dp,
                color = Color(if (isSelected) STATUS_BORDER_SELECTED else PRIMARY_BORDER),
                shape = RoundedCornerShape(10.dp),
            )
            .background(Color(if (isSelected) STATUS_BG_SELECTED else 0xFFFFFFFF), shape = RoundedCornerShape(10.dp))
            .clickable(
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

@Preview(showBackground = true)
@Composable
private fun UnSelectedStatusButtonPreview() {
    StatusButton(status = Status.IN_PROGRESS, onClick = {})
}
