package woowacourse.kanban.board.component.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import woowacourse.kanban.board.constant.CREATE_BG
import woowacourse.kanban.board.constant.CREATE_BG_ERROR
import woowacourse.kanban.board.constant.PRIMARY_TEXT
import woowacourse.kanban.board.constant.TEXT_FIELD_ERROR
import woowacourse.kanban.board.model.BoardData
import woowacourse.kanban.board.model.DialogStatus
import woowacourse.kanban.board.model.DialogStatus.CREATE
import woowacourse.kanban.board.model.DialogStatus.EDIT
import woowacourse.kanban.board.state.BoardDataState

@Composable
fun TaskDialog(
    modifier: Modifier = Modifier,
    boardDataState: BoardDataState,
    dialogStatus: DialogStatus,
    onTaskCreate: (BoardData) -> Unit,
    onEditTask: (BoardData) -> Unit,
    onDeleteTask: (BoardData) -> Unit,
    onDismissRequest: () -> Unit,
) {

    val isCreateError =
        boardDataState.isTitleError ||
            boardDataState.isTagsError ||
            boardDataState.titleInputValue.isBlank() ||
            boardDataState.isNicknameError

    Column(
        modifier = modifier
            .background(color = Color.White)
            .verticalScroll(rememberScrollState()),
    ) {
        DialogBar(
            modifier = Modifier.padding(
                vertical = 28.dp,
                horizontal = 24.dp,
            )
                .fillMaxWidth(),
            title = when (dialogStatus) {
                CREATE -> "새 태스크 생성"
                EDIT -> "기존 태스크 수정"
            },
            onClick = onDismissRequest,
        )
        HorizontalDivider()
        Column(
            modifier = Modifier.padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            CommonTextColumn(
                modifier = Modifier.fillMaxWidth(),
                title = "제목 *",
                content = boardDataState.titleInputValue,
                onValueChange = { boardDataState.titleOnValueChange(it) },
                isError = boardDataState.isTitleError,
                placeholderText = "태스크 제목을 입력하세요",
            )
            CommonTextColumn(
                modifier = Modifier.fillMaxWidth().height(116.dp),
                title = "설명",
                content = boardDataState.descriptionInputValue,
                onValueChange = { boardDataState.descriptionOnValueChange(it) },
                placeholderText = "태스크에 대한 자세한 설명을 입력하세요",
            )
            CommonTextColumn(
                modifier = Modifier.fillMaxWidth(),
                title = "태그 *",
                content = boardDataState.tagsInputValue,
                onValueChange = { boardDataState.tagsOnValueChange(it) },
                isError = boardDataState.isTagsError,
                placeholderText = "태그를 쉼표로 구분하여 입력하세요 (예: 버그, 긴급)",
                isSupportingText = true,
            )
            CommonButtonColumn(
                header = "상태 *",
                items = boardDataState.statuses,
                isSelected = { boardDataState.isSelectedStatus(it) },
                onValueChange = { boardDataState.statusOnValueChange(it) },
            ) { status, isSelected, onClick ->
                StatusButton(status = status, isSelected = isSelected, onClick = onClick)
            }
            CommonButtonColumn(
                header = "담당자 *",
                items = boardDataState.names(),
                isSelected = { boardDataState.isSelectedName(it) },
                onValueChange = { boardDataState.nameOnValueChange(it) },
            ) { name, isSelected, onClick ->
                CoachButton(
                    name = boardDataState.getNickname(name),
                    isSelected = isSelected,
                    onClick = onClick,
                )
            }
            HorizontalDivider()
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = boardDataState.getDialogMessage(),
                    color = Color(TEXT_FIELD_ERROR),
                )

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    when (dialogStatus) {
                        CREATE -> {
                            FooterButton(
                                modifier = Modifier,
                                text = "취소",
                                backgroundColor = Color.White,
                                textColor = Color(PRIMARY_TEXT),
                                onClick = onDismissRequest,
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            FooterButton(
                                modifier = Modifier,
                                text = "생성",
                                textColor = Color.White,
                                backgroundColor = if (!isCreateError) Color(CREATE_BG) else Color(CREATE_BG_ERROR),
                                onClick = {
                                    val boardData = BoardData(
                                        title = boardDataState.titleInputValue,
                                        description = boardDataState.descriptionInputValue,
                                        tags = boardDataState.changeTagsValue(),
                                        status = boardDataState.statusValue,
                                        nickname = boardDataState.nameValue,
                                    )
                                    onTaskCreate(boardData)
                                },
                                enabled = !isCreateError,
                            )
                        }

                        EDIT -> {
                            FooterButton(
                                modifier = Modifier,
                                text = "취소",
                                backgroundColor = Color.White,
                                textColor = Color(PRIMARY_TEXT),
                                onClick = onDismissRequest,
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            FooterButton(
                                modifier = Modifier,
                                text = "삭제",
                                textColor = Color.White,
                                backgroundColor = Color(0xFFDB6365),
                                onClick = {
                                    val boardData = BoardData(
                                        id = boardDataState.id,
                                        title = boardDataState.titleInputValue,
                                        description = boardDataState.descriptionInputValue,
                                        tags = boardDataState.changeTagsValue(),
                                        status = boardDataState.statusValue,
                                        nickname = boardDataState.nameValue,
                                    )
                                    onDeleteTask(boardData)
                                },
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            FooterButton(
                                modifier = Modifier,
                                text = "수정",
                                textColor = Color.White,
                                backgroundColor = if (!isCreateError) Color(CREATE_BG) else Color(CREATE_BG_ERROR),
                                onClick = {
                                    val boardData = BoardData(
                                        id = boardDataState.id,
                                        title = boardDataState.titleInputValue,
                                        description = boardDataState.descriptionInputValue,
                                        tags = boardDataState.changeTagsValue(),
                                        status = boardDataState.statusValue,
                                        nickname = boardDataState.nameValue,
                                    )
                                    onEditTask(boardData)
                                },
                                enabled = !isCreateError,
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun FooterButton(
    text: String,
    backgroundColor: Color,
    textColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(
                width = 68.dp,
                height = 44.dp,
            )
            .clip(shape = RoundedCornerShape(10.dp))
            .background(backgroundColor).clickable(onClick = onClick, enabled = enabled),
    ) {
        Text(text, color = textColor)
    }
}

@Preview(showBackground = true)
@Composable
private fun CancelFooterButtonPreview() {
    FooterButton(
        modifier = Modifier,
        text = "취소",
        backgroundColor = Color.White,
        textColor = Color(PRIMARY_TEXT),
        onClick = {},
    )
}

@Preview(showBackground = true)
@Composable
private fun CreateEnableFooterButtonPreview() {
    FooterButton(
        modifier = Modifier,
        text = "생성",
        textColor = Color.White,
        backgroundColor = Color(CREATE_BG),
        onClick = {},
        enabled = true,
    )
}

@Preview(showBackground = true)
@Composable
private fun CreateNotEnableFooterButtonPreview() {
    FooterButton(
        modifier = Modifier,
        text = "생성",
        textColor = Color.White,
        backgroundColor = Color(CREATE_BG_ERROR),
        onClick = {},
        enabled = false,
    )
}
