package com.confession.app.repository

import com.confession.app.ConfessionDatabase
import com.confession.app.database.Confession
import com.confession.app.sqldelight.DatabaseDriverFactory
import kotlinx.coroutines.flow.Flow

class ConfessionRepositoryImpl(
    databaseDriverFactory: DatabaseDriverFactory
): ConfessionRepository {
    private val database =
        ConfessionDatabase.invoke(
            databaseDriverFactory.createDriver(),
        )

    private val dbQuery = database.confessionQueries
    override fun getConfessions(): Flow<List<Confession>> {
        TODO("Not yet implemented")
    }

    override fun createConfession() {
        TODO("Not yet implemented")
    }


}