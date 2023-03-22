package com.chandra.animezone.models

import com.squareup.moshi.Json

data class Response(
    @Json(name = "mal_id") val id: Int?,
    val airing: Boolean?,
    val duration: String?,
    val episodes: Int?,
    val images: Images?,
    val rank: Int?,
    val rating: String?,
    val score: Double?,
    val synopsis: String?,
    val title: String?,
    val trailer: Trailer?
)