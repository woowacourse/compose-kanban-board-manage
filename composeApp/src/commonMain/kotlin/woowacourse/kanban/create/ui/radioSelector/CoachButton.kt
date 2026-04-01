package woowacourse.kanban.create.ui.radioSelector

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
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
import woowacourse.kanban.domain.Assignee

@Composable
fun CoachButton(
    isSelected: Boolean,
    assignee: Assignee,
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
            .clickable(
                onClick = onClick,
            )
            .semantics {
                selected = isSelected
            },

    ) {
        Row(
            modifier = Modifier.padding(
                horizontal = 16.dp,
                vertical = 20.dp,
            ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "코치 프로필 아이콘",
                tint = Colors.IconTertiary,
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                assignee.nickname.nickname,
                fontWeight = FontWeight.W500,
                fontSize = 14.sp,
                color = Colors.PrimarySubText,
            )
        }
    }
}
