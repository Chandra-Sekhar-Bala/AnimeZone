package com.chandra.animezone.models

import com.squareup.moshi.Json

data class Pagination(
    @Json(name="current_page")val currentPage: Int?,
    @Json(name="has_next_page") val hasNextPage: Boolean?,
    @Json(name="last_visible_page")val lastVisiblePage: Int?
)