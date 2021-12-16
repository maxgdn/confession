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
import com.confession.app.ui.misc.ResetButton
import com.confession.app.ui.qa.QAPrompt
import com.confession.app.util.howCanYouFeelFormattedText
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
            val desiredMoodState = moodViewModel.desiredMood.collectAsState()
            val desiredMood = desiredMoodState.value

            val howCanIFeelState = remarkViewModel.howCanYouFeel.collectAsState()
            val howCanIFeel = howCanIFeelState.value

            QAPrompt(
                questionText = howCanYouFeelFormattedText(desiredMood),
                answerState = howCanIFeel,
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
            val oneThingWellAnswerState = remarkViewModel.oneThingWell.collectAsState()
            val oneThingWellAnswer = oneThingWellAnswerState.value

            QAPrompt(
                questionText = ResString.questionOneThingDoneWell,
                answerState = oneThingWellAnswer,
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
            val oneThingToImproveOnState = remarkViewModel.oneThingToImproveOn.collectAsState()
            val oneThingToImproveOn = oneThingToImproveOnState.value

            QAPrompt(
                questionText = ResString.questionOneThingToImproveOn,
                answerState = oneThingToImproveOn,
                onAnswerChange = {
                    remarkViewModel.setOneThingToImproveOn(it)
                }
            )
        }
    }

    ResetButton(
        onReset = {
            remarkViewModel.reset()
        }
    )

}