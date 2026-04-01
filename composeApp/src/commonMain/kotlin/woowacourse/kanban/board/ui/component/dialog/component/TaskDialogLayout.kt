package woowacourse.kanban.board.ui.component.dialog.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun TaskDialogLayout(
    topBar: @Composable () -> Unit,
    bottomBar: @Composable () -> Unit,
    content: @Composable () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(Color.White)
            .width(672.dp)
            .padding(all = 24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
    ) {
        topBar()

        HorizontalDivider(color = Color.Black, thickness = Dp.Hairline)

        content()

        HorizontalDivider(color = Color.Black, thickness = Dp.Hairline)

        bottomBar()
    }
}
