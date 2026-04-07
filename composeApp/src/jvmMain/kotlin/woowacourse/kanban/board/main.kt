package woowacourse.kanban.board

import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Kanban Board",
        state = rememberWindowState(width = 1440.dp, height = 900.dp),
    ) {
        App()
    }
}