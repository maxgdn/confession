package com.confession.app.ui.qa

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun QAPrompt(
    questionText: String,
    answerState: String,
    onAnswerChange: (String) -> Unit
) {
    Column {
        Question(
            content = questionText
        )

        Spacer(Modifier.size(10.dp))

        Answer(
            answerState = answerState,
            onAnswerChange = onAnswerChange
        )
    }
}