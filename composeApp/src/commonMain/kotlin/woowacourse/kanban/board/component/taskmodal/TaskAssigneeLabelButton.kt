package woowacourse.kanban.board.component.taskmodal

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kanbanboard.composeapp.generated.resources.Res
import kanbanboard.composeapp.generated.resources.profile
import org.jetbrains.compose.resources.painterResource
import woowacourse.kanban.board.Blue50
import woowacourse.kanban.board.Blue80
import woowacourse.kanban.board.Gray20
import woowacourse.kanban.board.Gray70
import woowacourse.kanban.board.model.taskcard.Assignee

@Composable
fun TaskAssigneeLabelButton(
    currentState: Assignee?,
    myState: Assignee,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {

    val backgroundColor = if (currentState == myState) Blue80 else Color.Transparent
    val borderColor = if (currentState == myState) Blue50 else Gray70

    Box(
        modifier = modifier
            .width(200.dp)
            .clip(RoundedCornerShape(10.dp))
            .border(1.dp, color = borderColor, shape = RoundedCornerShape(10.dp))
            .background(color = backgroundColor)
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 20.dp),
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
        ) {
            Image(
                painter = painterResource(myState.icon),
                contentDescription = "프로필 이미지",
                modifier = Modifier.size(24.dp),
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = myState.nickname,
                modifier = Modifier.fillMaxWidth(),
                fontSize = 16.sp,
                color = Gray20,
                fontWeight = FontWeight.Normal,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TaskAssigneeLabelButtonNotChoicePreview() {
    TaskAssigneeLabelButton(
        currentState = Assignee("다이노", Res.drawable.profile),
        myState = Assignee("페임스", Res.drawable.profile),
        onClick = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun TaskAssigneeLabelButtonChoicePreview() {
    TaskAssigneeLabelButton(
        currentState = Assignee("페임스", Res.drawable.profile),
        myState = Assignee("페임스", Res.drawable.profile),
        onClick = {}
    )
}
