package com.ebinumer.kiemusictest.di

import com.ebinumer.kiemusictest.utils.SessionManager
import org.koin.dsl.module

val session_module = module {
    single { SessionManager(get()) }
}