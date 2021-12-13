package com.confession.app.service

import com.confession.app.meter.MoodMeterElement
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MoodViewModel {

    private val _currentMood: MutableStateFlow<MoodMeterElement?> = MutableStateFlow(null)
    val currentMood: StateFlow<MoodMeterElement?> = _currentMood

    private val _desiredMood: MutableStateFlow<MoodMeterElement?> = MutableStateFlow(null)
    val desiredMood: StateFlow<MoodMeterElement?> = _desiredMood

    fun setCurrentMood(mood: MoodMeterElement) {
        _currentMood.value = mood
    }

    fun resetCurrentMood() {
        _currentMood.value = null
    }

    fun setDesiredMood(mood: MoodMeterElement) {
        _desiredMood.value = mood
    }

    fun resetDesiredMood() {
        _desiredMood.value = null
    }
}