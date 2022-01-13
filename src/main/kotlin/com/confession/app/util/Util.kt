package com.confession.app.util

import com.confession.app.ResString
import com.confession.app.meter.MoodMeterElement
import com.confession.app.model.Task
import kotlin.time.DurationUnit
import kotlin.time.ExperimentalTime

fun understandingFormattedText(
    moodMeterElement: MoodMeterElement?,
): String {
    return String.format(
        ResString.questionUnderstanding,
        moodMeterElement?.mood ?: ResString.questionUnderstandingEmpty
    )
}

fun howCanYouFeelFormattedText(
    moodMeterElement: MoodMeterElement?
): String {
    return String.format(
        ResString.questionHowCanYouFeel,
        moodMeterElement?.mood ?: ResString.questionHowCanYouFeelEmpty
    )
}

@OptIn(ExperimentalTime::class)
fun totalTaskTimeInHours(tasks: List<Task>): String {
    val timeInHours =
        if(tasks.isNotEmpty())
            tasks.map { it.duration }
                .reduce { acc, duration -> acc.plus(duration) }
                .toDouble(DurationUnit.HOURS)
        else 0

    return "%.${4}f".format(timeInHours.toDouble())
}

fun tasksAsString(tasksSize: Int, timeInHoursFormatted: String): String {
    return "Number of Tasks: $tasksSize | $timeInHoursFormatted hours"
}
fun wordWrapPDF(string: String, amount: Int): List<String> {
    val size = string.length
    val into = string.length/amount

    val final = mutableListOf<String>()

    //sliding pane
    for(i in 0 until into) {
        val end = size.minus(1)
        val offset = i * amount
        val split = Integer.min(offset + amount, end)
        val partition = string.subSequence(offset, split)
        final.add(partition.toString())
    }

    return final
}

fun wordWrapReceipt(string: String, amount: Int): List<String> {
    val size = string.length
    val into = string.length/amount

    val final = mutableListOf<String>()

    //sliding pane
    for(i in 0..into) {
        val end = size
        val offset = i * amount
        val split = Integer.min(offset + amount, end)
        val partition = string.subSequence(offset, split)
        final.add(partition.toString())
    }

    return final
}