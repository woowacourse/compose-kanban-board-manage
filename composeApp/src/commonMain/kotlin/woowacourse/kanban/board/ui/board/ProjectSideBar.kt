package woowacourse.kanban.board.ui.board

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import woowacourse.kanban.board.domain.model.KanbanProject
import woowacourse.kanban.board.ui.component.SideBar
import woowacourse.kanban.board.ui.component.SideBarTab

@Composable
fun ProjectSideBar(
    projects: List<KanbanProject>,
    selectedProjectId: String,
    onProjectSelect: (String) -> Unit,
    modifier: Modifier = Modifier,
    innerPadding: Dp = 16.dp,
) {
    SideBar(
        title = "프로젝트",
        subtitle = "4주차 미션 보드",
        content = {
            Column(
                modifier = Modifier.padding(innerPadding),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                projects.forEach { project ->
                    SideBarTab(
                        isSelected = (project.id == selectedProjectId),
                        name = project.name,
                        onClick = { onProjectSelect(project.id) },
                    )
                }
            }
        },
        modifier = modifier,
    )
}

@Preview(showBackground = true)
@Composable
private fun ProjectSideBarPreview() {
    ProjectSideBar(
        modifier = Modifier.width(255.dp).fillMaxHeight(),
        projects = listOf(KanbanProject(id = "1", name = "project1"), KanbanProject(name = "project2")),
        selectedProjectId = "1",
        onProjectSelect = {},
    )
}
