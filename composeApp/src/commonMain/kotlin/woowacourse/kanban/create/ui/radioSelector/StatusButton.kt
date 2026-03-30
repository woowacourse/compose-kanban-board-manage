package woowacourse.kanban.create.ui.radioSelector

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
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import woowacourse.kanban.Colors
import woowacourse.kanban.domain.TaskStatus

private val TaskStatus.displayName: String
    get() = when (this) {
        TaskStatus.TO_DO -> "To Do"
        TaskStatus.IN_PROGRESS -> "In Progress"
        TaskStatus.DONE -> "Done"
    }

@Composable
fun StatusButton(
    status: TaskStatus,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
) {

    val shape = RoundedCornerShape(10.dp)

    Box(
        modifier = modifier.clip(shape)
            .then(
                if (isSelected)
                    Modifier.border(
                        width = 2.dp,
                        color = Colors.StatusBorderSelected,
                        shape = shape,
                    )
                        .background(Colors.StatusBgSelected)
                else
                    Modifier.border(
                        width = 2.dp,
                        color = Colors.PrimaryBorder,
                        shape = shape,
                    ),
            ).clickable(
                onClick = onClick,
            ).semantics {
                selected = isSelected
            },
        contentAlignment = Alignment.Center,
    ) {
        Text(
            status.displayName,
            fontWeight = FontWeight.W500,
            fontSize = 16.sp,
            modifier = Modifier.padding(vertical = 14.dp),
            color = if (!isSelected) {
                Colors.PrimarySubText
            } else {
                Colors.StatusTextSelected
            },
        )
    }
}
