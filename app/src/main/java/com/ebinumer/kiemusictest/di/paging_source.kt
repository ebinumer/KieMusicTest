package com.ebinumer.kiemusictest.di

import com.ebinumer.kiemusictest.data.pagingSource.SearchPagingSource
import org.koin.dsl.module

val pagingSource = module {
    single { SearchPagingSource(get(),get()) }
}
