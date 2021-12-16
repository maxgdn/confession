package com.confession.app.ui.qa

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue

@Composable
fun Answer(
    answerState: String,
    onAnswerChange: (String) -> Unit
) {
    val answerTextValue = remember { mutableStateOf("") }

    LaunchedEffect(answerState) {
        answerTextValue.value = answerState
    }

    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = answerTextValue.value,
        singleLine = false,
        onValueChange = {
            answerTextValue.value = it
            onAnswerChange(it)
        },
        trailingIcon = {
            Icon(
                modifier = Modifier.clickable {
                    answerTextValue.value = ""
                    onAnswerChange("")
                },
                imageVector = Icons.Default.Close,
                contentDescription = ""
            )
        }
    )
}