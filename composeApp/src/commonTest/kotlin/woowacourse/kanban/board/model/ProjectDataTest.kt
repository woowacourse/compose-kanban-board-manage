package woowacourse.kanban.board.model

import kotlin.test.Test
import org.assertj.core.api.Assertions.assertThat

class ProjectDataTest {

    @Test
    fun `칸반 보드 목록 중에 두 번쨰 칸반 보드를 클릭하면 그 보드 리스트를 반환한다`() {
        // given
        val projectData = ProjectData(kanbanBoardDatas = ProjectData.defaultKanbanBoardDatas)
        val selectedIndex = 1

        // when
        val selectedBoardData = projectData.getIndexingKanbanBoardData(selectedIndex)

        // then
        assertThat(selectedBoardData.title).isEqualTo(projectData.kanbanBoardDatas[1].title)
    }

    @Test
    fun `칸반 보드 목록 중에 두 번째 칸반 보드를 클릭하면 그 보드의 인덱스를 반환한다`() {
        // given
        val projectData = ProjectData(kanbanBoardDatas = ProjectData.defaultKanbanBoardDatas)
        val selectedKanbanBoardData = projectData.kanbanBoardDatas[1]

        // when
        val selectedIndex = projectData.getIndex(selectedKanbanBoardData)

        // then
        assertThat(selectedIndex).isEqualTo(1)
    }

    @Test
    fun `칸반 보드에 새로운 보드 데이터가 추가됐을 때 추가된 보드 데이터가 유지된다`() {
        // given
        val projectData = ProjectData(kanbanBoardDatas = ProjectData.defaultKanbanBoardDatas)
        val selectedKanbanBoardData = projectData.kanbanBoardDatas[0]
        val boardData = BoardData(
            title = "제목",
            status = Status.TODO,
            nickname = Nickname.DINO,
        )

        // when
        val addProjectData = projectData.addBoardData(selectedKanbanBoardData, boardData)

        // then
        assertThat(addProjectData.kanbanBoardDatas[0].boardList.count { it == boardData }).isEqualTo(1)
    }

    @Test
    fun `칸반 보드에 있는 보드 데이터를 수정했을 때 수정된 보드 데이터가 유지된다`() {
        // given
        val projectData = ProjectData(kanbanBoardDatas = ProjectData.defaultKanbanBoardDatas)
        val selectedKanbanBoardData = projectData.kanbanBoardDatas[0]
        val boardData = BoardData(
            id = "0",
            title = "제목",
            status = Status.TODO,
            nickname = Nickname.DINO,
        )
        val addProjectData = projectData.addBoardData(selectedKanbanBoardData, boardData)
        val newBoardData = BoardData(
            id = "0",
            title = "제목",
            status = Status.TODO,
            nickname = Nickname.PAMES,
        )

        // when
        val editProjectData = addProjectData.editBoardData(selectedKanbanBoardData, newBoardData)

        // then
        assertThat(editProjectData.kanbanBoardDatas[0].boardList.count { it == boardData }).isEqualTo(0)
        assertThat(editProjectData.kanbanBoardDatas[0].boardList.count { it == newBoardData }).isEqualTo(1)
    }

    @Test
    fun `id가 1인 보드 데이터를 지웠을 때 보드 리스트에서 id가 1인 태스크가 지워진다`() {
        // given
        val projectData = ProjectData(kanbanBoardDatas = ProjectData.defaultKanbanBoardDatas)
        val selectedKanbanBoardData = projectData.kanbanBoardDatas[0]
        val boardData1 = BoardData(
            id = "0",
            title = "제목",
            status = Status.TODO,
            nickname = Nickname.DINO,
        )
        val addProjectData = projectData.addBoardData(selectedKanbanBoardData, boardData1)
        val boardData2 = BoardData(
            id = "1",
            title = "제목",
            status = Status.TODO,
            nickname = Nickname.PAMES,
        )
        val newProjectData = addProjectData.addBoardData(selectedKanbanBoardData, boardData2)

        // when
        val deleteProjectData = newProjectData.deleteBoardData(selectedKanbanBoardData, boardData2)

        // then
        assertThat(deleteProjectData.kanbanBoardDatas[0].boardList.count { it == boardData1 }).isEqualTo(1)
        assertThat(deleteProjectData.kanbanBoardDatas[0].boardList.count { it == boardData2 }).isEqualTo(0)
    }

    @Test
    fun `id가 0이고 TODO 상태인 보드 데이터를 In Progress로 상태를 변경하면 변경된 보드 데이터가 유지된다`() {
        // given
        val projectData = ProjectData(kanbanBoardDatas = ProjectData.defaultKanbanBoardDatas)
        val selectedKanbanBoardData = projectData.kanbanBoardDatas[0]
        val boardData = BoardData(
            id = "0",
            title = "제목",
            status = Status.TODO,
            nickname = Nickname.DINO,
        )
        val addProjectData = projectData.addBoardData(selectedKanbanBoardData, boardData)

        // when
        val moveProjectData = addProjectData.moveBoardDataStatus(selectedKanbanBoardData, boardData, Status.IN_PROGRESS)

        // then
        assertThat(moveProjectData.kanbanBoardDatas[0].boardList.find { it.id == "0" }!!.status).isEqualTo(Status.IN_PROGRESS)
    }
}
