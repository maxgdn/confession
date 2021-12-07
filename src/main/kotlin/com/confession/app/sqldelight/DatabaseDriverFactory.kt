package com.confession.app.sqldelight

import com.confession.app.ConfessionDatabase
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver

class DatabaseDriverFactory {
    fun createDriver(): SqlDriver {
        val driver: SqlDriver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
        ConfessionDatabase.Schema.create(driver)
        return driver
    }
}