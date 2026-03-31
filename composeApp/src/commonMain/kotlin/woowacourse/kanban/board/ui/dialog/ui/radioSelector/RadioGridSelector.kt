package woowacourse.kanban.board.ui.dialog.ui.radioSelector

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import woowacourse.kanban.board.ui.dialog.ui.HeaderText

@Composable
fun RadioGridSelector(
    header: String,
    listSize: Int,
    modifier: Modifier = Modifier,
    itemContent: @Composable (index: Int) -> Unit,
) {
    Column(modifier) {
        HeaderText(title = header)
        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            items(
                listSize,
            ) { index ->
                itemContent(index)
            }
        }
    }
}
