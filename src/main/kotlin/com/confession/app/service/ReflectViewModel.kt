package com.confession.app.service

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ReflectViewModel() {
    private val _understandingAnswer: MutableStateFlow<String> = MutableStateFlow("")
    val understandingAnswer: StateFlow<String> = _understandingAnswer

    fun setUnderstandingAnswer(text: String) {
        _understandingAnswer.value = text
    }

    fun resetUnderstanding() {
        _understandingAnswer.value = ""
    }
}