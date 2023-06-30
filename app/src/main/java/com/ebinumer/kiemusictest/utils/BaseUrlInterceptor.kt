package com.ebinumer.kiemusictest.utils

import okhttp3.Interceptor
import okhttp3.Response

class BaseUrlInterceptor (private var baseUrl: String) : Interceptor {
    fun setBaseUrl(baseUrl: String) {
        this.baseUrl = baseUrl
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val modifiedRequest = originalRequest.newBuilder()
            .url(baseUrl + originalRequest.url.encodedPath)
            .build()
        return chain.proceed(modifiedRequest)
    }
}