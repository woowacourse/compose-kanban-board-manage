package woowacourse.kanban.board.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SideBarTab(isSelected: Boolean, name: String, onClick: () -> Unit) {
    FilterChip(
        selected = isSelected,
        onClick = { onClick() },
        label = { Text(name, maxLines = 1, overflow = TextOverflow.Ellipsis) },
        modifier = Modifier.fillMaxWidth().semantics { contentDescription = "$name 전환 버튼" },
        colors = FilterChipDefaults.filterChipColors(
            containerColor = Color.White,
            labelColor = Color(0xff364153),
            selectedContainerColor = Color(0xffEEF2FF),
            selectedLabelColor = Color(0xff432DD7),
        ),
        border = null,
        elevation = FilterChipDefaults.elevatedFilterChipElevation(
            elevation = if (isSelected) 2.dp else 0.dp,
        ),
    )
}

@Preview(showBackground = true)
@Composable
private fun SideBarTabPreview() {
    SideBarTab(true, "프로젝트1", {})
}
