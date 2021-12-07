package com.confession.app.database

import kotlin.String

public data class Confession(
  public val id: String,
  public val mood: String,
  public val remark_id: String,
  public val created_at: String
) {
  public override fun toString(): String = """
  |Confession [
  |  id: $id
  |  mood: $mood
  |  remark_id: $remark_id
  |  created_at: $created_at
  |]
  """.trimMargin()
}
