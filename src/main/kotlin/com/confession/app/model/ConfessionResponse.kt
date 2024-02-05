package com.confession.app.model

import com.confession.app.meter.MoodMeterElement

class ConfessionResponse(
    val presentMood: MoodMeterElement?,
    val understandingAnswer: String,
    val expressingAnswer: String,
    val regulatingAnswer: String,
    val desiredMood: MoodMeterElement?,
    val howCanYouFeelAnswer: String,
    val doneWellAnswer: String,
    val toImproveAnswer: String,
    val complancencyAnswer: String,
    val tasks: List<Task>
) {
}