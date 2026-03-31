package woowacourse.kanban.board.ui.dialog.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import woowacourse.kanban.Colors

@Preview(showBackground = true)
@Composable
fun DialogBar(modifier: Modifier = Modifier) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {
        Text("새 태스크 생성", fontWeight = FontWeight.W600, fontSize = 20.sp, color = Colors.PrimaryText)
        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "닫기 버튼",
            modifier = Modifier.size(10.dp),
            tint = Colors.IconSecondary,
        )
    }
}
