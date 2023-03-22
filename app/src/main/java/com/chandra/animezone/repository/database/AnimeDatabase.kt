package com.chandra.animezone.repository.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.chandra.animezone.models.AnimeDBModel


@Database(entities = [AnimeDBModel::class], version = 1)
abstract class AnimeDatabase : RoomDatabase() {
    abstract fun getDAO(): AnimeDAO
}