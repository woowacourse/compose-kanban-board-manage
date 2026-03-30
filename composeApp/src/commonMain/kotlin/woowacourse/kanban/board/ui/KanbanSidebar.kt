package woowacourse.kanban.board.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import woowacourse.kanban.Colors
import woowacourse.kanban.board.domain.KanbanProject

@Composable
fun KanbanSidebar(
    projects: List<KanbanProject>,
    modifier: Modifier = Modifier,
    onClick: (Int) -> Unit = {},
    selectedProjectIndex: Int = 0,
) {
    Column(
        modifier = modifier
            .width(256.dp)
            .fillMaxHeight(),
    ) {

        Column(modifier.padding(24.dp)) {
            Text("프로젝트", fontSize = 18.sp, fontWeight = FontWeight.W600, color = Colors.PrimarySubText)
            Spacer(modifier.height(4.dp))
            Text("4주차 미션 보드", fontSize = 14.sp, fontWeight = FontWeight.W400, color = Colors.IconSecondary)
        }
        HorizontalDivider()
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = modifier.padding(16.dp),
        ) {
            itemsIndexed(projects) { index, item ->
                ProjectItem(
                    item.title,
                    isSelected = selectedProjectIndex == index,
                    onClick = { onClick(index) },
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun KanbanSidebarPreview() {
    KanbanSidebar(
        projects = listOf(
            KanbanProject(
                inputTasks = mutableListOf(),
            ),
            KanbanProject(
                inputTasks = mutableListOf(),

            ),
        ),
    )
}
