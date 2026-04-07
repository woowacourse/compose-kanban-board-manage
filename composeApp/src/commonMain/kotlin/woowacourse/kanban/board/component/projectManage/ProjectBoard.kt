package woowacourse.kanban.board.component.projectManage

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import woowacourse.kanban.board.component.kanbanBoard.KanbanBoard
import woowacourse.kanban.board.model.ProjectData
import woowacourse.kanban.board.state.ProjectBoardState

@Composable
fun ProjectBoard(projectDataState: ProjectBoardState) {

    val selectedKanbanBoardData = projectDataState.projectData.getIndexingKanbanBoardData(projectDataState.selectedIndex)

    Row {
        ProjectSideBar(
            projectTitle = "프로젝트",
            subTitle = "4주차 미션 보드",
        ) {
            projectDataState.projectData.kanbanBoardDatas.forEach { kanbanBoardData ->
                KanbanBoardButton(
                    title = kanbanBoardData.title,
                    onClick = { projectDataState.onClickKanbanBoardButton(kanbanBoardData) },
                    isSelected = selectedKanbanBoardData == kanbanBoardData,
                )
            }
        }
        VerticalDivider()
        KanbanBoard(
            kanbanBoardData = selectedKanbanBoardData,
            onAddBoardData = { boardData ->
                projectDataState.addBoardData(selectedKanbanBoardData, boardData)
            },
            onEditBoardData = { boardData ->
                projectDataState.editBoardData(selectedKanbanBoardData, boardData)
            },
            onDeleteBoardData = { boardData ->
                projectDataState.deleteBoardData(selectedKanbanBoardData, boardData)
            },
            onMoveBoardDataStatus = { task, targetStatus ->
                projectDataState.moveBoardDataStatus(selectedKanbanBoardData, task, targetStatus)
            },
        )
    }
}

@Composable
private fun KanbanBoardButton(modifier: Modifier = Modifier, title: String = "", isSelected: Boolean = false, onClick: () -> Unit = {}) {
    Button(
        modifier = Modifier.padding(bottom = 4.dp).fillMaxWidth(),
        colors = ButtonColors(
            containerColor = if (isSelected) Color(0xFFEEF2FF) else Color.White,
            contentColor = Color.White,
            disabledContainerColor = if (isSelected) Color(0xFFEEF2FF) else Color.White,
            disabledContentColor = Color.White,
        ),
        shape = RoundedCornerShape(16.dp),
        onClick = onClick,
    ) {
        Text(
            text = title,
            color = if (isSelected) Color(0xFF432DD7) else Color(0xFF364153),
            fontSize = 16.sp,
            fontWeight = FontWeight.W500,
            modifier = modifier.fillMaxWidth(),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ProjectBoardPreview() {
    val projectDataState = remember { ProjectBoardState(ProjectData.defaultKanbanBoardDatas) }

    ProjectBoard(projectDataState)
}

@Preview(showBackground = true)
@Composable
private fun KanbanBoardButtonPreview() {
    KanbanBoardButton(
        title = "Compose1", isSelected = true,
    )
}

@Preview(showBackground = true)
@Composable
private fun KanbanBoardButtonLongTitlePreview() {
    KanbanBoardButton(
        title = "Compose3너무너무너무너무긴제목", isSelected = false,
    )
}
