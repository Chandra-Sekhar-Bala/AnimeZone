package com.chandra.animezone.repository.database

import androidx.room.*
import com.chandra.animezone.models.AnimeDBModel

@Dao
interface AnimeDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnime(item : AnimeDBModel)

    @Query("DELETE FROM ANIME WHERE ID =:id")
    suspend fun deleteAnime(id : Int)

    @Query("SELECT * FROM ANIME")
    suspend fun getAllAnime() : List<AnimeDBModel>

}