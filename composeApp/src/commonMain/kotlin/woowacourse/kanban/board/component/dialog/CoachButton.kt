package woowacourse.kanban.board.component.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import woowacourse.kanban.board.constant.COACH_BG_SELECTED
import woowacourse.kanban.board.constant.COACH_BORDER_SELECTED
import woowacourse.kanban.board.constant.COACH_ICON_TINT
import woowacourse.kanban.board.constant.PRIMARY_BORDER
import woowacourse.kanban.board.constant.PRIMARY_SUB_TEXT

@Composable
fun CoachButton(isSelected: Boolean, name: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.clip(shape = RoundedCornerShape(10.dp)).clickable(
            onClick = onClick,
        ).border(
            width = 2.dp, color = Color(if (isSelected) COACH_BORDER_SELECTED else PRIMARY_BORDER), shape = RoundedCornerShape(10.dp),
        ).background(color = Color(if (isSelected) COACH_BG_SELECTED else 0xFFFFFFFF), shape = RoundedCornerShape(10.dp)),
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 20.dp).fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            if (name.isNotBlank()) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "코치 프로필 아이콘",
                    tint = Color(COACH_ICON_TINT),
                )
                Spacer(
                    modifier = Modifier.width(12.dp),
                )
                Text(
                    name,
                    fontWeight = FontWeight.W500,
                    fontSize = 14.sp,
                    color = Color(PRIMARY_SUB_TEXT),
                )
            } else {
                Text("없음", fontWeight = FontWeight.W500, fontSize = 14.sp, color = Color(PRIMARY_SUB_TEXT))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SelectedCoachButtonPreview() {
    CoachButton(name = "다이노", isSelected = true, onClick = {})
}

@Preview(showBackground = true)
@Composable
private fun UnSelectedCoachButtonPreview() {
    CoachButton(name = "페임스", isSelected = false, onClick = {})
}

@Preview(showBackground = true)
@Composable
private fun UnNameCoachButtonPreview() {
    CoachButton(name = "", isSelected = false, onClick = {})
}
