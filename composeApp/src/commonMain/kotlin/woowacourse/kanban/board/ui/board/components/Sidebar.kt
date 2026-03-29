package woowacourse.kanban.board.ui.board.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import woowacourse.kanban.board.domain.Project
import woowacourse.kanban.board.domain.Tasks

@Composable
fun Sidebar(projects: List<Project>, selectedProject: Project, onProjectChange: (Project) -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
    ) {
        SidebarHeader(modifier.padding(24.dp))
        HorizontalDivider()
        NavigationBar(
            projects = projects,
            selectedProject = selectedProject,
            onProjectChange = onProjectChange,
        )
    }
}

@Composable
private fun SidebarHeader(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
    ) {
        Text(
            text = "프로젝트",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
        )
        Text(
            text = "4주차 미션 보드",
            fontSize = 14.sp,
            color = Color(0xFF6A7282),
        )
    }
}

@Composable
private fun NavigationBar(
    projects: List<Project>,
    selectedProject: Project,
    onProjectChange: (Project) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        projects.forEach {
            BoardSelectButton(
                project = it,
                onClick = { onProjectChange(it) },
                backgroundColor = if (selectedProject.name == it.name) Color(0xFFEEF2FF) else Color.White,
                textColor = if (selectedProject.name == it.name) Color(0xFF432DD7) else Color.Black,
            )
        }
    }
}

@Composable
private fun BoardSelectButton(
    onClick: () -> Unit,
    project: Project,
    backgroundColor: Color,
    textColor: Color,
    modifier: Modifier = Modifier,
) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 14.dp, end = 14.dp, top = 6.dp),
        onClick = onClick,
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
        ),
        content = {
            Text(
                text = project.name,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = textColor,
            )
        },
    )
}

@Preview
@Composable
private fun SidebarPreview() {
    Sidebar(
        modifier = Modifier,
        selectedProject = Project("Compose1", Tasks(emptyList())),
        onProjectChange = {},
        projects = listOf(Project("Compose1", tasks = Tasks(emptyList())), Project("Compose2", tasks = Tasks(emptyList()))),
    )
}
