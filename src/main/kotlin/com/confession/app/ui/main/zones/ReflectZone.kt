package com.confession.app.ui.main.zones

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.confession.app.ResString
import com.confession.app.service.MoodViewModel
import com.confession.app.service.ReflectViewModel
import com.confession.app.ui.misc.ResetButton
import com.confession.app.ui.qa.QAPrompt
import com.confession.app.util.understandingFormattedText

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
            val currentMoodState = moodViewModel.currentMood.collectAsState()
            val currentMood = currentMoodState.value

            val understandingAnswerState = reflectViewModel.understandingAnswer.collectAsState()
            val understandingAnswer = understandingAnswerState.value

            QAPrompt(
                questionText = understandingFormattedText(currentMood),
                answerState = understandingAnswer,
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
            val expressingAnswerState = reflectViewModel.expressingAnswer.collectAsState()
            val expressingAnswer = expressingAnswerState.value

            QAPrompt(
                questionText = ResString.questionExpressing,
                answerState = expressingAnswer,
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
            val regulatingAnswerState = reflectViewModel.regulatingAnswer.collectAsState()
            val regulatingAnswer = regulatingAnswerState.value

            QAPrompt(
                questionText = ResString.questionRegulating,
                answerState = regulatingAnswer,
                onAnswerChange = {
                    reflectViewModel.setRegulatingAnswer(it)
                }
            )
        }

        ResetButton(
            onReset = {
                reflectViewModel.reset()
            }
        )
    }
}