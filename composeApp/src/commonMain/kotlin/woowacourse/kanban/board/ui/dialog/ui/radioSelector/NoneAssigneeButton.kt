package woowacourse.kanban.board.ui.dialog.ui.radioSelector

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import woowacourse.kanban.Colors

@Composable
fun NoneAssigneeButton(
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val shape = RoundedCornerShape(10.dp)

    Box(
        modifier = modifier.clip(shape)
            .then(
                if (isSelected)
                    Modifier.border(
                        width = 2.dp,
                        color = Colors.AssigneeSelectedBorder,
                        shape = shape,
                    ).background(color = Colors.AssigneeSelectedBg)
                else
                    Modifier.border(
                        width = 2.dp,
                        color = Colors.PrimaryBorder,
                        shape = shape,
                    ),
            )
            .clickable(onClick = onClick)
            .semantics { selected = isSelected },
    ) {
        Text(
            "없음",
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 20.dp),
            fontWeight = FontWeight.W500,
            fontSize = 14.sp,
            color = Colors.PrimarySubText,
        )
    }
}
