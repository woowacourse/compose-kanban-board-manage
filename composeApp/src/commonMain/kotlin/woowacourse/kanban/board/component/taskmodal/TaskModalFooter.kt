package woowacourse.kanban.board.component.taskmodal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import woowacourse.kanban.board.Blue50
import woowacourse.kanban.board.Gray20
import woowacourse.kanban.board.component.ComponentText

@Composable
fun TaskModalFooter(
    onClickClose: () -> Unit,
    footerButtonSection: @Composable () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
    ) {
        HorizontalDivider()
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
        ) {
            TaskModalFooterButton(
                containerColor = Color.Transparent,
                contentColor = Gray20,
                text = ComponentText.CANCEL_BUTTON,
                onClick = onClickClose,
            )
            Spacer(modifier = Modifier.width(12.dp))
            footerButtonSection()
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CreateTaskModalTaskModalFooterButtonEnabledPreview() {
    TaskModalFooter(
        onClickClose = {},
        footerButtonSection = {
            TaskModalFooterButton(
                enabled = true,
                containerColor = Blue50,
                text = ComponentText.CREATE_BUTTON,
                onClick = { },
            )
        },
    )
}

@Preview(showBackground = true)
@Composable
private fun CreateTaskModalTaskModalFooterButtonDisabledPreview() {
    TaskModalFooter(
        onClickClose = {},
        footerButtonSection = {
            TaskModalFooterButton(
                enabled = false,
                containerColor = Blue50,
                text = ComponentText.CREATE_BUTTON,
                onClick = { },
            )
        },
    )
}

@Preview(showBackground = true)
@Composable
private fun EditTaskModalFooterButtonEnabledPreview() {
    TaskModalFooter(
        onClickClose = {},
        footerButtonSection = {
            EditModalFooterButtons(
                isButtonEnabled = true,
                onDeleteClick = {},
                onEditClick = {},
            )
        },
    )
}

@Preview(showBackground = true)
@Composable
private fun EditTaskModalFooterButtonDisabledPreview() {
    TaskModalFooter(
        onClickClose = {},
        footerButtonSection = {
            EditModalFooterButtons(
                isButtonEnabled = false,
                onDeleteClick = {},
                onEditClick = {},
            )
        },
    )
}
