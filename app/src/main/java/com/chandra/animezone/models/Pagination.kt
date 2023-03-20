package com.chandra.animezone.models

data class Pagination(
    val currentPage: Int?,
    val hasNextPage: Boolean?,
    val items: Items?,
    val lastVisiblePage: Int?
)