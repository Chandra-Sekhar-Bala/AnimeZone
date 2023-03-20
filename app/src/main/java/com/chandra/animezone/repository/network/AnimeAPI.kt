package com.chandra.animezone.repository.network

import retrofit2.http.GET
import retrofit2.http.Query

interface AnimeAPI {

    @GET("top/anime")
    suspend fun getTopAnime(
        @Query("page") page : Int
    )
}