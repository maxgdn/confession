package com.confession.app

import com.confession.app.confession.newInstance
import com.confession.app.confession.schema
import com.confession.app.database.PlayerQueries
import com.squareup.sqldelight.Transacter
import com.squareup.sqldelight.db.SqlDriver

public interface Player : Transacter {
  public val playerQueries: PlayerQueries

  public companion object {
    public val Schema: SqlDriver.Schema
      get() = Player::class.schema

    public operator fun invoke(driver: SqlDriver): Player = Player::class.newInstance(driver)
  }
}
