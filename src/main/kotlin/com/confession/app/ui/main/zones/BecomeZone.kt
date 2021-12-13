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
fun BecomeZone(moodViewModel: MoodViewModel) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier.verticalScroll(scrollState)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Question(ResString.questionBecoming)
        }

        Spacer(Modifier.size(20.dp))

        val desiredMoodState = moodViewModel.desiredMood.collectAsState()

        MoodMeterGraph(
            moodMeter = MoodMeter(),
            moodState = desiredMoodState.value,
            onMoodChange = {
                it?.let { moodViewModel.setDesiredMood(it) }
            },
            onMoodReset = {
                moodViewModel.resetDesiredMood()
            }
        )
    }
}