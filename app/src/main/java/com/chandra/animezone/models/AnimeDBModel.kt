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
