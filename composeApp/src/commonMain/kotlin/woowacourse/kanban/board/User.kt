package woowacourse.kanban.board

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

data class User(var name: String, val age: Int = 10)

@Composable
fun UnstableRecomposition() {
    var count by remember { mutableStateOf(0) }
    val user by remember { mutableStateOf(User(name = "Belinda McCall")) }

    Column {
        UserProfile(user)
        Text("카운트: $count")
        Button(
            onClick = {
                count++
                user.name = "New Name"
            },
        ) {
            Text("Increment")
        }
    }
}

@Composable
fun UserProfile(user: User) {
    SideEffect {
        println("UserProfile recomposed")
    }
    Row {
        Text("이름: ${user.name} ")
        Text("나이: ${user.age} ")
    }
}
