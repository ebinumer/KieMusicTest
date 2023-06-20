package com.ebinumer.kiemusictest.di

import com.ebinumer.kiemusictest.viewModel.HomeViewModel
import com.ebinumer.kiemusictest.viewModel.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mViewModel = module {
    viewModel { HomeViewModel(get()) }
    viewModel { SearchViewModel(get()) }
}