package com.confession.app.confession

import com.confession.app.Confession
import com.confession.app.database.PlayerQueries
import com.squareup.sqldelight.TransacterImpl
import com.squareup.sqldelight.db.SqlDriver
import kotlin.Int
import kotlin.Unit
import kotlin.reflect.KClass

internal val KClass<Confession>.schema: SqlDriver.Schema
  get() = ConfessionImpl.Schema

internal fun KClass<Confession>.newInstance(driver: SqlDriver): Confession = ConfessionImpl(driver)

private class ConfessionImpl(
  driver: SqlDriver
) : TransacterImpl(driver), Confession {
  public override val playerQueries: PlayerQueriesImpl = PlayerQueriesImpl(this, driver)

  public object Schema : SqlDriver.Schema {
    public override val version: Int
      get() = 1

    public override fun create(driver: SqlDriver): Unit {
      driver.execute(null, """
          |CREATE TABLE hockeyPlayer (
          |  player_number INTEGER NOT NULL,
          |  full_name TEXT NOT NULL
          |)
          """.trimMargin(), 0)
      driver.execute(null, """
          |INSERT INTO hockeyPlayer (player_number, full_name)
          |VALUES (15, 'Ryan Getzlaf')
          """.trimMargin(), 0)
      driver.execute(null, "CREATE INDEX hockeyPlayer_full_name ON hockeyPlayer(full_name)", 0)
    }

    public override fun migrate(
      driver: SqlDriver,
      oldVersion: Int,
      newVersion: Int
    ): Unit {
    }
  }
}

private class PlayerQueriesImpl(
  private val database: ConfessionImpl,
  private val driver: SqlDriver
) : TransacterImpl(driver), PlayerQueries
