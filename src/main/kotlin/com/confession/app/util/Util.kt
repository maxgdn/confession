package com.confession.app.util

import com.confession.app.ResString
import com.confession.app.meter.MoodMeterElement

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