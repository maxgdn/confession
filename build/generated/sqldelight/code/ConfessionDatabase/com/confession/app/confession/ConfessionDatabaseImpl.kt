package com.confession.app.confession

import com.confession.app.ConfessionDatabase
import com.confession.app.database.Confession
import com.confession.app.database.ConfessionQueries
import com.confession.app.database.PlayerQueries
import com.confession.app.database.Remark
import com.confession.app.database.RemarkQueries
import com.confession.app.database.WorkQueries
import com.squareup.sqldelight.Query
import com.squareup.sqldelight.TransacterImpl
import com.squareup.sqldelight.`internal`.copyOnWriteList
import com.squareup.sqldelight.db.SqlDriver
import kotlin.Any
import kotlin.Int
import kotlin.String
import kotlin.Unit
import kotlin.collections.MutableList
import kotlin.reflect.KClass

internal val KClass<ConfessionDatabase>.schema: SqlDriver.Schema
  get() = ConfessionDatabaseImpl.Schema

internal fun KClass<ConfessionDatabase>.newInstance(driver: SqlDriver): ConfessionDatabase =
    ConfessionDatabaseImpl(driver)

private class ConfessionDatabaseImpl(
  driver: SqlDriver
) : TransacterImpl(driver), ConfessionDatabase {
  public override val confessionQueries: ConfessionQueriesImpl = ConfessionQueriesImpl(this, driver)

  public override val playerQueries: PlayerQueriesImpl = PlayerQueriesImpl(this, driver)

  public override val remarkQueries: RemarkQueriesImpl = RemarkQueriesImpl(this, driver)

  public override val workQueries: WorkQueriesImpl = WorkQueriesImpl(this, driver)

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
      driver.execute(null, """
          |CREATE TABLE confession (
          |  id TEXT NOT NULL,
          |  mood TEXT NOT NULL,
          |  remark_id TEXT NOT NULL,
          |  created_at TEXT NOT NULL
          |)
          """.trimMargin(), 0)
      driver.execute(null, """
          |CREATE TABLE remark (
          |    id TEXT NOT NULL,
          |    question TEXT NOT NULL,
          |    answer TEXT NOT NULL
          |)
          """.trimMargin(), 0)
      driver.execute(null, """
          |CREATE TABLE Work (
          |    job INTEGER NOT NULL,
          |    location TEXT NOT NULL
          |)
          """.trimMargin(), 0)
      driver.execute(null, "CREATE INDEX hockeyPlayer_full_name ON hockeyPlayer(full_name)", 0)
      driver.execute(null, "CREATE INDEX confession_id ON confession(id)", 0)
      driver.execute(null, "CREATE INDEX remark_id ON confession(id)", 0)
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
  private val database: ConfessionDatabaseImpl,
  private val driver: SqlDriver
) : TransacterImpl(driver), PlayerQueries

private class ConfessionQueriesImpl(
  private val database: ConfessionDatabaseImpl,
  private val driver: SqlDriver
) : TransacterImpl(driver), ConfessionQueries {
  internal val selectAll: MutableList<Query<*>> = copyOnWriteList()

  public override fun <T : Any> selectAll(mapper: (
    id: String,
    mood: String,
    remark_id: String,
    created_at: String
  ) -> T): Query<T> = Query(1412874904, selectAll, driver, "Confession.sq", "selectAll", """
  |SELECT *
  |    FROM confession
  """.trimMargin()) { cursor ->
    mapper(
      cursor.getString(0)!!,
      cursor.getString(1)!!,
      cursor.getString(2)!!,
      cursor.getString(3)!!
    )
  }

  public override fun selectAll(): Query<Confession> = selectAll { id, mood, remark_id,
      created_at ->
    Confession(
      id,
      mood,
      remark_id,
      created_at
    )
  }

  public override fun createOne(
    id: String,
    mood: String,
    remark_id: String,
    created_at: String
  ): Unit {
    driver.execute(-567735459, """
    |INSERT INTO confession (id, mood, remark_id, created_at)
    |    VALUES (?, ?, ?, ?)
    """.trimMargin(), 4) {
      bindString(1, id)
      bindString(2, mood)
      bindString(3, remark_id)
      bindString(4, created_at)
    }
    notifyQueries(-567735459, {database.confessionQueries.selectAll})
  }
}

private class RemarkQueriesImpl(
  private val database: ConfessionDatabaseImpl,
  private val driver: SqlDriver
) : TransacterImpl(driver), RemarkQueries {
  internal val selectAll: MutableList<Query<*>> = copyOnWriteList()

  public override fun <T : Any> selectAll(mapper: (
    id: String,
    question: String,
    answer: String
  ) -> T): Query<T> = Query(9771071, selectAll, driver, "Remark.sq", "selectAll", """
  |SELECT *
  |    FROM remark
  """.trimMargin()) { cursor ->
    mapper(
      cursor.getString(0)!!,
      cursor.getString(1)!!,
      cursor.getString(2)!!
    )
  }

  public override fun selectAll(): Query<Remark> = selectAll { id, question, answer ->
    Remark(
      id,
      question,
      answer
    )
  }

  public override fun createOne(
    id: String,
    question: String,
    answer: String
  ): Unit {
    driver.execute(-1970839292, """
    |INSERT INTO remark (id, question, answer)
    |        VALUES (?, ?, ?)
    """.trimMargin(), 3) {
      bindString(1, id)
      bindString(2, question)
      bindString(3, answer)
    }
    notifyQueries(-1970839292, {database.remarkQueries.selectAll})
  }
}

private class WorkQueriesImpl(
  private val database: ConfessionDatabaseImpl,
  private val driver: SqlDriver
) : TransacterImpl(driver), WorkQueries
