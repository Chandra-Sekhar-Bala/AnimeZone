package com.chandra.animezone.models

import com.squareup.moshi.Json


data class Entry(
   @Json(name="mal_id") val id: Int?,
    val images: Images?,
    val title: String?
)