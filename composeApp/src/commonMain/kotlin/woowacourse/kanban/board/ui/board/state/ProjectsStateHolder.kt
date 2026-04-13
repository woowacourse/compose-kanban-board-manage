package woowacourse.kanban.board.ui.board.state

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import java.util.UUID

class ProjectsStateHolder(initialProjects: List<ProjectState> = emptyList()) {
    private val _projects = mutableStateListOf<ProjectState>().apply { addAll(initialProjects) }
    var selectedProjectId by mutableStateOf(initialProjects.firstOrNull()?.id)
        private set

    val projects: List<ProjectState>
        get() = _projects
    val selectedProject: ProjectState?
        get() = _projects.find { it.id == selectedProjectId }

    fun selectProject(projectId: UUID) {
        selectedProjectId = projectId
    }
}
