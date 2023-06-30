package com.ebinumer.kiemusictest.data.repo

import android.util.Base64
import com.ebinumer.kiemusictest.data.api.MusicApi
import com.ebinumer.kiemusictest.data.model.AccessTokenResponse
import com.ebinumer.kiemusictest.data.model.UserProfileResponse
import com.ebinumer.kiemusictest.di.baseUrlInterceptor

class AuthRepo(private val mApi: MusicApi) {


    suspend fun getAccessToken(code: String, redirectUri: String): AccessTokenResponse {
        baseUrlInterceptor.setBaseUrl("https://accounts.spotify.com")

        val clientId ="9593e253c55e4afd8129172930e8d73d"
        val clientSecret ="979c5b7ca2c947ce982bd1a23cf0af98"
        val authHeader = "Basic ${Base64.encodeToString("$clientId:$clientSecret".toByteArray(), Base64.NO_WRAP)}"
        return mApi.getAccessToken(
            "client_credentials",
             authHeader)
    }

    suspend fun getUserProfile(token: String): UserProfileResponse {
        return mApi.getUserProfile("Bearer $token")
    }

}