package com.confession.app.service

import com.confession.app.database.Remark
import com.confession.app.repository.RemarkRepository
import com.confession.app.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class RemarkViewModel(remarkRepository: RemarkRepository) {
    private val repository = remarkRepository

    private val _remarkState: MutableStateFlow<Resource<List<Remark>>> = MutableStateFlow(Resource.none(null))
    val remarkState: StateFlow<Resource<List<Remark>>> = _remarkState

    private val _howCanYouFeel: MutableStateFlow<String> = MutableStateFlow("")
    val howCanYouFeel: StateFlow<String> = _howCanYouFeel

    private val _oneThingWell: MutableStateFlow<String> = MutableStateFlow("")
    val oneThingWell: StateFlow<String> = _oneThingWell

    private val _oneThingToImproveOn: MutableStateFlow<String> = MutableStateFlow("")
    val oneThingToImproveOn: StateFlow<String> = _oneThingToImproveOn

    fun setHowCanYouFeelAnswer(text: String) {
        _howCanYouFeel.value = text
    }

    fun setDoingOneThingWell(text: String) {
        _oneThingWell.value = text
    }

    fun setOneThingToImproveOn(text: String) {
        _oneThingToImproveOn.value = text
    }

    fun reset() {
        val emptyString = ""
        this.setHowCanYouFeelAnswer(emptyString)
        this.setDoingOneThingWell(emptyString)
        this.setOneThingToImproveOn(emptyString)
    }
}