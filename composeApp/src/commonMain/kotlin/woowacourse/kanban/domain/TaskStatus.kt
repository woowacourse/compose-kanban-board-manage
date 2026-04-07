package woowacourse.kanban.domain

sealed class ChangeableResult {
    data object Changeable : ChangeableResult()
    data object NotAssigned : ChangeableResult()
    data object NotChangeable : ChangeableResult()
}

enum class TaskStatus {
    TO_DO,
    IN_PROGRESS,
    REVIEW,
    DONE,
    ;

    // TaskStatus에서 isChangeable을 관리한다, -> 가장 가까운 곳에서 메시지를 받아서 처리, 변동확률이 적은 곳에서 관리
    // KanbanProject에서 isChangeable을 관리한다 -> TaskStatus와 너무 멀다. 변경이 Project에서 일어나니까 여기에서 비즈니스 로직을 갖는 건 자연스러운 일임
    // KanbanTask에서 isChangeable을 관리한다 -> TaskStatus를 가진 객체가 자기 상태를 관리하니까 맞지 않나? 일단 아니란걸 알았음 근데 왜 아닐까
    // 1. 정보 전문가 원칙을 따르고 있음
    // 2. 변경의 이유(전이 규칙)이 같은 것들을 묶고 있음
    // 3. KanbanTask에서 전이 규칙을 체크하게 되면 TaskStatus가 판단하는 게 아닌 외부에서 판단을 하게 됨. Tell, Don't Ask를 지키고 있음

    fun isChangeable(
        other: TaskStatus,
        assignee: Assignee?,
    ): ChangeableResult {
        return when (this) {
            TO_DO -> when {
                assignee == null -> ChangeableResult.NotAssigned
                other == IN_PROGRESS -> ChangeableResult.Changeable
                else -> ChangeableResult.NotChangeable
            }
            IN_PROGRESS -> if (other == TO_DO || other == REVIEW) ChangeableResult.Changeable else ChangeableResult.NotChangeable
            REVIEW -> if (other == IN_PROGRESS || other == DONE) ChangeableResult.Changeable else ChangeableResult.NotChangeable
            DONE -> if (other == TO_DO) ChangeableResult.Changeable else ChangeableResult.NotChangeable
        }
    }

    val isRemovable: Boolean
        get() = when (this) {
            TO_DO -> true
            IN_PROGRESS -> true
            REVIEW -> false
            DONE -> false
        }
}
