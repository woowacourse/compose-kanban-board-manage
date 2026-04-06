package woowacourse.kanban.board.component.taskmodal

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import woowacourse.kanban.board.Blue50
import woowacourse.kanban.board.Blue80
import woowacourse.kanban.board.Gray20
import woowacourse.kanban.board.Gray70
import woowacourse.kanban.board.component.extension.toText
import woowacourse.kanban.board.model.taskcard.Status

@Composable
fun TaskStatusButton(
    currentStatus: Status,
    myStatus: Status,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val backgroundColor = if (currentStatus == myStatus) Blue80 else Color.Transparent
    val borderColor = if (currentStatus == myStatus) Blue50 else Gray70
    val textColor = if (currentStatus == myStatus) Blue50 else Gray20
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .border(
                1.dp,
                color = borderColor,
                shape = RoundedCornerShape(10.dp),
            )
            .background(color = backgroundColor)
            .clickable { onClick() }
            .padding(horizontal = 30.dp, vertical = 14.dp),
    ) {
        Text(
            text = myStatus.toText(),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
            fontSize = 16.sp,
            color = textColor,
            fontWeight = FontWeight.Normal,
        )
    }
}
