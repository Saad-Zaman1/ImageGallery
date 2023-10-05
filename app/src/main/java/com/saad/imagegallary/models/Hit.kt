package com.saad.imagegallary.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "images")
data class Hit(
    @PrimaryKey(autoGenerate = true)
    val imageId: Long,
    val largeImageURL: String,
    val previewURL: String,
    val comments: Int,
    val likes: Int,
    val views: Int
)