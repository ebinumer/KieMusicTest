package com.ebinumer.kiemusictest.di

import androidx.room.Room
import com.ebinumer.kiemusictest.data.roomDb.KieDataBase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dbModule = module {
    single {
        Room.databaseBuilder(androidContext(), KieDataBase::class.java, "Kie_data_base")
            .build()
    }
    single { get<KieDataBase>().searchDao }
}
