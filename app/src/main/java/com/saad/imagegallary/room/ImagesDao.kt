package com.saad.imagegallary.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.saad.imagegallary.models.Hit

@Dao
interface ImagesDao {


    @Insert
    suspend fun addImages(images: List<Hit>)

    @Query("Select * from images")
    suspend fun getImages(): List<Hit>

    @Delete
    suspend fun deleteAll(images: List<Hit>)


    @Query("Select * from images as i join favorite as f on i.largeImageURL = f.largeImageURL")
    fun getFav(): LiveData<List<Hit>>
}