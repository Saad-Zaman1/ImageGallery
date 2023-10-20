package com.saad.imagegallary.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.saad.imagegallary.models.CustomFavoriteModel
import com.saad.imagegallary.models.Hit
import com.saad.imagegallary.models.ImageList
import com.saad.imagegallary.retrofit.imageService
import com.saad.imagegallary.room.FavoriteDao
import com.saad.imagegallary.room.FavoriteEntity
import com.saad.imagegallary.room.ImagesDao
import com.saad.imagegallary.utils.ApplicationContextProvider
import com.saad.imagegallary.utils.NetworkUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class repository @Inject constructor(
    private val imageService: imageService,
    private val favoriteDao: FavoriteDao,
    private val imagesDao: ImagesDao,
    applicationContextProvider: ApplicationContextProvider
) {
    val imagesLiveData = MutableLiveData<ImageList>()
    val images: LiveData<ImageList>
        get() = imagesLiveData

    val context = applicationContextProvider.getApplicationContext()
    private val favImageLiveData = MutableLiveData<FavoriteEntity>()
    val favImage: LiveData<FavoriteEntity>
        get() = favImageLiveData

    fun getFav(): LiveData<List<CustomFavoriteModel>> {
        return imagesDao.getFav()
    }

    suspend fun getImagesfromDb(): List<Hit> {
        return imagesDao.getImages()
    }

    suspend fun deleteImagesfromDb(images: List<Hit>) {
        return imagesDao.deleteAll(images)
    }

    //    suspend fun getImages(page: Int, per_page: Int) {
//
//        if (NetworkUtils.isInternetAvailable(context)) {
//            val result = imageService.getImages(page, per_page)
//            if (result?.body() != null) {
//                imagesDao.addImages(result.body()!!.hits)
//                imagesLiveData.postValue(result.body())
//            }
//        } else {
//            val images = imagesDao.getImages()
//            val imageList = ImageList(images, 1, 1)
//            imagesLiveData.postValue(imageList)
//        }
//    }
    fun getImages(page: Int, per_page: Int, category: String): Flow<ImageList> = flow {
        if (NetworkUtils.isInternetAvailable(context)) {
            val result = imageService.getImages(page, per_page, category)
            if (result?.body() != null) {
                val imageList = result.body()!!
                imagesDao.addImages(imageList.hits)
                emit(imageList)
            }
        } else {
            val images = imagesDao.getImages()
            val imageList = ImageList(images, 1, 1)
            emit(imageList)
        }
    }.catch {
        Log.e("Error in flow", it.toString())
    }.flowOn(Dispatchers.IO)

    suspend fun checkFavorite(id: Int): FavoriteEntity {
        return favoriteDao.checkFavorite(id)
    }

    suspend fun addFavorite(favImage: FavoriteEntity) {
        return favoriteDao.addFavorite(favImage)
    }

    fun getAllFavorite(): LiveData<List<FavoriteEntity>> {
        return favoriteDao.getFavoriteImages()
    }

    suspend fun deleteFavorite(delFavImg: FavoriteEntity) {
        return favoriteDao.deleteFavorite(delFavImg)
    }
}