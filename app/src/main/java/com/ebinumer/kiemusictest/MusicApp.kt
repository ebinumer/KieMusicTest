package com.ebinumer.kiemusictest

import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.ebinumer.kiemusictest.di.dbModule
import com.ebinumer.kiemusictest.di.mNetworkModule
import com.ebinumer.kiemusictest.di.mViewModel
import com.ebinumer.kiemusictest.di.pagingSource
import com.ebinumer.kiemusictest.di.repoModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MusicApp : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        MultiDex.install(this)

        startKoin {
            printLogger()
            androidContext(this@MusicApp)
            modules(
                listOf(
                    mNetworkModule,
                    repoModule, mViewModel,pagingSource,dbModule
                )
            )
        }
    }

    companion object {
        @JvmStatic
        var instance: MusicApp? = null
            private set

    }
}