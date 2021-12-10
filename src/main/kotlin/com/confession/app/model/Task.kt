package com.confession.app.model

import kotlin.time.Duration
import kotlin.time.ExperimentalTime

data class Task @OptIn(ExperimentalTime::class) constructor(
    val name: String,
    val duration: Duration
)
