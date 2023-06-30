package com.ebinumer.kiemusictest.di

import com.ebinumer.kiemusictest.data.api.MusicApi
import com.ebinumer.kiemusictest.utils.ServiceInterCeptor
import com.google.gson.GsonBuilder
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.CallAdapter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import android.content.Context
import com.ebinumer.kiemusictest.utils.BaseUrlInterceptor


const val BASE_URL = "https://api.spotify.com/"
//const val BASE_URL = "https://api.spotify.com/"
//const val BASE_URL = "https://"
lateinit var cache: Cache
val baseUrlInterceptor = BaseUrlInterceptor("https://api.spotify.com/")



val mNetworkModule = module {
    single {
        val cacheSize = (10 * 1024 * 1024).toLong() // 10 MB cache size
        val cacheDirectory = get<Context>().cacheDir
         cache = Cache(cacheDirectory, cacheSize)

        createWebService<MusicApi>(
            okHttpClient = createHttpClient(),
            baseUrl = BASE_URL,
            factory = RxJava2CallAdapterFactory.create()
        )
    }
}



fun createHttpClient(): OkHttpClient {

    val client = OkHttpClient.Builder().apply {
        readTimeout(8 * 60, TimeUnit.SECONDS)
        cache(cache )
        addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        addInterceptor(baseUrlInterceptor)
        addInterceptor { chain ->
            val originalRequest = chain.request()
            val requestWithUserAgent = originalRequest.newBuilder()
                .build()
            chain.proceed(requestWithUserAgent)

        }
            .build()
    }


    return client.addInterceptor(
//        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)
        ServiceInterCeptor()
    ).build()
}



inline fun <reified T> createWebService(
    okHttpClient: OkHttpClient,
    factory: CallAdapter.Factory,
    baseUrl: String
): T {
    val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
//        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .addCallAdapterFactory(factory)
        .client(okHttpClient)
        .build()
    return retrofit.create(T::class.java)

}