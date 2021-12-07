package com.confession.app.database

import kotlin.Long
import kotlin.String

public data class Work(
  public val job: Long,
  public val location: String
) {
  public override fun toString(): String = """
  |Work [
  |  job: $job
  |  location: $location
  |]
  """.trimMargin()
}
