package com.saad.imagegallary.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavoriteDao {
    @Insert
    suspend fun addFavorite(favImage: FavoriteEntity)

    @Query("Select * from favorite")
    fun getFavoriteImages(): LiveData<List<FavoriteEntity>>

    @Delete
    suspend fun deleteFavorite(fav: FavoriteEntity)
}