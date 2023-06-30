package com.ebinumer.kiemusictest.data.api

import com.ebinumer.kiemusictest.data.model.AccessTokenResponse
import com.ebinumer.kiemusictest.data.model.GenreAllResponse
import com.ebinumer.kiemusictest.data.model.RecordingDetails
import com.ebinumer.kiemusictest.data.model.SearchResponse
import com.ebinumer.kiemusictest.data.model.TracksResponse
import com.ebinumer.kiemusictest.data.model.UserProfileResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface MusicApi {

    @FormUrlEncoded
    @POST("api/token")
    suspend fun getAccessToken(
        @Field("grant_type") grantType: String,
        @Header("Authorization") authHeader: String,
        @Header("AuthNotNeed") authNotNeed: Boolean = true
    ): AccessTokenResponse

    @GET("v1/me")
    suspend fun getUserProfile(@Header("Authorization") token: String): UserProfileResponse


    @GET("v1/browse/popular-tracks")
    suspend fun getPopularSongs(
        @Query("country") country: String,
        @Query("limit") limit: Int
    ): TracksResponse

    @Headers("Accept: application/json")
    @GET("2/genre/all")
    suspend fun getAllGenre(
        @Query("count")count:Int = 10,
        @Query("offset")page:Int = 0
    ): Response<GenreAllResponse>


//    @GET("v1/search")
//    suspend fun searchRecordings(
//        @Query("q") query: String,
//        @Query("type") type: String,
//        @Query("limit") limit: Int
//    ): SearchResponse


    @Headers("Accept: application/json")
    @GET("2/recording")
    suspend fun searchRecordings(
        @Query("query")query:String,
        @Query("offset")count:Int = 0,
        @Query("limit")limit:Int = 10,
    ): Response<SearchResponse>



    @Headers("Accept: application/json")
    @GET("2/recording/{recoding_id}")
    suspend fun getSong(
        @Path("recoding_id") recordingId:String
    ): Response<RecordingDetails>


}

