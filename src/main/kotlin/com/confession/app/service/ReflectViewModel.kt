package com.confession.app.service

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ReflectViewModel() {
    private val _understandingAnswer: MutableStateFlow<String> = MutableStateFlow("")
    val understandingAnswer: StateFlow<String> = _understandingAnswer

    private val _expressingAnswer: MutableStateFlow<String> = MutableStateFlow("")
    val expressingAnswer: StateFlow<String> = _expressingAnswer

    private val _regulatingAnswer: MutableStateFlow<String> = MutableStateFlow("")
    val regulatingAnswer: StateFlow<String> = _regulatingAnswer

    fun setUnderstandingAnswer(text: String) {
        _understandingAnswer.value = text
    }

    fun setExpressingAnswer(text: String) {
        _expressingAnswer.value = text
    }

    fun setRegulatingAnswer(text: String) {
        _regulatingAnswer.value = text
    }

}