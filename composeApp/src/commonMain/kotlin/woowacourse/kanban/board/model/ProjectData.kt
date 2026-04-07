package woowacourse.kanban.board.model

data class ProjectData(val kanbanBoardDatas: List<KanbanBoardData> = emptyList()) {
    fun getIndexingKanbanBoardData(selectedIndex: Int): KanbanBoardData {
        return kanbanBoardDatas[selectedIndex]
    }

    fun getIndex(kanbanBoardData: KanbanBoardData): Int {
        return kanbanBoardDatas.indexOfFirst { it.id == kanbanBoardData.id }
    }

    fun addBoardData(selectedKanbanBoardData: KanbanBoardData, boardData: BoardData): ProjectData {
        return copy(
            kanbanBoardDatas = kanbanBoardDatas.map { kanbanBoardData ->
                if (kanbanBoardData.id == selectedKanbanBoardData.id) kanbanBoardData.addBoardData(boardData) else kanbanBoardData
            },
        )
    }

    fun editBoardData(selectedKanbanBoardData: KanbanBoardData, targetBoardData: BoardData): ProjectData {
        return copy(
            kanbanBoardDatas = kanbanBoardDatas.map { kanbanBoardData ->
                if (kanbanBoardData.id == selectedKanbanBoardData.id) kanbanBoardData.editBoardData(targetBoardData) else kanbanBoardData
            },
        )
    }

    fun deleteBoardData(selectedKanbanBoardData: KanbanBoardData, targetBoardData: BoardData): ProjectData {
        return copy(
            kanbanBoardDatas = kanbanBoardDatas.map { kanbanBoardData ->
                if (kanbanBoardData.id == selectedKanbanBoardData.id) kanbanBoardData.deleteBoardData(targetBoardData) else kanbanBoardData
            },
        )
    }

    fun moveBoardDataStatus(selectedKanbanBoardData: KanbanBoardData, task: BoardData, targetStatus: Status): ProjectData {
        return copy(
            kanbanBoardDatas = kanbanBoardDatas.map { kanbanBoardData ->
                if (kanbanBoardData.id == selectedKanbanBoardData.id) kanbanBoardData.moveBoardDataStatus(
                    task = task,
                    targetStatus = targetStatus,
                ) else kanbanBoardData
            },
        )
    }

    companion object {
        val defaultKanbanBoardDatas = listOf(
            KanbanBoardData(title = "Compose1"),
            KanbanBoardData(title = "Compose2"),
            KanbanBoardData(title = "Compose3너무너무너무너무너무너무너무너무"),
        )
    }
}
