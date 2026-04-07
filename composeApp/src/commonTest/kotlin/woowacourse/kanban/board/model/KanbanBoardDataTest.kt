package woowacourse.kanban.board.model

import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import woowacourse.kanban.board.constant.DEFAULT_CONTENT
import woowacourse.kanban.board.constant.DEFAULT_TITLE
import woowacourse.kanban.board.constant.MAX_CONTENT
import woowacourse.kanban.board.constant.MAX_TITLE

class KanbanBoardDataTest {
    private val boardList = listOf(
        BoardData(
            id = "0",
            title = DEFAULT_TITLE,
            description = DEFAULT_CONTENT,
            tags = listOf(Tag("컴포넌트"), Tag("성능")),
            status = Status.REVIEW,
            nickname = Nickname.DINO,
        ),
        BoardData(
            id = "1",
            title = DEFAULT_TITLE,
            tags = listOf(Tag("컴포넌트"), Tag("성능")),
            status = Status.TODO,
            nickname = Nickname.DINO,
        ),
        BoardData(
            id = "2",
            title = DEFAULT_TITLE,
            description = DEFAULT_CONTENT,
            status = Status.IN_PROGRESS,
            nickname = Nickname.DINO,
        ),
        BoardData(
            id = "3",
            title = DEFAULT_TITLE,
            status = Status.TODO,
            nickname = Nickname.NONE,
        ),
        BoardData(
            id = "4",
            title = MAX_TITLE,
            description = MAX_CONTENT,
            tags = listOf(Tag("너무너무"), Tag("긴태그"), Tag("최대로"), Tag("5자까지"), Tag("5개제한임")),
            status = Status.DONE,
            nickname = Nickname.DINO,
        ),
    )

    private val kanbanBoardData = KanbanBoardData(
        title = "Compose1", boardList = boardList,
    )

    private val targetCard = kanbanBoardData.boardList[0]

    @Test
    fun `태스크 전체 개수를 알고 있다`() {

        Assertions.assertThat(kanbanBoardData.totalStatusCount()).isEqualTo(boardList.size)
    }

    @Test
    fun `상태(To-Do, In Progress, Review, Done)별 태스크 개수가 노출된다`() {
        Assertions.assertThat(kanbanBoardData.getStatusBoard(Status.TODO).size).isEqualTo(2)
        Assertions.assertThat(kanbanBoardData.getStatusBoard(Status.IN_PROGRESS).size).isEqualTo(1)
        Assertions.assertThat(kanbanBoardData.getStatusBoard(Status.REVIEW).size).isEqualTo(1)
        Assertions.assertThat(kanbanBoardData.getStatusBoard(Status.DONE).size).isEqualTo(1)
    }

    @Test
    fun `5개 중에 done이 1개라면 20%의 완료율을 계산한다`() {
        Assertions.assertThat(kanbanBoardData.progress()).isEqualTo(0.2f)
    }

    @Test
    fun `상태를 To-Do에서 In Progress으로 옮겼을 때 객체의 상태가 변경된다`() {
        val changeKanbanBoardData = kanbanBoardData.moveBoardDataStatus(task = targetCard, targetStatus = Status.IN_PROGRESS)

        assertThat(changeKanbanBoardData.boardList[0].status).isEqualTo(Status.IN_PROGRESS)
    }

    @Test
    fun `상태를 Review에서 Done으로 옮겼을 때 doneCount가 증가한다`() {
        val changeKanbanBoardData = kanbanBoardData.moveBoardDataStatus(task = targetCard, targetStatus = Status.DONE)

        assertThat(changeKanbanBoardData.doneCount()).isEqualTo(2)
    }

    @Test
    fun `상태를 To-Do에서 In Progress로 변경했을 때 완료율은 변하지 않는다`() {
        val changeKanbanBoardData = kanbanBoardData.moveBoardDataStatus(task = targetCard, targetStatus = Status.IN_PROGRESS)

        assertThat(changeKanbanBoardData.progress()).isEqualTo(kanbanBoardData.progress())
    }

    @Test
    fun `칸반 보드 리스트에 보드 데이터를 추가하면 칸반 보드 리스트에 추가된 보드 데이터가 유지된다`() {
        val boardData = BoardData(
            id = "7",
            title = "제목",
            status = Status.TODO,
            nickname = Nickname.DINO,
        )

        val changeKanbanBoardData = kanbanBoardData.addBoardData(boardData)

        assertThat(changeKanbanBoardData.boardList.count { it.title == "제목" }).isEqualTo(1)
    }

    @Test
    fun `칸반 보드 리스트에 존재하는 보드 데이터의 정보 중 닉네임을 다이노에서 페임스로 수정하면 수정된 보드 데이터가 유지된다`() {
        val targetBoardData = BoardData(
            id = "0",
            title = "제목",
            status = Status.TODO,
            nickname = Nickname.PAMES,
        )

        val changeKanbanBoardData = kanbanBoardData.editBoardData(targetBoardData)

        assertThat(changeKanbanBoardData.boardList.find { it.id == "0" }!!.nickname).isEqualTo(Nickname.PAMES)
    }

    @Test
    fun `칸반 보드 리스트에 존재하는 id가 0인 보드 데이터를 삭제했을 때 칸반 보드 리스트에 id가 0인 보드 데이터가 존재하지 않는다`() {
        val changeKanbanBoardData = kanbanBoardData.deleteBoardData(targetCard)

        assertThat(changeKanbanBoardData.boardList.count { it.id == "0" }).isEqualTo(0)
    }

    @Test
    fun `To Do 상태이며 담당자가 없는 보드 데이터를 In Progress로 옮기려고 할 때 변경이 일어나지 않는다`() {
        val changeKanbanBoardData = kanbanBoardData.moveBoardDataStatus(boardList[3], Status.IN_PROGRESS)

        assertThat(changeKanbanBoardData.boardList[3].status).isEqualTo(Status.TODO)
    }

    @Test
    fun `To Do 상태인 보드 데이터를 Review로 옮기려고 할 때 변경이 일어나지 않는다`() {
        val changeKanbanBoardData = kanbanBoardData.moveBoardDataStatus(boardList[1], Status.REVIEW)

        assertThat(changeKanbanBoardData.boardList[1].status).isEqualTo(Status.TODO)
    }

    @Test
    fun `To Do 상태이며 담당자가 존재하는 보드 데이터를 In Progress로 옮겼을 때 보드 데이터의 상태가 변경된다`() {
        val changeKanbanBoardData = kanbanBoardData.moveBoardDataStatus(boardList[1], Status.IN_PROGRESS)

        assertThat(changeKanbanBoardData.boardList[1].status).isEqualTo(Status.IN_PROGRESS)
    }

    @Test
    fun `In Progress 상태인 보드 데이터를 Done으로 옮기려고 할 때 변경이 일어나지 않는다`() {
        val changeKanbanBoardData = kanbanBoardData.moveBoardDataStatus(boardList[2], Status.DONE)

        assertThat(changeKanbanBoardData.boardList[2].status).isEqualTo(Status.IN_PROGRESS)
    }

    @Test
    fun `In Progress 상태인 보드 데이터를 Review로 옮겼을 때 보드 데이터의 상태가 변경된다`() {
        val changeKanbanBoardData = kanbanBoardData.moveBoardDataStatus(boardList[2], Status.REVIEW)

        assertThat(changeKanbanBoardData.boardList[2].status).isEqualTo(Status.REVIEW)
    }

    @Test
    fun `보드 데이터를 REVIEW에서 DONE으로 옮겼을 때 옮겨진 보드 데이터가 유지된다`() {
        val changeKanbanBoardData = kanbanBoardData.moveBoardDataStatus(boardList[0], Status.DONE)

        assertThat(changeKanbanBoardData.boardList[0].status).isEqualTo(Status.DONE)
    }

    @Test
    fun `보드 데이터를 DONE에서 TODO로 옮겼을 때 옮겨진 보드 데이터가 유지된다`() {
        val changeKanbanBoardData = kanbanBoardData.moveBoardDataStatus(boardList[4], Status.TODO)

        assertThat(changeKanbanBoardData.boardList[4].status).isEqualTo(Status.TODO)
    }
}
