package woowacourse.kanban.board.component.taskmodal

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import woowacourse.kanban.board.component.ComponentText

@Composable
fun TaskModalHeader(
    title: @Composable () -> Unit,
    onClickClose: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .padding(horizontal = 24.dp)
            .fillMaxWidth(),
    ) {
        title()
        Icon(
            modifier = Modifier
                .clickable { onClickClose() },
            imageVector = Icons.Default.Close,
            contentDescription = "닫기",
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TaskModalHeaderPreview() {
    TaskModalHeader(
        title = {
            Text(
                text = ComponentText.CREATE_MODAL_HEADER_LABEL,
                fontSize = 20.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
            )
        },
        onClickClose = {},
    )
}
