package com.chandra.animezone.models

import com.squareup.moshi.Json

data class Jpg(
    @Json(name = "large_image_url") val largeImageUrl: String?,
)