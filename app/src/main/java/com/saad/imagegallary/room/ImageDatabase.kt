package com.saad.imagegallary.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.saad.imagegallary.models.Hit

@Database(entities = [Hit::class, FavoriteEntity::class], version = 1, exportSchema = false)
abstract class ImageDatabase : RoomDatabase() {

    abstract fun imageDao(): ImagesDao
    abstract fun favoriteDao(): FavoriteDao

}