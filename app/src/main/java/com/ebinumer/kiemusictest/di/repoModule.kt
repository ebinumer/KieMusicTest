package com.ebinumer.kiemusictest.di

import com.ebinumer.kiemusictest.data.repo.Repository
import org.koin.dsl.module

val repoModule = module {
    factory { Repository(get(),get()) }
}