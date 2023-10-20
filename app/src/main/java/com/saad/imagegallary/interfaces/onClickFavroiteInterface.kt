package com.saad.imagegallary.interfaces

import com.saad.imagegallary.models.Hit
import com.saad.imagegallary.room.FavoriteEntity

interface onClickFavroiteInterface {
    fun onClick(fav: FavoriteEntity)
    fun onClickDetails(image: Hit)
}