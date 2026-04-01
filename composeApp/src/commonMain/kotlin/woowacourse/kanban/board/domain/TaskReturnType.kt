package woowacourse.kanban.board.domain

enum class TaskReturnType {
    CREATE_SUCCESS,
    TASK_STATUS_SUCCESS,
    UPDATE_SUCCESS,
    NOT_UPDATABLE,
    DELETE_SUCCESS,
    NOT_DELETABLE,
    NOT_ASSIGNED,
}
