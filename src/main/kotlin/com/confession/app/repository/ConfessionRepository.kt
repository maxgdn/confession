package com.confession.app.repository

import com.confession.app.database.Confession
import kotlinx.coroutines.flow.Flow

interface ConfessionRepository {
    fun getConfessions(): Flow<List<Confession>>

    fun createConfession(
        
    )
}