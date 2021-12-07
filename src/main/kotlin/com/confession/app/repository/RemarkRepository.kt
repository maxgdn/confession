package com.confession.app.repository

import com.confession.app.database.Remark
import kotlinx.coroutines.flow.Flow

interface RemarkRepository {
    suspend fun getRemarks(): Flow<List<Remark>>
    suspend fun createOne(
        question: String,
        answer: String,
    )
}