package com.chandra.animezone.repository.network

import com.chandra.animezone.models.AnimeList
import retrofit2.http.GET
import retrofit2.http.Query

interface AnimeAPI {

    @GET("top/anime")
    suspend fun getTopAnime(
        @Query("page") page : Int
    ) : AnimeList
}