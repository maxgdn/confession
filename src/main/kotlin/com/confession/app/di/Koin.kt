package com.confession.app.di

import com.confession.app.repository.RemarkRepository
import com.confession.app.repository.RemarkRepositoryImpl
import com.confession.app.service.*
import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoin(appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(
            repositoryModule,
            viewModelModule,
            platformModule()
        )
    }

val repositoryModule = module {
    single<RemarkRepository> { RemarkRepositoryImpl(get()) }
}

val viewModelModule = module {
    single { RemarkViewModel(get()) }
    single { AccomplishViewModel() }
    single { MoodViewModel() }
    single { ReflectViewModel() }
    single { ConfessionViewModel(get(),get(),get(),get()) }
}