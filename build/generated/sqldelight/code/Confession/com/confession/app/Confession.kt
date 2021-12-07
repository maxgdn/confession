package com.confession.app

import com.confession.app.confession.main.newInstance
import com.confession.app.confession.main.schema
import com.confession.app.database.ConfessionQueries
import com.confession.app.database.PlayerQueries
import com.confession.app.database.QuestionQueries
import com.confession.app.database.WorkQueries
import com.squareup.sqldelight.Transacter
import com.squareup.sqldelight.db.SqlDriver

public interface Confession : Transacter {
  public val confessionQueries: ConfessionQueries

  public val playerQueries: PlayerQueries

  public val questionQueries: QuestionQueries

  public val workQueries: WorkQueries

  public companion object {
    public val Schema: SqlDriver.Schema
      get() = Confession::class.schema

    public operator fun invoke(driver: SqlDriver): Confession =
        Confession::class.newInstance(driver)
  }
}
