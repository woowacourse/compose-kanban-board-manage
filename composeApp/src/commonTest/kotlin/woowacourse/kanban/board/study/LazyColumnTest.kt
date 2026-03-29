package woowacourse.kanban.board.study

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.runComposeUiTest
import org.junit.Test

@OptIn(ExperimentalTestApi::class)
class LazyColumnTest {

    @Test
    fun column() = runComposeUiTest {
        setContent {
            Column {
                list.forEach {
                    Text(it, modifier = Modifier.testTag("text"))
                }
            }
        }

        onAllNodesWithTag("text")
            .assertCountEquals(10_000)
    }

//    @Test
//    fun lazyColumn() = runComposeUiTest {
//        setContent {
//            LazyColumn {
//                items(list.size) { index ->
//                    Text(list[index], modifier = Modifier.testTag("text"))
//                }
//            }
//
//            onAllNodesWithTag("text")
//                .assertCountEquals(48)
//        }
//    }
//
//    @Test
//    fun lazyColumn2() = runComposeUiTest {
//        setContent {
//            LazyColumn(modifier = Modifier.testTag("lazyColumn")) {
//                items(list.size) { index ->
//                    Text(list[index], modifier = Modifier.testTag("text"))
//                }
//            }
//
//            onNodeWithTag("lazyColumn")
//                .performScrollToIndex(9_999)
//            onAllNodesWithTag("text")
//                .assertCountEquals(48)
//        }
//    }

    companion object {
        val list: List<String> = List(10_000) { it.toString() }
    }
}
