package com.saad.imagegallary.retrofit

import com.saad.imagegallary.models.ImageList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface imageService {

    @GET("?key=39752121-35bc4e031b88744eed448385c")
    suspend fun getImages(
        @Query("page") page: Int,
        @Query("per_page") per_page: Int,
    ): Response<ImageList>
}