package woowacourse.kanban.card.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import woowacourse.kanban.Colors
import woowacourse.kanban.domain.Nickname

@Composable
fun Profile(
    nickname: Nickname,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = Icons.Default.Person,
            contentDescription = null,
            tint = Colors.OnNeutral,
            modifier = Modifier.size(25.dp)
                .clip(CircleShape)
                .border(width = 2.dp, color = Colors.NeutralGray)
                .background(color = Colors.NeutralGray),
        )
        Text(
            nickname.nickname,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
        )
    }
}
