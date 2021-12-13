package com.confession.app.ui.qa

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue

@Composable
fun Answer(
    answerState: String,
    onAnswerChange: (String) -> Unit
) {
    val answerTextValue = remember { mutableStateOf(TextFieldValue(answerState)) }

    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = answerTextValue.value,
        singleLine = false,
        onValueChange = {
            answerTextValue.value = it
            onAnswerChange(it.text)
        }
    )
}