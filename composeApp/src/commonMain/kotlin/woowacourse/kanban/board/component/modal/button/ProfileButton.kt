package woowacourse.kanban.board.component.modal.button

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
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import woowacourse.kanban.board.component.util.Blue50
import woowacourse.kanban.board.component.util.Blue80
import woowacourse.kanban.board.component.util.Gray20
import woowacourse.kanban.board.component.util.Gray70
import woowacourse.kanban.board.model.taskcard.Profile

@Composable
fun ProfileButton(
    currentState: Profile,
    myState: Profile,
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
            myState.icon.toDrawableResource()?.let {
                Image(
                    painter = painterResource(it),
                    contentDescription = "프로필 이미지",
                    modifier = Modifier.size(24.dp),
                )
            }
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
private fun ProfileButtonNotChoicePreview() {
    ProfileButton(
        currentState = Profile("다이노"),
        myState = Profile("페임스"),
        onClick = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun ProfileButtonChoicePreview() {
    ProfileButton(
        currentState = Profile("페임스"),
        myState = Profile("페임스"),
        onClick = {}
    )
}

private fun String.toDrawableResource(): DrawableResource? = when (this) {
    "DEFAULT" -> Res.drawable.profile
    else -> null
}
