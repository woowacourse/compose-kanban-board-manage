package woowacourse.kanban.board.ui.component.board.sidebar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import woowacourse.kanban.board.ui.KanbanColors
import woowacourse.kanban.board.ui.KanbanTypography

@Composable
fun TitleButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .shadow(elevation = if (isSelected) 1.dp else 0.dp, shape = RoundedCornerShape(10.dp))
            .clip(shape = RoundedCornerShape(10.dp))
            .background(color = if (isSelected) KanbanColors.SideBar.selectedBackground else Color.Unspecified)
            .clickable(onClick = onClick)
            .padding(start = 15.dp, end = 41.dp, top = 12.dp, bottom = 12.dp),
    ) {
        Text(
            text = text,
            color = if (isSelected) KanbanColors.SideBar.selectedText else KanbanColors.SideBar.unselectedText,
            style = KanbanTypography.label16Medium,
            lineHeight = 24.sp,
            letterSpacing = (-0.31).sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Preview(showBackground = true, name = "선택한 타이틀버튼")
@Composable
private fun SelectedTitleButtonPreview() {
    TitleButton(
        text = "Compose1",
        isSelected = true,
        onClick = { },
        modifier = Modifier.padding(5.dp),
    )
}

@Preview(showBackground = true, name = "선택하지 않은 타이틀버튼")
@Composable
private fun UnSelectedTitleButtonPreview() {
    TitleButton(
        text = "Compose1",
        onClick = { },
        isSelected = false,
    )
}
