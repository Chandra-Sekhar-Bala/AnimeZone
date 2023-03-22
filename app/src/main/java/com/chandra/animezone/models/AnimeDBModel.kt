package com.chandra.animezone.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("anime")
data class AnimeDBModel(
   @PrimaryKey val id : Int,
    val imageUrl : String,
    val title : String,
    val rating : Double
)

fun List<AnimeDBModel>.toResponse(): List<Response> {
    val list = ArrayList<Response>()
    for (item in this) {
        list.add(
            Response(
                item.id,
                null,
                null,
                null,
                Images(Jpg(item.imageUrl)),
                null,
                null,
                item.rating,
                null,
                item.title,
                null
            )
        )
    }

    return list
}