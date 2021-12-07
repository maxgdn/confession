package com.confession.app.database

import com.squareup.sqldelight.Query
import com.squareup.sqldelight.Transacter
import kotlin.Any
import kotlin.String
import kotlin.Unit

public interface ConfessionQueries : Transacter {
  public fun <T : Any> selectAll(mapper: (
    id: String,
    mood: String,
    remark_id: String,
    created_at: String
  ) -> T): Query<T>

  public fun selectAll(): Query<Confession>

  public fun createOne(
    id: String,
    mood: String,
    remark_id: String,
    created_at: String
  ): Unit
}
