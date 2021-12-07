package com.confession.app.database

import com.squareup.sqldelight.Query
import com.squareup.sqldelight.Transacter
import kotlin.Any
import kotlin.String
import kotlin.Unit

public interface RemarkQueries : Transacter {
  public fun <T : Any> selectAll(mapper: (
    id: String,
    question: String,
    answer: String
  ) -> T): Query<T>

  public fun selectAll(): Query<Remark>

  public fun createOne(
    id: String,
    question: String,
    answer: String
  ): Unit
}
