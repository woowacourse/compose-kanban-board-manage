package woowacourse.kanban.board.component

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import woowacourse.kanban.board.component.board.Board
import woowacourse.kanban.board.component.sample.ProfilePreviewData
import woowacourse.kanban.board.component.sample.ProjectPreviewData
import woowacourse.kanban.board.component.state.WorkSpaceStateHolder
import woowacourse.kanban.board.component.util.Blue80
import woowacourse.kanban.board.component.util.Gray10
import woowacourse.kanban.board.component.util.Gray20
import woowacourse.kanban.board.component.util.Gray40
import woowacourse.kanban.board.component.util.Purple80
import woowacourse.kanban.board.model.project.Project
import woowacourse.kanban.board.model.taskcard.Profile
import woowacourse.kanban.board.model.workspace.WorkSpace as WorkSpaceModel

@Composable
fun WorkSpace(
    workSpace: WorkSpaceModel,
    profiles: ImmutableList<Profile>,
    modifier: Modifier = Modifier
) {
    var stateHolder by remember(workSpace) {
        mutableStateOf(WorkSpaceStateHolder(workSpace))
    }

    Row(
        modifier = modifier
    ) {
        stateHolder.selectedProject?.let { currentProject ->
            SideBar(
                workSpace = stateHolder.workSpace,
                selectedProject = currentProject,
                onChangeProject = { project ->
                    stateHolder = stateHolder.selectProject(project)
                },
            )
        }
        stateHolder.selectedProject?.let {
            Board(
                project = it,
                profiles = profiles,
                onCreateTask = { task ->
                    stateHolder = stateHolder.addTask(task)
                },
                onUpdateTask = { id, task ->
                    stateHolder = stateHolder.updateTask(id, task)
                },
                onDeleteTask = { id ->
                    stateHolder = stateHolder.deleteTask(id)
                },
                onUpdateTaskStatus = { id, status ->
                    stateHolder = stateHolder.updateTaskStatus(id, status)
                },
            )
        }
    }
}

@Composable
private fun SideBar(
    workSpace: WorkSpaceModel,
    selectedProject: Project,
    onChangeProject: (Project) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .width(255.dp)
            .background(Color.White),
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = "프로젝트",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Gray10
                ),
            )
            Text(
                text = "4주차 미션 보드",
                style = TextStyle(
                    fontSize = 14.sp,
                    color = Gray40
                ),
            )
        }
        HorizontalDivider()
        workSpace.projects.forEach { project ->
            val backgroundColor =
                if (project == selectedProject) Blue80
                else Color.Transparent
            val textColor =
                if (project == selectedProject) Purple80
                else Gray20
            Button(
                onClick = { onChangeProject(project) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = backgroundColor,
                    contentColor = textColor
                ),
                elevation = ButtonDefaults.buttonElevation(
                    hoveredElevation = 0.dp
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                shape = RoundedCornerShape(10.dp),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        text = project.title,
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal,
                        ),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 1500)
@Composable
private fun WorkSpacePreview() {
    val workSpace = WorkSpaceModel(
        ProjectPreviewData().values.toImmutableList()
    )
    val profiles = ProfilePreviewData().values.toImmutableList()
    MaterialTheme {
        WorkSpace(workSpace, profiles)
    }
}
