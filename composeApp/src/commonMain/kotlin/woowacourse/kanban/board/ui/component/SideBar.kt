package woowacourse.kanban.board.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SideBar(
    title: String,
    content: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    innerPadding: Dp = 16.dp,
    subtitle: String? = null,
) {
    Column(
        modifier = modifier,
    ) {
        SideBarHeader(title = title, subtitle = subtitle, modifier = Modifier.padding(innerPadding))
        HorizontalDivider(modifier = Modifier.height(1.dp).background(Color(0xffE5E7EB)))
        content()
    }
}

@Composable
private fun SideBarHeader(title: String, modifier: Modifier = Modifier, subtitle: String? = null) {
    Column(
        modifier = modifier,
    ) {
        Text(text = title, color = Color(0xff101828), fontSize = 18.sp, fontWeight = FontWeight.W600)
        subtitle?.let {
            Text(text = subtitle, color = Color(0xff6A7282), fontSize = 14.sp, fontWeight = FontWeight.W400)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SideBarPreview() {
    SideBar(
        title = "프로젝트",
        content = {
            SideBarTab(true, "프로젝트1", {})
        },
    )
}
