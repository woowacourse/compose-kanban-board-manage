package woowacourse.kanban.board.component.workspace

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import woowacourse.kanban.board.Blue80
import woowacourse.kanban.board.Gray10
import woowacourse.kanban.board.Gray20
import woowacourse.kanban.board.Gray40
import woowacourse.kanban.board.Purple50
import woowacourse.kanban.board.component.ComponentText
import woowacourse.kanban.board.component.sample.ProjectPreviewData
import woowacourse.kanban.board.model.project.Project

@Composable
fun WorkSpaceSideBar(
    projects: ImmutableList<Project>,
    selectedProject: Project,
    onChangeProject: (Project) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxHeight()
            .width(255.dp)
            .background(Color.White),
    ) {
        SideBarHeader()
        SideBarContents(
            projects = projects,
            selectedProject = selectedProject,
            onChangeProject = onChangeProject,
        )
    }
}

@Composable
private fun SideBarHeader(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Text(
            text = ComponentText.SIDEBAR_HEADER_TITLE,
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Gray10,
            ),
        )
        Text(
            text = ComponentText.SIDEBAR_HEADER_SUBTITLE,
            style = TextStyle(
                fontSize = 14.sp,
                color = Gray40,
            ),
        )
    }
    HorizontalDivider()
}

@Composable
private fun SideBarContents(
    projects: ImmutableList<Project>,
    selectedProject: Project,
    onChangeProject: (Project) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        projects.forEach { project ->
            val backgroundColor =
                if (project == selectedProject) Blue80
                else Color.Transparent
            val textColor =
                if (project == selectedProject) Purple50
                else Gray20
            Button(
                onClick = { onChangeProject(project) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = backgroundColor,
                    contentColor = textColor,
                ),
                elevation = ButtonDefaults.buttonElevation(
                    hoveredElevation = 0.dp,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                shape = RoundedCornerShape(10.dp),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                ) {
                    Text(
                        text = project.title,
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal,
                        ),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SideBarHeaderPreview() {
    SideBarHeader()
}

@Preview(showBackground = true)
@Composable
private fun SideBarContentsPreview() {
    val projects = ProjectPreviewData().values.toImmutableList()
    SideBarContents(
        projects = projects,
        selectedProject = projects.first(),
        onChangeProject = {},
    )
}
