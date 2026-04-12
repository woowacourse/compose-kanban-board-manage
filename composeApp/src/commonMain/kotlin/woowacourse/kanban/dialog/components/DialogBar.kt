package woowacourse.kanban.dialog.components

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
import woowacourse.kanban.core.design.Colors

@Composable
fun DialogBar(text: String, modifier: Modifier = Modifier) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {
        Text(text, fontWeight = FontWeight.W600, fontSize = 20.sp, color = Colors.PrimaryText)
        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "닫기 버튼",
            modifier = Modifier.size(10.dp),
            tint = Colors.IconSecondary,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun NewDialogBarPreview() {
    DialogBar("새 태스크 생성")
}

@Preview(showBackground = true)
@Composable
fun EditDialogBarPreview() {
    DialogBar("기존 태스크 수정")
}
