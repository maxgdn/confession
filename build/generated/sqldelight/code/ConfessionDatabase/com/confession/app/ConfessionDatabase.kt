package com.confession.app

import com.confession.app.confession.newInstance
import com.confession.app.confession.schema
import com.confession.app.database.ConfessionQueries
import com.confession.app.database.PlayerQueries
import com.confession.app.database.RemarkQueries
import com.confession.app.database.WorkQueries
import com.squareup.sqldelight.Transacter
import com.squareup.sqldelight.db.SqlDriver

public interface ConfessionDatabase : Transacter {
  public val confessionQueries: ConfessionQueries

  public val playerQueries: PlayerQueries

  public val remarkQueries: RemarkQueries

  public val workQueries: WorkQueries

  public companion object {
    public val Schema: SqlDriver.Schema
      get() = ConfessionDatabase::class.schema

    public operator fun invoke(driver: SqlDriver): ConfessionDatabase =
        ConfessionDatabase::class.newInstance(driver)
  }
}
