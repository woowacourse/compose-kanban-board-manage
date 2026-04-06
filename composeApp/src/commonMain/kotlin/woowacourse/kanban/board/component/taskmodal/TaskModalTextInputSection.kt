package woowacourse.kanban.board.component.taskmodal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import woowacourse.kanban.board.model.taskmodal.TextInputState
import woowacourse.kanban.board.model.taskmodal.TextInputValue
import woowacourse.kanban.board.model.taskcard.TaskTag
import woowacourse.kanban.board.model.taskcard.TaskTags
import woowacourse.kanban.board.model.taskcard.TaskTitle

@Composable
fun TaskModalTextInputSection(
    titleInputState: TextInputState,
    descriptionInputState: TextInputState,
    tagsInputState: TextInputState,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        TaskModalTextInput(
            textInputValue = TextInputValue.TITLE,
            value = titleInputState.value,
            modifier = Modifier.height(100.dp),
            onTextChange = titleInputState.onChange,
            isError = titleInputState.isError,
        )
        TaskModalTextInput(
            textInputValue = TextInputValue.DESCRIPTION,
            value = descriptionInputState.value,
            singleLine = false,
            modifier = Modifier.height(200.dp),
            onTextChange = descriptionInputState.onChange,
        )
        TaskModalTextInput(
            textInputValue = TextInputValue.TAGS,
            value = tagsInputState.value,
            modifier = Modifier.height(100.dp),
            onTextChange = tagsInputState.onChange,
            isError = tagsInputState.isError,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TaskModalTextInputSectionInvalidTitlePreview() {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var tags by remember { mutableStateOf("") }

    val isTaskTitleValid by remember {
        derivedStateOf {
            TaskTitle.isTitleValid(title)
        }
    }

    val isTagValid by remember {
        derivedStateOf {
            val extractedTaskTags = TaskTag.extractedTags(tags)
            TaskTags.isTagsValid(extractedTaskTags)
        }
    }
    val titleInputState = TextInputState(
        value = title,
        onChange = { title = it },
        isError = isTaskTitleValid.not(),
    )

    val descriptionInputState = TextInputState(
        value = description,
        onChange = { description = it },
    )

    val tagsInputState = TextInputState(
        value = tags,
        onChange = { tags = it },
        isError = isTagValid.not(),
    )

    Column {
        TaskModalTextInput(
            textInputValue = TextInputValue.TITLE,
            value = titleInputState.value,
            modifier = Modifier.height(100.dp),
            onTextChange = titleInputState.onChange,
            isError = titleInputState.isError,
        )
        TaskModalTextInput(
            textInputValue = TextInputValue.DESCRIPTION,
            value = descriptionInputState.value,
            singleLine = false,
            modifier = Modifier.height(200.dp),
            onTextChange = descriptionInputState.onChange,
        )
        TaskModalTextInput(
            textInputValue = TextInputValue.TAGS,
            value = tagsInputState.value,
            onTextChange = tagsInputState.onChange,
            modifier = Modifier.height(100.dp),
            isError = tagsInputState.isError,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TaskModalTextInputSectionValidTitlePreview() {
    var title by remember { mutableStateOf("제목") }
    var description by remember { mutableStateOf("") }
    var tags by remember { mutableStateOf("") }

    val isTaskTitleValid by remember {
        derivedStateOf {
            TaskTitle.isTitleValid(title)
        }
    }

    val isTagValid by remember {
        derivedStateOf {
            val extractedTaskTags = TaskTag.extractedTags(tags)
            TaskTags.isTagsValid(extractedTaskTags)
        }
    }
    val titleInputState = TextInputState(
        value = title,
        onChange = { title = it },
        isError = isTaskTitleValid.not(),
    )

    val descriptionInputState = TextInputState(
        value = description,
        onChange = { description = it },
    )

    val tagsInputState = TextInputState(
        value = tags,
        onChange = { tags = it },
        isError = isTagValid.not(),
    )

    Column {
        TaskModalTextInput(
            textInputValue = TextInputValue.TITLE,
            value = titleInputState.value,
            modifier = Modifier.height(100.dp),
            onTextChange = titleInputState.onChange,
            isError = titleInputState.isError,
        )
        TaskModalTextInput(
            textInputValue = TextInputValue.DESCRIPTION,
            value = descriptionInputState.value,
            singleLine = false,
            modifier = Modifier.height(200.dp),
            onTextChange = descriptionInputState.onChange,
        )
        TaskModalTextInput(
            textInputValue = TextInputValue.TAGS,
            value = tagsInputState.value,
            onTextChange = tagsInputState.onChange,
            modifier = Modifier.height(100.dp),
            isError = tagsInputState.isError,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TaskModalTextInputSectionInvalidTagPreview() {
    var title by remember { mutableStateOf("제목") }
    var description by remember { mutableStateOf("") }
    var tags by remember { mutableStateOf("태그,,") }

    val isTaskTitleValid by remember {
        derivedStateOf {
            TaskTitle.isTitleValid(title)
        }
    }

    val isTaskTagValid by remember {
        derivedStateOf {
            TaskTag.isTagValid(tags)
        }
    }
    val titleInputState = TextInputState(
        value = title,
        onChange = { title = it },
        isError = isTaskTitleValid.not(),
    )

    val descriptionInputState = TextInputState(
        value = description,
        onChange = { description = it },
    )

    val tagsInputState = TextInputState(
        value = tags,
        onChange = { tags = it },
        isError = isTaskTagValid.not(),
    )

    Column {
        TaskModalTextInput(
            textInputValue = TextInputValue.TITLE,
            value = titleInputState.value,
            modifier = Modifier.height(100.dp),
            onTextChange = titleInputState.onChange,
            isError = titleInputState.isError,
        )
        TaskModalTextInput(
            textInputValue = TextInputValue.DESCRIPTION,
            value = descriptionInputState.value,
            singleLine = false,
            modifier = Modifier.height(200.dp),
            onTextChange = descriptionInputState.onChange,
        )
        TaskModalTextInput(
            textInputValue = TextInputValue.TAGS,
            value = tagsInputState.value,
            onTextChange = tagsInputState.onChange,
            modifier = Modifier.height(100.dp),
            isError = tagsInputState.isError,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TaskModalTextInputSectionValidTagPreview() {
    var title by remember { mutableStateOf("제목") }
    var description by remember { mutableStateOf("") }
    var tags by remember { mutableStateOf("태그1,태그2,태그3") }

    val isTaskTitleValid by remember {
        derivedStateOf {
            TaskTitle.isTitleValid(title)
        }
    }

    val isTagValid by remember {
        derivedStateOf {
            val extractedTaskTags = TaskTag.extractedTags(tags)
            TaskTags.isTagsValid(extractedTaskTags)
        }
    }
    val titleInputState = TextInputState(
        value = title,
        onChange = { title = it },
        isError = isTaskTitleValid.not(),
    )

    val descriptionInputState = TextInputState(
        value = description,
        onChange = { description = it },
    )

    val tagsInputState = TextInputState(
        value = tags,
        onChange = { tags = it },
        isError = isTagValid.not(),
    )

    Column {
        TaskModalTextInput(
            textInputValue = TextInputValue.TITLE,
            value = titleInputState.value,
            modifier = Modifier.height(100.dp),
            onTextChange = titleInputState.onChange,
            isError = titleInputState.isError,
        )
        TaskModalTextInput(
            textInputValue = TextInputValue.DESCRIPTION,
            value = descriptionInputState.value,
            singleLine = false,
            modifier = Modifier.height(200.dp),
            onTextChange = descriptionInputState.onChange,
        )
        TaskModalTextInput(
            textInputValue = TextInputValue.TAGS,
            value = tagsInputState.value,
            onTextChange = tagsInputState.onChange,
            modifier = Modifier.height(100.dp),
            isError = tagsInputState.isError,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TaskModalTextInputSectionAllValueInputPreview() {
    var title by remember { mutableStateOf("제목") }
    var description by remember { mutableStateOf("설명이에요") }
    var tags by remember { mutableStateOf("태그") }

    val isTaskTitleValid by remember {
        derivedStateOf {
            TaskTitle.isTitleValid(title)
        }
    }

    val isTagValid by remember {
        derivedStateOf {
            val extractedTaskTags = TaskTag.extractedTags(tags)
            TaskTags.isTagsValid(extractedTaskTags)
        }
    }
    val titleInputState = TextInputState(
        value = title,
        onChange = { title = it },
        isError = isTaskTitleValid.not(),
    )

    val descriptionInputState = TextInputState(
        value = description,
        onChange = { description = it },
    )

    val tagsInputState = TextInputState(
        value = tags,
        onChange = { tags = it },
        isError = isTagValid.not(),
    )

    Column {
        TaskModalTextInput(
            textInputValue = TextInputValue.TITLE,
            value = titleInputState.value,
            modifier = Modifier.height(100.dp),
            onTextChange = titleInputState.onChange,
            isError = titleInputState.isError,
        )
        TaskModalTextInput(
            textInputValue = TextInputValue.DESCRIPTION,
            value = descriptionInputState.value,
            singleLine = false,
            modifier = Modifier.height(200.dp),
            onTextChange = descriptionInputState.onChange,
        )
        TaskModalTextInput(
            textInputValue = TextInputValue.TAGS,
            value = tagsInputState.value,
            onTextChange = tagsInputState.onChange,
            modifier = Modifier.height(100.dp),
            isError = tagsInputState.isError,
        )
    }
}
