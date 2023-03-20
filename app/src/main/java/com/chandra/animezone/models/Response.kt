package com.chandra.animezone.models

import com.squareup.moshi.Json

data class Response(
    @Json(name = "mail_id") val id: Int,
    val airing: Boolean?,
    val approved: Boolean?,
    val background: String?,
    val duration: String?,
    val episodes: Int?,
    val explicitGenres: List<Any>?,
    val genres: List<Genre>?,
    val images: Images?,
    val rank: Int?,
    val rating: String?,
    val score: Double?,
    val scoredBy: Int?,
    val season: String?,
    val source: String?,
    val status: String?,
    val studios: List<Studio>?,
    val synopsis: String?,
    val themes: List<Theme>?,
    val title: String?,
    val titleEnglish: String?,
    val titles: List<Title>?,
    val trailer: Trailer?,
    val type: String?,
    val url: String?,
    val year: Int?
)