package woowacourse.kanban.board.ui.component.board

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import woowacourse.kanban.board.ui.KanbanTypography

@Composable
fun NewTaskButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Blue,
            contentColor = Color.White,
        ),
        shape = RoundedCornerShape(10.dp),
        contentPadding = PaddingValues(vertical = 7.5.dp, horizontal = 10.dp),
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "추가 버튼",
            modifier = Modifier
                .size(20.dp),
        )

        Spacer(modifier = Modifier.width(2.dp))

        Text(
            text = text,
            style = KanbanTypography.label16Regular,
            maxLines = 1,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun NewTaskButtonPreview() {
    NewTaskButton(
        text = "새 태스크 생성",
        onClick = {},
    )
}
