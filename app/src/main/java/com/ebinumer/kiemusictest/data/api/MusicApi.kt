package com.ebinumer.kiemusictest.data.api

import com.ebinumer.kiemusictest.data.model.GenreAllResponse
import com.ebinumer.kiemusictest.data.model.SearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface MusicApi {

    @Headers("Accept: application/json")
    @GET("2/genre/all")
    suspend fun getAllGenre(
        @Query("count")count:Int = 10,
        @Query("offset")page:Int = 0
    ): Response<GenreAllResponse>



    @Headers("Accept: application/json")
    @GET("2/recording")
    suspend fun searchRecordings(
        @Query("query")query:String,
        @Query("offset")count:Int = 0,
        @Query("limit")limit:Int = 10,
    ): Response<SearchResponse>
}