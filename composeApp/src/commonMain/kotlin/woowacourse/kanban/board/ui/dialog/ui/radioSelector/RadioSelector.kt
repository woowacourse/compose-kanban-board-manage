package woowacourse.kanban.board.ui.dialog.ui.radioSelector

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import woowacourse.kanban.board.ui.dialog.ui.HeaderText

@Composable
fun RadioSelector(
    header: String,
    listSize: Int,
    modifier: Modifier = Modifier,
    noneButton: (@Composable () -> Unit)? = null,
    itemContent: @Composable (index: Int) -> Unit,
) {
    Column(modifier) {
        HeaderText(title = header)
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            if (noneButton != null) {
                item { noneButton() }
            }
            items(
                listSize,
            ) { index ->
                itemContent(index)
            }
        }
    }
}
