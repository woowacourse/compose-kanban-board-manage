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
import kanbanboard.composeapp.generated.resources.Res
import kanbanboard.composeapp.generated.resources.kanban_board_subtitle
import kanbanboard.composeapp.generated.resources.kanban_board_title
import org.jetbrains.compose.resources.stringResource
import woowacourse.kanban.board.ui.board.state.ProjectState

@Composable
fun Sidebar(
    projects: List<ProjectState>,
    selectedProject: ProjectState?,
    modifier: Modifier = Modifier,
    onProjectChange: (ProjectState) -> Unit,
) {
    Column(
        modifier = modifier,
    ) {
        SidebarHeader(modifier.padding(24.dp))
        HorizontalDivider()
        if (projects.isNotEmpty() && selectedProject != null) {
            NavigationBar(
                projects = projects,
                selectedProject = selectedProject,
                onProjectChange = onProjectChange,
            )
        }
    }
}

@Composable
private fun SidebarHeader(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
    ) {
        Text(
            text = stringResource(Res.string.kanban_board_title),
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
        )
        Text(
            text = stringResource(Res.string.kanban_board_subtitle),
            fontSize = 14.sp,
            color = Color(0xFF6A7282),
        )
    }
}

@Composable
private fun NavigationBar(projects: List<ProjectState>, selectedProject: ProjectState, onProjectChange: (ProjectState) -> Unit) {
    Column(
        modifier = Modifier,
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
private fun BoardSelectButton(onClick: () -> Unit, project: ProjectState, backgroundColor: Color, textColor: Color) {
    Button(
        modifier = Modifier
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
        projects = listOf(
            ProjectState(name = "Compose Desktop 칸반보드"),
        ),
        selectedProject = ProjectState(name = "Compose Desktop 칸반보드"),
        onProjectChange = {},
    )
}
