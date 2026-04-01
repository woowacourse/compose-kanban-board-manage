package woowacourse.kanban.board.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import woowacourse.kanban.Colors

@Composable
fun ProjectItem(
    projectTitle: String,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    val bgColor = if (isSelected) Colors.AssigneeSelectedBg else Color.Transparent
    val textColor = if (isSelected) Colors.ProjectSelectedText else Colors.PrimaryText
    val selectedBorder = if (isSelected) BorderStroke(1.dp, Colors.PrimaryBorder) else null
    val shadowModifier = if (isSelected) modifier.dropShadow(
        shape = RoundedCornerShape(10.dp),
        shadow = Shadow(10.dp, spread = 0.dp, color = Colors.ProjectShadow, offset = DpOffset(0.dp, 12.dp)),
    ) else modifier

    OutlinedButton(
        onClick = { onClick() },
        shape = RoundedCornerShape(10.dp),
        contentPadding = PaddingValues(vertical = 12.dp, horizontal = 15.dp),
        border = selectedBorder,
        colors = ButtonColors(
            containerColor = bgColor,
            contentColor = ButtonDefaults.outlinedButtonColors().contentColor,
            disabledContainerColor = ButtonDefaults.outlinedButtonColors().disabledContainerColor,
            disabledContentColor = ButtonDefaults.outlinedButtonColors().disabledContentColor,
        ),
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .then(
                shadowModifier,
            ),

    ) {
        Text(
            projectTitle,
            fontSize = 16.sp,
            color = textColor,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Start,
            maxLines = 1,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Preview
@Composable
fun ProjectItemPreview(modifier: Modifier = Modifier) {
    ProjectItem("Compose1", isSelected = false)
}
