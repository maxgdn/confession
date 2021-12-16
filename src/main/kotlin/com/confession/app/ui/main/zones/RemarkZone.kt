package com.confession.app.ui.main.zones

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.confession.app.ResString
import com.confession.app.service.MoodViewModel
import com.confession.app.service.RemarkViewModel
import com.confession.app.ui.qa.QAPrompt
import kotlinx.coroutines.launch

@Composable
fun RemarkZone(
    remarkViewModel: RemarkViewModel,
    moodViewModel: MoodViewModel
) {
    val scope = rememberCoroutineScope()

    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier.verticalScroll(scrollState).fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(1/3f),
            horizontalArrangement = Arrangement.Center
        ) {
            val desiredMood = moodViewModel.desiredMood.value

            val howCanIFeelState = remarkViewModel.howCanYouFeel.value

            QAPrompt(
                questionText = String.format(
                    ResString.questionHowCanYouFeel,
                    desiredMood?.mood ?: ResString.questionHowCanYouFeelEmpty
                ),
                answerState = howCanIFeelState,
                onAnswerChange = {
                    remarkViewModel.setHowCanYouFeelAnswer(it)
                }
            )
        }

        Spacer(Modifier.size(40.dp))

        Row(
            modifier = Modifier.fillMaxWidth(1/3f),
            horizontalArrangement = Arrangement.Center
        ) {
            val oneThingWellAnswerState = remarkViewModel.oneThingWell.value

            QAPrompt(
                questionText = ResString.questionOneThingDoneWell,
                answerState = oneThingWellAnswerState,
                onAnswerChange = {
                    remarkViewModel.setDoingOneThingWell(it)
                }
            )
        }

        Spacer(Modifier.size(40.dp))

        Row(
            modifier = Modifier.fillMaxWidth(1/3f),
            horizontalArrangement = Arrangement.Center
        ) {
            val regulatingAnswerState = remarkViewModel.oneThingToImproveOn.value

            QAPrompt(
                questionText = ResString.questionOneThingToImproveOn,
                answerState = regulatingAnswerState,
                onAnswerChange = {
                    remarkViewModel.setOneThingToImproveOn(it)
                }
            )
        }
    }

    //one thing to improve on

}