package com.confession.app.di

import com.confession.app.sqldelight.DatabaseDriverFactory
import org.koin.core.module.Module
import org.koin.dsl.module


fun platformModule(): Module = module {
    single { DatabaseDriverFactory() }
}