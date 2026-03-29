package woowacourse.kanban.board.study

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.runComposeUiTest
import kotlin.test.Test
import org.assertj.core.api.Assertions.assertThat

@OptIn(ExperimentalTestApi::class)
class LaunchedEffectTest {

    @Test
    fun launchedEffect() = runComposeUiTest {
        var triggerCount = 0
        setContent {
            LaunchedEffect(key1 = Unit) {
                triggerCount++
            }
        }
        waitForIdle()
        assertThat(triggerCount).isEqualTo(1)
    }

//    @Test
//    fun launchedEffect1() = runComposeUiTest {
//        setContent {
//            var flag by remember { mutableStateOf(true) }
//            var triggerCount by remember { mutableStateOf(0) }
//            LaunchedEffect(key1 = flag) {
//                triggerCount++
//            }
//            Button(onClick = { flag = flag.not() }, modifier = Modifier.testTag("button")) {
//                Text(text = triggerCount.toString())
//            }
//        }
//        onNodeWithText("1").assertExists()
//        onNodeWithText("button").performClick()
//        onNodeWithText("2").assertExists()
//    }
//
//    @Test
//    fun `key1과 key2가 변경될 때마다 LaunchedEffect가 트리거되는지 확인`() = runComposeUiTest {
//        setContent {
//            var flag by remember { mutableStateOf(true) }
//            var flag2 by remember { mutableStateOf(true) }
//            var triggerCount by remember { mutableStateOf(0) }
//            LaunchedEffect(key1 = flag, key2 = flag2) {
//                triggerCount++
//            }
//            Text(text = triggerCount.toString())
//            Button(onClick = { flag = flag.not() }, modifier = Modifier.testTag("button1")) { }
//            Button(onClick = { flag2 = flag2.not() }, modifier = Modifier.testTag("button2")) { }
//        }
//        onNodeWithText("1", useUnmergedTree = true).assertExists()
//        onNodeWithText("button1").performClick()
//        onNodeWithText("2", useUnmergedTree = true).assertExists()
//        onNodeWithText("button2").performClick()
//        onNodeWithText("3", useUnmergedTree = true).assertExists()
//    }
}
