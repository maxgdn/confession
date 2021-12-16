package com.confession.app.service

import com.confession.app.model.ConfessionResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ConfessionViewModel(
    private val moodViewModel: MoodViewModel,
    private val reflectViewModel: ReflectViewModel,
    private val remarkViewModel: RemarkViewModel,
    private val accomplishViewModel: AccomplishViewModel
) {

    private val _response: MutableStateFlow<ConfessionResponse?> = MutableStateFlow(null)
    val response: StateFlow<ConfessionResponse?> = _response

    fun generateResponse() {
        val currentMood = moodViewModel.currentMood.value

        val understandingAnswer = reflectViewModel.understandingAnswer.value
        val expressingAnswer = reflectViewModel.expressingAnswer.value
        val regulatingAnswer = reflectViewModel.regulatingAnswer.value

        val desiredMood = moodViewModel.desiredMood.value

        val howCanYouFeel = remarkViewModel.howCanYouFeel.value
        val oneThingDoneWellAnswer = remarkViewModel.oneThingWell.value
        val oneThingToImproveOn = remarkViewModel.oneThingToImproveOn.value

        val tasks = accomplishViewModel.tasksState.value

        _response.value = ConfessionResponse(
            presentMood = currentMood,
            understandingAnswer = understandingAnswer,
            expressingAnswer = expressingAnswer,
            regulatingAnswer = regulatingAnswer,
            desiredMood = desiredMood,
            howCanYouFeelAnswer = howCanYouFeel,
            doneWellAnswer = oneThingDoneWellAnswer,
            toImproveAnswer = oneThingToImproveOn,
            tasks = tasks
        )
    }

    //clears everything!
    fun clearResponse() {
        moodViewModel.resetCurrentMood()
        reflectViewModel.reset()
        moodViewModel.resetDesiredMood()
        remarkViewModel.reset()
        accomplishViewModel.reset()
        _response.value = null
    }

}