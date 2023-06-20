package com.ebinumer.kiemusictest.utils

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import okio.Buffer
import org.koin.core.component.KoinComponent

class ServiceInterCeptor: Interceptor, KoinComponent {

//    var token: String = ""
//    val mSessionManager by inject<SessionManager>()


//    fun Token(token: String) {
//        this.token = token
//    }

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        if( request.body!= null) {
            val requestBuffer = Buffer()
            request.body!!.writeTo(requestBuffer)
            Log.d("OkHttp", requestBuffer.readUtf8())
        }
        if (request.header("AuthNotNeed") == null) {
//            Timber.e("Token=${mSessionManager.token}")
//            val token =mSessionManager.token
//            token?.let {
//                request = request.newBuilder()
//                    .addHeader("Authorization", "Bearer $token")
//                    .build()
//            }
        }
        return chain.proceed(request)
    }

}