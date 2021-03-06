package com.confession.app.ui.main.zones

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.confession.app.ResString
import com.confession.app.meter.MoodMeter
import com.confession.app.service.MoodViewModel
import com.confession.app.ui.mood.MoodMeterGraph
import com.confession.app.ui.qa.Question

@Composable
fun RecognizeZone(moodViewModel: MoodViewModel) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier.verticalScroll(scrollState)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Question(ResString.questionRecognizing)
        }

        Spacer(Modifier.size(20.dp))

        val currentMoodState = moodViewModel.currentMood.collectAsState()

        MoodMeterGraph(
            moodMeter = MoodMeter(),
            moodState = currentMoodState.value,
            onMoodChange = {
                it?.let { moodViewModel.setCurrentMood(it) }
            },
            onMoodReset = {
                moodViewModel.resetCurrentMood()
            }
        )
    }
}