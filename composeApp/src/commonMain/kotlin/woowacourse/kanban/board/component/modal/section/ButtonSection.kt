package woowacourse.kanban.board.component.modal.section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import woowacourse.kanban.board.component.modal.button.ProfileButton
import woowacourse.kanban.board.component.modal.button.StateButton
import woowacourse.kanban.board.component.sample.ProfilePreviewData
import woowacourse.kanban.board.component.util.ComponentText
import woowacourse.kanban.board.component.util.Gray20
import woowacourse.kanban.board.model.taskcard.Profile
import woowacourse.kanban.board.model.taskcard.Status

@Composable
fun ButtonSection(
    state: Status,
    currentProfile: Profile,
    profiles: ImmutableList<Profile>,
    onStateClick: (Status) -> Unit,
    onProfileClick: (Profile) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            text = ComponentText.STATE_BUTTON_LABEL,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = Gray20,
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Status.entries.forEach { status ->
                StateButton(
                    currentState = state,
                    myState = status,
                    onClick = { onStateClick(status) },
                    modifier = Modifier.weight(1f),
                )
            }
        }
        Text(
            text = ComponentText.PROFILE_BUTTON_LABEL,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = Gray20,
        )
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            if (state == Status.TODO) ProfileButton(
                currentState = currentProfile,
                myState = Profile.NONE,
                onClick = { onProfileClick(Profile.NONE) }
            )
            profiles.forEach { profile ->
                ProfileButton(
                    currentState = currentProfile,
                    myState = profile,
                    onClick = { onProfileClick(profile) }
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun ButtonSectionTodoStatusPreview() {
    val profiles = ProfilePreviewData().values.toImmutableList()
    ButtonSection(
        state = Status.TODO,
        currentProfile = profiles[0],
        profiles = profiles,
        onStateClick = { },
        onProfileClick = {},
    )
}
