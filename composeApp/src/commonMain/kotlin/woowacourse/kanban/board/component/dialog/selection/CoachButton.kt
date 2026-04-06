package woowacourse.kanban.board.component.dialog.selection

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import woowacourse.kanban.board.theme.COACH_BG_SELECTED
import woowacourse.kanban.board.theme.COACH_BORDER_SELECTED
import woowacourse.kanban.board.theme.COACH_ICON_TINT
import woowacourse.kanban.board.theme.PRIMARY_BORDER
import woowacourse.kanban.board.theme.PRIMARY_SUB_TEXT

@Composable
fun CoachButton(isSelected: Boolean, name: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    val unSelectedModifier = modifier.border(
        width = 2.dp, color = Color(PRIMARY_BORDER), shape = RoundedCornerShape(10.dp),
    )
    val selectedModifier = modifier.border(
        width = 2.dp, color = Color(COACH_BORDER_SELECTED), shape = RoundedCornerShape(10.dp),
    ).background(color = Color(COACH_BG_SELECTED), shape = RoundedCornerShape(10.dp))

    Box(
        modifier = if (!isSelected) {
            unSelectedModifier
        } else {
            selectedModifier
        }
            .clickable(
                onClick = onClick,
            ),
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(imageVector = Icons.Default.AccountCircle, contentDescription = "코치 프로필 아이콘", tint = Color(COACH_ICON_TINT))
            Spacer(modifier = Modifier.width(12.dp))
            Text(name, fontWeight = FontWeight.W500, fontSize = 14.sp, color = Color(PRIMARY_SUB_TEXT))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SelectedCoachButtonPreview() {
    CoachButton(name = "다이노", isSelected = true, onClick = {})
}
