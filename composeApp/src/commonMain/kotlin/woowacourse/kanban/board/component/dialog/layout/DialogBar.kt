package woowacourse.kanban.board.component.dialog.layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import woowacourse.kanban.board.theme.CLOSE_ICON_TINT
import woowacourse.kanban.board.theme.HEADER_TEXT

@Composable
fun DialogBar(modifier: Modifier = Modifier,
              title: String,
              onClick: () -> Unit) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {
        Text(text = title,
            fontWeight = FontWeight.W600,
            fontSize = 20.sp,
            color = Color(HEADER_TEXT))
        IconButton(
            modifier = Modifier.size(36.dp),
            onClick = onClick,
        ) {
            Icon(imageVector = Icons.Default.Close, contentDescription = "닫기 버튼", tint = Color(CLOSE_ICON_TINT))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DialogBarPreview() {
    DialogBar(title = "새 태스크 생성",onClick = {})
}
