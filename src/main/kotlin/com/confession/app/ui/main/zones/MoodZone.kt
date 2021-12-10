package com.confession.app.ui.main.zones

import androidx.compose.runtime.Composable
import com.confession.app.meter.MoodMeter
import com.confession.app.ui.mood.MoodMeterGraph

@Composable
fun MoodZone() {
    MoodMeterGraph(
        moodMeter = MoodMeter()
    )
}