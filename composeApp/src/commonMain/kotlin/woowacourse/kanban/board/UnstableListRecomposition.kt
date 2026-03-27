package woowacourse.kanban.board

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun UnstableListRecomposition() {
    var count by remember { mutableStateOf(0) }
    Text("$count")

    val list by remember { mutableStateOf(listOf(1, 2, 3)) }
    MyList(list)

    Button(onClick = { count++ }) {
        Text("Increment")
    }
}

@Composable
fun MyList(list: List<Int>) {
    SideEffect {
        println("MyList recomposed")
    }
    Column {
        list.forEach {
            Text("$it")
        }
    }
}
