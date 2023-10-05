package com.saad.imagegallary.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite")
data class FavoriteEntity(
    @PrimaryKey(autoGenerate = true)
    val imageId: Long,
    val largeImageURL: String,
    val previewURL: String,
    val comments: Int,
    val likes: Int,
    val views: Int

)

