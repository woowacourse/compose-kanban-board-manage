package woowacourse.kanban.board.ui.component.board.sidebar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TitleButtonGroup(
    titles: List<String>,
    selectedIndex: Int,
    onTitleClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        titles.forEachIndexed { index, title ->
            TitleButton(
                text = title,
                isSelected = index == selectedIndex,
                onClick = { onTitleClick(index) },
            )
        }
    }
}

@Preview(showBackground = true, name = "타이틀 버튼 그룹")
@Composable
fun TitleButtonGroupPreview() {
    TitleButtonGroup(
        titles = listOf("Compose1", "Compose2", "compose3너무너무너무너무너무너무너무너무너무너무너무너무너무너무너무너무너무너무너무너무너무너무너무너무"),
        selectedIndex = 0,
        onTitleClick = { },
        modifier = Modifier.width(200.dp),
    )
}
