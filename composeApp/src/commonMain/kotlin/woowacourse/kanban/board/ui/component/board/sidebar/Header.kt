package woowacourse.kanban.board.ui.component.board.sidebar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import woowacourse.kanban.board.ui.KanbanColors
import woowacourse.kanban.board.ui.KanbanTypography

@Composable
fun Header(
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Text(
            text = title,
            color = KanbanColors.sideBarTitle,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            lineHeight = 28.sp,
            letterSpacing = (-0.44).sp,
        )

        Text(
            text = subtitle,
            style = KanbanTypography.subtitle,
            lineHeight = 20.sp,
            letterSpacing = (-0.15).sp,
        )
    }
}

@Preview(showBackground = true, name = "헤더")
@Composable
private fun HeaderPreview() {
    Header(title = "프로젝트", subtitle = "4주차 미션 보드", modifier = Modifier)
}
