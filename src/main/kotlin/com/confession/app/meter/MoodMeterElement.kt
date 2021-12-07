package com.confession.app.meter

import com.confession.app.model.Mood

data class MoodMeterElement(
    val mood: Mood,
    val energy: Int,
    val pleasantness: Int
)
