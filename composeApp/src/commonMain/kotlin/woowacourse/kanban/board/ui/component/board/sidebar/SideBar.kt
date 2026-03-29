package woowacourse.kanban.board.ui.component.board.sidebar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SideBar(
    title: String,
    subTitle: String,
    titles: List<String>,
    selectedIndex: Int,
    onTitleClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .width(255.dp)
            .fillMaxHeight(),
    ) {
        Header(title = title, subtitle = subTitle)

        HorizontalDivider(modifier = Modifier.fillMaxWidth())

        TitleButtonGroup(
            titles = titles,
            selectedIndex = selectedIndex,
            onTitleClick = onTitleClick,
            modifier = Modifier.padding(16.dp),
        )
    }
}

@Preview(showBackground = true, name = "사이드바")
@Composable
private fun SideBarPreview() {
    SideBar(
        title = "프로젝트",
        subTitle = "4주차 미션 보드",
        titles = listOf("Compose1", "Compose2", "compose3너무너무너무너무너무너무"),
        selectedIndex = 0,
        onTitleClick = { },
    )
}
