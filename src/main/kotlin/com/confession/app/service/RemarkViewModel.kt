package com.confession.app.service

import com.confession.app.database.Remark
import com.confession.app.repository.RemarkRepository
import com.confession.app.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect

class RemarkViewModel(remarkRepository: RemarkRepository) {
    private val repository = remarkRepository

    private val _remarkState: MutableStateFlow<Resource<List<Remark>>> = MutableStateFlow(Resource.none(null))
    val remarkState: StateFlow<Resource<List<Remark>>> = _remarkState

    suspend fun fetchAll() {
        repository.getRemarks().collect {
            try {
                _remarkState.value = Resource.success(it)
            } catch (e: Exception) {
                _remarkState.value = Resource.error(
                    msg = "Failed to get remarks",
                    data = null
                )
            }
        }
    }

    suspend fun createRemark(
        question: String,
        answer: String,
    ): Resource<Remark> {
        return try {
            repository.createOne(
                question = question,
                answer = answer
            )
            return Resource.success(
                null
            )
        } catch (e: Exception) {
            Resource.error(
                data = null,
                msg = "Failed to create remark"
            )
        }
    }
}