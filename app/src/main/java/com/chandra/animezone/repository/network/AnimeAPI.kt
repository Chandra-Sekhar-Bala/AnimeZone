package com.chandra.animezone.repository.network

import com.chandra.animezone.models.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import kotlin.reflect.jvm.internal.impl.load.java.ReportLevel

interface AnimeAPI {

    @GET("top/anime")
    suspend fun getTopAnime(
        @Query("page") page : Int,
    ) : AnimeList
    @GET("watch/episodes/popular")
    suspend fun getPopularAnime(
        @Query("limit") limit : Int = 10
    ) : AnimePopularList

    @GET("anime/{anime_id}")
    suspend fun getAnimeDetails(
       @Path("anime_id") id : Int
    ) : AnimeDetails
}