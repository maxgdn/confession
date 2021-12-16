package com.confession.app.ui.main.zones

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.confession.app.ResString
import com.confession.app.service.MoodViewModel
import com.confession.app.service.ReflectViewModel
import com.confession.app.ui.qa.QAPrompt

@Composable
fun ReflectZone(
    moodViewModel: MoodViewModel,
    reflectViewModel: ReflectViewModel
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier.verticalScroll(scrollState).fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(1/3f),
            horizontalArrangement = Arrangement.Center
        ) {
            val currentMood = moodViewModel.currentMood.value

            val understandingAnswerState = reflectViewModel.understandingAnswer.value

            QAPrompt(
                questionText = String.format(ResString.questionUnderstanding,
                    currentMood?.mood ?: ResString.questionUnderstandingEmpty
                ),
                answerState = understandingAnswerState,
                onAnswerChange = {
                    reflectViewModel.setUnderstandingAnswer(it)
                }
            )
        }

        Spacer(Modifier.size(40.dp))

        Row(
            modifier = Modifier.fillMaxWidth(1/3f),
            horizontalArrangement = Arrangement.Center
        ) {
            val expressingAnswerState = reflectViewModel.expressingAnswer.value

            QAPrompt(
                questionText = ResString.questionExpressing,
                answerState = expressingAnswerState,
                onAnswerChange = {
                    reflectViewModel.setExpressingAnswer(it)
                }
            )
        }

        Spacer(Modifier.size(40.dp))

        Row(
            modifier = Modifier.fillMaxWidth(1/3f),
            horizontalArrangement = Arrangement.Center
        ) {
            val regulatingAnswerState = reflectViewModel.regulatingAnswer.value

            QAPrompt(
                questionText = ResString.questionRegulating,
                answerState = regulatingAnswerState,
                onAnswerChange = {
                    reflectViewModel.setRegulatingAnswer(it)
                }
            )
        }
    }
}