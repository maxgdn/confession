package com.confession.app.ui.main.zones

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.confession.app.meter.MoodMeter
import com.confession.app.ui.mood.MoodMeterGraph

@Composable
fun RecognizeZone() {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier.verticalScroll(scrollState)
    ) {
        MoodMeterGraph(
            moodMeter = MoodMeter()
        )
    }

}