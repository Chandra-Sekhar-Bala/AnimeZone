package com.chandra.animezone.models

data class AnimePopularList(
    val data: List<PopularResponse>?,
    val pagination: Pagination?
)