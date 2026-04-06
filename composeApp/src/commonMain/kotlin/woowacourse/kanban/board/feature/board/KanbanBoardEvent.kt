package woowacourse.kanban.board.feature.board

sealed class KanbanBoardEvent {
    data object TaskMoved : KanbanBoardEvent()
    data object TaskAdded : KanbanBoardEvent()
    data object TaskAddFailed : KanbanBoardEvent()
    data object TaskEdited : KanbanBoardEvent()
    data object TaskEditFailed : KanbanBoardEvent()
    data object TaskDeleted : KanbanBoardEvent()
    data object TaskDeleteFailed : KanbanBoardEvent()
    data object TaskTransitionFailed : KanbanBoardEvent()
    data object TaskAssigneeMissing : KanbanBoardEvent()
}
