package com.confession.app.database

import kotlin.String

public data class Remark(
  public val id: String,
  public val question: String,
  public val answer: String
) {
  public override fun toString(): String = """
  |Remark [
  |  id: $id
  |  question: $question
  |  answer: $answer
  |]
  """.trimMargin()
}
