package com.confession.app.repository

import com.confession.app.ConfessionDatabase
import com.confession.app.database.Remark
import com.confession.app.sqldelight.DatabaseDriverFactory
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.flow.Flow
import java.util.*


class RemarkRepositoryImpl(
    databaseDriverFactory: DatabaseDriverFactory
): RemarkRepository {

    private val database =
        ConfessionDatabase.invoke(
            databaseDriverFactory.createDriver(),
        )
    private val dbQuery = database.remarkQueries

    override suspend fun getRemarks(): Flow<List<Remark>> {
        return dbQuery.selectAll().asFlow().mapToList()
    }

    override suspend fun createOne(question: String, answer: String) {
       dbQuery.createOne(
           id = UUID.randomUUID().toString(),
           question = question,
           answer = answer
       )
    }

}