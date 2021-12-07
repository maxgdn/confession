package com.confession.app.database

import kotlin.Int
import kotlin.String

public data class HockeyPlayer(
  public val player_number: Int,
  public val full_name: String
) {
  public override fun toString(): String = """
  |HockeyPlayer [
  |  player_number: $player_number
  |  full_name: $full_name
  |]
  """.trimMargin()
}
