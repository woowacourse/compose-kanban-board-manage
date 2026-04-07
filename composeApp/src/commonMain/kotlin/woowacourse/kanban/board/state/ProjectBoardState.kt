package woowacourse.kanban.board.state

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import woowacourse.kanban.board.model.BoardData
import woowacourse.kanban.board.model.KanbanBoardData
import woowacourse.kanban.board.model.ProjectData
import woowacourse.kanban.board.model.Status

class ProjectBoardState(kanbanBoardDatas: List<KanbanBoardData>) {
    var projectData by mutableStateOf(ProjectData(kanbanBoardDatas))

    var selectedIndex by mutableStateOf(0)

    fun addBoardData(selectedKanbanBoardData: KanbanBoardData, boardData: BoardData) {
        projectData = projectData.addBoardData(selectedKanbanBoardData = selectedKanbanBoardData, boardData = boardData)
    }

    fun editBoardData(selectedKanbanBoardData: KanbanBoardData, boardData: BoardData) {
        projectData = projectData.editBoardData(selectedKanbanBoardData, boardData)
    }

    fun deleteBoardData(selectedKanbanBoardData: KanbanBoardData, boardData: BoardData) {
        projectData = projectData.deleteBoardData(selectedKanbanBoardData, boardData)
    }

    fun moveBoardDataStatus(selectedKanbanBoardData: KanbanBoardData, task: BoardData, targetStatus: Status) {
        projectData = projectData.moveBoardDataStatus(selectedKanbanBoardData, task, targetStatus)
    }

    fun onClickKanbanBoardButton(kanbanBoardData: KanbanBoardData) {
        selectedIndex = projectData.getIndex(kanbanBoardData = kanbanBoardData)
    }
}
