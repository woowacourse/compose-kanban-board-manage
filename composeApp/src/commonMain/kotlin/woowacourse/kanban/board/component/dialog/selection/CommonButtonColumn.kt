package woowacourse.kanban.board.component.dialog.selection

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import woowacourse.kanban.board.component.dialog.input.HeaderText
import woowacourse.kanban.board.domain.Status

@Composable
fun <T> CommonButtonColumn(
    header: String,
    items: List<T>,
    isSelected: (T) -> Boolean,
    onValueChange: (T) -> Unit,
    modifier: Modifier = Modifier,
    composable: @Composable (T, Boolean, () -> Unit) -> Unit,
) {
    Column(modifier) {
        HeaderText(title = header)
        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            modifier = Modifier.fillMaxWidth().heightIn(max = 100.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            userScrollEnabled = false,
        ) {
            items(
                items.size,
            ) { index ->
                composable(
                    items[index],
                    isSelected(items[index]),
                ) { onValueChange(items[index]) }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun StatusCommonButtonColumnPreview() {
    CommonButtonColumn(
        header = "상태 *",
        items = Status.entries,
        isSelected = { true },
        onValueChange = {},
    ) { status, isSelected, onClick ->
        StatusButton(status = status, isSelected = isSelected, onClick = onClick)
    }
}

@Preview(showBackground = true)
@Composable
private fun NamesCommonButtonColumnPreview() {
    CommonButtonColumn(
        header = "담당자",
        items = listOf("다이노", "페임스"),
        isSelected = { true },
        onValueChange = {},
    ) { name, isSelected, onClick ->
        CoachButton(name = name, isSelected = isSelected, onClick = onClick)
    }
}
